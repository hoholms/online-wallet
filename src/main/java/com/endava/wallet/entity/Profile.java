package com.endava.wallet.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "profile_id", nullable = false)
    private Long id;

    @OneToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "user_id", nullable = false)
    private Long user_id;

    @Column(name = "email", nullable = false, length = 256)
    private String email;

    @Column(name = "first_name", nullable = false, length = 50)
    private String first_name;

    @Column(name = "last_name", nullable = false, length = 50)
    private String last_name;

    @Column(name = "created_date")
    private Timestamp created_on;

    private final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private java.sql.Timestamp parseTimestamp(String timestamp) {
        try {
            return new Timestamp(DATE_TIME_FORMAT.parse(timestamp).getTime());
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }


}
