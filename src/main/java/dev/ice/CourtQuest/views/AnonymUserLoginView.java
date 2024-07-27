package dev.ice.CourtQuest.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("forgot-password")
@AnonymousAllowed
public class AnonymUserLoginView extends VerticalLayout {

    public AnonymUserLoginView() {
        // Creating the back button with an icon
        Button backButton = new Button(new Icon(VaadinIcon.ARROW_LEFT));
        backButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("login")));

        // Creating the header
        H1 header = new H1("CourtQuest");
        header.getStyle().set("margin", "0");

        // Creating a layout for the header and back button
        HorizontalLayout headerLayout = new HorizontalLayout(backButton, header);
        headerLayout.setAlignItems(Alignment.CENTER);
        headerLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        // Creating the sub-header
        H2 subHeader = new H2("Welcome!");
        subHeader.getStyle().set("text-align", "center");
        subHeader.getStyle().set("margin-top", "20px"); // Adding space above the sub-header
        subHeader.getStyle().set("margin-bottom", "10px"); // Adding space below the sub-header

        // Creating the entrance message
        H3 entranceMessage = new H3("Please Enter Your Bilkent Email");
        entranceMessage.getStyle().set("text-align", "left");
        entranceMessage.getStyle().set("margin-top", "10px"); // Adding space above the entrance message
        entranceMessage.getStyle().set("margin-bottom", "20px"); // Adding space below the entrance message

        // Creating the email field
        EmailField emailField = new EmailField();
        emailField.setPlaceholder("Input Field");
        emailField.setWidth("400px"); // Increase the size of the email field

        // Creating the continue button
        Button continueButton = new Button("Continue");
        continueButton.addClickListener(e -> {
            // Navigate to the VerificationCodeView
            continueButton.getUI().ifPresent(ui -> ui.navigate("verification-code"));
        });

        // Creating the main layout and adding components
        VerticalLayout mainLayout = new VerticalLayout(headerLayout, subHeader, entranceMessage, emailField, continueButton);
        mainLayout.setAlignItems(Alignment.CENTER);
        mainLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        mainLayout.setHeightFull();
        mainLayout.getStyle().set("margin-top", "auto");
        mainLayout.getStyle().set("margin-bottom", "auto");

        add(mainLayout);

        // Setting the main layout to be centered
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setHeightFull();
    }
}
