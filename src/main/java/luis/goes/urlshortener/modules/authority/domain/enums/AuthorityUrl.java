package luis.goes.urlshortener.modules.authority.domain.enums;

import lombok.Getter;

@Getter
public enum AuthorityUrl implements IAuthority {

    VIEW_ALL("url:view-all", "View all URLs"),
    VIEW("url:view", "View user's URLs"),
    CREATE("url:create", "Create a new URL"),
    CHANGE_NAME("url:change-name", "Change the URL name"),
    CHANGE_URL("url:change-url", "Change the URL address"),
    DELETE("url:delete", "Delete a URL");

    private final String value;
    private final String description;

    AuthorityUrl(String value, String description) {
        this.value = value;
        this.description = description;
    }

}