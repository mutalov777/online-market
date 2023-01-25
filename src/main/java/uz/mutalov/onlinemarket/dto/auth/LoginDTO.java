package uz.mutalov.onlinemarket.dto.auth;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.mutalov.onlinemarket.dto.base.BaseDTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO implements BaseDTO {

    @NotBlank(message = "Phone number cannot be blank")
    @Email
    private String email;

    @NotBlank(message = "Code cannot be blank")
    private String password;


}
