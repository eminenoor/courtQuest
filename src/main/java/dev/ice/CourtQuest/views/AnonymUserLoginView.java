package dev.ice.CourtQuest.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.ice.CourtQuest.entities.UserDB;
import dev.ice.CourtQuest.repos.UserRepository;
import dev.ice.CourtQuest.services.UserService;

import java.util.List;

@Route("forgot-password")
@AnonymousAllowed
public class AnonymUserLoginView extends VerticalLayout implements BeforeEnterObserver {

    private UserService userService;
    private UserRepository userRepository;


    private String source;



    @Override

    public void beforeEnter(BeforeEnterEvent event) {

        source = event.getLocation().getQueryParameters().getParameters().getOrDefault("source", List.of("")).get(0);

    }



    public AnonymUserLoginView(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;



        Button backButton = new Button(new Icon(VaadinIcon.ARROW_LEFT));
        backButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("login")));

        H1 header = new H1("CourtQuest");
        header.getStyle().set("margin", "0");

        HorizontalLayout headerLayout = new HorizontalLayout(backButton, header);
        headerLayout.setAlignItems(Alignment.CENTER);
        headerLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        H2 subHeader = new H2("Welcome!");
        subHeader.getStyle().set("text-align", "center");
        subHeader.getStyle().set("margin-top", "20px");
        subHeader.getStyle().set("margin-bottom", "10px");

        H3 entranceMessage = new H3("Please Enter Your Bilkent Email");
        entranceMessage.getStyle().set("text-align", "left");
        entranceMessage.getStyle().set("margin-top", "10px");
        entranceMessage.getStyle().set("margin-bottom", "20px");

        EmailField emailField = new EmailField();
        emailField.setPlaceholder("Input Field");
        emailField.setWidth("400px");

        Button continueButton = new Button("Continue");

        continueButton.addClickListener(e -> {

            String email = emailField.getValue();
            if(email.contains("ug.bilkent.edu.tr")){
                UserDB existingUser = userRepository.findByEmail(email);



                if ("create-account".equals(source)) {

                    if (existingUser != null) {

                        Notification.show("The user already exists!", 3000, Notification.Position.BOTTOM_START);

                        getUI().ifPresent(ui -> ui.navigate("login"));

                    } else {

                        String otp = userService.getOtp(email);

                        VaadinSession.getCurrent().setAttribute("otp", otp);

                        VaadinSession.getCurrent().setAttribute("source", source);

                        VaadinSession.getCurrent().setAttribute("email", email);

                        continueButton.getUI().ifPresent(ui -> ui.navigate("verification-code"));

                    }

                } else if ("forgot-password".equals(source)) {

                    if (existingUser == null) {

                        Notification.show("The user does not exist!", 3000, Notification.Position.BOTTOM_START);

                    } else {

                        String otp = userService.getOtp(email);

                        VaadinSession.getCurrent().setAttribute("otp", otp);

                        VaadinSession.getCurrent().setAttribute("source", source);

                        VaadinSession.getCurrent().setAttribute("email", email);

                        continueButton.getUI().ifPresent(ui -> ui.navigate("verification-code"));

                    }

                }
            }
            else{
                Notification.show("You should register with your Bilkent email!", 3000, Notification.Position.BOTTOM_START);
            }


        });

        VerticalLayout mainLayout = new VerticalLayout(headerLayout, subHeader, entranceMessage, emailField, continueButton);
        mainLayout.setAlignItems(Alignment.CENTER);
        mainLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        mainLayout.setHeightFull();
        mainLayout.getStyle().set("margin-top", "auto");
        mainLayout.getStyle().set("margin-bottom", "auto");

        add(mainLayout);

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setHeightFull();
    }
}
