package luis.goes.urlshortener.modules.userAuthority.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import luis.goes.urlshortener.modules.authority.domain.AuthorityEntity;
import luis.goes.urlshortener.modules.user.domain.UserEntity;

import java.util.UUID;

@Entity
@Table(name = "user_authority_db")
@Getter
@NoArgsConstructor
public class UserAuthorityEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authority_id", nullable = false)
    private AuthorityEntity authority;

    public UserAuthorityEntity(UserEntity user, AuthorityEntity authority) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.authority = authority;
    }

}