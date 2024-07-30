package dev.ice.CourtQuest.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
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
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
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

    private UserService userService;
    private Long userId;

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

        HorizontalLayout headerContainer = new HorizontalLayout(headerLayout, crossIcon);
        headerContainer.setWidthFull();
        headerContainer.setAlignItems(FlexComponent.Alignment.START);
        headerContainer.expand(headerLayout);
        crossIcon.getElement().getStyle().set("margin-left", "auto");

        Image profilePicture = new Image("images/profile-picture-placeholder.png", "Profile Picture");
        profilePicture.setWidth("150px");

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

        H1 personalRatingsTitle = new H1("Personal Ratings");
        personalRatingsTitle.getElement().getStyle().set("font-size", "30px");

        VerticalLayout personalRatings = new VerticalLayout(
                personalRatingsTitle,
                createPersonalRatingComponent("Volleyball", 4.5),
                createPersonalRatingComponent("Football", 3.5),
                createPersonalRatingComponent("Basketball", 4.0),
                createPersonalRatingComponent("Tennis", 3.0)
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

        H1 generalRatingsTitle = new H1("General Ratings");
        generalRatingsTitle.getElement().getStyle().set("font-size", "30px");

        VerticalLayout generalRatings = new VerticalLayout(
                generalRatingsTitle,
                createGeneralRatingComponent("Volleyball", 2.5),
                createGeneralRatingComponent("Football", 1.5),
                createGeneralRatingComponent("Basketball", 4.0),
                createGeneralRatingComponent("Tennis", 5.0)
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

        VerticalLayout mainContent = new VerticalLayout(headerContainer, profileAndRatings);
        mainContent.setWidthFull();
        mainContent.setAlignItems(FlexComponent.Alignment.START);
        mainContent.setSpacing(true);

        // Add components to the root layout
        add(mainContent);
        setAlignItems(FlexComponent.Alignment.START);
        setSizeFull();
        getElement().getStyle().set("overflow", "hidden");
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
        } else {
            H1 error = new H1("User not found");
            add(error);
        }
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
