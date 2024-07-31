package dev.ice.CourtQuest.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class GeneralActivityCard extends VerticalLayout {

    protected HorizontalLayout quotaLayout;
    protected HorizontalLayout topRightLayout;
    protected Span publicLabel;
    protected HorizontalLayout topLayout;
    protected Span sportLabel;
    protected Span sportValue;
    protected HorizontalLayout sportLayout;
    protected HorizontalLayout placeLayout;
    protected HorizontalLayout dateLayout;
    protected HorizontalLayout timeLayout;
    protected VerticalLayout detailsLayout;
    protected Span placeLabel;
    protected Span placeValue;
    protected Span dateLabel;
    protected Span dateValue;
    protected Span timeLabel;
    protected Span timeValue;
    protected Span quotaLabel;
    protected Span quotaValue;
    protected VerticalLayout contentLayout;
    protected Button playersButton;

    public GeneralActivityCard() {
    }

    public GeneralActivityCard(String sportName, boolean isPublic) {
        addClassName("activity-card");
        getStyle().set("background-color", "#f2f2f2");
        getStyle().set("border-radius", "10px");
        getStyle().set("padding", "15px");
        getStyle().set("margin", "10px 0");
        getStyle().set("width", "75%");
        getStyle().set("height", "auto");

        sportLabel = new Span(sportName);
        sportLabel.getStyle().set("font-weight", "bold");
        sportLabel.getStyle().set("font-size", "24px");

        publicLabel = new Span(isPublic ? "Public" : "Private");
        publicLabel.getStyle().set("background-color", isPublic ? "green" : "red");
        publicLabel.getStyle().set("color", "white");
        publicLabel.getStyle().set("padding", "5px 10px");
        publicLabel.getStyle().set("border-radius", "5px");
        publicLabel.getStyle().set("height", "28px");
    }

    public void addDetails(String place, String date, String time, String quota) {
        placeLabel = new Span("Place: ");
        placeLabel.getStyle().set("font-weight", "bold");
        placeValue = new Span(place);

        dateLabel = new Span("Date: ");
        dateLabel.getStyle().set("font-weight", "bold");
        dateValue = new Span(date);

        timeLabel = new Span("Time: ");
        timeLabel.getStyle().set("font-weight", "bold");
        timeValue = new Span(time);

        quotaLabel = new Span("Quota: ");
        quotaLabel.getStyle().set("font-weight", "bold");
        quotaValue = new Span(quota);

        placeLayout = new HorizontalLayout(placeLabel, placeValue);
        dateLayout = new HorizontalLayout(dateLabel, dateValue);
        timeLayout = new HorizontalLayout(timeLabel, timeValue);
        quotaLayout = new HorizontalLayout(quotaLabel, quotaValue);

        placeLayout.getStyle().set("margin-bottom", "10px");
        dateLayout.getStyle().set("margin-bottom", "10px");
        timeLayout.getStyle().set("margin-bottom", "5px");

        detailsLayout = new VerticalLayout(placeLayout, dateLayout, timeLayout, quotaLayout);
        detailsLayout.setSpacing(false);
        detailsLayout.setPadding(false);
        detailsLayout.getStyle().set("gap", "5px"); // Ensure equal spacing
    }

    public void addDetails(String sport, String place, String date, String time, String quota) {
        Span sportNameLabel = new Span("Sport: ");
        sportNameLabel.getStyle().set("font-weight", "bold");
        sportValue = new Span(sport);

        placeLabel = new Span("Place: ");
        placeLabel.getStyle().set("font-weight", "bold");
        placeValue = new Span(place);

        dateLabel = new Span("Date: ");
        dateLabel.getStyle().set("font-weight", "bold");
        dateValue = new Span(date);

        timeLabel = new Span("Time: ");
        timeLabel.getStyle().set("font-weight", "bold");
        timeValue = new Span(time);

        quotaLabel = new Span("Quota: ");
        quotaLabel.getStyle().set("font-weight", "bold");
        quotaValue = new Span(quota);

        sportLayout = new HorizontalLayout(sportNameLabel, sportValue);
        placeLayout = new HorizontalLayout(placeLabel, placeValue);
        dateLayout = new HorizontalLayout(dateLabel, dateValue);
        timeLayout = new HorizontalLayout(timeLabel, timeValue);
        quotaLayout = new HorizontalLayout(quotaLabel, quotaValue);

        sportLayout.getStyle().set("margin-bottom", "10px");
        placeLayout.getStyle().set("margin-bottom", "10px");
        dateLayout.getStyle().set("margin-bottom", "10px");
        timeLayout.getStyle().set("margin-bottom", "5px");

        detailsLayout = new VerticalLayout(sportLayout, placeLayout, dateLayout, timeLayout, quotaLayout);
        detailsLayout.setSpacing(false);
        detailsLayout.setPadding(false);
        detailsLayout.getStyle().set("gap", "5px");
    }

    public void finalizeLayout() {
        contentLayout.setSpacing(false);
        contentLayout.setPadding(false);
        contentLayout.getStyle().set("gap", "10px");
        contentLayout.getStyle().set("margin-top", "0px");

        add(contentLayout);
    }

    public Button getPlayersButton() {return playersButton;}
}
