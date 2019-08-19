package com.akveo.bundlejava.ecommerce.entity;

import com.akveo.bundlejava.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_activity")
public class UserActivity implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User user;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "url")
    private String url;
}
