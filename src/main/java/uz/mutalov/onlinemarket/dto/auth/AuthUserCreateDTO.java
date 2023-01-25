package uz.mutalov.onlinemarket.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.mutalov.onlinemarket.dto.base.BaseDTO;

import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserCreateDTO implements BaseDTO {

    private String fullName;

    @Email
    private String email;

    private String password;
}
