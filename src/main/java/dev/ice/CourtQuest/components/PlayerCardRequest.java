package dev.ice.CourtQuest.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class PlayerCardRequest extends PlayerCard{

    Button acceptButton;
    Button declineButton;

    public PlayerCardRequest(Long userId, String name, String department, String gender, int age, double selfRating, double generalRating) {
        super(userId, name, department, gender, age, selfRating, generalRating);
        //super(avatarURL, name, department, gender, age, selfRating, generalRating);


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

        acceptButton.getElement().addEventListener("mouseover", e -> {
            acceptButton.getElement().getStyle().set("cursor", "pointer");
        });

        declineButton.getElement().addEventListener("mouseover", e -> {
            declineButton.getElement().getStyle().set("cursor", "pointer");
        });

        HorizontalLayout buttonsLayout = new HorizontalLayout(acceptButton, declineButton);
        buttonsLayout.setAlignItems(HorizontalLayout.Alignment.CENTER);
        buttonsLayout.getStyle().set("margin-top", "10px");

        setWidth("auto");
        setHeight("370px");

        add(buttonsLayout);

    }
    public Button getAcceptButton(){
        return acceptButton;
    }

    public Button getDeclineButton(){
        return declineButton;
    }
}
