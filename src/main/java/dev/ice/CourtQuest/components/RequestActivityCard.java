package dev.ice.CourtQuest.components;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class RequestActivityCard extends GeneralActivityCard {

    Span sportLabel;
    Span sportSpan;

    public RequestActivityCard(String sportName, String place, String date, String time, String quota, boolean isPublic) {
        super(sportName, isPublic);
        H3 sportTitle = new H3(sportName);
        add(sportTitle);
        sportTitle.getStyle().set("margin-bottom", "10px");
        addDetails(place, date, time, quota);
        contentLayout = new VerticalLayout(detailsLayout);
        finalizeLayout();
        setWidth("100%");
        setHeight("120%");
        setSpacing(false);
        setPadding(false);
    }
}
