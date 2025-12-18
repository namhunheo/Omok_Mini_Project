package team.omok.omok_mini_project.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MoveResultPayload {
    private int x;
    private int y;
    private String color; // BLACK / WHITE
}
