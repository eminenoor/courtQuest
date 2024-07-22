package dev.ice.CourtQuest.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route("edit-profile")
@PermitAll
public class EditProfileView extends VerticalLayout {

    public EditProfileView() {
        add(new H1("Edit Profile"));
        // Add your edit profile components here
    }
}
