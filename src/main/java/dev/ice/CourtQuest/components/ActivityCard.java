package dev.ice.CourtQuest.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ActivityCard extends GeneralActivityCard {

    private Button playersButton;
    private Button joinButton;
    private HorizontalLayout quotaJoinLayout;

    public ActivityCard(String sportName, String place, String date, String time, String quota, boolean isPublic) {
        super(sportName, isPublic);

        playersButton = new Button("Players");
        playersButton.getStyle().set("background-color", "#1E3A8A");
        playersButton.getStyle().set("color", "white");
        playersButton.getStyle().set("margin-left", "10px");
        playersButton.getStyle().set("height", "36px");

        topRightLayout = new HorizontalLayout(publicLabel, playersButton);
        topRightLayout.setAlignItems(Alignment.CENTER);

        topLayout = new HorizontalLayout(sportLabel, topRightLayout);
        topLayout.setWidthFull();
        topLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        topLayout.setAlignItems(Alignment.CENTER);

        addDetails(place, date, time, quota);

        joinButton = new Button("Join");
        joinButton.getStyle().set("background-color", "#1E3A8A");
        joinButton.getStyle().set("color", "white");

        quotaJoinLayout = new HorizontalLayout(quotaLayout, joinButton);
        quotaJoinLayout.setWidthFull();
        quotaJoinLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        quotaJoinLayout.setAlignItems(Alignment.CENTER);

        detailsLayout.add(quotaJoinLayout);
        contentLayout = new VerticalLayout(topLayout, detailsLayout);
        finalizeLayout();
    }

    public Button getJoinButton() {
        return joinButton;
    }

}
