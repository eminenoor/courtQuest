package dev.ice.CourtQuest.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class MyInvitationsCard extends GeneralActivityCard {

    private Button playersButton;
    private Button acceptButton;
    private Button declineButton;
    private Button inviterButton;
    private HorizontalLayout inviterLayout;
    private Span inviterSpan;
    private VerticalLayout buttonsLayout;

    public MyInvitationsCard(String inviter, String sportName, String place, String date, String time, String quota, boolean isPublic) {
        super(sportName, isPublic);

        inviterSpan = new Span(inviter + " has invited you to a game");

        inviterButton = new Button(inviterSpan);
        inviterButton.getElement().getStyle().set("color", "white");
        inviterButton.getElement().getStyle().set("padding", "10px 20px");
        inviterButton.getElement().getStyle().set("border-radius", "10px");
        inviterButton.getElement().getStyle().set("background-color", "#1E3A8A");
        inviterButton.getElement().addEventListener("mouseover", e -> {
            inviterButton.getElement().getStyle().set("cursor", "pointer");
        });
        inviterButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("profile")));

        inviterLayout = new HorizontalLayout(inviterButton);
        inviterLayout.setWidthFull();
        inviterLayout.setJustifyContentMode(JustifyContentMode.START);

        addDetails(place, date, time, quota);

        playersButton = new Button("Players");
        playersButton.getStyle().set("background-color", "#1E3A8A");
        playersButton.getStyle().set("color", "white");
        playersButton.getElement().addEventListener("mouseover", e -> {
            playersButton.getElement().getStyle().set("cursor", "pointer");
        });
        playersButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("players")));

        acceptButton = new Button("Accept");
        acceptButton.getStyle().set("background-color", "green");
        acceptButton.getStyle().set("color", "white");
        acceptButton.getElement().addEventListener("mouseover", e -> {
            acceptButton.getElement().getStyle().set("cursor", "pointer");
        });

        declineButton = new Button("Decline");
        declineButton.getStyle().set("background-color", "red");
        declineButton.getStyle().set("color", "white");
        declineButton.getElement().addEventListener("mouseover", e -> {
            declineButton.getElement().getStyle().set("cursor", "pointer");
        });

        // Create a horizontal layout for the accept and decline buttons and center them
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
        setWidth("350px");
        setHeight("90%");
    }
}
