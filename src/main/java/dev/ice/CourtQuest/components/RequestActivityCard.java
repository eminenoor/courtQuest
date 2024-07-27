package dev.ice.CourtQuest.components;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class RequestActivityCard extends GeneralActivityCard {

    public RequestActivityCard(String sportName, String place, String date, String time, String quota, boolean isPublic) {
        super(sportName, isPublic);
        setWidth("auto");
        setHeight("auto");

        add(new H3(sportName));
        addDetails(place, date, time, quota);
        contentLayout = new VerticalLayout(detailsLayout);
        finalizeLayout();
    }
}
