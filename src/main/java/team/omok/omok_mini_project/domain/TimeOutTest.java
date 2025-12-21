package team.omok.omok_mini_project.domain;

public class TimeOutTest {

	public static void main(String[] args) throws Exception {
        System.out.println("=== [TEST 1] 시작 후 아무도 안 두고 31초 기다리면 TIMEOUT 처리 ===");

        Game game = new Game(1, 2);
        game.startGame();

        System.out.println("start status=" + game.state.getStatus());
        System.out.println("start turn=" + game.state.getTurn());
        System.out.println("deadline(ms)=" + game.state.getTurnDeadlineMs());

        Thread.sleep(5_000);

        // Room이 하는 방식 그대로: rule.placeStone(state, x, y)
        MoveResult r1 = game.rule.placeStone(game.state, 7, 7);

        System.out.println("place after 31s => type=" + r1.getType() + ", reason=" + r1.getReason());
        System.out.println("after status=" + game.state.getStatus());
        System.out.println("endReason=" + game.state.getEndReason());
        System.out.println("winnerId=" + game.state.getWinnerId());
        System.out.println(r1);

//        System.out.println("=== [TEST 2] 10초 내에 흑이 두면 턴이 넘어가고, 다시 30초가 시작됨 ===");
//
//        game = new Game(1, 2);
//        game.startGame();
//
//        Thread.sleep(10_000);
//
//        MoveResult r2 = game.rule.placeStone(game.state, 7, 7); // 흑 수
//        System.out.println("black place @10s => type=" + r2.getType() + ", reason=" + r2.getReason());
//        System.out.println("turn should be WHITE => " + game.state.getTurn());
//        System.out.println("deadline(ms)=" + game.state.getTurnDeadlineMs());
//
//        System.out.println("이제 백 턴에서 31초 대기 후 착수 시도 (TIMEOUT 기대)");
//        Thread.sleep(31_000);
//
//        MoveResult r3 = game.rule.placeStone(game.state, 7, 8); // 백 수 시도
//        System.out.println("white place after 31s => type=" + r3.getType() + ", reason=" + r3.getReason());
//        System.out.println("after status=" + game.state.getStatus());
//        System.out.println("endReason=" + game.state.getEndReason());
//        System.out.println("winnerId=" + game.state.getWinnerId());
    }
}