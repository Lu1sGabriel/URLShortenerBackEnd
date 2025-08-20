package luis.goes.urlshortener.modules.url.application.useCase.create;

import luis.goes.urlshortener.modules.url.presentation.dto.UrlRequestDTO;
import luis.goes.urlshortener.modules.url.presentation.dto.UrlResponseDTO;

import java.util.UUID;

public interface IUrlCreate {
    UrlResponseDTO create(UUID userId, UrlRequestDTO dto);
}
