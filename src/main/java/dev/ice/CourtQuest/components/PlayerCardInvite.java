package dev.ice.CourtQuest.components;

import com.vaadin.flow.component.button.Button;

public class PlayerCardInvite extends PlayerCard{
    Button inviteButton;

    public PlayerCardInvite(String avatarUrl, String name, String department, String gender, int age, double selfRating, double generalRating) {
        super(avatarUrl, name, department, gender, age, selfRating, generalRating);

        inviteButton = new Button("Invite");
        //inviteButton.getStyle().setFontSize("15px");
        inviteButton.getStyle().setWidth("auto");
        inviteButton.getStyle().setHeight("35px");
        inviteButton.getStyle().setBackgroundColor("lightgreen");
        inviteButton.getElement().getStyle().set("color", "white");
        inviteButton.getStyle().set("margin-top", "15px");
        //inviteButton.getStyle().set("box-shadow", "2px 2px 5px rgba(0, 0, 0, 0.5)");
        inviteButton.setDisableOnClick(true);

        inviteButton.addClickListener(event -> {
            String currentColor = inviteButton.getStyle().get("background-color");
            if ("lightgreen".equals(currentColor)) {
                inviteButton.getStyle().set("background-color", "lightcoral");
                inviteButton.setText("Sent!");
            } else {
                inviteButton.getStyle().set("background-color", "lightgreen");
            }
        });
        setWidth("auto");
        setHeight("auto");
        add(inviteButton);
    }
}
