package luis.goes.urlshortener.modules.authority.shared.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import luis.goes.urlshortener.modules.authority.domain.enums.IAuthority;
import luis.goes.urlshortener.modules.authority.shared.utils.AuthorityUtils;

@Converter(autoApply = true)
public class AuthorityConverter implements AttributeConverter<IAuthority, String> {

    @Override
    public String convertToDatabaseColumn(IAuthority attribute) {
        return attribute != null ? attribute.getValue() : null;
    }

    @Override
    public IAuthority convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;

        return AuthorityUtils.fromValue(dbData);
    }

}