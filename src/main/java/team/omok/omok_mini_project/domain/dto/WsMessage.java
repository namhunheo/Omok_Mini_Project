package team.omok.omok_mini_project.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.omok.omok_mini_project.enums.MessageType;

@Data
@AllArgsConstructor
@NoArgsConstructor
// 서버 ↔ 클라이언트 간 통신 규약
public class WsMessage<T> {
    private MessageType type;
    private T payload;
}
