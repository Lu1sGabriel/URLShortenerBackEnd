package luis.goes.urlshortener.modules.user.shared.mapper;

import luis.goes.urlshortener.core.shared.mapper.entityToDto.Mapper;
import luis.goes.urlshortener.modules.authority.presentation.dto.AuthorityResponseDTO;
import luis.goes.urlshortener.modules.user.domain.UserEntity;
import luis.goes.urlshortener.modules.user.presentation.dto.UserResponseDto;
import luis.goes.urlshortener.modules.user.presentation.dto.UserWithAuthorityDto;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public final class UserMapper implements Mapper<UserResponseDto, UserEntity> {

    @Override
    public UserResponseDto toDto(UserEntity userEntity) {
        return new UserResponseDto(
                userEntity.getId(),
                userEntity.getName().getValue(),
                userEntity.getUserCredentials().getEmail().getValue(),
                userEntity.getDateInfo().getCreatedAt(),
                userEntity.getDateInfo().getUpdatedAt()
        );
    }

    @Override
    public List<UserResponseDto> toDtoList(List<UserEntity> userEntities) {
        return userEntities.stream().map(this::toDto).toList();
    }

    public UserWithAuthorityDto toDtoWithAuthority(UserEntity userEntity) {
        return new UserWithAuthorityDto(
                userEntity.getId(),
                userEntity.getName().getValue(),
                userEntity.getUserAuthorities().stream()
                        .map(authority ->
                                new AuthorityResponseDTO(
                                        authority.getAuthority().getId(),
                                        authority.getAuthority().getAuthorityName().getValue(),
                                        authority.getAuthority().getDescription().getValue()
                                )
                        )
                        .sorted(Comparator.comparing(AuthorityResponseDTO::authority))
                        .toList()
        );
    }

}