package uz.mutalov.onlinemarket.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.mutalov.onlinemarket.config.security.utils.JWTUtils;
import uz.mutalov.onlinemarket.dto.auth.AuthUserDTO;
import uz.mutalov.onlinemarket.dto.auth.LoginDTO;
import uz.mutalov.onlinemarket.dto.auth.SessionDTO;
import uz.mutalov.onlinemarket.dto.message.MessageShortDTO;
import uz.mutalov.onlinemarket.entity.AuthUser;
import uz.mutalov.onlinemarket.exceptions.NotFoundException;
import uz.mutalov.onlinemarket.mappers.AuthUserMapper;
import uz.mutalov.onlinemarket.property.ServerProperties;
import uz.mutalov.onlinemarket.repository.AuthUserRepository;
import uz.mutalov.onlinemarket.response.AppErrorDTO;
import uz.mutalov.onlinemarket.response.DataDTO;
import uz.mutalov.onlinemarket.response.ResponseEntity;
import uz.mutalov.onlinemarket.service.base.BaseService;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Service
public class AuthService implements UserDetailsService, BaseService {
    @Qualifier("sessionRegistry")
    private final SessionRegistry sessionRegistry;
    private final AuthUserRepository repository;
    private final ServerProperties serverProperties;
    private final ObjectMapper objectMapper;

    private final AuthUserMapper mapper;

    public AuthService(SessionRegistry sessionRegistry, AuthUserRepository repository, ServerProperties serverProperties, ObjectMapper objectMapper, @Qualifier("authUserMapperImpl") AuthUserMapper mapper) {
        this.sessionRegistry = sessionRegistry;
        this.repository = repository;
        this.serverProperties = serverProperties;
        this.objectMapper = objectMapper;
        this.mapper = mapper;
    }


    public ResponseEntity<DataDTO<SessionDTO>> getAccessToken(LoginDTO dto) {
        try {
            HttpClient httpclient = HttpClientBuilder.create().build();
            HttpPost httppost = new HttpPost(serverProperties.getServerUrl() + "/api/login");
            byte[] bytes = objectMapper.writeValueAsBytes(dto);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            httppost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            httppost.setEntity(new InputStreamEntity(byteArrayInputStream));
            HttpResponse response = httpclient.execute(httppost);
            JsonNode json_auth = objectMapper.readTree(EntityUtils.toString(response.getEntity()));
            if (json_auth.has("success") && json_auth.get("success").asBoolean()) {
                JsonNode node = json_auth.get("data");
                SessionDTO sessionDTO = objectMapper.readValue(node.toString(), SessionDTO.class);
                AuthUser user = getUserByUsername(dto.getEmail());
                AuthUserDTO authUserDTO = mapper.toDTO(user);
                sessionDTO.setUser(authUserDTO);
                return new ResponseEntity<>(new DataDTO<>(sessionDTO));
            }
            return new ResponseEntity<>(new DataDTO<>(objectMapper.readValue(json_auth.get("error").toString(),
                    AppErrorDTO.class)), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(new DataDTO<>(AppErrorDTO.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(e.getLocalizedMessage())
                    .build()), HttpStatus.OK);
        }
    }


    public ResponseEntity<DataDTO<SessionDTO>> getRefreshToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith(JWTUtils.BEARER)) {
            try {
                String refresh_token = authorizationHeader.substring(JWTUtils.BEARER.length());
                Algorithm algorithm = JWTUtils.getAlgorithm();
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String email = decodedJWT.getSubject();
                AuthUser user = getUserByUsername(email);
                String access_token = JWT.create()
                        .withSubject(user.getEmail())
                        .withExpiresAt(JWTUtils.getExpiry())
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", Collections.singletonList(user.getRole().name()))
                        .sign(algorithm);
                SessionDTO sessionDTO = SessionDTO.builder()
                        .refreshToken(refresh_token)
                        .accessToken(access_token)
                        .issuedAt(System.currentTimeMillis())
                        .refreshTokenExpiry(JWTUtils.getExpiryForRefreshToken().getTime())
                        .accessTokenExpiry(JWTUtils.getExpiry().getTime())
                        .user(mapper.toDTO(user))
                        .build();
                return new ResponseEntity<>(new DataDTO<>(sessionDTO));
            } catch (Exception exception) {
                return new ResponseEntity<>(new DataDTO<>(new AppErrorDTO(HttpStatus.FORBIDDEN, exception.getMessage(), request)));
            }
        } else {
            return new ResponseEntity<>(new DataDTO<>(new AppErrorDTO(HttpStatus.NOT_FOUND, "Not Found")));
        }
    }

    public ResponseEntity<DataDTO<List<MessageShortDTO>>> getOnlineUsers() {
        List<MessageShortDTO> messageShortDTOS = sessionRegistry
                .getAllPrincipals()
                .stream()
                .map(principal -> repository.findAllByRoleUser(principal.toString()).orElse(null)).filter(Objects::nonNull)
                .map(user -> new MessageShortDTO(0, user.getId(), user.getFullName()))
                .toList();
        return new ResponseEntity<>(new DataDTO<>(messageShortDTOS, (long) messageShortDTOS.size()));
    }

    public AuthUser getUserByUsername(String phoneNumber) {
        return getOptionalByPhoneNumber(phoneNumber).orElseThrow(() -> new NotFoundException("User is not found"));
    }


    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        AuthUser user = getUserByUsername(phone);
        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())))
                .build();
    }

    public Optional<AuthUser> getOptionalByPhoneNumber(String email) {
        log.info("Getting user by email : {}", email);
        return repository.findByEmail(email);
    }
}
