package dev.ice.CourtQuest.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import dev.ice.CourtQuest.components.PlayerCard;
import dev.ice.CourtQuest.entities.UserDB;
import dev.ice.CourtQuest.components.PlayerCardInvite;
import dev.ice.CourtQuest.components.PlayerCardRequest;
import dev.ice.CourtQuest.services.InvitationService;
import dev.ice.CourtQuest.services.UserService;
import jakarta.annotation.security.PermitAll;

import java.util.ArrayList;
import java.util.List;

@Route("invite-players")
@PermitAll
public class InvitePlayersView extends HorizontalLayout {

    /*
    PlayerCardInvite player1 = new PlayerCardInvite("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQIF0ePQThnXeyYbDWWcFFchDy4Oq2mW4m4OA&s", "DERBEDERBERK", "HAYAT", "M", 29, 5, 5);
    PlayerCardInvite player = new PlayerCardInvite("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSeujnl7lsLBPalSsz1LLXMY2hwKeNh_Lg_5w&s", "Metin Çalışkan", "CS", "M", 20, 5, 0.5);
    PlayerCardInvite player2 = new PlayerCardInvite("İlke", "İlke Latifoğlu", "CS", "F", 20, 4, 4.5);
    PlayerCardInvite player3 = new PlayerCardInvite("Emine", "Emine Noor", "CS", "F", 20, 3.5, 4);
    PlayerCardInvite player4 = new PlayerCardInvite("Elif", "Elif Lara", "CS", "F", 20, 2.5, 5);
    PlayerCardInvite player5 = new PlayerCardInvite("Murathan", "Murathan Işık", "CS", "M", 22, 5, 1);
    PlayerCardInvite player6 = new PlayerCardInvite("Can", "Can Akpınar", "CS", "M", 22, 3.5, 4);
    PlayerCardInvite player7 = new PlayerCardInvite("Ekin", "Ekin Köylü", "CS", "F", 20, 5, 3);
    PlayerCardInvite[] playerList = {player1, player, player2, player3, player4, player5, player6};

     */
    Div playerContainer = new Div();
    List<PlayerCardInvite> playerList = new ArrayList<>();

    InvitationService invitationService;
    UserService userService;

    public InvitePlayersView(InvitationService invitationService, UserService userService){
        this.invitationService = invitationService;
        this.userService = userService;

        List<UserDB> allUsers = userService.getAllUsers();
        for(UserDB user : allUsers){
            playerList.add(new PlayerCardInvite(user.getUser_id(),user.getFirst_name(),user.getFirst_name(),user.getDepartment(),user.getGender(),user.getAge(),user.getRating(),2));
        }

        H1 profileTitle = new H1("Invite Players");

        RouterLink logoutLink = new RouterLink("Log out", LogoutView.class);
        logoutLink.getStyle().set("margin-right", "auto");

        Icon bellIcon = new Icon(VaadinIcon.BELL);
        bellIcon.getStyle().set("cursor", "pointer");
        bellIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("notifications")));

        Icon profileIcon = new Icon(VaadinIcon.USER);
        profileIcon.getStyle().set("cursor", "pointer");
        profileIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("profile")));

        HorizontalLayout topRightIcons = new HorizontalLayout(logoutLink, bellIcon, profileIcon);
        topRightIcons.getStyle().set("margin-left", "auto");

        HorizontalLayout headerLayout = new HorizontalLayout(profileTitle, topRightIcons);
        headerLayout.setWidthFull();
        headerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        headerLayout.getStyle().set("padding", "10px");

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

        TextField searchField = new TextField();
        searchField.setPlaceholder("Search...");
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.getStyle().setWidth("300px");
        Icon searchIcon = VaadinIcon.SEARCH.create();
        searchIcon.getStyle().set("cursor", "pointer");
        searchField.addValueChangeListener(event -> search(String.valueOf(searchField.getValue())));
        Button searchButton = new Button(searchIcon);
        searchButton.addClickListener(event -> search(searchField.getValue()));
        HorizontalLayout searchLayout = new HorizontalLayout(searchField, searchButton);


        playerContainer = new Div();
        playerContainer.getStyle().set("display", "grid");
        playerContainer.getStyle().set("grid-template-columns", "repeat(5, 1fr)");
        playerContainer.getStyle().set("gap", "16px");

        UserDB user = userService.getCurrentUser();
        Long activityId = (Long) VaadinSession.getCurrent().getAttribute("activityId");

        for(PlayerCardInvite p: playerList){
            p.getInviteButton().addClickListener(e ->{
                System.out.println("activity id: " + user.getUser_id() + " " + p.getPlayerId() + " " + activityId);
                invitationService.sendInvitation(user.getUser_id(),p.getPlayerId(),activityId);
            });
            playerContainer.add(p);
        }


        VerticalLayout mainContent = new VerticalLayout(headerLayout, searchLayout, playerContainer);
        headerLayout.getStyle().set("margin-bottom", "20px");
        mainContent.setWidthFull();
        mainContent.setAlignItems(Alignment.CENTER);
        mainContent.setSpacing(true);
        mainContent.setPadding(false);
        mainContent.setHeightFull();
        mainContent.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        mainContent.getStyle().set("overflow", "auto");

        add(iconBar, mainContent);
        setAlignItems(Alignment.STRETCH);
        setSizeFull();
    }

    public void search(String name){
        playerContainer.removeAll();
        for (int i = 0; i < playerList.size(); i++) {
            PlayerCard checkCard = (PlayerCard) playerList.get(i);
            String check = (String) playerList.get(i).getName().toLowerCase();
            String search = name.toLowerCase();
            if(check.startsWith(search)){
                playerContainer.add(playerList.get(i));
            }
        }

        if(name.isEmpty()){
            displayPlayers();
        }
    }

    public void displayPlayers(){
        for (PlayerCard playerCard : playerList) {
            playerContainer.add(playerCard);
        }
    }

}
