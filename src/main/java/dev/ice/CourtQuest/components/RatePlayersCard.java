package dev.ice.CourtQuest.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

public class RatePlayersCard extends HorizontalLayout {

    private Span activityInfo;
    private Button rateButton;

    public RatePlayersCard(String sportLabel, String time, String date, String court, Long activityId) {
        setWidthFull();
        getStyle().set("background-color", "#f2f2f2");
        getStyle().set("border-radius", "10px");
        getStyle().set("padding", "10px");
        getStyle().set("margin", "10px 0");

        activityInfo = new Span("Your " + sportLabel + " activity at " + time + " has ended. Date: " + date + " / Place: " + court);
        activityInfo.getStyle().set("color", "black");

        rateButton = new Button("RATE");
        rateButton.getStyle().set("background-color", "black");
        rateButton.getStyle().set("color", "white");
        rateButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("rate-players-detailed/" + activityId)));
        rateButton.getElement().addEventListener("mouseover", e -> {
            rateButton.getElement().getStyle().set("cursor", "pointer");
        });

        HorizontalLayout contentLayout = new HorizontalLayout(activityInfo, rateButton);
        contentLayout.setWidthFull();
        contentLayout.setAlignItems(Alignment.CENTER);
        contentLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);

        add(contentLayout);
    }
    public Button getRateButton() {
        return rateButton;
    }
}
