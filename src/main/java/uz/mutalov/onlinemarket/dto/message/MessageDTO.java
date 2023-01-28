package uz.mutalov.onlinemarket.dto.message;

import uz.mutalov.onlinemarket.dto.auth.AuthUserDTO;
import uz.mutalov.onlinemarket.dto.base.GenericDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO extends GenericDTO {

    private AuthUserDTO to;

    private AuthUserDTO from;

    private String text;

    private LocalDateTime createdAt;
}
