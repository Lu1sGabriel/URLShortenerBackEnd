package luis.goes.urlshortener.modules.auth.presentation.controller;

import jakarta.servlet.http.HttpServletResponse;
import luis.goes.urlshortener.core.shared.utils.JwtUtils;
import luis.goes.urlshortener.modules.auth.application.useCases.LoginUseCases;
import luis.goes.urlshortener.modules.auth.presentation.dto.AuthStatusDTO;
import luis.goes.urlshortener.modules.auth.presentation.dto.AuthRequestDTO;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    private final LoginUseCases useCases;

    public AuthController(LoginUseCases useCases) {
        this.useCases = useCases;
    }

    @GetMapping("/status")
    public ResponseEntity<AuthStatusDTO> checkAuth() {
        try {
            List<String> permissions = JwtUtils.getCurrentPermissions();
            return ResponseEntity.ok(new AuthStatusDTO(true, permissions));
        } catch (Exception e) {
            // JWT ausente ou inválido
            return ResponseEntity.ok(new AuthStatusDTO(false, List.of()));
        }
    }


    @PostMapping(value = "/login")
    public ResponseEntity<Void> login(@RequestBody AuthRequestDTO dto, HttpServletResponse response) {
        String jwt = useCases.getLogin().login(dto);

        ResponseCookie cookie = ResponseCookie.from("jwt", jwt)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(3600)
                .sameSite("Strict")
                .build();

        response.setHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        ResponseCookie expiredCookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();

        response.setHeader("Set-Cookie", expiredCookie.toString());

        return ResponseEntity.ok().build();
    }

}