package team.omok.omok_mini_project.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.omok.omok_mini_project.enums.MessageType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WsMessage<T> {
    private MessageType type;
    private T payload;
}
