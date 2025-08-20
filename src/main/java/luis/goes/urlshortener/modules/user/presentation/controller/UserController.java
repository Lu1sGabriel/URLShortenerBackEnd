package luis.goes.urlshortener.modules.user.presentation.controller;

import luis.goes.urlshortener.core.shared.utils.JwtUtils;
import luis.goes.urlshortener.modules.user.application.useCase.UserUseCases;
import luis.goes.urlshortener.modules.user.presentation.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {

    private final UserUseCases userUseCases;

    public UserController(UserUseCases userUseCases) {
        this.userUseCases = userUseCases;
    }

    @GetMapping(value = "/view-profile")
    public ResponseEntity<UserResponseDto> getById() {
        UUID userId = getUserIdByJwt();
        return ResponseEntity.ok(userUseCases.getGetters().byId(userId));
    }

    @GetMapping(value = "/all/deactivated")
    public ResponseEntity<List<UserResponseDto>> getAllDeactivated() {
        return ResponseEntity.ok(userUseCases.getGetters().allDeactivated());
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        return ResponseEntity.ok(userUseCases.getGetters().all());
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<UserResponseDto> getByName(@PathVariable String name) {
        return ResponseEntity.ok(userUseCases.getGetters().byName(name));
    }

    @GetMapping(value = "/email/{email}")
    public ResponseEntity<UserResponseDto> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userUseCases.getGetters().byEmail(email));
    }

    @GetMapping(value = "/userAuthorities")
    public ResponseEntity<UserWithAuthorityDto> getAllUserAuthorities() {
        UUID userId = getUserIdByJwt();
        return ResponseEntity.ok(userUseCases.getGetters().allUserAuthorities(userId));
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody UserRequestDTO dto) {
        return ResponseEntity.ok(userUseCases.getCreate().create(dto));
    }

    @PatchMapping(value = "/change/name")
    public ResponseEntity<UserResponseDto> changeName(@RequestBody UserChangeNameDTO dto) {
        UUID userId = getUserIdByJwt();
        return ResponseEntity.ok(userUseCases.getChangeName().change(userId, dto));
    }

    @PatchMapping(value = "/change/email")
    public ResponseEntity<UserResponseDto> changeEmail(@RequestBody UserChangeEmailDTO dto) {
        UUID userId = getUserIdByJwt();
        return ResponseEntity.ok(userUseCases.getChangeEmail().change(userId, dto));
    }

    @PatchMapping(value = "/change/password")
    public ResponseEntity<UserResponseDto> changePassword(@RequestBody UserChangePasswordDTO dto) {
        UUID userId = getUserIdByJwt();
        return ResponseEntity.ok(userUseCases.getChangePassword().change(userId, dto));
    }

    @PatchMapping(value = "/activate/{id}")
    public ResponseEntity<Void> activate(@PathVariable UUID id) {
        userUseCases.getActivate().activate(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deactivate() {
        UUID userId = getUserIdByJwt();
        userUseCases.getDeactivate().deactivate(userId);
        return ResponseEntity.noContent().build();
    }

    private UUID getUserIdByJwt() {
        return JwtUtils.getSubject();
    }

}