package dev.ice.CourtQuest.views;

import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import dev.ice.CourtQuest.components.RatePlayersDetailedCard;
import dev.ice.CourtQuest.entities.Activity;
import dev.ice.CourtQuest.entities.Rating;
import dev.ice.CourtQuest.entities.UserDB;
import dev.ice.CourtQuest.services.ActivityService;
import dev.ice.CourtQuest.services.RatingService;
import dev.ice.CourtQuest.services.UserService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Route("rate-players-detailed/:activityId")
@PermitAll
public class RatePlayersDetailedView extends VerticalLayout implements BeforeEnterObserver {

    private final ActivityService activityService;
    private final UserService userService;
    private final RatingService ratingService;
    private Activity activity;
    private String activityType;
    private final Map<UserDB, RadioButtonGroup<Double>> participantRatingMap = new HashMap<>();

    @Autowired
    public RatePlayersDetailedView(ActivityService activityService, UserService userService, RatingService ratingService) {
        this.activityService = activityService;
        this.userService = userService;
        this.ratingService = ratingService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String activityIdParam = event.getRouteParameters().get("activityId").orElse(null);
        if (activityIdParam != null) {
            Long activityId = Long.parseLong(activityIdParam);
            this.activity = activityService.getActivity(activityId);
            if(activity.getName().equals("Volleyball")){
                this.activityType = "V";
            }
            else if(activity.getName().equals("Tennis")){
                this.activityType = "T";
            }
            else if(activity.getName().equals("Football")){
                this.activityType = "F";
            }
            else if(activity.getName().equals("Basketball")){
                this.activityType = "B";
            }
            buildView();
        }
    }

    private void buildView() {
        H1 header = new H1("Rate Players");
        header.getStyle().set("margin-bottom", "20px");

        Icon closeIcon = new Icon(VaadinIcon.CLOSE);
        closeIcon.getStyle().set("cursor", "pointer");
        closeIcon.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("rate-players")));
        HorizontalLayout headerLayout = new HorizontalLayout(header, closeIcon);
        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);

        RatePlayersDetailedCard label = new RatePlayersDetailedCard(activity.getName(), activity.getDate(), activity.getTime(), activity.getPlace());

        FlexLayout playersLayout = new FlexLayout();
        playersLayout.getStyle().set("flex-wrap", "wrap");
        playersLayout.setWidthFull();
        playersLayout.getStyle().set("overflow-y", "auto");
        playersLayout.setHeight("60vh"); // Set height to ensure scroll bar appears if needed

        Set<UserDB> participants = activity.getParticipants();
        UserDB user = userService.getCurrentUser();
        participants.remove(user);

        for (UserDB participant : participants) {
            VerticalLayout playerLayout = createPlayerLayout(participant);
            playersLayout.add(playerLayout);
        }

        Button sendButton = new Button("Send");
        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        sendButton.getStyle().set("background-color", "black");
        sendButton.getStyle().set("color", "white");
        sendButton.addClickListener(e -> {
            saveRatings();
            getUI().ifPresent(ui -> ui.navigate("rate-players"));
        });

        add(headerLayout, label, playersLayout, sendButton);
        setHorizontalComponentAlignment(Alignment.END, sendButton);
        setSizeFull();
        getStyle().set("padding", "20px");
    }

    private VerticalLayout createPlayerLayout(UserDB participant) {
        Avatar profileImage = new Avatar(participant.getFirst_name() + " " + participant.getLast_name());
        profileImage.setImage("https://via.placeholder.com/50");

        Span playerNameLabel = new Span(participant.getFirst_name() + " " + participant.getLast_name());
        playerNameLabel.getStyle().set("background-color", "#1E3A8A");
        playerNameLabel.getStyle().set("color", "white");
        playerNameLabel.getStyle().set("padding", "5px 10px");
        playerNameLabel.getStyle().set("border-radius", "5px");

        RadioButtonGroup<Double> ratingGroup = new RadioButtonGroup<>();
        ratingGroup.setItems(1.0, 2.0, 3.0, 4.0, 5.0);
        ratingGroup.setLabel("Rate");

        // Store the rating group associated with the participant
        participantRatingMap.put(participant, ratingGroup);

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

    private void saveRatings() {
        for (Map.Entry<UserDB, RadioButtonGroup<Double>> entry : participantRatingMap.entrySet()) {

            UserDB participant = entry.getKey();
            Double rating = entry.getValue().getValue();

            if(participant != null && rating != null){
                Rating rating1 = ratingService.getRatingsForUser(participant.getUser_id());

                if(activityType.equals("V")){
                    int no = rating1.getVolleyballNo();
                    double sum = (Double) 0.0;
                    sum = rating1.getRatingVolleyball() * no;
                    sum += rating;
                    rating1.setVolleyballNo(no + 1);
                    double avg = sum / (no + 1);
                    rating1.setRatingVolleyball(avg);
                }
                else if(activityType.equals("T")){
                    int no = rating1.getTennisNo();
                    double sum = (Double) 0.0;
                    sum = rating1.getRatingTennis() * no;
                    sum += rating;
                    rating1.setTennisNo(no + 1);
                    double avg = sum / (no + 1);
                    rating1.setRatingTennis(avg);
                }
                else if(activityType.equals("F")){
                    int no = rating1.getFootballNo();
                    double sum = (Double) 0.0;
                    sum = rating1.getRatingFootball() * no;
                    sum += rating;
                    rating1.setFootballNo(no + 1);
                    double avg = sum / (no + 1);
                    rating1.setRatingFootball(avg);
                }
                else if(activityType.equals("B")){
                    int no = rating1.getBasketballNo();
                    double sum = (Double) 0.0;
                    sum = rating1.getRatingBasketball() * no;
                    sum += rating;
                    rating1.setBasketballNo(no + 1);
                    double avg = sum / (no + 1);
                    rating1.setRatingBasketball(avg);
                }
                ratingService.saveRating(rating1);
                //userService.save(participant, rating1);
                UserDB current = userService.getCurrentUser();
                activityService.removeUserFromActivity(activity.getActivityId(), current.getUser_id());
                Notification.show("You have rated the players.");
            }
            else{
                Notification.show("You need to rate all players.");
            }

            /*

            if (rating != null) {
                participant.setRating(rating);
                //userService.saveUser(participant);
            }
             */
        }
    }

}
