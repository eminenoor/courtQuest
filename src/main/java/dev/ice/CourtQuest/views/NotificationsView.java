package dev.ice.CourtQuest.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import dev.ice.CourtQuest.components.NotificationsCard;
import dev.ice.CourtQuest.controllers.NotificationController;
import dev.ice.CourtQuest.entities.Notification;
import dev.ice.CourtQuest.entities.UserDB;
import dev.ice.CourtQuest.services.NotificationService;
import dev.ice.CourtQuest.services.UserService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Route("notifications")
@PermitAll
public class NotificationsView extends HorizontalLayout {

    private NotificationController notificationController;
    private UserService userService;
    private Long currentUserId;

    @Autowired
    public NotificationsView(NotificationController notificationController, UserService userService) {
        this.notificationController = notificationController;
        this.userService = userService;

        H1 currentActivitiesTitle = new H1("Notifications");

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

        VerticalLayout mainContent = new VerticalLayout(headerLayout);
        mainContent.setWidthFull();
        mainContent.setAlignItems(Alignment.CENTER);
        mainContent.setHeightFull();
        mainContent.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        mainContent.getStyle().set("overflow", "auto");

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDB currentUser = userService.findUserByEmail(username);
        currentUserId = currentUser.getUser_id();

        List<Notification> notifications = notificationController.getUserNotifications(currentUserId);
        if (notifications != null) {
            for (Notification notification : notifications) {
                mainContent.add(new NotificationsCard(notification.getUser().getFirst_name(),
                        notification.getMessage(), notification.getActivityName(), notification.getActivityDate(), notification.getActivityTime(), notification.getActivityPlace()));
            }
        }

        add(iconBar, mainContent);
        setAlignItems(Alignment.STRETCH);
        setSizeFull();
    }
}
