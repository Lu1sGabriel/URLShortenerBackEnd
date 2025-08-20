package luis.goes.urlshortener.modules.authority.presentation.controller;

import luis.goes.urlshortener.modules.authority.application.AuthorityUseCase;
import luis.goes.urlshortener.modules.authority.presentation.dto.AuthorityAssignToUseRequestDto;
import luis.goes.urlshortener.modules.authority.presentation.dto.AuthorityRequestDTO;
import luis.goes.urlshortener.modules.authority.presentation.dto.AuthorityResponseDTO;
import luis.goes.urlshortener.modules.authority.presentation.dto.AuthorityWithUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/authority")
public class AuthorityController {

    private final AuthorityUseCase useCase;

    public AuthorityController(AuthorityUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping
    public ResponseEntity<List<AuthorityResponseDTO>> getAll() {
        return ResponseEntity.ok(useCase.getGetters().all());
    }

    @PostMapping
    public ResponseEntity<AuthorityResponseDTO> create(@RequestBody AuthorityRequestDTO dto) {
        return ResponseEntity.ok(useCase.getCreate().create(dto));
    }

    @PostMapping(value = "assign")
    public ResponseEntity<AuthorityWithUserDto> assignToUse(@RequestBody AuthorityAssignToUseRequestDto dto) {
        return ResponseEntity.ok(useCase.getAssignToUser().assign(dto));
    }

    @DeleteMapping(value = "{authorityId}/{userId}")
    public ResponseEntity<Void> removeAuthorityFromUser(@PathVariable UUID authorityId, @PathVariable UUID userId) {
        useCase.getRemoveFromUser().remove(authorityId, userId);
        return ResponseEntity.noContent().build();
    }

}