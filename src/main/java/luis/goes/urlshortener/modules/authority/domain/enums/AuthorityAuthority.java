package luis.goes.urlshortener.modules.authority.domain.enums;

import lombok.Getter;

@Getter
public enum AuthorityAuthority implements IAuthority {

    VIEW_ALL_AUTHORITIES("authority:view-all", "View all available authorities."),
    ASSING_TO_USER("authority:assing-to-user", "Assing an authority to a user."),
    REMOVE_FROM_USER("authority:remove-from-user", "Remove one or more authorities from a user."),
    CREATE("authority:create", "Create a new authority.");

    private final String value;
    private final String description;

    AuthorityAuthority(String value, String description) {
        this.value = value;
        this.description = description;
    }

}