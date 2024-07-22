package dev.ice.CourtQuest.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route ("mailVerify")
@AnonymousAllowed
public class EmailVerification extends VerticalLayout {

    public EmailVerification() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);


    }
}
