package team.omok.omok_mini_project.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameStartPayload {
    private int firstTurnUserId;
}
