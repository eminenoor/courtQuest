package dev.ice.CourtQuest.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class PlayerCardRequest extends PlayerCard{

    Button acceptButton;
    Button declineButton;

    public PlayerCardRequest(String avatarUrl, String name, String department, String gender, int age, double selfRating, double generalRating) {
        super(avatarUrl, name, department, gender, age, selfRating, generalRating);

        acceptButton = new Button("Accept");
        declineButton = new Button("Decline");

        acceptButton.getStyle().setBackgroundColor("green");
        declineButton.getStyle().setBackgroundColor("red");

        acceptButton.getStyle().setFontSize("15px");
        acceptButton.getStyle().setWidth("auto");
        acceptButton.getStyle().setHeight("25px");
        declineButton.getStyle().setWidth("auto");
        declineButton.getStyle().setHeight("25px");
        acceptButton.getElement().getStyle().set("color", "white");
        declineButton.getElement().getStyle().set("color", "white");
        declineButton.getStyle().setFontSize("15px");

        HorizontalLayout buttonsLayout = new HorizontalLayout(acceptButton, declineButton);
        buttonsLayout.setAlignItems(HorizontalLayout.Alignment.CENTER);
        buttonsLayout.getStyle().set("margin-top", "10px");

        setWidth("auto");
        setHeight("350px");

        add(buttonsLayout);

    }
}
