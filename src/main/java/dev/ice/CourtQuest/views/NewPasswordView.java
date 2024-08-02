package dev.ice.CourtQuest.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.ice.CourtQuest.entities.UserDB;
import dev.ice.CourtQuest.services.UserService;

@Route("new-password")
@AnonymousAllowed
public class NewPasswordView extends VerticalLayout {

    private final UserService userService;
    private PasswordField newPassword;
    private PasswordField confirmPassword;

    public NewPasswordView(UserService userService) {
        this.userService = userService;
        String email = VaadinSession.getCurrent().getAttribute("email").toString();

        addClassName("new-password-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H1 title = new H1("Reset Your Password");

        newPassword = new PasswordField("Enter New Password");
        newPassword.setWidth("400px");
        confirmPassword = new PasswordField("Confirm New Password");
        confirmPassword.setWidth("400px");

        Button resetButton = new Button("Reset Password");

        resetButton.addClickListener(event -> {
            if (newPassword.getValue().equals(confirmPassword.getValue())) {
                UserDB user = userService.findUserByEmail(email);
                user.setPassword(newPassword.getValue());
                userService.resetPassword(user.getUser_id(), user);
                Notification.show("Password reset successfully!", 2000, Notification.Position.MIDDLE)
                        .addOpenedChangeListener(e -> getUI().ifPresent(ui -> ui.navigate("login")));

            } else {
                Notification.show("Passwords do not match!", 2000, Notification.Position.MIDDLE);
            }
        });
        resetButton.setWidth("150px");

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setAlignItems(Alignment.CENTER);
        formLayout.add(newPassword, confirmPassword, resetButton);

        setSpacing(true);
        add(title, formLayout);
    }
}
