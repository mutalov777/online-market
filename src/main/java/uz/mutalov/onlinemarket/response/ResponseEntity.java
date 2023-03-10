package uz.mutalov.onlinemarket.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ResponseEntity<T> implements BaseResponse {

    private T data;
    private Integer status;

    public ResponseEntity(T data) {
        this(data, HttpStatus.OK);
    }

    public ResponseEntity(T data, HttpStatus status) {
        this.data = data;
        this.status = status.value();
    }

}
