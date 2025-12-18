package team.omok.omok_mini_project.domain;

import lombok.Data;
import team.omok.omok_mini_project.domain.dto.WsMessage;
import team.omok.omok_mini_project.enums.MessageType;
import team.omok.omok_mini_project.enums.RoomStatus;
import team.omok.omok_mini_project.manager.RoomManager;
import team.omok.omok_mini_project.util.JsonUtil;

import javax.websocket.Session;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 하나의 게임 방에 대해 모든 상태 저장 + 유일하게 상태 변경 할 수 있는 객체
 *
 * 방 ID
 * 참가자 목록
 * 관전자 목록
 * WebSocket 세션들
 * 현재 게임(Game) 참조
 *
 * (참고) 스레드 세이프해야하는 부분
 * * 게임 시작 조건 판단
 * * 플레이어 추가
 * * 게임 상태 변경
 * @see RoomManager
 */
@Data
public class Room {
    private static final int MAX_PLAYER = 2;

    private final String roomId;
    private final int ownerId;
    private final long createdAt;           // 방 생성 시간

    private final List<Integer> players = new ArrayList<>(MAX_PLAYER);                // 플레이어(user_id 저장)
    private final Set<Session> playerSessions = ConcurrentHashMap.newKeySet();        // 플레이어 세션
    private final Set<Session> spectators = ConcurrentHashMap.newKeySet();            // 관전자 세션

    private RoomStatus status = RoomStatus.WAIT;                     // 방 상태: WAITING, READY, COUNTDOWN, PLAYING, END

    // 게임
    GameState gameState = new GameState();
    OmokRule omokRule = new OmokRule();

    public Room(String roomId, int ownerId) {
        this.roomId = roomId;
        this.ownerId = ownerId;
        this.players.add(ownerId);              // 방장은 자동 입장
        this.createdAt = System.currentTimeMillis();
    }

    ////////////// 상태 관리 ///////////////
    private synchronized void updateStatus(RoomStatus nextStatus){
        if(this.status == nextStatus) return;
        this.status = nextStatus;

        switch (nextStatus){
            case WAIT ->{
                broadcast(new WsMessage<>(
                        MessageType.ROOM_WAIT,
                        Map.of("reason", "PLAYER_LEFT"))
                );
            }

            case READY->{
            }

            case COUNTDOWN ->{
                startCountdown();
            }
            case PLAYING->{

            }
            case END->{
                broadcast(new WsMessage<>(
                        MessageType.ROOM_END,
                        Map.of("reason", "EMPTY")
                ));
                RoomManager.getInstance().removeRoom(roomId);
            }
            default->{

            }

        }
    }

    ////////////// 세션 관리 ///////////////

    public synchronized void addSession(int userId, Session session) {
        System.out.println("[INFO]Room-addSession: " + session);

        if(players.contains(userId)){
            playerSessions.add(session);
            broadcast(new WsMessage<>(MessageType.JOIN, userId));
        }else{
            spectators.add(session);
        }

        if(isReady() && status == RoomStatus.WAIT){
            System.out.println("플레이어 세션: " + session.getId());
            updateStatus(RoomStatus.READY);
            tryStartGame();
        }
    }

    public synchronized void removeSession(int userId, Session session) {
        players.remove(Integer.valueOf(userId));
        playerSessions.remove(session);
        spectators.remove(session);

        // 게임 도중 방 나간 경우
        if(!isReady() && status == RoomStatus.PLAYING){
            updateStatus(RoomStatus.END);
            endGame("PLAYER_DISCONNECTED");
            return;
        }

        // 게임 시작 전에 방 나간 경우
        if(!isReady() && (status == RoomStatus.READY || status == RoomStatus.COUNTDOWN)){
            updateStatus(RoomStatus.WAIT);
        }

        // 아예 방이 비어버린 경우
        if(playerSessions.isEmpty() && players.isEmpty()){
            updateStatus(RoomStatus.END);
        }
    }

    public synchronized void tryAddPlayer(int userId) {
        if (isFull()) {
            throw new IllegalStateException("방이 가득 찼습니다");
        }
        players.add(userId);
    }


