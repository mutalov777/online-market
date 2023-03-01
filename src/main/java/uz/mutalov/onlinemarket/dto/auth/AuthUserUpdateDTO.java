package uz.mutalov.onlinemarket.dto.auth;

import uz.mutalov.onlinemarket.dto.base.GenericDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserUpdateDTO extends GenericDTO {

    private String fullName;

    @Email
    private String email;

    private String oldPassword;
    private String newPassword;

    private String phone;
}
