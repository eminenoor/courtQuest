// InvitePlayersView.java
package dev.ice.CourtQuest.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import dev.ice.CourtQuest.components.PlayerCardInvite;
import dev.ice.CourtQuest.entities.UserDB;
import dev.ice.CourtQuest.services.InvitationService;
import dev.ice.CourtQuest.services.UserService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

@Route("invite-players/:activityId")
@PermitAll
public class InvitePlayersView extends HorizontalLayout implements BeforeEnterObserver {

    private Long activityId;
    private UserService userService;
    private InvitationService invitationService;

    private Div playerContainer;

    @Autowired
    public InvitePlayersView(UserService userService, InvitationService invitationService) {
        this.userService = userService;
        this.invitationService = invitationService;

        H1 profileTitle = new H1("Invite Players");

        RouterLink logoutLink = new RouterLink("Log out", LogoutView.class);
        logoutLink.getStyle().set("margin-right", "auto");

        Icon bellIcon = new Icon(VaadinIcon.BELL);
        bellIcon.getStyle().set("cursor", "pointer");
        bellIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("notifications")));

        Icon profileIcon = new Icon(VaadinIcon.USER);
        profileIcon.getStyle().set("cursor", "pointer");
        profileIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("profile")));

        HorizontalLayout topRightIcons = new HorizontalLayout(logoutLink, bellIcon, profileIcon);
        topRightIcons.getStyle().set("margin-left", "auto");

        HorizontalLayout headerLayout = new HorizontalLayout(profileTitle, topRightIcons);
        headerLayout.setWidthFull();
        headerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        headerLayout.getStyle().set("padding", "10px");

        VerticalLayout iconBar = new VerticalLayout();
        iconBar.setWidth("50px");
        iconBar.getStyle().set("background-color", "#1E3A8A");
        iconBar.getStyle().set("height", "100vh");

        Icon groupIcon = new Icon(VaadinIcon.GROUP);
        groupIcon.getStyle().set("cursor", "pointer");
        groupIcon.getStyle().set("color", "white");
        groupIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("")));

        Icon calendarIcon = new Icon(VaadinIcon.CALENDAR);
        calendarIcon.getStyle().set("cursor", "pointer");
        calendarIcon.getStyle().set("color", "white");
        calendarIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("my-activities")));

        Icon envelopeIcon = new Icon(VaadinIcon.ENVELOPE);
        envelopeIcon.getStyle().set("cursor", "pointer");
        envelopeIcon.getStyle().set("color", "white");
        envelopeIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("my-invitations")));

        Icon checkIcon = new Icon(VaadinIcon.CHECK);
        checkIcon.getStyle().set("cursor", "pointer");
        checkIcon.getStyle().set("color", "white");
        checkIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("requests")));

        Icon plusIcon = new Icon(VaadinIcon.PLUS);
        plusIcon.getStyle().set("cursor", "pointer");
        plusIcon.getStyle().set("color", "white");
        plusIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("make-reservation")));

        Icon starIcon = new Icon(VaadinIcon.STAR);
        starIcon.getStyle().set("cursor", "pointer");
        starIcon.getStyle().set("color", "white");
        starIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("rate-players")));

        iconBar.add(groupIcon, calendarIcon, envelopeIcon, checkIcon, plusIcon, starIcon);

        TextField searchField = new TextField();
        searchField.setPlaceholder("Search...");
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.getStyle().setWidth("300px");
        Icon searchIcon = VaadinIcon.SEARCH.create();
        searchIcon.getStyle().set("cursor", "pointer");
        searchField.addValueChangeListener(event -> search(String.valueOf(searchField.getValue())));
        Button searchButton = new Button(searchIcon);
        searchButton.addClickListener(event -> search(searchField.getValue()));
        HorizontalLayout searchLayout = new HorizontalLayout(searchField, searchButton);

        playerContainer = new Div();
        playerContainer.getStyle().set("display", "grid");
        playerContainer.getStyle().set("grid-template-columns", "repeat(5, 1fr)");
        playerContainer.getStyle().set("gap", "16px");

        VerticalLayout mainContent = new VerticalLayout(profileTitle, searchLayout, playerContainer);
        headerLayout.getStyle().set("margin-bottom", "20px");
        mainContent.setWidthFull();
        mainContent.setAlignItems(Alignment.CENTER);
        mainContent.setSpacing(true);
        mainContent.setPadding(false);
        mainContent.setHeightFull();
        mainContent.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        mainContent.getStyle().set("overflow", "auto");

        add(iconBar, mainContent);
        setAlignItems(Alignment.STRETCH);
        setSizeFull();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> activityIdOpt = event.getRouteParameters().get("activityId").map(Long::parseLong);
        if (activityIdOpt.isPresent()) {
            activityId = activityIdOpt.get();
            displayPlayers();
        } else {
            Notification.show("Activity ID is missing");
        }
    }

    private void displayPlayers() {
        List<UserDB> allUsers = userService.getAllUsers();
        playerContainer.removeAll();
        for (UserDB user : allUsers) {
            PlayerCardInvite playerCard = new PlayerCardInvite(user.getFirst_name() + " " + user.getLast_name(), user.getDepartment(), user.getGender(), user.getAge(), user.getRating(), user.getRating());
            playerCard.getInviteButton().addClickListener(event -> sendInvitation(user.getUser_id()));
            playerContainer.add(playerCard);
        }
    }

    private void sendInvitation(Long recipientId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDB currentUser = userService.findUserByEmail(username);
        if (currentUser != null) {
            invitationService.sendInvitation(currentUser.getUser_id(), recipientId, activityId);
            Notification.show("Invitation sent");
        } else {
            Notification.show("Unable to send invitation. Please try again.");
        }
    }

    public void search(String name) {
        playerContainer.removeAll();
        List<UserDB> allUsers = userService.getAllUsers();
        for (UserDB user : allUsers) {
            if ((user.getFirst_name() + " " + user.getLast_name()).toLowerCase().startsWith(name.toLowerCase())) {
                PlayerCardInvite playerCard = new PlayerCardInvite(user.getFirst_name() + " " + user.getLast_name(), user.getDepartment(), user.getGender(), user.getAge(), user.getRating(), user.getRating());
                playerCard.getInviteButton().addClickListener(event -> sendInvitation(user.getUser_id()));
                playerContainer.add(playerCard);
            }
        }
    }
}
