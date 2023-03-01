package uz.mutalov.onlinemarket.config.security.utils;

public class SecurityUtils {

    public final static String[] WHITE_LIST = {
            "/error",
            "/api/login",
            "/api/auth/access-token",
            "/api/auth/create",
            "/api/auth/refresh-token",
            "/swagger-ui/**",
            "/api-docs/**",
            "/api/category/get/**",
            "/api/category/get-list",
            "/api/product/get/**",
            "/api/product/get-list",
            "/",
            "/static/**",
            "/locales/**",
            "/favicon.ico",
            "/manifest.json"
    };
}
