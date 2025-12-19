package team.omok.omok_mini_project.service;

import team.omok.omok_mini_project.domain.Room;
import team.omok.omok_mini_project.domain.UserVO;
import team.omok.omok_mini_project.manager.RoomManager;

import java.util.List;

//public class RoomService {
//
//    private final RoomManager roomManager = RoomManager.getInstance();
//
//    public Room createRoom(String userId) {
//        if (userId == null) {
//            throw new IllegalStateException("로그인 필요");
//        }
//        return roomManager.createRoom(userId);
//    }
//
//    public void enterRoom(String roomId, UserVO user) {
//        roomManager.enterRoom(roomId, user);
//    }
//
//    public List<Room> getWaitingRooms() {
//        return roomManager.getWaitingRooms();
//    }
//}
