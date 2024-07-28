package dev.ice.CourtQuest.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.PermitAll;

@Route("profile")
@PermitAll
public class MyProfileView extends HorizontalLayout {

    private Text nameValue;
    private Text ageValue;
    private Text genderValue;
    private Text departmentValue;
    private Text emailValue;

    public MyProfileView() {
        H1 profileTitle = new H1("My Profile");

        RouterLink logoutLink = new RouterLink("Log out", LogoutView.class); // Assuming LogoutView is the class handling logout
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

        nameValue = new Text("Emine Noor");
        ageValue = new Text("20");
        genderValue = new Text("Female");
        departmentValue = new Text("CS");
        emailValue = new Text("emine.noor@ug.bilkent.edu.tr");

        HorizontalLayout nameLayout = new HorizontalLayout(new Text("Name: "), nameValue);
        nameLayout.setSpacing(true);
        HorizontalLayout ageLayout = new HorizontalLayout(new Text("Age: "), ageValue);
        ageLayout.setSpacing(true);
        HorizontalLayout genderLayout = new HorizontalLayout(new Text("Gender: "), genderValue);
        genderLayout.setSpacing(true);
        HorizontalLayout departmentLayout = new HorizontalLayout(new Text("Department: "), departmentValue);
        departmentLayout.setSpacing(true);
        HorizontalLayout emailLayout = new HorizontalLayout(new Text("Email: "), emailValue);
        emailLayout.setSpacing(true);

        VerticalLayout profileDetails = new VerticalLayout(
                profilePicture,
                editProfileLink,
                nameLayout,
                ageLayout,
                genderLayout,
                departmentLayout,
                emailLayout
        );

        profileDetails.setAlignItems(Alignment.START);
        profileDetails.setSpacing(true);

        // Ratings
        H1 personalRatingsTitle = new H1("Personal Ratings");
        personalRatingsTitle.getStyle().set("color", "white");

        VerticalLayout personalRatings = new VerticalLayout(
                personalRatingsTitle,
                createRatingComponent("Volleyball", 4.5),
                createRatingComponent("Football", 3.5),
                createRatingComponent("Basketball", 4.0),
                createRatingComponent("Tennis", 3.0)
        );
        personalRatings.getStyle().set("background-color", "#ADD8E6");
        personalRatings.getStyle().set("padding", "20px");
        personalRatings.getStyle().set("border-radius", "10px");
        personalRatings.getStyle().set("color", "white");

        H1 othersRatingsTitle = new H1("Other's Ratings");
        othersRatingsTitle.getStyle().set("color", "white");

        VerticalLayout othersRatings = new VerticalLayout(
                othersRatingsTitle,
                createRatingComponent("Volleyball", 4.0),
                createRatingComponent("Football", 3.0),
                createRatingComponent("Basketball", 3.5),
                createRatingComponent("Tennis", 4.5)
        );
        othersRatings.getStyle().set("background-color", "#ADD8E6");
        othersRatings.getStyle().set("padding", "20px");
        othersRatings.getStyle().set("border-radius", "10px");
        othersRatings.getStyle().set("color", "white");

        HorizontalLayout ratingsLayout = new HorizontalLayout(personalRatings, othersRatings);
        ratingsLayout.setSpacing(true);

        HorizontalLayout profileAndRatings = new HorizontalLayout(profileDetails, ratingsLayout);
        profileAndRatings.setSpacing(true);
        profileAndRatings.setAlignItems(Alignment.START);

        VerticalLayout mainContent = new VerticalLayout(headerLayout, profileAndRatings);
        mainContent.setWidthFull();
        mainContent.setAlignItems(Alignment.START);
        mainContent.setSpacing(true);

        add(iconBar, mainContent);
        setAlignItems(Alignment.STRETCH);
        setSizeFull();
    }

    private HorizontalLayout createRatingComponent(String sport, double rating) {
        HorizontalLayout ratingLayout = new HorizontalLayout();
        ratingLayout.add(new Text(sport + ": "));
        for (int i = 0; i < 5; i++) {
            Icon star = new Icon(VaadinIcon.STAR);
            star.setColor(i < rating ? "yellow" : "gray");
            ratingLayout.add(star);
        }
        return ratingLayout;
    }

    public String getAgeValue() {
        return new String(ageValue.getText());
    }

    public String getDepartmentValue() {
        return new String(departmentValue.getText());
    }

    public String getNameValue() {
        return new String(nameValue.getText());
    }

    public String getGenderValue() {
        return new String(genderValue.getText());
    }

    public String getEmailValue() {
        return new String(emailValue.getText());
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
