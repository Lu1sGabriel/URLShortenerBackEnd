package luis.goes.urlshortener.modules.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.time.Instant;

@Embeddable
@Getter
public class UserDateInfo {

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    public UserDateInfo() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public void update() {
        this.updatedAt = Instant.now();
    }

    public void deactivate() {
        this.deletedAt = Instant.now();
    }

    public void activate() {
        this.deletedAt = null;
    }

}