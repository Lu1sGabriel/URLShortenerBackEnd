package luis.goes.urlshortener.modules.url.infrastructure.repository;

import luis.goes.urlshortener.modules.url.domain.URLEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UrlRepository extends JpaRepository<URLEntity, UUID> {
    List<URLEntity> findAllByUser_Id(UUID userId);

    Optional<URLEntity> findByUser_IdAndId(UUID userId, UUID id);

    Optional<URLEntity> findByShortened(String shortened);
}