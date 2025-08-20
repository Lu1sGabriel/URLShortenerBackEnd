package luis.goes.urlshortener.modules.authority.application.getMethods;

import luis.goes.urlshortener.modules.authority.presentation.dto.AuthorityResponseDTO;

import java.util.List;

public interface IAuthorityGetters {

    List<AuthorityResponseDTO> all();

}