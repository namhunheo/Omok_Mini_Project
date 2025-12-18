package team.omok.omok_mini_project.service;

import team.omok.omok_mini_project.domain.Room;
import team.omok.omok_mini_project.domain.UserVO;
import team.omok.omok_mini_project.manager.RoomManager;

import java.util.List;

public class RoomService {

    private final RoomManager roomManager = RoomManager.getInstance();

    // 방 생성
    public Room createRoom(int userId) {
        return roomManager.createRoom(userId);
    }

    // 방 입장
    public void enterRoom(String roomId, UserVO user) {
        roomManager.enterRoom(roomId, user);
    }

    // 대기 중인 방 목록 가져오기
    public List<Room> getWaitingRooms() {
        return roomManager.getWaitingRooms();
    }

    // 빠른 입장: 가장 먼저 생성된 대기 방 반환
    public Room getFirstWaitingRoom() {
        return roomManager.getFirstWaitingRoom();
    }
}
