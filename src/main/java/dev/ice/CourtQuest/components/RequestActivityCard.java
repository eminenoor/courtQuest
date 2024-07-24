package dev.ice.CourtQuest.components;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class RequestActivityCard extends GeneralActivityCard {

    public RequestActivityCard(String sportName, String place, String date, String time, String quota, boolean isPublic) {
        super(sportName, isPublic);
        setWidth("30px");
        setHeight("auto");

        addDetails(place, date, time, quota);
        contentLayout = new VerticalLayout(detailsLayout);
        finalizeLayout();
    }
}
