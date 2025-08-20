package luis.goes.urlshortener.modules.authority.application.create;

import luis.goes.urlshortener.modules.authority.presentation.dto.AuthorityRequestDTO;
import luis.goes.urlshortener.modules.authority.presentation.dto.AuthorityResponseDTO;

public interface IAuthorityCreate {

    AuthorityResponseDTO create(AuthorityRequestDTO dto);

}