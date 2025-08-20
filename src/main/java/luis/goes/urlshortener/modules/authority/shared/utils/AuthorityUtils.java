package luis.goes.urlshortener.modules.authority.shared.utils;

import luis.goes.urlshortener.modules.authority.domain.enums.IAuthority;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class AuthorityUtils {

    private static final Map<String, IAuthority> PERMISSION_MAP = new HashMap<>();

    static {
        Reflections reflections = new Reflections("luis.goes.urlshortener.modules.authority.domain.enums");

        // Busca todos os enums que implementam IPermission
        Set<Class<? extends IAuthority>> permissionEnums = reflections.getSubTypesOf(IAuthority.class);

        for (Class<? extends IAuthority> aClass : permissionEnums) {
            if (aClass.isEnum()) {
                IAuthority[] enumConstants = aClass.getEnumConstants();
                for (IAuthority permission : enumConstants) {
                    PERMISSION_MAP.put(permission.getValue(), permission);
                }
            }
        }
    }

    private AuthorityUtils() {
    }

    public static IAuthority[] getAllPermissions() {
        return PERMISSION_MAP.values().toArray(new IAuthority[0]);
    }

    public static IAuthority fromValue(String value) {
        IAuthority permission = PERMISSION_MAP.get(value);
        if (permission == null) {
            throw new IllegalArgumentException("Unknown permission value: " + value);
        }
        return permission;
    }

}