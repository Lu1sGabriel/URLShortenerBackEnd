package luis.goes.urlshortener.modules.redirector.presentation;

import luis.goes.urlshortener.modules.url.application.useCase.UrlUseCases;
import luis.goes.urlshortener.modules.url.presentation.dto.UrlResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(value = "/redirector")
public class RedirectorController {

    private final UrlUseCases useCases;

    public RedirectorController(UrlUseCases useCases) {
        this.useCases = useCases;
    }

    @GetMapping(value = "/{userName}/{shortenedId}")
    public ResponseEntity<Void> redirectToOriginal(@PathVariable String userName, @PathVariable String shortenedId) {
        UrlResponseDTO urlResponseDTO = useCases.getGetters().getByShortened(userName, shortenedId);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(urlResponseDTO.url()));

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

}