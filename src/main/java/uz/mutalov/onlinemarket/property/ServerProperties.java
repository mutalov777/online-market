package uz.mutalov.onlinemarket.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "service.prod")
public class ServerProperties {

    private String port;
    private String ip;
    private String url;
    private String protocol;

    public String getServerUrl() {
        return "http://localhost:8080";
//        return this.protocol + "://" + this.ip + ":" + this.port;
    }

}
