package org.sqs.smartqueue.data.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "app_notification")
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User client;
    @ManyToOne(fetch = FetchType.LAZY)
    private Booking booking;
    @Column(name = "message", nullable = false)
    private String message;
}
