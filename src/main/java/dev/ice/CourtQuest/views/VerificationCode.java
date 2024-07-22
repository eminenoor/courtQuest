package dev.ice.CourtQuest.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("verification-code")
@AnonymousAllowed
public class VerificationCode extends VerticalLayout {

    public VerificationCode() {
        // Creating the back button with an icon
        Button backButton = new Button(new Icon(VaadinIcon.ARROW_LEFT));
        backButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("forgot-password")));

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

        // Creating the entrance message
        H3 entranceMessage = new H3("Please Enter The Verification Code");
        entranceMessage.getStyle().set("text-align", "center");

        // Creating the verification code field
        TextField verificationCodeField = new TextField();
        verificationCodeField.setPlaceholder("Input Field");
        verificationCodeField.setWidth("400px"); // Increase the size of the text field

        // Creating the continue button
        Button continueButton = new Button("Continue");
        continueButton.addClickListener(e -> {
            // Navigate to the next view (e.g., dashboard or reset password view)
            // continueButton.getUI().ifPresent(ui -> ui.navigate("nextView"));
        });

        // Creating the main layout and adding components
        VerticalLayout mainLayout = new VerticalLayout(headerLayout, subHeader, entranceMessage, verificationCodeField, continueButton);
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
