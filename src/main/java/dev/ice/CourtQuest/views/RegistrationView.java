package dev.ice.CourtQuest.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.ice.CourtQuest.entities.User;
import dev.ice.CourtQuest.services.UserService;

import java.time.LocalDate;

@Route("register")
@AnonymousAllowed
public class RegistrationView extends VerticalLayout {

    private final UserService userService;

    public RegistrationView(UserService userService) {
        this.userService = userService;

        // Back button with arrow icon
        Button backButton = new Button(new Icon(VaadinIcon.ARROW_LEFT));
        backButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("login")));

        // Header
        H1 header = new H1("Registration");
        header.getStyle().set("margin-top", "0");
        header.getStyle().set("align-self", "flex-start");

        // Form fields
        TextField firstName = new TextField("First Name");
        TextField lastName = new TextField("Last Name");
        DatePicker birthday = new DatePicker("Birthday");
        TextField department = new TextField("Department");
        RadioButtonGroup<String> gender = new RadioButtonGroup<>();
        EmailField email = new EmailField("Email Address");
        PasswordField passwordField = new PasswordField("Password");
        Button registerButton = new Button("Register");

        gender.setLabel("Gender");
        gender.setItems("Male", "Female");
        gender.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

        birthday.setMax(LocalDate.now());

        FormLayout formLayout = new FormLayout();
        formLayout.add(firstName, lastName, birthday, department, gender, email, passwordField);
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2),
                new FormLayout.ResponsiveStep("800px", 3)
        );

        formLayout.setColspan(email, 3);
        formLayout.setColspan(passwordField, 3);

        // Center the Register button
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setWidthFull();
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        buttonLayout.add(registerButton);

        // Validation and registration logic
        registerButton.addClickListener(e -> {
            if (validateForm(firstName, lastName, birthday, department, gender, email, passwordField)) {
                try {
                    // Gather form data and create a new User object
                    User newUser = new User();
                    newUser.setFirstName(firstName.getValue());
                    newUser.setLastName(lastName.getValue());
                    newUser.setBirthDate(birthday.getValue().toString());
                    newUser.setDepartment(department.getValue());
                    newUser.setGender(gender.getValue());
                    newUser.setEmail(email.getValue());
                    newUser.setPassword(passwordField.getValue());
                    newUser.setAge(LocalDate.now().getYear() - birthday.getValue().getYear());
                    newUser.setRating(0.0); // Initial rating

                    // Save the user using UserService
                    userService.saveUser(newUser);

                    Notification.show("Registration successful!");
                    // Optionally, navigate to another page
                    getUI().ifPresent(ui -> ui.navigate("login"));
                } catch (Exception ex) {
                    Notification.show("Registration failed: " + ex.getMessage());
                }
            } else {
                Notification.show("Please correct the errors in the form!");
            }
        });

        // Adding components to the layout
        HorizontalLayout headerLayout = new HorizontalLayout(backButton, header);
        headerLayout.setWidthFull();
        headerLayout.setAlignItems(Alignment.CENTER);

        VerticalLayout formContainer = new VerticalLayout(headerLayout, formLayout, buttonLayout);
        formContainer.setAlignItems(Alignment.START);
        formContainer.setWidth("600px"); // Set a max-width for the form container
        formContainer.setPadding(false);
        formContainer.getStyle().set("margin", "0 auto"); // Center the form container horizontally

        add(formContainer);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSizeFull();
    }

    private boolean validateForm(TextField firstName, TextField lastName, DatePicker birthday, TextField department, RadioButtonGroup<String> gender, EmailField email, PasswordField passwordField) {
        boolean isValid = true;
        // Check each field and set isValid to false if any fields are empty
        if (firstName.isEmpty()) {
            firstName.setErrorMessage("First name is required");
            firstName.setInvalid(true);
            isValid = false;
        }
        if (lastName.isEmpty()) {
            lastName.setErrorMessage("Last name is required");
            lastName.setInvalid(true);
            isValid = false;
        }
        if (birthday.isEmpty()) {
            birthday.setErrorMessage("Birthday is required");
            birthday.setInvalid(true);
            isValid = false;
        }
        if (department.isEmpty()) {
            department.setErrorMessage("Department is required");
            department.setInvalid(true);
            isValid = false;
        }
        if (gender.isEmpty()) {
            gender.setErrorMessage("Gender is required");
            gender.setInvalid(true);
            isValid = false;
        }
        if (email.isEmpty()) {
            email.setErrorMessage("Email is required");
            email.setInvalid(true);
            isValid = false;
        }
        if (passwordField.isEmpty()) {
            passwordField.setErrorMessage("Password is required");
            passwordField.setInvalid(true);
            isValid = false;
        }
        return isValid;
    }
}