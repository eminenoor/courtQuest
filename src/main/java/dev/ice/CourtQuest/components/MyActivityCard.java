package dev.ice.CourtQuest.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class MyActivityCard extends GeneralActivityCard{

    private Button playersButton;
    private Button inviteButton;
    private Button cancelButton;
    private HorizontalLayout quotaInviteLayout;
    private Long activityId;

    public MyActivityCard (String sportName, String place, String date, String time, String quota, boolean isPublic) {
        super(sportName, isPublic);
        this.activityId = activityId;

        cancelButton = new Button("Cancel Reservation");
        cancelButton.getStyle().set("background-color", "#1E3A8A");
        cancelButton.getStyle().set("color", "white");
        cancelButton.getStyle().set("margin-left", "10px");
        cancelButton.getStyle().set("height", "36px");
        cancelButton.getElement().addEventListener("mouseover", e -> {
            cancelButton.getElement().getStyle().set("cursor", "pointer");
        });

        topRightLayout = new HorizontalLayout(publicLabel, cancelButton);
        topRightLayout.setAlignItems(Alignment.CENTER);

        topLayout = new HorizontalLayout(sportLabel, topRightLayout);
        topLayout.setWidthFull();
        topLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        topLayout.setAlignItems(Alignment.CENTER);

        addDetails(place, date, time, quota);

        playersButton = new Button("Players");
        playersButton.getStyle().set("background-color", "#1E3A8A");
        playersButton.getStyle().set("color", "white");
        playersButton.getElement().addEventListener("mouseover", e -> {
            playersButton.getElement().getStyle().set("cursor", "pointer");
        });
        playersButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("players")));


        inviteButton = new Button("Invite");
        inviteButton.getStyle().set("background-color", "#1E3A8A");
        inviteButton.getStyle().set("color", "white");

        inviteButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("invite-players/" + activityId)));
        inviteButton.getElement().addEventListener("mouseover", e -> {
            inviteButton.getElement().getStyle().set("cursor", "pointer");
        });


        topRightLayout = new HorizontalLayout(playersButton, inviteButton);
        topRightLayout.setAlignItems(Alignment.CENTER);

        quotaInviteLayout = new HorizontalLayout(quotaLayout, topRightLayout);
        quotaInviteLayout.setWidthFull();
        quotaInviteLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        quotaInviteLayout.setAlignItems(Alignment.CENTER);

        detailsLayout.add(quotaInviteLayout);
        contentLayout = new VerticalLayout(topLayout, detailsLayout);
        finalizeLayout();
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public Button getInviteButton() {
        return inviteButton;
    }
}
