package uz.mutalov.onlinemarket.dto.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.mutalov.onlinemarket.dto.auth.UserDTO;
import uz.mutalov.onlinemarket.dto.base.GenericDTO;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO extends GenericDTO {
    private UserDTO to;
    private UserDTO from;
    private String text;
    private LocalDateTime updatedAt;
    private boolean view;
}
