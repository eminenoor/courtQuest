package dev.ice.CourtQuest.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import dev.ice.CourtQuest.entities.Rating;
import dev.ice.CourtQuest.entities.UserDB;
import dev.ice.CourtQuest.services.UserService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Route("user_profile/:userId")
@PermitAll
public class UserProfileView extends VerticalLayout implements BeforeEnterObserver {

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

    private final UserService userService;
    private Long userId;

    // Define headerContainer and profileDetails as instance variables
    private HorizontalLayout headerContainer;
    private VerticalLayout profileDetails;

    @Autowired
    public UserProfileView(UserService userService) {
        this.userService = userService;

        nameValue = new Text("");
        ageValue = new Text("");
        genderValue = new Text("");
        departmentValue = new Text("");
        emailValue = new Text("");

        createLayout();
    }

    private void createLayout() {
        H1 profileTitle = new H1("Profile");

        Icon crossIcon = new Icon(VaadinIcon.CLOSE);
        crossIcon.getStyle().set("cursor", "pointer");
        crossIcon.addClickListener(e -> UI.getCurrent().getPage().getHistory().back());

        HorizontalLayout headerLayout = new HorizontalLayout(profileTitle);
        headerLayout.setWidthFull();
        headerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        headerLayout.setSpacing(true);

        headerContainer = new HorizontalLayout(headerLayout, crossIcon);
        headerContainer.setWidthFull();
        headerContainer.setAlignItems(FlexComponent.Alignment.START);
        headerContainer.expand(headerLayout);
        crossIcon.getElement().getStyle().set("margin-left", "auto");

        avatar = new Avatar(nameValue.getText());
        avatar.setWidth("150px");
        avatar.setHeight("150px");
        avatar.getElement().getStyle().set("margin-top", "0");

        VerticalLayout avatarLayout = new VerticalLayout();
        avatarLayout.add(avatar);
        avatarLayout.setAlignItems(FlexComponent.Alignment.START);
        avatarLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        avatarLayout.getStyle().set("margin-left", "0px");
        avatarLayout.setPadding(false);
        avatarLayout.setSpacing(false);
        avatarLayout.getStyle().setWidth("150px");
        avatar.getStyle().set("margin-bottom", "20px");

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

        nameField = new Span("Name: ");
        createStyledSpan(nameField);
        HorizontalLayout nameLayout = new HorizontalLayout(nameField, nameValueSpan);
        nameLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        ageField = new Span("Age:  ");
        createStyledSpan(ageField);
        HorizontalLayout ageLayout = new HorizontalLayout(ageField, ageValueSpan);
        ageLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        genderField = new Span("Gender:  ");
        createStyledSpan(genderField);
        HorizontalLayout genderLayout = new HorizontalLayout(genderField, genderValueSpan);
        genderLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        departmentField = new Span("Department:  ");
        createStyledSpan(departmentField);
        HorizontalLayout departmentLayout = new HorizontalLayout(departmentField, departmentValueSpan);
        departmentLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        emailField = new Span("Email:  ");
        createStyledSpan(emailField);
        HorizontalLayout emailLayout = new HorizontalLayout(emailField, emailValueSpan);
        emailLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        profileDetails = new VerticalLayout(
                avatarLayout,
                nameLayout,
                ageLayout,
                genderLayout,
                departmentLayout,
                emailLayout
        );

        profileDetails.setAlignItems(FlexComponent.Alignment.START);
        profileDetails.setSpacing(true);
        avatarLayout.getStyle().set("margin-bottom", "20px");
        profileDetails.getElement().getStyle().set("padding", "10px");

        add(headerContainer);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> userIdOpt = event.getRouteParameters().get("userId").map(Long::parseLong);
        if (userIdOpt.isPresent()) {
            userId = userIdOpt.get();
            loadUserProfile();
        } else {
            H1 error = new H1("User ID is missing");
            add(error);
        }
    }

