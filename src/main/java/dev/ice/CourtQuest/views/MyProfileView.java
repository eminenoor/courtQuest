package dev.ice.CourtQuest.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import dev.ice.CourtQuest.entities.Rating;
import com.vaadin.flow.server.StreamResource;
import dev.ice.CourtQuest.entities.UserDB;
import dev.ice.CourtQuest.services.UserService;
import jakarta.annotation.security.PermitAll;

import java.io.ByteArrayInputStream;
import java.util.Base64;

import java.time.LocalDate;

@Route("profile")
@PermitAll
public class MyProfileView extends HorizontalLayout {

    private Text nameValue;
    private Text ageValue;
    private Text genderValue;
    private Text departmentValue;
    private Text emailValue;
    private Avatar avatar;
    private Span nameField;
    private Span ageField;
    private Span genderField;
    private Span departmentField;
    private Span emailField;

    UserService userService;

    public MyProfileView(UserService userService) {
        this.userService = userService;
        H1 profileTitle = new H1("My Profile");

        RouterLink logoutLink = new RouterLink("Log out", LogoutView.class);
        logoutLink.getStyle().set("margin-right", "auto");

        Icon bellIcon = new Icon(VaadinIcon.BELL);
        bellIcon.getStyle().set("cursor", "pointer");
        bellIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("notifications")));

        Icon profileIcon = new Icon(VaadinIcon.USER);
        profileIcon.getStyle().set("cursor", "pointer");
        profileIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("profile")));

        HorizontalLayout topRightIcons = new HorizontalLayout(logoutLink, bellIcon, profileIcon);
        topRightIcons.getStyle().set("margin-left", "auto"); // Push to the right

        HorizontalLayout headerLayout = new HorizontalLayout(profileTitle, topRightIcons);
        headerLayout.setWidthFull();
        headerLayout.setAlignItems(Alignment.CENTER);

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

        Image profilePicture = new Image("images/profile-picture-placeholder.png", "Profile Picture");
        profilePicture.setWidth("150px");

        RouterLink editProfileLink = new RouterLink("Edit Profile", EditProfileView.class);

        UserDB user = userService.getCurrentUser();
        nameValue = new Text(user.getFirst_name() + " " + user.getLast_name());
        ageValue = new Text(Integer.toString(user.getAge()));
        genderValue = new Text(user.getGender());
        departmentValue = new Text(user.getDepartment());
        emailValue = new Text(user.getEmail());

        Span nameValueSpan = new Span(nameValue);
        nameValueSpan.getElement().getStyle().set("font-size", "24px");

        Span ageValueSpan = new Span(ageValue);
        ageValueSpan.getElement().getStyle().set("font-size", "24px");

        Span genderValueSpan = new Span(genderValue);
        genderValueSpan.getElement().getStyle().set("font-size", "24px");

        Span departmentValueSpan = new Span(departmentValue);
        departmentValueSpan.getElement().getStyle().set("font-size", "24px");

        Span emailValueSpan = new Span(emailValue);
        emailValueSpan.getElement().getStyle().set("font-size", "24px");

        if(user.getAvatar() == null){
            avatar = new Avatar(nameValue.getText());
        }else{
            StreamResource resource = new StreamResource("avatar", () -> new ByteArrayInputStream(user.getAvatar()));
            avatar = new Avatar();
            avatar.setImageResource(resource);
        }
        avatar.setWidth("150px");
        avatar.setHeight("150px");
        avatar.getElement().getStyle().set("margin-top", "0");

        Button editProfileButton = new Button("Edit Profile");
        editProfileButton.getStyle().setBackgroundColor("#e6e6e6");
        editProfileButton.getElement().getStyle().set("margin-top", "0");
        editProfileButton.getElement().getStyle().setFontSize("15px");
        editProfileButton.getElement().getStyle().set("color", "#3F51B5");
        editProfileButton.getStyle().setWidth("auto");
        editProfileButton.getStyle().setHeight("20px");

        editProfileButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("edit-profile")));
        editProfileButton.getElement().addEventListener("mouseover", e -> {
            editProfileButton.getElement().getStyle().set("cursor", "pointer");
        });

        VerticalLayout avatarLayout = new VerticalLayout();
        avatarLayout.add(avatar, editProfileButton);
        avatarLayout.setAlignItems(FlexComponent.Alignment.START); // Align items to start
        avatarLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        avatarLayout.getStyle().set("margin-left", "0px");
        avatarLayout.setPadding(false);
        avatarLayout.setSpacing(false);
        avatarLayout.getStyle().setWidth("150px");
        avatar.getStyle().set("margin-bottom", "20px");

        nameField = new Span("Name: ");
        createStyledSpan(nameField);
        HorizontalLayout nameLayout = new HorizontalLayout(nameField, nameValueSpan);
        nameLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        ageField = new Span("Age:  ");
        createStyledSpan(ageField);
        HorizontalLayout ageLayout = new HorizontalLayout(ageField, ageValueSpan);
        ageLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        departmentField = new Span("Department:  ");
        createStyledSpan(departmentField);
        HorizontalLayout departmentLayout = new HorizontalLayout(departmentField, departmentValueSpan);
        departmentLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        emailField = new Span("Email:  ");
        createStyledSpan(emailField);
        HorizontalLayout emailLayout = new HorizontalLayout(emailField, emailValueSpan);
        emailLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        VerticalLayout profileDetails = new VerticalLayout(
                avatarLayout,
                nameLayout,
                ageLayout,
                departmentLayout,
                emailLayout
        );
        profileDetails.setAlignItems(FlexComponent.Alignment.START);
        profileDetails.setAlignItems(VerticalLayout.Alignment.START);
        profileDetails.setSpacing(true);
        avatarLayout.getStyle().set("margin-bottom", "20px");
        profileDetails.getElement().getStyle().set("padding", "10px");

        // Ratings
        H1 personalRatingsTitle = new H1("Personal Ratings");
        personalRatingsTitle.getElement().getStyle().set("font-size", "30px");

        Rating rating = user.getReceivedRatings();
        double volleyballPersonal = rating.getRatingVolleyballPersonal();
        double footballPersonal = rating.getRatingFootballPersonal();
        double basketballPersonal = rating.getRatingBasketballPersonal();
        double tennisPersonal = rating.getRatingTennisPersonal();

        VerticalLayout personalRatings = new VerticalLayout(
                personalRatingsTitle,
                createPersonalRatingComponent("Volleyball", volleyballPersonal),
                createPersonalRatingComponent("Football", footballPersonal),
                createPersonalRatingComponent("Basketball", basketballPersonal),
                createPersonalRatingComponent("Tennis", tennisPersonal)
        );
        personalRatingsTitle.getStyle().set("margin-bottom", "25px");
        personalRatings.getElement().getStyle().set("background-color", "#ADD8E6");
        personalRatings.getElement().getStyle().set("padding", "50px");
        personalRatings.getElement().getStyle().set("border-radius", "30px");
        personalRatings.getElement().getStyle().set("font-size", "23px");
        personalRatings.setWidth("400px");
        personalRatings.setHeight("500px");
        personalRatings.setAlignItems(FlexComponent.Alignment.BASELINE);
        personalRatings.setJustifyContentMode(FlexComponent.JustifyContentMode.AROUND);

        // Ratings
        H1 generalRatingsTitle = new H1("General Ratings");
        generalRatingsTitle.getElement().getStyle().set("font-size", "30px");

        double volleyballGeneral = rating.getRatingVolleyball();
        double footballGeneral = rating.getRatingFootball();
        double basketballGeneral = rating.getRatingBasketball();
        double tennisGeneral = rating.getRatingTennis();
        VerticalLayout generalRatings = new VerticalLayout(
                generalRatingsTitle,
                createGeneralRatingComponent("Volleyball", volleyballGeneral),
                createGeneralRatingComponent("Football", footballGeneral),
                createGeneralRatingComponent("Basketball", basketballGeneral),
                createGeneralRatingComponent("Tennis", tennisGeneral)
        );
        generalRatingsTitle.getStyle().set("margin-bottom", "25px");
        generalRatings.getElement().getStyle().set("background-color", "#ADD8E6");
        generalRatings.getElement().getStyle().set("padding", "50px");
        generalRatings.getElement().getStyle().set("border-radius", "30px");
        generalRatings.getElement().getStyle().set("font-size", "23px");
        generalRatings.setWidth("400px");
        generalRatings.setHeight("500px");
        generalRatings.setAlignItems(FlexComponent.Alignment.BASELINE);
        generalRatings.setJustifyContentMode(JustifyContentMode.AROUND);

        HorizontalLayout profileAndRatings = new HorizontalLayout(profileDetails, personalRatings, generalRatings);
        personalRatings.getStyle().set("margin-right", "25px");
        profileAndRatings.setAlignItems(FlexComponent.Alignment.CENTER);
        profileAndRatings.setSpacing(true);
        profileAndRatings.setWidthFull();

        VerticalLayout mainContent = new VerticalLayout(headerLayout, profileAndRatings);
        mainContent.setWidthFull();
        mainContent.setAlignItems(FlexComponent.Alignment.START);
        mainContent.setSpacing(true);

        // Add components to the root layout
        add(iconBar, mainContent);
        setAlignItems(FlexComponent.Alignment.START);
        setSizeFull();
        getElement().getStyle().set("overflow", "hidden");
    }

    private HorizontalLayout createPersonalRatingComponent(String sport, double rating) {
        HorizontalLayout ratingLayout = new HorizontalLayout();
        ratingLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        Span sportText = new Span(sport + ": ");
        sportText.getElement().getStyle().set("margin-right", "auto");
        ratingLayout.add(sportText);
        ratingLayout.setSpacing(false);
        ratingLayout.setPadding(false);
        ratingLayout.setWidthFull();

        for (int i = 1; i <= 5; i++) {
            Icon star = new Icon(VaadinIcon.STAR);
            star.setColor(i <= rating ? "#F8FFFD" : "gray");
            star.getElement().setProperty("data-rating", i);
            star.setSize("30px");
            ratingLayout.add(star);
            ratingLayout.getElement().getStyle().set("margin-bottom", "15px");

        }
        return ratingLayout;
    }

    private HorizontalLayout createGeneralRatingComponent(String sport, double rating) {
        HorizontalLayout ratingLayout = new HorizontalLayout();
        ratingLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        Span sportText = new Span(sport + ": ");
        sportText.getElement().getStyle().set("margin-right", "auto");
        ratingLayout.add(sportText);
        ratingLayout.setSpacing(false);
        ratingLayout.setPadding(false);
        ratingLayout.setWidthFull();

        for (int i = 1; i <= 5; i++) {
            Icon star = new Icon(VaadinIcon.STAR);
            star.setColor(i <= rating ? "yellow" : "gray");
            star.getElement().setProperty("data-rating", i);
            star.setSize("30px");
            ratingLayout.add(star);
            ratingLayout.getElement().getStyle().set("margin-bottom", "15px");

        }
        return ratingLayout;
    }

    private Span createStyledSpan(Span span) {
        span.getElement().getStyle().set("font-size", "20px");
        span.getElement().getStyle().set("padding", "5px");
        span.getElement().getStyle().set("border-radius", "5px");
        span.getStyle().setBackgroundColor("#e6e6e6");
        return span;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public String getAgeValue() {
        return new String(ageValue.getText());
    }

    public String getDepartmentValue() {
        return new String(departmentValue.getText());
    }

    public String getNameValue() {
        UserDB user = userService.getCurrentUser();
        return user.getFirst_name();
    }

    public String getLastNameValue(){
        UserDB user = userService.getCurrentUser();
        return user.getLast_name();
    }

    public String getGenderValue() {
        return new String(genderValue.getText());
    }

    public String getEmailValue() {
        return new String(emailValue.getText());
    }

    public String getBirthDate(){
        UserDB user = userService.getCurrentUser();
        return user.getBirth_date();
    }

    public void setNameValue(String nameValue) {
        this.nameValue = new Text(nameValue);
    }

    public void setAgeValue(String ageValue) {
        this.ageValue = new Text(ageValue);
    }

    public void setDepartmentValue(String departmentValue) {
        this.departmentValue = new Text(departmentValue);
    }

    public void setEmailValue(String emailValue) {
        this.emailValue = new Text(emailValue);
    }

}
