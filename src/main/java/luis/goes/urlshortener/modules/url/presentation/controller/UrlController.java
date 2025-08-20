package luis.goes.urlshortener.modules.url.presentation.controller;

import luis.goes.urlshortener.core.shared.utils.JwtUtils;
import luis.goes.urlshortener.modules.url.application.useCase.UrlUseCases;
import luis.goes.urlshortener.modules.url.presentation.dto.UrlChangeUrlDTO;
import luis.goes.urlshortener.modules.url.presentation.dto.UrlChangeUrlNameDTO;
import luis.goes.urlshortener.modules.url.presentation.dto.UrlRequestDTO;
import luis.goes.urlshortener.modules.url.presentation.dto.UrlResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/url")
public class UrlController {

    private final UrlUseCases useCases;

    public UrlController(UrlUseCases useCases) {
        this.useCases = useCases;
    }

    @GetMapping(value = "/all-user-urls")
    public ResponseEntity<List<UrlResponseDTO>> getAllUserUrls() {
        return ResponseEntity.ok(useCases.getGetters().getAllUserUrls(JwtUtils.getSubject()));
    }

    @PostMapping
    public ResponseEntity<UrlResponseDTO> create(@RequestBody UrlRequestDTO dto) {
        UUID userId = getUserIdByJwt();
        return ResponseEntity.ok(useCases.getCreate().create(userId, dto));
    }

    @PatchMapping(value = "/change/url-name")
    public ResponseEntity<UrlResponseDTO> changeUrlName(@RequestBody UrlChangeUrlNameDTO dto) {
        UUID userId = getUserIdByJwt();
        return ResponseEntity.ok(useCases.getChangeUrlName().change(userId, dto));
    }

    @PatchMapping(value = "/change/url")
    public ResponseEntity<UrlResponseDTO> changeUrl(@RequestBody UrlChangeUrlDTO dto) {
        UUID userId = getUserIdByJwt();
        return ResponseEntity.ok(useCases.getChangeUrl().change(userId, dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        UUID userId = getUserIdByJwt();
        useCases.getDelete().delete(userId, id);
        return ResponseEntity.noContent().build();
    }

    private UUID getUserIdByJwt() {
        return JwtUtils.getSubject();
    }

}