package luis.goes.urlshortener.modules.url.application.useCase;

import lombok.Getter;
import luis.goes.urlshortener.modules.url.application.useCase.changeUrl.IUrlChangeUrl;
import luis.goes.urlshortener.modules.url.application.useCase.changeUrlName.IUrlChangeUrlName;
import luis.goes.urlshortener.modules.url.application.useCase.create.IUrlCreate;
import luis.goes.urlshortener.modules.url.application.useCase.delete.IUrlDelete;
import luis.goes.urlshortener.modules.url.application.useCase.gettersMethods.IUrlGetters;
import org.springframework.stereotype.Component;

@Component
@Getter
public class UrlUseCases {
    private final IUrlGetters getters;
    private final IUrlCreate create;
    private final IUrlChangeUrl changeUrl;
    private final IUrlChangeUrlName changeUrlName;
    private final IUrlDelete delete;


    public UrlUseCases(IUrlGetters getters, IUrlCreate create, IUrlChangeUrl changeUrl, IUrlChangeUrlName changeUrlName, IUrlDelete delete) {
        this.getters = getters;
        this.create = create;
        this.changeUrl = changeUrl;
        this.changeUrlName = changeUrlName;
        this.delete = delete;
    }

}