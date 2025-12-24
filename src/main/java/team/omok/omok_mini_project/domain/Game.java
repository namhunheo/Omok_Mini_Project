package team.omok.omok_mini_project.domain;

import lombok.Data;

@Data
public class Game {
    GameState state = new GameState();
    OmokRule rule = new OmokRule();

    public Game(int blackUserId, int whiteUserId){
        state.setBlackUserId(blackUserId);
        state.setWhiteUserId(whiteUserId);
    }

    public void startGame(){
        state.startGame();
    }
}
