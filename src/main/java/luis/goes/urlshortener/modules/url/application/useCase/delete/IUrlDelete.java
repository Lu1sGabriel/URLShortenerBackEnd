package luis.goes.urlshortener.modules.url.application.useCase.delete;

import java.util.UUID;

public interface IUrlDelete {
    void delete(UUID userId, UUID id);
}