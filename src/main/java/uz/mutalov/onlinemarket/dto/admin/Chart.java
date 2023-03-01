package uz.mutalov.onlinemarket.dto.admin;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chart {
    private LocalDateTime date;
    private Integer orderCount=0;
    private Integer userCount=0;
}
