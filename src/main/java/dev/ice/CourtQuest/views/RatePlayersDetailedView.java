package dev.ice.CourtQuest.views;

import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import dev.ice.CourtQuest.components.RatePlayersDetailedCard;
import dev.ice.CourtQuest.entities.Activity;
import dev.ice.CourtQuest.entities.UserDB;
import dev.ice.CourtQuest.services.ActivityService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@Route("rate-players-detailed/:activityId")
@PermitAll
public class RatePlayersDetailedView extends VerticalLayout implements BeforeEnterObserver {

    private final ActivityService activityService;
    private Activity activity;

    @Autowired
    public RatePlayersDetailedView(ActivityService activityService) {
        this.activityService = activityService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String activityIdParam = event.getRouteParameters().get("activityId").orElse(null);
        if (activityIdParam != null) {
            Long activityId = Long.parseLong(activityIdParam);
            this.activity = activityService.getActivity(activityId);
            buildView();
        }
    }

    private void buildView() {
        H1 header = new H1("Rate Players");
        header.getStyle().set("margin-bottom", "20px");

        Icon closeIcon = new Icon(VaadinIcon.CLOSE);
        closeIcon.getStyle().set("cursor", "pointer");
        closeIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("rate-players")));
        HorizontalLayout headerLayout = new HorizontalLayout(header, closeIcon);
        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);

        RatePlayersDetailedCard label = new RatePlayersDetailedCard(activity.getName(), activity.getDate(), activity.getTime(), activity.getPlace());

        FlexLayout playersLayout = new FlexLayout();
        playersLayout.getStyle().set("flex-wrap", "wrap");
        playersLayout.setWidthFull();
        playersLayout.getStyle().set("overflow-y", "auto");
        playersLayout.setHeight("60vh"); // Set height to ensure scroll bar appears if needed

        Set<UserDB> participants = activity.getParticipants();

        for (UserDB participant : participants) {

            VerticalLayout playerLayout = createPlayerLayout(participant.getFirst_name() + " " + participant.getLast_name());
            playersLayout.add(playerLayout);
        }

        Button sendButton = new Button("Send");
        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        sendButton.getStyle().set("background-color", "black");
        sendButton.getStyle().set("color", "white");
        sendButton.addClickListener(e -> {
            getUI().ifPresent(ui -> ui.navigate("rate-players"));
        });

        add(headerLayout, label, playersLayout, sendButton);
        setHorizontalComponentAlignment(Alignment.END, sendButton);
        setSizeFull();
        getStyle().set("padding", "20px");
    }

    private VerticalLayout createPlayerLayout(String playerName) {
        Image profileImage = new Image("https://via.placeholder.com/50", "Profile Image");
        profileImage.getStyle().set("border-radius", "50%");

        Span playerNameLabel = new Span(playerName);
        playerNameLabel.getStyle().set("background-color", "#1E3A8A");
        playerNameLabel.getStyle().set("color", "white");
        playerNameLabel.getStyle().set("padding", "5px 10px");
        playerNameLabel.getStyle().set("border-radius", "5px");

        RadioButtonGroup<Integer> ratingGroup = new RadioButtonGroup<>();
        ratingGroup.setItems(1, 2, 3, 4, 5);
        ratingGroup.setLabel("Rate");

        VerticalLayout playerLayout = new VerticalLayout(profileImage, playerNameLabel, ratingGroup);
        playerLayout.setSpacing(false);
        playerLayout.setPadding(false);
        playerLayout.getStyle().set("border", "1px solid #D3D3D3");
        playerLayout.getStyle().set("border-radius", "10px");
        playerLayout.getStyle().set("padding", "10px");
        playerLayout.setAlignItems(Alignment.CENTER);
        playerLayout.setWidth("200px");
        playerLayout.setHeight("200px");
        playerLayout.getStyle().set("margin", "10px");

        return playerLayout;
    }
}
