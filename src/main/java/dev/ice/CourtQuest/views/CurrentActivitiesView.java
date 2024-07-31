package dev.ice.CourtQuest.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import dev.ice.CourtQuest.components.ActivityCard;
import dev.ice.CourtQuest.entities.Activity;
import dev.ice.CourtQuest.entities.UserDB;
import dev.ice.CourtQuest.services.ActivityService;
import dev.ice.CourtQuest.services.NotificationService;
import dev.ice.CourtQuest.services.RequestService;
import dev.ice.CourtQuest.services.UserService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route("")
@PermitAll
public class CurrentActivitiesView extends HorizontalLayout {

    @Autowired
    private ActivityService activityService;
    @Autowired
    private RequestService requestService;
    @Autowired
    private UserService userService;
    @Autowired
    private NotificationService notificationService;


    public CurrentActivitiesView(ActivityService activityService, UserService userService, RequestService requestService) {        this.activityService = activityService;
        this.activityService = activityService;
        this.userService = userService;
        this.requestService = requestService;

        H1 currentActivitiesTitle = new H1("Current Activities");

        RouterLink logoutLink = new RouterLink("Log out", LogoutView.class); // Assuming LogoutView is the class handling logout
        logoutLink.getStyle().set("margin-right", "auto");

        Icon bellIcon = new Icon(VaadinIcon.BELL);
        bellIcon.getStyle().set("cursor", "pointer");
        bellIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("notifications")));

        Icon profileIcon = new Icon(VaadinIcon.USER);
        profileIcon.getStyle().set("cursor", "pointer");
        profileIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("profile")));

        HorizontalLayout topRightIcons = new HorizontalLayout(logoutLink, bellIcon, profileIcon);
        topRightIcons.getStyle().set("margin-left", "auto"); // Push to the right

        HorizontalLayout headerLayout = new HorizontalLayout(currentActivitiesTitle, topRightIcons);
        headerLayout.setWidthFull();
        headerLayout.setAlignItems(Alignment.CENTER);

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

        Button createReservationButton = new Button("Create Reservation");
        Button myActivitiesButton = new Button("My Activities");
        Button myInvitationsButton = new Button("My Invitations");

        createReservationButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("make-reservation")));
        myActivitiesButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("my-activities")));
        myInvitationsButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("my-invitations")));

        HorizontalLayout buttonLayout = new HorizontalLayout(createReservationButton, myActivitiesButton, myInvitationsButton);
        buttonLayout.setWidthFull();
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        buttonLayout.setSpacing(true);

        VerticalLayout activityLayout = new VerticalLayout();
        activityLayout.setWidthFull();
        activityLayout.setSpacing(true);

        List<Activity> publicActivities = activityService.getPublicActivities();
        List<Activity> myActivities = activityService.getMyActivities();
        for(int i = 0; i < myActivities.size(); i++) {
            publicActivities.remove(myActivities.get(i));
        }

        for (Activity activity : publicActivities) {
            ActivityCard activityCard = new ActivityCard(
                    activity.getName(),
                    activity.getPlace(),
                    activity.getDate(),
                    (activity.getTime()+ " - " + (Integer.parseInt(activity.getTime().substring(0,2)) + 1) + ".00"),
                    activity.getParticipants().size() + "/" + activity.getQuota(),
                    activity.getStatus().equalsIgnoreCase("public")
            );
            activityCard.getPlayersButton().addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("players/" + activity.getActivityId())));
            activityCard.getJoinButton().addClickListener(e -> {
                UserDB user = userService.getCurrentUser();
                requestService.joinActivity(user.getUser_id(), activity.getCreator().getUser_id(), activity.getActivityId());
                Notification.show("Your Join request has been sent.");
                String message = user.getFirst_name() + " wants to join your activity.";
                notificationService.createNotification(
                        activity.getCreator().getUser_id(),
                        message,
                        "join request",
                        activity.getName(),
                        activity.getDate(),
                        activity.getTime(),
                        activity.getPlace()
                );
            });
            activityLayout.add(activityCard);
        }

        VerticalLayout mainContent = new VerticalLayout(headerLayout, buttonLayout, activityLayout);
        mainContent.setWidthFull();
        mainContent.setAlignItems(Alignment.CENTER);
        mainContent.setHeightFull();
        mainContent.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        mainContent.getStyle().set("overflow", "auto");

        add(iconBar, mainContent);
        setAlignItems(Alignment.STRETCH);
        setSizeFull();
    }
}
