package luis.goes.urlshortener.modules.user.application.useCase;

import lombok.Getter;
import luis.goes.urlshortener.modules.user.application.useCase.activate.IUserActivate;
import luis.goes.urlshortener.modules.user.application.useCase.changeEmail.IUserChangeEmail;
import luis.goes.urlshortener.modules.user.application.useCase.changeName.IUserChangeName;
import luis.goes.urlshortener.modules.user.application.useCase.changePassword.IUserChangePassword;
import luis.goes.urlshortener.modules.user.application.useCase.create.IUserCreate;
import luis.goes.urlshortener.modules.user.application.useCase.deactivate.IUserDeactivate;
import luis.goes.urlshortener.modules.user.application.useCase.getMethods.IUserGetters;
import org.springframework.stereotype.Component;

@Component
@Getter
public class UserUseCases {

    private final IUserGetters getters;
    private final IUserCreate create;
    private final IUserChangePassword changePassword;
    private final IUserChangeEmail changeEmail;
    private final IUserChangeName changeName;
    private final IUserActivate activate;
    private final IUserDeactivate deactivate;


    public UserUseCases(IUserGetters getters, IUserCreate create, IUserChangePassword changePassword,
                        IUserChangeEmail changeEmail, IUserChangeName changeName,
                        IUserActivate activate, IUserDeactivate deactivate) {
        this.getters = getters;
        this.create = create;
        this.changePassword = changePassword;
        this.changeEmail = changeEmail;
        this.changeName = changeName;
        this.activate = activate;
        this.deactivate = deactivate;
    }

}