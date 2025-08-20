package luis.goes.urlshortener.modules.url.application.useCase.changeUrl;

import luis.goes.urlshortener.modules.url.presentation.dto.UrlChangeUrlDTO;
import luis.goes.urlshortener.modules.url.presentation.dto.UrlResponseDTO;

import java.util.UUID;

public interface IUrlChangeUrl {
    UrlResponseDTO change(UUID userId, UrlChangeUrlDTO dto);
}