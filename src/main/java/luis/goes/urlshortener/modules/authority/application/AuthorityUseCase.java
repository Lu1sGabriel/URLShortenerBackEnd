package luis.goes.urlshortener.modules.authority.application;

import lombok.Getter;
import luis.goes.urlshortener.modules.authority.application.assignToUser.IAuthorityAssignToUser;
import luis.goes.urlshortener.modules.authority.application.create.IAuthorityCreate;
import luis.goes.urlshortener.modules.authority.application.getMethods.IAuthorityGetters;
import luis.goes.urlshortener.modules.authority.application.removeFromUser.IAuthorityRemoveFromUser;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AuthorityUseCase {
    private final IAuthorityGetters getters;
    private final IAuthorityCreate create;
    private final IAuthorityAssignToUser assignToUser;
    private final IAuthorityRemoveFromUser removeFromUser;


    public AuthorityUseCase(IAuthorityGetters getters, IAuthorityCreate create,
                            IAuthorityAssignToUser assignToUser, IAuthorityRemoveFromUser removeFromUser) {
        this.getters = getters;
        this.create = create;
        this.assignToUser = assignToUser;
        this.removeFromUser = removeFromUser;
    }

}