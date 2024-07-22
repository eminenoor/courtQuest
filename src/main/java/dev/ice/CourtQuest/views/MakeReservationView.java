package dev.ice.CourtQuest.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.PermitAll;

import java.util.stream.IntStream;

@Route("make-reservation")
@PermitAll
public class MakeReservationView extends VerticalLayout {

    public MakeReservationView() {
        // Header
        H1 makeReservationTitle = new H1("Make a Reservation");

        // Log out link
        RouterLink logoutLink = new RouterLink("Log out", LogoutView.class); // Assuming LogoutView is the class handling logout
        logoutLink.getStyle().set("margin-right", "auto");

        // Top right icons
        Icon bellIcon = new Icon(VaadinIcon.BELL);
        bellIcon.getStyle().set("cursor", "pointer");
        bellIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("notifications")));

        Icon profileIcon = new Icon(VaadinIcon.USER);
        profileIcon.getStyle().set("cursor", "pointer");
        profileIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("profile")));

        HorizontalLayout topRightIcons = new HorizontalLayout(logoutLink, bellIcon, profileIcon);
        topRightIcons.getStyle().set("margin-left", "auto"); // Push to the right

        // Header with top right icons
        HorizontalLayout headerLayout = new HorizontalLayout(topRightIcons);
        headerLayout.setWidthFull();
        headerLayout.setAlignItems(Alignment.CENTER);

        // Navigation bar on the left
        VerticalLayout iconBar = new VerticalLayout();
        iconBar.setWidth("50px"); // Adjust width as needed
        iconBar.getStyle().set("background-color", "#1E3A8A"); // Dark blue color
        iconBar.getStyle().set("height", "100vh"); // Full height

        // Icons with navigation
        Icon groupIcon = new Icon(VaadinIcon.GROUP);
        groupIcon.getStyle().set("cursor", "pointer");
        groupIcon.getStyle().set("color", "white");
        groupIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(""))); // Current Activities

        Icon calendarIcon = new Icon(VaadinIcon.CALENDAR);
        calendarIcon.getStyle().set("cursor", "pointer");
        calendarIcon.getStyle().set("color", "white");
        calendarIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("my-activities"))); // My Activities

        Icon envelopeIcon = new Icon(VaadinIcon.ENVELOPE);
        envelopeIcon.getStyle().set("cursor", "pointer");
        envelopeIcon.getStyle().set("color", "white");
        envelopeIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("my-invitations"))); // My Invitations

        Icon checkIcon = new Icon(VaadinIcon.CHECK);
        checkIcon.getStyle().set("cursor", "pointer");
        checkIcon.getStyle().set("color", "white");
        checkIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("requests"))); // Requests

        Icon plusIcon = new Icon(VaadinIcon.PLUS);
        plusIcon.getStyle().set("cursor", "pointer");
        plusIcon.getStyle().set("color", "white");
        plusIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("make-reservation"))); // Make a Reservation

        Icon starIcon = new Icon(VaadinIcon.STAR);
        starIcon.getStyle().set("cursor", "pointer");
        starIcon.getStyle().set("color", "white");
        starIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("rate-players"))); // Rate Players

        iconBar.add(groupIcon, calendarIcon, envelopeIcon, checkIcon, plusIcon, starIcon);

        // Form components
        RadioButtonGroup<String> visibility = new RadioButtonGroup<>();
        visibility.setLabel("Visibility");
        visibility.setItems("Public", "Private");

        ComboBox<String> courtField = new ComboBox<>("Court/Field:");
        courtField.setItems("Basketball", "Football", "Tennis", "Volleyball");

        ComboBox<String> time = new ComboBox<>("Time:");
        time.setItems(IntStream.rangeClosed(8, 22).mapToObj(hour -> String.format("%02d:00", hour)).toArray(String[]::new));

        TextField quota = new TextField("Quota:");

        Button doneButton = new Button("Done");
        doneButton.addClickListener(e -> Notification.show("Reservation made!"));

        FormLayout formLayout = new FormLayout();
        formLayout.add(visibility, courtField, time, quota);
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1)
        );

        // Center the Done button
        HorizontalLayout buttonLayout = new HorizontalLayout(doneButton);
        buttonLayout.setWidthFull();
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        // Form container layout
        VerticalLayout formContainer = new VerticalLayout(makeReservationTitle, formLayout, buttonLayout);
        formContainer.setAlignItems(Alignment.CENTER);
        formContainer.setPadding(false);
        formContainer.getStyle().set("margin", "0 auto");
        formContainer.getStyle().set("padding-top", "20px"); // Adjust padding as needed
        formContainer.getStyle().set("width", "400px"); // Set width for compact form

        // Main content layout with header at the top
        VerticalLayout mainContent = new VerticalLayout(headerLayout, formContainer);
        mainContent.setAlignItems(Alignment.CENTER);
        mainContent.setJustifyContentMode(JustifyContentMode.START);
        mainContent.setHeightFull();
        mainContent.setPadding(false);

        // Add components to the root layout
        HorizontalLayout rootLayout = new HorizontalLayout(iconBar, mainContent);
        rootLayout.setAlignItems(Alignment.STRETCH);
        rootLayout.setSizeFull();
        rootLayout.getStyle().set("padding", "0"); // Remove any padding around the root layout

        // Ensure the sidebar is flush with the left edge
        iconBar.getStyle().set("position", "fixed");
        iconBar.getStyle().set("top", "0");
        iconBar.getStyle().set("left", "0");
        iconBar.getStyle().set("bottom", "0");

        add(rootLayout);
    }
}
