package dev.ice.CourtQuest.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import dev.ice.CourtQuest.services.ActivityService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Route("make-reservation")
@PermitAll
public class MakeReservationView extends VerticalLayout {
    private int count;

    @Autowired
    private ActivityService activityService;

    public MakeReservationView(ActivityService activityService) {
        this.activityService = activityService;

        H1 makeReservationTitle = new H1("Make a Reservation");

        count = 0;
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

        HorizontalLayout headerLayout = new HorizontalLayout(topRightIcons);
        headerLayout.setWidthFull();
        headerLayout.setAlignItems(Alignment.CENTER);

        VerticalLayout iconBar = new VerticalLayout();
        iconBar.setWidth("50px"); // Adjust width as needed
        iconBar.getStyle().set("background-color", "#1E3A8A"); // Dark blue color
        iconBar.getStyle().set("height", "100vh"); // Full height

        Icon groupIcon = new Icon(VaadinIcon.GROUP);
        groupIcon.getStyle().set("cursor", "pointer");
        groupIcon.getStyle().set("color", "white");
        groupIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(""))); // Current Activities

        Icon calendarIcon = new Icon(VaadinIcon.CALENDAR);
        calendarIcon.getStyle().set("cursor", "pointer");
        calendarIcon.getStyle().set("color", "white");
        calendarIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("my-activities"))); // My Activities

        Icon envelopeIcon = new Icon(VaadinIcon.ENVELOPE);
        envelopeIcon.getStyle().set("cursor", "pointer");
        envelopeIcon.getStyle().set("color", "white");
        envelopeIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("my-invitations"))); // My Invitations

        Icon checkIcon = new Icon(VaadinIcon.CHECK);
        checkIcon.getStyle().set("cursor", "pointer");
        checkIcon.getStyle().set("color", "white");
        checkIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("requests"))); // Requests

        Icon plusIcon = new Icon(VaadinIcon.PLUS);
        plusIcon.getStyle().set("cursor", "pointer");
        plusIcon.getStyle().set("color", "white");
        plusIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("make-reservation"))); // Make a Reservation

        Icon starIcon = new Icon(VaadinIcon.STAR);
        starIcon.getStyle().set("cursor", "pointer");
        starIcon.getStyle().set("color", "white");
        starIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("rate-players"))); // Rate Players

        iconBar.add(groupIcon, calendarIcon, envelopeIcon, checkIcon, plusIcon, starIcon);

        RadioButtonGroup<String> visibility = new RadioButtonGroup<>();
        visibility.setLabel("Visibility");
        visibility.setItems("Public", "Private");

        ComboBox<String> sports = new ComboBox<>("Sport:");
        sports.setItems("Basketball", "Football", "Tennis", "Volleyball");

        ComboBox<String> courtField = new ComboBox<>("Court/Field:");
        courtField.setEnabled(false);
        sports.addValueChangeListener(event -> {
            if(event.getValue().equals("Football")) {
                courtField.setItems("Dormitary Sports Hall Football Pitch 1", "Dormitary Sports Hall Football Pitch 2", "Dormitary Sports Hall Football Pitch 3");
            }else if(event.getValue().equals("Tennis")) {
                courtField.setItems("Dormitary Sports Hall Closed Tennis Court 1", "Dormitary Sports Hall Closed Tennis Court 2", "Dormitary Sports Hall Open Tennis Court 1");
            }else if(event.getValue().equals("Volleyball")) {
                courtField.setItems("Dormitary Sports Hall Closed Volleyball Field", "Dormitary Sports Hall Open Volleyball Field", "Main Sports Hall Closed Volleyball Field");
            }else if(event.getValue().equals("Basketball")) {
                courtField.setItems("Dormitary Sports Hall Basketball Field", "Main Sports Hall Basketball Field");
            }
        });

        DatePicker date = new DatePicker("Date:");
        if (LocalTime.now().isAfter(LocalTime.of(21, 59))) {
            date.setMin(LocalDate.now().plusDays(1));
        }else{
            date.setMin(LocalDate.now());
        }
        date.setMax(LocalDate.now().withDayOfMonth(1).plusMonths(2).minusDays(1));
        date.setEnabled(false);

        ComboBox<String> time = new ComboBox<>("Time:");
        //time.setItems(IntStream.rangeClosed(8, 22).mapToObj(hour -> String.format("%02d:00", hour)).toArray(String[]::new));

        date.addValueChangeListener(event -> {
            LocalDate selectedDate = event.getValue();
            List<String> availableTimes;
            if (selectedDate != null && selectedDate.equals(LocalDate.now())) {
                availableTimes = IntStream.rangeClosed(LocalTime.now().getHour() + 1, 22)
                        .mapToObj(hour -> String.format("%02d:00", hour))
                        .collect(Collectors.toList());
            } else {
                availableTimes = IntStream.rangeClosed(8, 22)
                        .mapToObj(hour -> String.format("%02d:00", hour))
                        .collect(Collectors.toList());
            }
            time.setItems(availableTimes);
        });
        time.setEnabled(false);

        TextField quota = new TextField("Quota:");
        quota.setEnabled(false);

        Button doneButton = new Button("Done");
        doneButton.setEnabled(false);

        sports.addValueChangeListener(event -> {
            if(event.getValue() != null) {
                courtField.setEnabled(true);
                count++;
            }
        });

        courtField.addValueChangeListener(event -> {
            if (event.getValue() != null) {
                date.setEnabled(true);
                count++;
            }
        });

        date.addValueChangeListener(event -> {
            if (event.getValue() != null) {
                time.setEnabled(true);
                count++;
            }
        });

        time.addValueChangeListener(event -> {
            if (event.getValue() != null) {
                quota.setEnabled(true);
                count++;
            }
        });

        quota.addValueChangeListener(event -> {
            if (event.getValue() != null) {
                count++;
                if(count == 6){
                    doneButton.setEnabled(true);
                }
            }
        });

        visibility.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                count++;
                if(count == 6){
                    doneButton.setEnabled(true);
                }
            }
        });
        doneButton.addClickListener(e -> {
            // Get form data
            String sport = sports.getValue();
            String court = courtField.getValue();
            LocalDate selectedDate = date.getValue();
            String selectedTime = time.getValue();
            String visibilityStatus = visibility.getValue();
            int quotaValue = Integer.parseInt(quota.getValue());
            // Call the service to create and save the activity
            activityService.createActivity(sport, visibilityStatus, court, selectedDate.toString(), selectedTime, quotaValue);

            Notification.show("Reservation made!");

            UI.getCurrent().navigate("my-activities");
        });

        FormLayout formLayout = new FormLayout();
        formLayout.add(visibility, sports, courtField, date, time, quota);
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1)
        );

        HorizontalLayout buttonLayout = new HorizontalLayout(doneButton);
        buttonLayout.setWidthFull();
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        VerticalLayout formContainer = new VerticalLayout(makeReservationTitle, formLayout, buttonLayout);
        formContainer.setAlignItems(Alignment.CENTER);
        formContainer.setPadding(false);
        formContainer.getStyle().set("margin", "0 auto");
        formContainer.getStyle().set("padding-top", "20px");
        formContainer.getStyle().set("width", "400px");

        VerticalLayout mainContent = new VerticalLayout(headerLayout, formContainer);
        mainContent.setAlignItems(Alignment.CENTER);
        mainContent.setJustifyContentMode(JustifyContentMode.START);
        mainContent.setHeightFull();
        mainContent.setPadding(false);

        HorizontalLayout rootLayout = new HorizontalLayout(iconBar, mainContent);
        rootLayout.setAlignItems(Alignment.STRETCH);
        rootLayout.setSizeFull();
        rootLayout.getStyle().set("padding", "0");

        iconBar.getStyle().set("position", "fixed");
        iconBar.getStyle().set("top", "0");
        iconBar.getStyle().set("left", "0");
        iconBar.getStyle().set("bottom", "0");

        add(rootLayout);
    }
}
