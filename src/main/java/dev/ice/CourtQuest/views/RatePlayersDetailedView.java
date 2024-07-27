package dev.ice.CourtQuest.views;

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
import com.vaadin.flow.router.Route;
import dev.ice.CourtQuest.components.RatePlayersDetailedCard;
import jakarta.annotation.security.PermitAll;

@Route("rate-players-detailed")
@PermitAll
public class RatePlayersDetailedView extends VerticalLayout {

    public RatePlayersDetailedView() {
        // Header
        H1 header = new H1("Rate Players");
        header.getStyle().set("margin-bottom", "20px");

        // Close icon
        Icon closeIcon = new Icon(VaadinIcon.CLOSE);
        closeIcon.getStyle().set("cursor", "pointer");
        closeIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("rate-players")));
        HorizontalLayout headerLayout = new HorizontalLayout(header, closeIcon);
        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);

        // Activity details
        RatePlayersDetailedCard label = new RatePlayersDetailedCard("Volleyball", "12.07.2024", "15.00 - 17.00", "Dormitory Sports Hall");

        // Players list
        FlexLayout playersLayout = new FlexLayout();
        playersLayout.getStyle().set("flex-wrap", "wrap");
        playersLayout.setWidthFull();
        playersLayout.getStyle().set("overflow-y", "auto");
        playersLayout.setHeight("60vh"); // Set height to ensure scroll bar appears if needed

        // Sample players
        String[] players = {"Can Akpinar", "Emine Noor", "Elif Lara Oğuzhan", "İlke Latifoğlu"};
        for (String player : players) {
            VerticalLayout playerLayout = createPlayerLayout(player);
            playersLayout.add(playerLayout);
        }

        // Send button
        Button sendButton = new Button("Send");
        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        sendButton.getStyle().set("background-color", "black");
        sendButton.getStyle().set("color", "white");
        sendButton.addClickListener(e -> {
            // Navigate back to RatePlayersView with the rated activity removed
            getUI().ifPresent(ui -> ui.navigate("rate-players"));
        });

        // Layout adjustments
        add(headerLayout, label, playersLayout, sendButton);
        setHorizontalComponentAlignment(Alignment.END, sendButton);
        setSizeFull();
        getStyle().set("padding", "20px");
    }

    private VerticalLayout createPlayerLayout(String playerName) {
        // Profile image placeholder
        Image profileImage = new Image("https://via.placeholder.com/50", "Profile Image");
        profileImage.getStyle().set("border-radius", "50%");

        // Player name
        Span playerNameLabel = new Span(playerName);
        playerNameLabel.getStyle().set("background-color", "#1E3A8A");
        playerNameLabel.getStyle().set("color", "white");
        playerNameLabel.getStyle().set("padding", "5px 10px");
        playerNameLabel.getStyle().set("border-radius", "5px");

        // Rating options
        RadioButtonGroup<Integer> ratingGroup = new RadioButtonGroup<>();
        ratingGroup.setItems(1, 2, 3, 4, 5);
        ratingGroup.setLabel("Rate");

        // Player layout
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
