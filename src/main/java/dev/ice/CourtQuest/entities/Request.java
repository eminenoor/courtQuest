package dev.ice.CourtQuest.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

// May need something like @EqualsAndHashCode(exclude = " ")
@Entity
@Table(name="request")
@Data
//@EqualsAndHashCode(exclude = {"activity","sender","recipient"})
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long requestId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id")
    private UserDB sender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipient_id")
    private UserDB recipient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_id")
    private Activity activity;

    private String status; // "Pending", "Accepted", "Declined"

}