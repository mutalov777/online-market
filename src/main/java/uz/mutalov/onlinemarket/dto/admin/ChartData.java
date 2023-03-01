package uz.mutalov.onlinemarket.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ChartData {
    private LocalDateTime date;
    private Long orderCount;

    public ChartData(LocalDateTime date, Long orderCount) {
        this.date = date;
        this.orderCount = orderCount;
    }
}
