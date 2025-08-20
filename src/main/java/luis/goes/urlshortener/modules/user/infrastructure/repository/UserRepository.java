package luis.goes.urlshortener.modules.user.infrastructure.repository;

import luis.goes.urlshortener.modules.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @Query(
            value = "SELECT * FROM user_db WHERE unaccent(LOWER(name)) = unaccent(LOWER(:name))",
            nativeQuery = true
    )
    Optional<UserEntity> findByName_Name(@Param("name") String name);

    Optional<UserEntity> findByUserCredentials_Email_Email(String email);

    List<UserEntity> findAllByDateInfoDeletedAtNotNull();
}