package dev.ice.CourtQuest.components;

import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class PlayerCard extends VerticalLayout {

    private Avatar avatar;
    private Span name;
    private Span department;
    private Span age;
    private Span gender;
    private double selfRating;
    private double generalRating;
    private HorizontalLayout selfRatingLayout;
    private HorizontalLayout generalRatingLayout;

    public PlayerCard(String avatarUrl, String name, String department, String gender, int age, double selfRating, double generalRating) {
        this.avatar = new Avatar(name);
        this.avatar.setImage(avatarUrl);

        this.name = new Span(name);
        this.name.getElement().getStyle().set("font-weight", "bold");

        this.department = new Span(department);
        this.department.getElement().getStyle().set("font-weight", "bold");
        this.age = new Span(String.valueOf(age));
        this.age.getElement().getStyle().set("font-weight", "bold");

        this.selfRating = selfRating;
        this.generalRating = generalRating;

        this.selfRatingLayout = createRatingLayout(selfRating, false);
        this.generalRatingLayout = createRatingLayout(generalRating, true);

        Span selfRatingSpan = new Span("Self Rating:");

        setGender(gender);
        this.gender.getElement().getStyle().set("font-weight", "bold");

        avatar.getElement().getStyle().setWidth("100px");
        avatar.getElement().getStyle().setHeight("100px");
        avatar.getElement().getStyle().set("color", "white");

        HorizontalLayout infoLayout = new HorizontalLayout();
        infoLayout.add(this.department, this.gender, this.age);
        this.department.getStyle().set("margin-right", "10px");
        this.gender.getStyle().set("margin-right", "10px");

        this.department.getElement().getStyle().set("font-weight", "bold");

        infoLayout.setSpacing(true);

        Div line = new Div();
        line.setHeight("1.2px");
        line.setWidth("80%");
        line.getStyle().set("background-color", "white");

        add(this.avatar, this.name, line, infoLayout,
                new Span("Personal Rating:"), this.selfRatingLayout,
                new Span("General Rating:"), this.generalRatingLayout);

        this.name.getElement().getStyle().set("margin-bottom", "2px");
        this.selfRatingLayout.getElement().getStyle().set("margin-bottom", "10px");
        this.selfRatingLayout.getElement().getStyle().set("margin-top", "5px");
        this.generalRatingLayout.getElement().getStyle().set("margin-top", "5px");
        infoLayout.getStyle().set("margin-bottom", "15px");
        line.getStyle().set("margin-top", "0.5px");
        line.getStyle().set("margin-bottom", "10px");
        setAlignItems(Alignment.CENTER);
        setPadding(true);
        setSpacing(false);
        getElement().getStyle().set("background-color", "#3F51B5").set("color", "white").set("border-radius", "10px").set("padding", "10px");
        setWidth("200px");
        setHeight("320px");
    }

    private HorizontalLayout createRatingLayout(double rating, boolean isGeneralRating) {
        HorizontalLayout layout = new HorizontalLayout();
        Icon star;
        for (int i = 1; i <= 5; i++) {
            if (i <= rating) {
               star = new Icon(VaadinIcon.STAR);
            } else if (i - rating < 1) {
                star = new Icon(VaadinIcon.STAR_HALF_LEFT);
            } else {
                star = new Icon(VaadinIcon.STAR_O);
            }
            if (isGeneralRating) {
                star.getElement().getStyle().set("color", "yellow");
            }
            star.getStyle().set("font-size", "15px");
            layout.add(star);
        }

        return layout;
    }

    public void setGender(String gender) {
        if(gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("M")) {
            this.gender = new Span("M");
        }else if(gender.equalsIgnoreCase("Female") || gender.equalsIgnoreCase("F")) {
            this.gender = new Span("F");
        }
    }

    public String getName() {
        String nameString = name.getText();
        return nameString;
    }

    public String getAvatarUrl() {
        return String.valueOf(avatar);
    }

    public String getDepartment() {
        return department.getText();
    }

    public String getGender() {
        return gender.getText();
    }

    public int getAge() {
        return Integer.parseInt(age.getText());
    }

    public double getSelfRating() {
        return selfRating;
    }

    public double getGeneralRating() {
        return generalRating;
    }
}
