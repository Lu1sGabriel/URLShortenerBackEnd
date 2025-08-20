package luis.goes.urlshortener.modules.authority.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import luis.goes.urlshortener.core.shared.mapper.entityToDto.Mappable;
import luis.goes.urlshortener.modules.authority.domain.enums.IAuthority;
import luis.goes.urlshortener.modules.authority.valueObject.AuthorityName;
import luis.goes.urlshortener.modules.authority.valueObject.DescriptionAuthority;

import java.util.UUID;

@Entity
@Table(name = "authority_db")
@NoArgsConstructor
@Getter
public class AuthorityEntity implements Mappable {

    @Id
    private UUID id;

    @Embedded
    @AttributeOverride(name = "authority", column = @Column(name = "authority", nullable = false, unique = true))
    private AuthorityName authorityName;

    @Embedded
    @AttributeOverride(name = "description", column = @Column(name = "description", nullable = false, unique = true))
    private DescriptionAuthority description;

    public AuthorityEntity(IAuthority authority) {
        this.id = UUID.randomUUID();
        this.authorityName = new AuthorityName(authority.getValue());
        this.description = new DescriptionAuthority(authority.getDescription());
    }

    public AuthorityEntity(String authority, String description) {
        this.id = UUID.randomUUID();
        this.authorityName = new AuthorityName(authority);
        this.description = new DescriptionAuthority(description);
    }

}