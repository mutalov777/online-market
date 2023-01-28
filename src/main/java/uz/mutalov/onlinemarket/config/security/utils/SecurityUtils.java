package uz.mutalov.onlinemarket.config.security.utils;

public class SecurityUtils {

    public final static String[] WHITE_LIST = {
            "/error",
            "/api/login",
            "/auth/access-token",
            "/auth/create",
            "/auth/refresh-token",
            "/student/create",
            "/otp/**",
            "/swagger-ui/**",
            "/api-docs/**",
            "/category/get/**",
            "/category/get-list",
            "/product/get/**",
            "/product/get-list"
    };

}
