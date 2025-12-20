package team.omok.omok_mini_project.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String URL =
//            "jdbc:postgresql://192.168.0.139:5432/omok";
                // 집에서 진행하는 local
                "jdbc:postgresql://localhost:5432/omok";
    //private static final String USER = "omokuser";
    private static final String USER = "kimjaemin"; // 각자 본인 db 연결시 필요한 id
    //private static final String PASSWORD = "omokpass";
    private static final String PASSWORD = "postgres";

    public static Connection getConnection() throws Exception {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
