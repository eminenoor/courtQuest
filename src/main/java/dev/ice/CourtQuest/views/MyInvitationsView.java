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
import dev.ice.CourtQuest.components.MyInvitationsCard;
import dev.ice.CourtQuest.entities.Activity;
import dev.ice.CourtQuest.entities.Invitation;
import dev.ice.CourtQuest.entities.UserDB;
import dev.ice.CourtQuest.services.ActivityService;
import dev.ice.CourtQuest.services.InvitationService;
import dev.ice.CourtQuest.services.UserService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Route("my-invitations")
@PermitAll
public class MyInvitationsView extends HorizontalLayout {
    Div invitationContainer;
    private UserService userService;
    private ActivityService activityService;
    private InvitationService invitationService;

    @Autowired
    public MyInvitationsView(UserService userService, ActivityService activityService, InvitationService invitationService) {
        this.userService = userService;
        this.activityService = activityService;
        this.invitationService = invitationService;

        H1 currentInvitationsTitle = new H1("My Invitations");

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

        HorizontalLayout headerLayout = new HorizontalLayout(currentInvitationsTitle, topRightIcons);
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

        invitationContainer = new Div();
        invitationContainer.getStyle().set("display", "grid");
        invitationContainer.getStyle().set("grid-template-columns", "repeat(5, 1fr)");
        invitationContainer.getStyle().set("gap", "16px");
        displayInvitations(invitationContainer);
        VerticalLayout mainContent = new VerticalLayout(headerLayout, invitationContainer); // Add the invitation card here
        mainContent.setWidthFull();
        mainContent.setAlignItems(Alignment.START);
        mainContent.setHeightFull();
        mainContent.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        mainContent.getStyle().set("overflow", "auto");

        add(iconBar, mainContent);
        setAlignItems(Alignment.STRETCH);
        setSizeFull();


    }

    private void displayInvitations(Div invitationContainer) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDB currentUser = userService.findUserByEmail(username);
        if (currentUser != null) {
            List<Invitation> invitations = invitationService.getUserInvitations(currentUser.getUser_id());
            for (Invitation invitation : invitations) {
                Activity activity = activityService.getActivity(invitation.getActivity().getActivityId());
                MyInvitationsCard invitationCard = new MyInvitationsCard(
                        invitation.getSender().getFirst_name(),
                        activity.getName(),
                        activity.getPlace(),
                        activity.getDate(),
                        (activity.getTime() + " - " + (Integer.parseInt(activity.getTime().substring(0, 2)) + 1) + ".00"),
                        activity.getParticipants().size() + "/" + activity.getQuota(),
                        activity.getStatus().equalsIgnoreCase("public")
                );

                invitationCard.getAcceptButton().addClickListener(event -> {
                    invitationService.respondToInvitation(invitation.getInvitationId(), "Accepted");
                    Notification.show("Invitation accepted");
                    invitationContainer.remove(invitationCard);
                });

                invitationCard.getDeclineButton().addClickListener(event -> {
                    invitationService.respondToInvitation(invitation.getInvitationId(), "Declined");
                    Notification.show("Invitation declined");
                    invitationContainer.remove(invitationCard);
                });

                invitationContainer.add(invitationCard);
            }
        } else {
            Notification.show("Unable to load invitations. Please try again.");
        }
    }
}
