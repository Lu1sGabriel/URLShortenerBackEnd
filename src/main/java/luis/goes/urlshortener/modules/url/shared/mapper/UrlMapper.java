package luis.goes.urlshortener.modules.url.shared.mapper;

import luis.goes.urlshortener.modules.url.domain.URLEntity;
import luis.goes.urlshortener.modules.url.presentation.dto.UrlResponseDTO;
import luis.goes.urlshortener.core.shared.mapper.entityToDto.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UrlMapper implements Mapper<UrlResponseDTO, URLEntity> {

    @Override
    public UrlResponseDTO toDto(URLEntity urlEntity) {
        return new UrlResponseDTO(
                urlEntity.getId(),
                urlEntity.getUrlName().getValue(),
                urlEntity.getUrl().getValue(),
                urlEntity.getShortened(),
                new UrlResponseDTO.UserInfo(
                        urlEntity.getId(),
                        urlEntity.getUser().getName().getValue(),
                        urlEntity.getUser().getUserCredentials().getEmail().getValue())
        );
    }

    @Override
    public List<UrlResponseDTO> toDtoList(List<URLEntity> urlEntities) {
        return urlEntities.stream().map(this::toDto).toList();
    }

}