    ////////////// 게임 흐름 제어 ///////////////

    public synchronized void tryStartGame() {
        if(status != RoomStatus.READY) {
            return;
        }
        updateStatus(RoomStatus.COUNTDOWN);
    }

    public synchronized void handleMove(int userId, int x, int y){
        if(status != RoomStatus.PLAYING){
            return;
        }
        broadcast(new WsMessage<>(
                MessageType.MOVE,
                Map.of(
                        "x", x,
                        "y", y,
                        "color", "BLACK"
                )
        ));

        // 게임에 데이터 전달 및 결과 반환 로직
//        MoveResult result = omokRule.placeStone(state, x, y);
//        handleMoveResult(result);
    }

    private void handleMoveResult(MoveResult result){
        /*
        switch (result.getType()) {

            case MOVE_OK -> {
                broadcast(new WsMessage<>(
                        MessageType.MOVE,
                        Map.of(
                                "x", result.getX(),
                                "y", result.getY(),
                                "color", state.turn.toString()
                        )
                ));
            }

            case INVALID_TURN -> {
                sendErrorToUser(userId, "NOT_YOUR_TURN");
            }

            case INVALID_POSITION -> {
                sendErrorToUser(userId, "INVALID_POSITION");
            }

            case WIN -> {
                broadcast(new WsMessage<>(
                        MessageType.MOVE,
                        Map.of(
                                "x", result.getX(),
                                "y", result.getY(),
                                "color", state.turn.toString()
                        )
                ));

                broadcast(new WsMessage<>(
                        MessageType.GAME_END,
                        Map.of("winner", result.getWinnerId())
                ));

                status = RoomStatus.END;
            }
        }
        */
    }

    private void startCountdown() {
        System.out.println("[INFO]Room-startCountdown");

        new Thread(() -> {
            try {
                for (int i = 5; i >= 1; i--) {
                    if(status != RoomStatus.COUNTDOWN){
                        return;
                    }

                    Thread.sleep(1000);

//                    broadcast("{\"type\":\"COUNTDOWN\",\"sec\":" + i + "}");
                    broadcast(new WsMessage<>(
                            MessageType.COUNTDOWN,
                            Map.of("sec", i)
                    ));
                }

                // 게임 시작
                if(isReady()){
                    startGame();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private synchronized void startGame(){
        System.out.println("플레이어: " + players + " / 플레이어 세션: " + playerSessions);
        if(status != RoomStatus.COUNTDOWN) return;
        if(!isReady()) return;

//        broadcast("{\"type\":\"GAME_START\"}");
        broadcast(new WsMessage<>(
                MessageType.GAME_START,
                null
        ));
        updateStatus(RoomStatus.PLAYING);
    }

    private synchronized void endGame(String reason) {
        status = RoomStatus.END;

        broadcast(new WsMessage<>(
                MessageType.GAME_END,
                Map.of("reason", reason)
        ));

        RoomManager.getInstance().removeRoom(roomId);
    }

    ////////////// 유틸 ///////////////

    public synchronized boolean isFull() {
        return players.size() >= MAX_PLAYER;
    }

    // 게임 시작 가능 조건
    private boolean isReady() {
        return players.size() == MAX_PLAYER && playerSessions.size() == MAX_PLAYER;
    }

    public void broadcast(Object message) {
        try {
            String json = JsonUtil.MAPPER.writeValueAsString(message);
            for (Session s : playerSessions) {
                s.getBasicRemote().sendText(json);
                System.out.println(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendErrorToUser(int userId, String msg) {
        for (Session s : playerSessions) {
            Object sid = s.getUserProperties().get("user_id");
            if (sid != null && Integer.parseInt(sid.toString()) == userId) {
                try {
                    s.getBasicRemote().sendText(
                            JsonUtil.MAPPER.writeValueAsString(
                                    new WsMessage<>(MessageType.ERROR, msg)
                            )
                    );
                } catch (Exception ignored) {}
            }
        }
    }


}
