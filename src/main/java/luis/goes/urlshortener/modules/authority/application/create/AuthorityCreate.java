package luis.goes.urlshortener.modules.authority.application.create;

import luis.goes.urlshortener.core.exception.HttpException;
import luis.goes.urlshortener.modules.authority.domain.AuthorityEntity;
import luis.goes.urlshortener.modules.authority.infrastructure.repository.AuthorityRepository;
import luis.goes.urlshortener.modules.authority.presentation.dto.AuthorityRequestDTO;
import luis.goes.urlshortener.modules.authority.presentation.dto.AuthorityResponseDTO;
import luis.goes.urlshortener.modules.authority.shared.mapper.AuthorityMapper;
import org.springframework.stereotype.Service;

@Service
public class AuthorityCreate implements IAuthorityCreate {
    private final AuthorityRepository repository;
    private final AuthorityMapper mapper;

    public AuthorityCreate(AuthorityRepository repository, AuthorityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public AuthorityResponseDTO create(AuthorityRequestDTO dto) {
        if (repository.findByAuthorityName_Authority(dto.authority()).isPresent()) throw HttpException.conflict("There is already an authority with this name.");

        AuthorityEntity authority = new AuthorityEntity(dto.authority(), dto.description());

        return mapper.toDto(repository.save(authority));
    }

}