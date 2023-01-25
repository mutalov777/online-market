package uz.mutalov.onlinemarket.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataDTO<T> implements Serializable {

    private T data;
    private AppErrorDTO error;
    private boolean success;
    private Integer totalCount;

    public DataDTO(T data) {
        this.data = data;
        this.success = true;
        this.totalCount = 1;
    }

    public DataDTO(AppErrorDTO error) {
        this.error = error;
        this.success = false;
    }

    public DataDTO(T data, Integer totalCount) {
        this.data = data;
        this.totalCount = totalCount;
        this.success = true;
    }

}