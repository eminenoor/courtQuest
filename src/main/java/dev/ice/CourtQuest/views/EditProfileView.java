package dev.ice.CourtQuest.views;

import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.StreamResource;
import dev.ice.CourtQuest.entities.UserDB;
import dev.ice.CourtQuest.entities.Rating;
import dev.ice.CourtQuest.services.UserService;
import jakarta.annotation.security.PermitAll;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.Period;

@Route("edit-profile")
@PermitAll
public class EditProfileView extends HorizontalLayout {

    private TextField nameField;
    private TextField lastNameField;
    private DatePicker birthDateField;
    private ComboBox<String> departmentField;
    private Avatar avatar;
    private UserService userService;
    private UserDB currentUser;
    private byte[] avatarData;
    private MyProfileView profile;

    @Autowired
    public EditProfileView(UserService userService) {
        this.userService = userService;
        this.profile = new MyProfileView(userService);

        H1 profileTitle = new H1("Edit Profile");

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

        avatar = new Avatar();
        if (profile.getAvatar().getImage() != null) {
            avatar.setImageResource(profile.getAvatar().getImageResource());
        } else {
            avatar.setName(profile.getNameValue() + " " + profile.getLastNameValue());
        }
        avatar.setWidth("120px");
        avatar.setHeight("120px");

        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg", "image/png");
        Button uploadButton = new Button("Upload Image");
        upload.setUploadButton(uploadButton);
        upload.setDropAllowed(false);
        upload.addSucceededListener(event -> {
            try (InputStream inputStream = buffer.getInputStream()) {
                avatarData = inputStream.readAllBytes();
                StreamResource resource = new StreamResource(event.getFileName(), () -> buffer.getInputStream());
                avatar.setImageResource(resource);
            } catch (IOException ex) {
                Notification.show("Error uploading image: " + ex.getMessage());
            }
        });
        upload.addFileRemovedListener(event -> {
            avatar.setImageResource(profile.getAvatar().getImageResource());
            avatarData = null;
        });
        uploadButton.getStyle().set("font-size", "12px");
        upload.getUploadButton().getStyle().setWidth("120px");
        upload.getUploadButton().getStyle().setHeight("auto");

        Icon redCrossIcon = VaadinIcon.CLOSE.create();
        redCrossIcon.getElement().getStyle().set("color", "red");
        Button deleteAvatarButton = new Button(redCrossIcon);
        deleteAvatarButton.getStyle().setWidth("5px");
        deleteAvatarButton.getStyle().setHeight("5px");
        deleteAvatarButton.getStyle().setBackgroundColor("white");
        deleteAvatarButton.addClickListener(e -> {
            avatar.setImage(null);
            avatar.setName(profile.getNameValue() + " " + profile.getLastNameValue());
            avatarData = null;
        });

        nameField = new TextField("Name");
        nameField.setValue(profile.getNameValue());
        nameField.setWidthFull();

        lastNameField = new TextField("Last Name");
        lastNameField.setValue(profile.getLastNameValue());
        lastNameField.setWidthFull();

        birthDateField = new DatePicker("Birthday");
        birthDateField.setValue(LocalDate.parse(profile.getBirthDate()));
        birthDateField.setWidthFull();
        birthDateField.setMax(LocalDate.now());

        String[] departments = {
                "AMER",  // American Culture and Literature
                "ARCH",  // Architecture
                "COMD",  // Communication and Design
                "FA",    // Fine Arts
                "GRA",   // Graphic Design
                "IAED",  // Interior Architecture and Environmental Design
                "LAUD",  // Urban Design and Landscape Architecture
                "MAN",   // Management
                "ECON",  // Economics
                "HIST",  // History
                "IR",    // International Relations
                "POLS",  // Political Science and Public Administration
                "PSYC",  // Psychology
                "CS",    // Computer Engineering
                "EE",   // Electrical and Electronics Engineering
                "IE",    // Industrial Engineering
                "ME",    // Mechanical Engineering
                "ARC",   // Archaeology
                "ELIT",  // English Language and Literature
                "PHIL",  // Philosophy
                "TRIN",  // Translation and Interpretation
                "LAW",   // Law
                "MUS",   // Music
                "THEA",  // Performing Arts
                "CHEM",  // Chemistry
                "MATH",  // Mathematics
                "MBG",   // Molecular Biology and Genetics
                "PHYS",  // Physics
        };

        departmentField = new ComboBox<>("Department", departments);
        departmentField.setValue(profile.getDepartmentValue());
        departmentField.setWidthFull();

        Div avatarContainer = new Div();
        deleteAvatarButton.getElement().getStyle().set("position", "absolute");
        deleteAvatarButton.getElement().getStyle().set("bottom", "0");
        deleteAvatarButton.getElement().getStyle().set("right", "0");
        avatarContainer.getElement().getStyle().set("position", "relative");
        avatarContainer.add(avatar, deleteAvatarButton);

        VerticalLayout avatarLayout = new VerticalLayout(avatarContainer, upload);
        avatarLayout.setAlignItems(FlexComponent.Alignment.START);
        avatarLayout.getStyle().set("padding-left", "10px");
        avatarLayout.setPadding(false);

        VerticalLayout personalInformationLayout = new VerticalLayout(nameField, lastNameField, birthDateField, departmentField);
        personalInformationLayout.setAlignItems(FlexComponent.Alignment.START);
        personalInformationLayout.setSpacing(false);
        personalInformationLayout.setPadding(true);

        VerticalLayout profileDetails = new VerticalLayout(avatarLayout, personalInformationLayout);

        profileDetails.setAlignItems(FlexComponent.Alignment.START);
        profileDetails.setSpacing(false);
        profileDetails.setPadding(false);
        profileDetails.getStyle().set("gap", "0px");
        profileDetails.getStyle().set("max-width", "300px");
        profileDetails.getStyle().set("margin-right", "100px");

        avatarLayout.getStyle().set("margin-bottom", "0px");

        // Ratings
        H1 personalRatingsTitle = new H1("Personal Ratings");
        personalRatingsTitle.getStyle().set("font-size", "30px");

        Div line = new Div();
        line.setHeight("1.5px");
        line.setWidth("100%");
        line.getStyle().set("background-color", "black");

        VerticalLayout titleLine = new VerticalLayout();
        titleLine.add(personalRatingsTitle, line);
        titleLine.getStyle().set("margin-bottom", "30px");
        titleLine.setAlignItems(FlexComponent.Alignment.CENTER);

        UserDB user = userService.getCurrentUser();
        Rating rating = user.getReceivedRatings();
        double volleyballPersonal = rating.getRatingVolleyballPersonal();
        double footballPersonal = rating.getRatingFootballPersonal();
        double basketballPersonal = rating.getRatingBasketballPersonal();
        double tennisPersonal = rating.getRatingTennisPersonal();

        VerticalLayout personalRatings = new VerticalLayout(
                titleLine,
                createEditableRatingComponent("Volleyball", volleyballPersonal),
                createEditableRatingComponent("Football", footballPersonal),
                createEditableRatingComponent("Basketball", basketballPersonal),
                createEditableRatingComponent("Tennis", tennisPersonal)
        );
        personalRatings.getStyle().set("background-color", "#ADD8E6");
        personalRatings.getStyle().set("padding", "50px");
        personalRatings.getStyle().set("border-radius", "30px");
        personalRatings.getStyle().set("font-size", "23px");
        personalRatings.setWidth("400px");
        personalRatings.setHeight("500px");
        personalRatings.setAlignItems(FlexComponent.Alignment.BASELINE);

        HorizontalLayout profileAndRatings = new HorizontalLayout(profileDetails, personalRatings);
        profileAndRatings.setAlignItems(FlexComponent.Alignment.CENTER);
        profileAndRatings.setSpacing(true);
        profileAndRatings.setWidthFull();

        Button saveButton = new Button("Save Changes");
        saveButton.addClickListener(e -> saveChanges());
        saveButton.getStyle().set("margin-top", "20px");

        VerticalLayout buttonLayout = new VerticalLayout(saveButton);
        buttonLayout.setWidthFull();
        buttonLayout.setAlignItems(FlexComponent.Alignment.END);
        buttonLayout.getStyle().set("position", "absolute");
        buttonLayout.getStyle().set("bottom", "20px");
        buttonLayout.getStyle().set("left", "50%");
        buttonLayout.getStyle().set("transform", "translateX(-50%)");

        VerticalLayout mainContent = new VerticalLayout(headerLayout, profileAndRatings);
        profileAndRatings.getStyle().set("margin-top", "0px");
        headerLayout.getStyle().set("margin-bottom", "0px");
        mainContent.setWidthFull();
        mainContent.setAlignItems(FlexComponent.Alignment.START);
        mainContent.setSpacing(false);
        mainContent.getStyle().set("position", "relative");

        add(iconBar, mainContent, buttonLayout);
        setAlignItems(FlexComponent.Alignment.START);
        setSizeFull();
        getStyle().set("overflow", "hidden");
    }

