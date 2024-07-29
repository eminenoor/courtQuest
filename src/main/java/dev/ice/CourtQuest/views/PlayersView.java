package dev.ice.CourtQuest.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import dev.ice.CourtQuest.components.PlayerCard;
import dev.ice.CourtQuest.components.PlayerCardInvite;
import jakarta.annotation.security.PermitAll;

@Route("players")
@PermitAll
public class PlayersView extends HorizontalLayout{

    PlayerCard player1 = new PlayerCard("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQIF0ePQThnXeyYbDWWcFFchDy4Oq2mW4m4OA&s", "DERBEDERBERK", "HAYAT", "M", 29, 5, 5);
    PlayerCard player = new PlayerCard("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSeujnl7lsLBPalSsz1LLXMY2hwKeNh_Lg_5w&s", "Metin Çalışkan", "CS", "M", 20, 5, 0.5);
    PlayerCard player2 = new PlayerCard("İlke", "İlke Latifoğlu", "CS", "F", 20, 4, 4.5);
    PlayerCard player3 = new PlayerCard("Emine", "Emine Noor", "CS", "F", 20, 3.5, 4);
    PlayerCard player4 = new PlayerCard("Elif", "Elif Lara", "CS", "F", 20, 2.5, 5);
    PlayerCard player5 = new PlayerCard("Murathan", "Murathan Işık", "CS", "M", 22, 5, 1);
    PlayerCard player6 = new PlayerCard("Can", "Can Akpınar", "CS", "M", 22, 3.5, 4);
    PlayerCard player7 = new PlayerCard("Ekin", "Ekin Köylü", "CS", "F", 20, 5, 3);
    PlayerCard[] playerList = {player1, player, player2, player3, player4, player5, player6};
    Div playerContainer;

    public PlayersView(){
        H1 profileTitle = new H1("Players");

        // Log out link
        RouterLink logoutLink = new RouterLink("Log out", LogoutView.class);
        logoutLink.getStyle().set("margin-right", "auto");

        // Top right icons
        Icon bellIcon = new Icon(VaadinIcon.BELL);
        bellIcon.getStyle().set("cursor", "pointer");
        bellIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("notifications")));

        Icon profileIcon = new Icon(VaadinIcon.USER);
        profileIcon.getStyle().set("cursor", "pointer");
        profileIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("profile")));

        HorizontalLayout topRightIcons = new HorizontalLayout(logoutLink, bellIcon, profileIcon);
        topRightIcons.getStyle().set("margin-left", "auto");

        // Header with top right icons
        HorizontalLayout headerLayout = new HorizontalLayout(profileTitle, topRightIcons);
        headerLayout.setWidthFull();
        headerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        headerLayout.getStyle().set("padding", "10px");

        // Navigation bar on the left
        VerticalLayout iconBar = new VerticalLayout();
        iconBar.setWidth("50px");
        iconBar.getStyle().set("background-color", "#1E3A8A");
        iconBar.getStyle().set("height", "100vh");

        Icon groupIcon = new Icon(VaadinIcon.GROUP);
        groupIcon.getStyle().set("cursor", "pointer");
        groupIcon.getStyle().set("color", "white");
        groupIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("")));

        Icon calendarIcon = new Icon(VaadinIcon.CALENDAR);
        calendarIcon.getStyle().set("cursor", "pointer");
        calendarIcon.getStyle().set("color", "white");
        calendarIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("my-activities")));

        Icon envelopeIcon = new Icon(VaadinIcon.ENVELOPE);
        envelopeIcon.getStyle().set("cursor", "pointer");
        envelopeIcon.getStyle().set("color", "white");
        envelopeIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("my-invitations")));

        Icon checkIcon = new Icon(VaadinIcon.CHECK);
        checkIcon.getStyle().set("cursor", "pointer");
        checkIcon.getStyle().set("color", "white");
        checkIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("requests")));

        Icon plusIcon = new Icon(VaadinIcon.PLUS);
        plusIcon.getStyle().set("cursor", "pointer");
        plusIcon.getStyle().set("color", "white");
        plusIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("make-reservation")));

        Icon starIcon = new Icon(VaadinIcon.STAR);
        starIcon.getStyle().set("cursor", "pointer");
        starIcon.getStyle().set("color", "white");
        starIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("rate-players")));

        iconBar.add(groupIcon, calendarIcon, envelopeIcon, checkIcon, plusIcon, starIcon);

        playerContainer = new Div();
        playerContainer.getStyle().set("display", "grid");
        playerContainer.getStyle().set("grid-template-columns", "repeat(5, 1fr)");
        playerContainer.getStyle().set("gap", "16px");

        playerContainer.add(playerList);

        VerticalLayout mainContent = new VerticalLayout(headerLayout, playerContainer);
        headerLayout.getStyle().set("margin-bottom", "20px");
        mainContent.setWidthFull();
        mainContent.setAlignItems(Alignment.CENTER);
        mainContent.setSpacing(true);
        mainContent.setPadding(false);
        mainContent.setHeightFull();
        mainContent.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        mainContent.getStyle().set("overflow", "auto");

        // Add components to the root layout
        add(iconBar, mainContent);
        setAlignItems(Alignment.STRETCH);
        setSizeFull();
    }
}
