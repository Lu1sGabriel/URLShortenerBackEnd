package luis.goes.urlshortener.modules.authority.application.assignToUser;

import luis.goes.urlshortener.modules.authority.presentation.dto.AuthorityAssignToUseRequestDto;
import luis.goes.urlshortener.modules.authority.presentation.dto.AuthorityWithUserDto;

public interface IAuthorityAssignToUser {

    AuthorityWithUserDto assign(AuthorityAssignToUseRequestDto dto);

}