    private HorizontalLayout createEditableRatingComponent(String sport, double rating) {
        HorizontalLayout ratingLayout = new HorizontalLayout();
        ratingLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        Span sportText = new Span(sport + ": ");
        ratingLayout.add(sportText);
        ratingLayout.setSpacing(false);
        ratingLayout.setPadding(false);
        ratingLayout.setWidthFull();
        sportText.getStyle().set("margin-right", "auto");

        for (int i = 1; i <= 5; i++) {
            Icon star = new Icon(VaadinIcon.STAR);
            star.setColor(i <= rating ? "yellow" : "gray");
            star.getElement().setProperty("data-rating", i);
            star.setSize("30px");
            star.getStyle().set("cursor", "pointer");
            star.addClickListener(e -> {
                double newRating = Double.parseDouble(star.getElement().getProperty("data-rating"));
                updateStars(ratingLayout, newRating);
                saveRating(sport, newRating);
            });
            ratingLayout.add(star);
            ratingLayout.getStyle().set("margin-bottom", "15px");
        }
        return ratingLayout;
    }

    private void updateStars(HorizontalLayout ratingLayout, double newRating) {
        for (int i = 1; i < ratingLayout.getComponentCount(); i++) {
            Icon star = (Icon) ratingLayout.getComponentAt(i);
            int starRating = Integer.parseInt(star.getElement().getProperty("data-rating"));
            star.setColor(starRating <= newRating ? "yellow" : "gray");
        }
    }

