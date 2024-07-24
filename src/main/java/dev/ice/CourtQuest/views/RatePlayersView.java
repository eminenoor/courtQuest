package dev.ice.CourtQuest.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import dev.ice.CourtQuest.components.RatePlayersCard;
import jakarta.annotation.security.PermitAll;

@Route("rate-players")
@PermitAll
public class RatePlayersView extends HorizontalLayout {

    public RatePlayersView() {
        // Header
        H1 currentActivitiesTitle = new H1("Rate Players");

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
        HorizontalLayout headerLayout = new HorizontalLayout(currentActivitiesTitle, topRightIcons);
        headerLayout.setWidthFull();
        headerLayout.setAlignItems(Alignment.CENTER);

        // Navigation bar on the left
        VerticalLayout iconBar = new VerticalLayout();
        iconBar.setWidth("50px"); // Adjust width as needed
        iconBar.getStyle().set("margin-top", "20px"); // Space below the header
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

        RatePlayersCard activity1 = new RatePlayersCard(
                "volleyball",
                "15:00-17:00",
                "12.07.2024",
                "Dormitory Sports Hall"
        );

        RatePlayersCard activity2 = new RatePlayersCard(
                "basketball",
                "12:00-13:00",
                "13.07.2024",
                "Main Sports Hall"
        );

        // Main content layout
        VerticalLayout mainContent = new VerticalLayout(headerLayout, activity1, activity2);
        mainContent.setWidthFull();
        mainContent.setAlignItems(Alignment.START);

        // Add components to the root layout
        add(iconBar, mainContent);
        setAlignItems(Alignment.STRETCH);
        setSizeFull();
    }
}
