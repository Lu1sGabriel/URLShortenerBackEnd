package luis.goes.urlshortener.modules.url.application.useCase.changeUrlName;

import luis.goes.urlshortener.modules.url.presentation.dto.UrlChangeUrlNameDTO;
import luis.goes.urlshortener.modules.url.presentation.dto.UrlResponseDTO;

import java.util.UUID;

public interface IUrlChangeUrlName {
    UrlResponseDTO change(UUID userId, UrlChangeUrlNameDTO dto);
}