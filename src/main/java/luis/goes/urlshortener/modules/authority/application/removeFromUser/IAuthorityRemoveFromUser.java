package luis.goes.urlshortener.modules.authority.application.removeFromUser;

import java.util.UUID;

public interface IAuthorityRemoveFromUser {

    void remove(UUID authorityId, UUID userId);

}