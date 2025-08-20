package luis.goes.urlshortener.modules.authority.application.getMethods;

import luis.goes.urlshortener.modules.authority.infrastructure.repository.AuthorityRepository;
import luis.goes.urlshortener.modules.authority.presentation.dto.AuthorityResponseDTO;
import luis.goes.urlshortener.modules.authority.shared.mapper.AuthorityMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityGetters implements IAuthorityGetters {

    private final AuthorityRepository repository;
    private final AuthorityMapper mapper;

    public AuthorityGetters(AuthorityRepository repository, AuthorityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<AuthorityResponseDTO> all() {
        return mapper.toDtoList(repository.findAll(Sort.by(Sort.Direction.ASC, "description")));
    }

}