package uz.mutalov.onlinemarket.dto.admin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserData {
    private LocalDateTime date;
    private Long userCount;

    public UserData(LocalDateTime date, Long userCount) {
        this.date = date;
        this.userCount = userCount;
    }
}
