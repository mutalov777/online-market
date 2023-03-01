package uz.mutalov.onlinemarket.dto.auth;

import lombok.Getter;
import lombok.Setter;
import uz.mutalov.onlinemarket.dto.base.GenericDTO;

@Getter
@Setter
public class UserDTO extends GenericDTO {
    private String fullName;
    private String phone;

    public UserDTO(Long id, String fullName, String phone) {
        super(id);
        this.fullName = fullName;
        this.phone = phone;
    }
}
