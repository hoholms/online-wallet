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

    @OneToOne()
    @JoinColumn(name = "id")
    private User user;

    @Column(name = "profile_id", nullable = false)
    private Long profile_id;

    @Id
    @Column(name = "email", nullable = false, length = 256)
    private String email;

    @Column(name = "f_name", nullable = false, length = 50)
    private String f_name;

    @Column(name = "l_name", nullable = false, length = 50)
    private String l_name;

    @Column(name = "created_on")
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
