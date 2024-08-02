package dev.ice.CourtQuest.views;


import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;


@Route("login")
@AnonymousAllowed
@CssImport("./styles/loginCss.css")
public class LoginView extends VerticalLayout {

    public LoginView() {
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        LoginI18n i18n = LoginI18n.createDefault();
        LoginI18n.Form i18nForm = i18n.getForm();
        i18nForm.setTitle("Welcome!");
        i18nForm.setForgotPassword("");
        i18n.setForm(i18nForm);

        var login = new LoginForm();
        login.setI18n(i18n);
        login.setAction("login");

        Div links = new Div();
        links.addClassName("additional-links");

        RouterLink createAccount = new RouterLink("Create an account", AnonymUserLoginView.class);

        createAccount.addClassName("create-account");

        createAccount.getElement().setAttribute("href", "forgot-password?source=create-account");

        RouterLink forgotPassword = new RouterLink("Forgot password", AnonymUserLoginView.class);

        forgotPassword.addClassName("forgot-password");

        forgotPassword.getElement().setAttribute("href", "forgot-password?source=forgot-password");

        links.add(createAccount, forgotPassword);



        add(new H1("CourtQuest"), login, links);

    }
}