    private void loadUserProfile() {
        UserDB user = userService.getUser(userId);
        if (user != null) {
            nameValue.setText(user.getFirst_name() + " " + user.getLast_name());
            ageValue.setText(Integer.toString(user.getAge()));
            genderValue.setText(user.getGender());
            departmentValue.setText(user.getDepartment());
            emailValue.setText(user.getEmail());
            avatar.setName(user.getFirst_name() + " " + user.getLast_name());

            // Add ratings after the user details are loaded
            Rating rating = user.getReceivedRatings();
            if (rating != null) {
                H1 personalRatingsTitle = new H1("Personal Ratings");
                personalRatingsTitle.getElement().getStyle().set("font-size", "30px");

                double volleyballPersonal = rating.getRatingVolleyballPersonal();
                double footballPersonal = rating.getRatingFootballPersonal();
                double basketballPersonal = rating.getRatingBasketballPersonal();
                double tennisPersonal = rating.getRatingTennisPersonal();

                VerticalLayout personalRatings = new VerticalLayout(
                        personalRatingsTitle,
                        createRatingComponent("Volleyball", volleyballPersonal),
                        createRatingComponent("Football", footballPersonal),
                        createRatingComponent("Basketball", basketballPersonal),
                        createRatingComponent("Tennis", tennisPersonal)
                );

                personalRatings.getElement().getStyle().set("background-color", "#ADD8E6");
                personalRatings.getElement().getStyle().set("padding", "50px");
                personalRatings.getElement().getStyle().set("border-radius", "30px");
                personalRatings.getElement().getStyle().set("font-size", "23px");
                personalRatings.setWidth("400px");
                personalRatings.setHeight("500px");
                personalRatings.setAlignItems(FlexComponent.Alignment.BASELINE);
                personalRatings.setJustifyContentMode(FlexComponent.JustifyContentMode.AROUND);

                H1 generalRatingsTitle = new H1("General Ratings");
                generalRatingsTitle.getElement().getStyle().set("font-size", "30px");

                double volleyballGeneral = rating.getRatingVolleyball();
                double footballGeneral = rating.getRatingFootball();
                double basketballGeneral = rating.getRatingBasketball();
                double tennisGeneral = rating.getRatingTennis();

                VerticalLayout generalRatings = new VerticalLayout(
                        generalRatingsTitle,
                        createRatingComponent("Volleyball", volleyballGeneral),
                        createRatingComponent("Football", footballGeneral),
                        createRatingComponent("Basketball", basketballGeneral),
                        createRatingComponent("Tennis", tennisGeneral)
                );

                generalRatings.getElement().getStyle().set("background-color", "#ADD8E6");
                generalRatings.getElement().getStyle().set("padding", "50px");
                generalRatings.getElement().getStyle().set("border-radius", "30px");
                generalRatings.getElement().getStyle().set("font-size", "23px");
                generalRatings.setWidth("400px");
                generalRatings.setHeight("500px");
                generalRatings.setAlignItems(FlexComponent.Alignment.BASELINE);
                generalRatings.setJustifyContentMode(FlexComponent.JustifyContentMode.AROUND);

                HorizontalLayout ratingsLayout = new HorizontalLayout(personalRatings, generalRatings);
                ratingsLayout.setAlignItems(FlexComponent.Alignment.CENTER);
                ratingsLayout.setSpacing(true);

                HorizontalLayout profileAndRatings = new HorizontalLayout(profileDetails, ratingsLayout);
                profileAndRatings.setAlignItems(FlexComponent.Alignment.START);
                profileAndRatings.setSpacing(true);
                profileAndRatings.setWidthFull();

                removeAll(); // Clear the layout
                add(headerContainer, profileAndRatings);
            } else {
                H1 error = new H1("Ratings not found for the user");
                add(error);
            }
        } else {
            H1 error = new H1("User not found");
            add(error);
        }
    }

    private HorizontalLayout createRatingComponent(String sport, double rating) {
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

    public String getAvatar() {
        return avatar.getImage();
    }

    public String getAgeValue() {
        return ageValue.getText();
    }

    public String getDepartmentValue() {
        return departmentValue.getText();
    }

    public String getNameValue() {
        return nameValue.getText();
    }

    public String getGenderValue() {
        return genderValue.getText();
    }

    public String getEmailValue() {
        return emailValue.getText();
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
