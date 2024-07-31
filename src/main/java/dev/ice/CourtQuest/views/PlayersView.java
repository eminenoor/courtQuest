package dev.ice.CourtQuest.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import dev.ice.CourtQuest.components.PlayerCard;
import dev.ice.CourtQuest.components.PlayerCardInvite;
import dev.ice.CourtQuest.entities.Activity;
import dev.ice.CourtQuest.entities.Rating;
import dev.ice.CourtQuest.entities.UserDB;
import dev.ice.CourtQuest.services.ActivityService;
import dev.ice.CourtQuest.services.UserService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Route("players/:activityId")
@PermitAll
public class PlayersView extends HorizontalLayout implements BeforeEnterObserver {

    private Long activityId;
    Div playerContainer;
    private final ActivityService activityService;
    private final UserService userService;

    @Autowired
    public PlayersView(ActivityService activityService, UserService userService) {
        this.activityService = activityService;
        this.userService = userService;
        createLayout();
    }

    private void createLayout() {
        H1 profileTitle = new H1("Players");

        // Log out link
        RouterLink logoutLink = new RouterLink("Log out", LogoutView.class);
        logoutLink.getStyle().set("margin-right", "auto");

        // Top right icons
        Icon bellIcon = new Icon(VaadinIcon.BELL);
        bellIcon.getStyle().set("cursor", "pointer");
        bellIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("notifications")));

        Icon profileIcon = new Icon(VaadinIcon.USER);
        profileIcon.getStyle().set("cursor", "pointer");
        profileIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("profile")));

        HorizontalLayout topRightIcons = new HorizontalLayout(logoutLink, bellIcon, profileIcon);
        topRightIcons.getStyle().set("margin-left", "auto");

        // Header with top right icons
        HorizontalLayout headerLayout = new HorizontalLayout(profileTitle, topRightIcons);
        headerLayout.setWidthFull();
        headerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        headerLayout.getStyle().set("padding", "10px");

        // Navigation bar on the left
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

        playerContainer = new Div();
        playerContainer.getStyle().set("display", "grid");
        playerContainer.getStyle().set("grid-template-columns", "repeat(5, 1fr)");
        playerContainer.getStyle().set("gap", "16px");

        VerticalLayout mainContent = new VerticalLayout(headerLayout, playerContainer);
        headerLayout.getStyle().set("margin-bottom", "20px");
        mainContent.setWidthFull();
        mainContent.setAlignItems(FlexComponent.Alignment.CENTER);
        mainContent.setSpacing(true);
        mainContent.setPadding(false);
        mainContent.setHeightFull();
        mainContent.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        mainContent.getStyle().set("overflow", "auto");

        // Add components to the root layout
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
        List<UserDB> players = activityService.getUsersByActivityId(activityId);
        Activity activity = activityService.getActivity(activityId);
        double ratingGeneral = 0;
        double ratingPersonal = 0;

        playerContainer.removeAll();
        for (UserDB player : players) {
            Rating rating = player.getReceivedRatings();
            if(activity.getName().equals("Volleyball")){
                ratingGeneral = rating.getRatingVolleyball();
                ratingPersonal = rating.getRatingVolleyballPersonal();
            }
            else if(activity.getName().equals("Football")){
                ratingGeneral = rating.getRatingFootball();
                ratingPersonal = rating.getRatingFootballPersonal();
            }
            else if(activity.getName().equals("Basketball")){
                ratingGeneral = rating.getRatingBasketball();
                ratingPersonal = rating.getRatingBasketballPersonal();
            }
            else if(activity.getName().equals("Tennis")){
                ratingGeneral = rating.getRatingTennis();
                ratingPersonal = rating.getRatingTennisPersonal();
            }
            PlayerCard playerCard = new PlayerCard(
                    player.getUser_id(),
                    player.getAvatar(),
                    player.getFirst_name() + " " + player.getLast_name(),
                    player.getDepartment(),
                    player.getGender(),
                    player.getAge(),
                    ratingPersonal,
                    ratingGeneral
            );
            playerContainer.add(playerCard);
        }
    }

}
