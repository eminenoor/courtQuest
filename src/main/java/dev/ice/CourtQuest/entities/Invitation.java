package dev.ice.CourtQuest.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="invitation")
@Data
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invitationId;

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

    // Getters and Setters
}
