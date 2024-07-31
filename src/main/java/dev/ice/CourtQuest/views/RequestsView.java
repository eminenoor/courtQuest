package dev.ice.CourtQuest.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import dev.ice.CourtQuest.components.PlayerCardRequest;
import dev.ice.CourtQuest.components.RequestActivityCard;
import dev.ice.CourtQuest.entities.Activity;
import dev.ice.CourtQuest.entities.Request;
import dev.ice.CourtQuest.entities.UserDB;
import dev.ice.CourtQuest.services.ActivityService;
import dev.ice.CourtQuest.services.RequestService;
import dev.ice.CourtQuest.services.UserService;
import dev.ice.CourtQuest.services.NotificationService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Route("requests")
@PermitAll
public class RequestsView extends HorizontalLayout {

    private final ActivityService activityService;
    private UserService userService;
    private RequestService requestService;
    private NotificationService notificationService;

    @Autowired
    public RequestsView(UserService userService, RequestService requestService, ActivityService activityService, NotificationService notificationService) {
        this.userService = userService;
        this.requestService = requestService;
        this.activityService = activityService;
        this.notificationService = notificationService;

        // Header
        H1 currentActivitiesTitle = new H1("Requests");

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
        HorizontalLayout headerLayout = new HorizontalLayout(currentActivitiesTitle, topRightIcons);
        headerLayout.setWidthFull();
        headerLayout.setAlignItems(Alignment.CENTER);

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

        // Get current user and their received requests
        UserDB user = userService.getCurrentUser();
        Set<Request> requestsSet = user.getReceivedRequests();

        // Convert Set to List
        List<Request> requestList = new ArrayList<>(requestsSet);

        // Create a layout to hold all request cards
        VerticalLayout requestCardsLayout = new VerticalLayout();
        requestCardsLayout.setSpacing(true);
        requestCardsLayout.setPadding(true);
        requestCardsLayout.setWidthFull();

        // Create and add a RequestActivityCard for each request
        for (Request request : requestList) {
            Activity activity = request.getActivity();

            // Horizontal layout to hold activity card and player cards
            HorizontalLayout activityWithPlayersLayout = new HorizontalLayout();
            activityWithPlayersLayout.setWidthFull();

            RequestActivityCard requestCard = new RequestActivityCard(
                    activity.getName(),
                    activity.getPlace(),
                    activity.getDate(),
                    activity.getTime(),
                    activity.getParticipants().size() + "/" + activity.getQuota(),
                    true
            );
            activityWithPlayersLayout.add(requestCard);

            VerticalLayout playersLayout = new VerticalLayout();
            playersLayout.setWidth("max-content");
            UserDB player = request.getSender();
            PlayerCardRequest playerCard = new PlayerCardRequest(player.getUser_id(), player.getFirst_name(), player.getDepartment(), player.getGender(), player.getAge(), player.getRating(), 4.5);
            playerCard.getAcceptButton().addClickListener(e -> {
                if (activity.getQuota() > activity.getParticipants().size()) {
                    //activityService.acceptUser(activity, player);
                    activityService.addParticipant(activity.getActivityId(), player.getUser_id());
                    notificationService.createNotification(
                            player.getUser_id(),
                            "your request to join the activity has been accepted",
                            "acceptance",
                            activity.getName(),
                            activity.getDate(),
                            activity.getTime(),
                            activity.getPlace()
                    );
                    //requestService.deleteRequest(request);
                    requestService.deleteRequestById(request);
                    requestCardsLayout.remove(activityWithPlayersLayout);
                    Notification.show("Player added to activity.");
                } else {
                    Notification.show("No available quota.");
                }
            });
            playerCard.getDeclineButton().addClickListener(e -> {
                activityService.declineUser(activity, player);
                notificationService.createNotification(
                        player.getUser_id(),
                        "your request to join the activity has been declined",
                        "decline",
                        activity.getName(),
                        activity.getDate(),
                        activity.getTime(),
                        activity.getPlace()
                );
                requestService.deleteRequest(request);
                requestCardsLayout.remove(activityWithPlayersLayout);
                Notification.show("Player declined.");
            });
            playersLayout.add(playerCard);

            activityWithPlayersLayout.add(playersLayout);
            requestCardsLayout.add(activityWithPlayersLayout);
        }

        // Make the layout scrollable
        Div scrollableContainer = new Div(requestCardsLayout);
        scrollableContainer.getStyle().set("overflow", "auto");
        scrollableContainer.setHeight("100%");
        scrollableContainer.setWidth("100%");

        // Main content layout
        VerticalLayout mainContent = new VerticalLayout(headerLayout, scrollableContainer);
        mainContent.setWidthFull();
        mainContent.setAlignItems(Alignment.START);
        mainContent.setHeightFull();
        mainContent.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        mainContent.getStyle().set("overflow", "auto");

        // Add components to the root layout
        add(iconBar, mainContent);
        setAlignItems(Alignment.STRETCH);
        setSizeFull();
    }
}
