package luis.goes.urlshortener.modules.authority.shared.mapper;

import luis.goes.urlshortener.core.shared.mapper.entityToDto.Mapper;
import luis.goes.urlshortener.modules.authority.domain.AuthorityEntity;
import luis.goes.urlshortener.modules.authority.presentation.dto.AuthorityResponseDTO;
import luis.goes.urlshortener.modules.authority.presentation.dto.AuthorityWithUserDto;
import luis.goes.urlshortener.modules.user.domain.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorityMapper implements Mapper<AuthorityResponseDTO, AuthorityEntity> {

    @Override
    public AuthorityResponseDTO toDto(AuthorityEntity authorityEntity) {
        return new AuthorityResponseDTO(
                authorityEntity.getId(),
                authorityEntity.getAuthorityName().getValue(),
                authorityEntity.getDescription().getValue()
        );
    }

    @Override
    public List<AuthorityResponseDTO> toDtoList(List<AuthorityEntity> authorityEntities) {
        return authorityEntities.stream().map(this::toDto).toList();
    }

    public AuthorityWithUserDto toDto(AuthorityEntity authorityEntity, UserEntity userEntity) {
        return new AuthorityWithUserDto(
                authorityEntity.getId(),
                authorityEntity.getAuthorityName().getValue(),
                authorityEntity.getDescription().getValue(),
                userEntity.getId(),
                userEntity.getName().getValue()
        );
    }

}