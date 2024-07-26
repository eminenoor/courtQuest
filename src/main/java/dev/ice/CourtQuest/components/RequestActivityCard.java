package dev.ice.CourtQuest.components;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class RequestActivityCard extends GeneralActivityCard {

    Span sportLabel;
    Span sportSpan;

    public RequestActivityCard(String sportName, String place, String date, String time, String quota, boolean isPublic) {
        super(sportName, isPublic);
        setWidth(30);
        setHeight(25);

        addDetails(sportName, place, date, time, quota);
        contentLayout = new VerticalLayout(detailsLayout);
        finalizeLayout();
    }
}
