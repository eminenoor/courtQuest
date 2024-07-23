package dev.ice.CourtQuest.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.StreamResource;
import jakarta.annotation.security.PermitAll;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;

@Route("edit-profile")
@PermitAll
public class EditProfileView extends HorizontalLayout {

    private TextField nameField;
    private TextField ageField;
    private ComboBox<String> departmentField;
    private EmailField emailField;
    private Avatar avatar;
    MyProfileView profile = new MyProfileView();

    public EditProfileView() {
        // Header
        H1 profileTitle = new H1("Edit Profile");

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

        avatar = new Avatar(profile.getNameValue());
        avatar.setImageResource(new StreamResource("avatar.png", () -> getClass().getResourceAsStream("/images/avatar.png")));
        avatar.setWidth("150px");
        avatar.setHeight("150px");

        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
        upload.setUploadButton(new Button("Upload Avatar"));
        upload.setDropAllowed(false);
        upload.addSucceededListener(event -> {
            StreamResource resource = new StreamResource(event.getFileName(), buffer::getInputStream);
            avatar.setImageResource(resource);
        });
        upload.getStyle().setFontSize("10px");
        upload.getUploadButton().getStyle().setWidth("150px");
        upload.getUploadButton().getStyle().setHeight("auto");

        nameField = new TextField("Name");
        nameField.setValue(profile.getNameValue());
        nameField.setWidthFull();

        ageField = new TextField("Age");
        ageField.setValue(profile.getAgeValue());
        ageField.setWidthFull();

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

        emailField = new EmailField("Email");
        emailField.setValue(profile.getEmailValue());
        emailField.setWidthFull();

        VerticalLayout profileDetails = new VerticalLayout(
                avatar,
                upload,
                nameField,
                ageField,
                departmentField,
                emailField
        );

        profileDetails.setAlignItems(FlexComponent.Alignment.START);
        profileDetails.setSpacing(false);
        profileDetails.getStyle().set("padding", "10px");
        profileDetails.getStyle().set("max-width", "300px");
        profileDetails.getStyle().set("margin-right", "100px");

        // Ratings
        H1 personalRatingsTitle = new H1("Personal Ratings");
        personalRatingsTitle.getStyle().set("font-size", "30px");
        //personalRatingsTitle.getStyle().set("margin-bottom", "30px");

        Div line = new Div();
        line.setHeight("1.5px");
        line.setWidth("100%");
        line.getStyle().set("background-color", "black");

        VerticalLayout titleLine = new VerticalLayout();
        titleLine.add(personalRatingsTitle, line);
        titleLine.getStyle().set("margin-bottom", "30px");
        titleLine.setAlignItems(FlexComponent.Alignment.CENTER);

        VerticalLayout personalRatings = new VerticalLayout(
                titleLine,
                //personalRatingsTitle,
                createEditableRatingComponent("Volleyball", 4.5),
                createEditableRatingComponent("Football", 3.5),
                createEditableRatingComponent("Basketball", 4.0),
                createEditableRatingComponent("Tennis", 3.0)
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

        Button saveButton = new Button("Save Changes", e -> saveChanges());
        saveButton.getStyle().set("margin-top", "20px");

        // Center bottom button layout
        VerticalLayout buttonLayout = new VerticalLayout(saveButton);
        buttonLayout.setWidthFull();
        buttonLayout.setAlignItems(FlexComponent.Alignment.END);
        buttonLayout.getStyle().set("position", "absolute");
        buttonLayout.getStyle().set("bottom", "20px");
        buttonLayout.getStyle().set("left", "50%");
        buttonLayout.getStyle().set("transform", "translateX(-50%)");

        VerticalLayout mainContent = new VerticalLayout(headerLayout, profileAndRatings);
        mainContent.setWidthFull();
        mainContent.setAlignItems(FlexComponent.Alignment.START);
        mainContent.setSpacing(true);
        mainContent.getStyle().set("position", "relative");

        // Add components to the root layout
        add(iconBar, mainContent, buttonLayout);
        setAlignItems(FlexComponent.Alignment.START);
        setSizeFull();
        getStyle().set("overflow", "hidden");
    }

    private HorizontalLayout createEditableRatingComponent(String sport, double rating) {
        HorizontalLayout ratingLayout = new HorizontalLayout();
        ratingLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        //ratingLayout.setJustifyContentMode(JustifyContentMode.AROUND);
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

    private void saveChanges() {
        boolean changesMade = false;

        if (!nameField.getValue().equals(profile.getNameValue())) {
            profile.setNameValue(nameField.getValue());
            changesMade = true;
        }
        if (!ageField.getValue().equals(profile.getAgeValue())) {
            profile.setAgeValue(ageField.getValue());
            changesMade = true;
        }
        if (!departmentField.getValue().equals(profile.getDepartmentValue())) {
            profile.setDepartmentValue(departmentField.getValue());
            changesMade = true;
        }
        if (!emailField.getValue().equals(profile.getEmailValue())) {
            profile.setEmailValue(emailField.getValue());
            changesMade = true;
        }

        if (changesMade) {
            Notification.show("Changes saved successfully!", 3000, Notification.Position.MIDDLE)
                    .addOpenedChangeListener(e -> getUI().ifPresent(ui -> ui.navigate("profile")));
        } else {
            getUI().ifPresent(ui -> ui.navigate("profile"));
        }
    }
}
