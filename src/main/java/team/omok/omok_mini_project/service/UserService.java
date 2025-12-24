package team.omok.omok_mini_project.service;

import team.omok.omok_mini_project.domain.vo.UserVO;
import team.omok.omok_mini_project.repository.RecordDAO;
import team.omok.omok_mini_project.repository.UserDAO;
import team.omok.omok_mini_project.util.DBConnection;

import java.sql.Connection;

public class UserService {

    private final UserDAO userDAO = new UserDAO();
    private final RecordDAO recordDAO = new RecordDAO();

    public UserVO getUserById(int userId) throws Exception {
        return userDAO.findByUserId(userId);
    }

    public void register(UserVO user) throws Exception {

        // 검증
        if (user.getLoginId() == null || user.getLoginId().isBlank())
            throw new IllegalArgumentException("아이디 입력하세요");

        if (user.getUserPwd() == null || user.getUserPwd().isBlank())
            throw new IllegalArgumentException("비밀번호 입력하세요");

        if (user.getNickname() == null || user.getNickname().isBlank())
            throw new IllegalArgumentException("닉네임 입력하세요");

        // 2) 트랜잭션 시작
        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try {
                //  중복 검사는 트랜잭션 안에서 (원자성 조금 더 좋아짐)
                if (userDAO.existsByLoginId(user.getLoginId()))
                    throw new IllegalArgumentException("이미 존재하는 아이디");

                if (userDAO.existsByNickname(user.getNickname()))
                    throw new IllegalArgumentException("이미 존재하는 닉네임");

                //  users insert -> 생성된 user_id 받기
                int userId = userDAO.insertUser(con, user);

                // record 기본 row 생성 (user_id만 넣고 DEFAULT 사용)
                recordDAO.insertRecordDefault(con, userId);

                //  커밋
                con.commit();
            } catch (Exception e) {
                con.rollback();  //실패하면 롤백으로 !
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }

    public UserVO login(String loginId, String password) throws Exception {
        if (loginId == null || loginId.isBlank())
            throw new IllegalArgumentException("아이디 입력하세요");
        if (password == null || password.isBlank())
            throw new IllegalArgumentException("비밀번호 입력하세요");

        UserVO user = userDAO.findByLoginId(loginId);
        if (user == null)
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");

        if (!user.getUserPwd().equals(password))
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");

        return user;
    }
}
