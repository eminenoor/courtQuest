package dev.ice.CourtQuest.components;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class RatePlayersDetailedCard extends HorizontalLayout {

    public RatePlayersDetailedCard(String sportName, String date, String time, String place) {
        String activityDetailsText = String.format("%s / %s / %s / %s", sportName, date, time, place);

        Span activityDetails = new Span(activityDetailsText);
        activityDetails.getStyle().set("background-color", "#D3D3D3");
        activityDetails.getStyle().set("padding", "10px 20px");
        activityDetails.getStyle().set("border-radius", "10px");
        activityDetails.getStyle().set("margin-bottom", "20px");

        add(activityDetails);
    }
}
