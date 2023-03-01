package uz.mutalov.onlinemarket.config.security.filter;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.mutalov.onlinemarket.config.security.utils.JWTUtils;
import uz.mutalov.onlinemarket.dto.auth.LoginDTO;
import uz.mutalov.onlinemarket.dto.auth.SessionDTO;
import uz.mutalov.onlinemarket.response.AppErrorDTO;
import uz.mutalov.onlinemarket.response.DataDTO;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper mapper;

    public AuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper mapper) {
        super.setFilterProcessesUrl("/api/login");
        this.authenticationManager = authenticationManager;
        this.mapper = mapper;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginDTO loginDto = mapper.readValue(request.getReader(), LoginDTO.class);
            log.info("Email is: {}", loginDto.getEmail());
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException | RuntimeException e) {
            log.error(e.getMessage(), e);
            throw new BadCredentialsException(e.getMessage(), e.getCause());
        }
    }

    @Override
        protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();
        Date expiryForAccessToken = JWTUtils.getExpiry();
        Date expiryForRefreshToken = JWTUtils.getExpiryForRefreshToken();
        List<String> authorities = user
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(expiryForAccessToken)
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", authorities)
                .sign(JWTUtils.getAlgorithm());
        String refreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(expiryForRefreshToken)
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", authorities)
                .sign(JWTUtils.getAlgorithm());
        SessionDTO sessionDto = SessionDTO.builder()
                .accessToken(accessToken)
                .accessTokenExpiry(expiryForAccessToken.getTime())
                .refreshToken(refreshToken)
                .refreshTokenExpiry(expiryForRefreshToken.getTime())
                .issuedAt(System.currentTimeMillis())
                .build();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getOutputStream(), new DataDTO<>(sessionDto));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        DataDTO<AppErrorDTO> resp = new DataDTO<>(
                AppErrorDTO.builder()
                        .message(failed.getMessage())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .build()
        );
        mapper.writeValue(response.getOutputStream(), resp);
    }

}
