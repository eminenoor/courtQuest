package dev.ice.CourtQuest.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class NotificationsCard extends VerticalLayout {

    public NotificationsCard(String sender, String message, String sportName, String date, String time, String place) {
        // Container for the spans
        Div container = new Div();
        container.getStyle().set("background-color", "#F2F2F2");
        container.getStyle().set("border-radius", "10px");
        container.getStyle().set("padding", "10px");
        container.getStyle().set("width", "75%");
        container.getStyle().set("box-shadow", "0px 2px 5px rgba(0,0,0,0.1)");

        // Sender and message segment
        Span senderSpan = new Span(sender + " " + message);
        senderSpan.getStyle().set("background-color", "#1E3A8A");
        senderSpan.getStyle().set("color", "white");
        senderSpan.getStyle().set("padding", "10px 20px");
        senderSpan.getStyle().set("border-radius", "10px");
        senderSpan.getStyle().set("display", "block");
        senderSpan.getStyle().set("margin-bottom", "10px");

        // Summary segment
        Span summarySpan = new Span(String.format("%s / %s / %s / %s", sportName, date, time, place));
        summarySpan.getStyle().set("background-color", "#D3D3D3");
        summarySpan.getStyle().set("padding", "10px 20px");
        summarySpan.getStyle().set("border-radius", "10px");
        summarySpan.getStyle().set("display", "block");

        // Add spans to container
        container.add(senderSpan, summarySpan);

        // Add container to the card
        add(container);
        getStyle().set("margin-bottom", "20px"); // Space between cards
    }
}