    private void saveRating(String sport, double newRating) {
        UserDB currentUser = userService.getCurrentUser();
        Rating userRating = currentUser.getReceivedRatings();

        switch (sport) {
            case "Volleyball":
                userRating.setRatingVolleyballPersonal(newRating);
                break;
            case "Football":
                userRating.setRatingFootballPersonal(newRating);
                break;
            case "Basketball":
                userRating.setRatingBasketballPersonal(newRating);
                break;
            case "Tennis":
                userRating.setRatingTennisPersonal(newRating);
                break;
        }

        // Save the updated rating to the database
        userService.editUser(currentUser.getUser_id(), currentUser);
    }

    private void saveChanges() {
        boolean changesMade = false;

        UserDB currentUser = userService.getCurrentUser();

        if (nameField.getValue() != null && !nameField.getValue().equals(currentUser.getFirst_name())) {
            currentUser.setFirst_name(nameField.getValue());
            changesMade = true;
        }

        if (lastNameField.getValue() != null && !lastNameField.getValue().equals(currentUser.getLast_name())) {
            currentUser.setLast_name(lastNameField.getValue());
            changesMade = true;
        }

        if (birthDateField.getValue() != null && !birthDateField.getValue().toString().equals(currentUser.getBirth_date())) {
            currentUser.setBirth_date(birthDateField.getValue().toString());
            currentUser.setAge(calculateAge(birthDateField.getValue()));
            changesMade = true;
        }

        if (departmentField.getValue() != null && !departmentField.getValue().equals(currentUser.getDepartment())) {
            currentUser.setDepartment(departmentField.getValue());
            changesMade = true;
        }

        if (avatarData != null && !avatarData.equals(currentUser.getAvatar())) {
            currentUser.setAvatar(avatarData);
            changesMade = true;
        }

        if (changesMade) {
            userService.editUser(currentUser.getUser_id(), currentUser);
            Notification.show("Changes saved successfully!", 2000, Notification.Position.MIDDLE)
                    .addOpenedChangeListener(e -> getUI().ifPresent(ui -> ui.navigate("profile")));
        } else {
            Notification.show("No Changes Made!", 2000, Notification.Position.MIDDLE)
                    .addOpenedChangeListener(e -> getUI().ifPresent(ui -> ui.navigate("profile")));
        }
    }

    private int calculateAge(LocalDate birthDate) {
        if (birthDate != null) {
            return Period.between(birthDate, LocalDate.now()).getYears();
        } else {
            return 0;
        }
    }
}
