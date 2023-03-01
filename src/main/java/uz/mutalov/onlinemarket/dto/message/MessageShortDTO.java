package uz.mutalov.onlinemarket.dto.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class MessageShortDTO {
    private Integer messageCount;
    private Long userId;
    private String fullName;

    public MessageShortDTO(Integer messageCount, Long userId, String fullName) {
        this.messageCount = messageCount;
        this.userId = userId;
        this.fullName = fullName;
    }
}
