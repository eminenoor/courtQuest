package dev.ice.CourtQuest.components;

import com.vaadin.flow.component.ClickNotifier;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class MyInvitationsCard extends GeneralActivityCard {

    private Button playersButton;
    private Button acceptButton;
    private Button declineButton;
    private HorizontalLayout inviterLayout;
    private Span inviterSpan;
    private VerticalLayout buttonsLayout;

    public MyInvitationsCard(String inviter, String sportName, String place, String date, String time, String quota, boolean isPublic) {
        super(sportName, isPublic);
        setWidth("350px");
        setHeight("auto");

        inviterSpan = new Span(inviter + " has invited you to a game");
        inviterSpan.getStyle().set("background-color", "#1E3A8A");
        inviterSpan.getStyle().set("color", "white");
        inviterSpan.getStyle().set("padding", "10px 20px");
        inviterSpan.getStyle().set("border-radius", "10px");

        inviterLayout = new HorizontalLayout(inviterSpan);
        inviterLayout.setWidthFull();
        inviterLayout.setJustifyContentMode(JustifyContentMode.START);

        addDetails(place, date, time, quota);

        playersButton = new Button("Players");
        playersButton.getStyle().set("background-color", "#1E3A8A");
        playersButton.getStyle().set("color", "white");

        acceptButton = new Button("Accept");
        acceptButton.getStyle().set("background-color", "green");
        acceptButton.getStyle().set("color", "white");

        declineButton = new Button("Decline");
        declineButton.getStyle().set("background-color", "red");
        declineButton.getStyle().set("color", "white");

        HorizontalLayout acceptDeclineLayout = new HorizontalLayout(acceptButton, declineButton);
        acceptDeclineLayout.setAlignItems(Alignment.CENTER);
        acceptDeclineLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        acceptDeclineLayout.setWidthFull();

        // Create a vertical layout for the buttons
        buttonsLayout = new VerticalLayout(playersButton, acceptDeclineLayout);
        buttonsLayout.setAlignItems(Alignment.START);
        buttonsLayout.setWidthFull();

        detailsLayout.add(buttonsLayout);
        contentLayout = new VerticalLayout(inviterLayout, detailsLayout);
        finalizeLayout();
    }

    public Button getAcceptButton() {
        return acceptButton;
    }

    public Button getDeclineButton() {
        return declineButton;
    }
}
