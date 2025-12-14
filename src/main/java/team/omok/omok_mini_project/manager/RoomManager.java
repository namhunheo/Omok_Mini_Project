package team.omok.omok_mini_project.manager;

import team.omok.omok_mini_project.domain.Room;
import team.omok.omok_mini_project.domain.UserVO;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class RoomManager {
    private static final RoomManager instance = new RoomManager();
    private final Map<String, Room> rooms = new ConcurrentHashMap<>();


    public static RoomManager getInstance() {
        return instance;
    }

    public Room getRoom(String roomId) {
        return rooms.get(roomId);
    }

    public Room createRoom(String userId) {
        String roomId = UUID.randomUUID().toString();
        Room room = new Room(roomId, userId);
        rooms.put(roomId, room);

        return room;
    }

    public List<Room> getWaitingRooms() {
        return rooms.values().stream()
                .filter(room -> !room.isFull())
                .toList();
    }

    public void enterRoom(String roomId, UserVO user) {
        Room room = rooms.get(roomId);
        if (room == null) {
            throw new IllegalArgumentException("방 없음");
        }
        room.addPlayer(user.getId());
    }
}
