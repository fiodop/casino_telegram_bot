package com.casino_telegram_bot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

//entity class of casino users
@Entity
@Table(name = "users_")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(unique = true, nullable = false, name = "username")
    String username;
    int balance;
    //field-counter of played games
    int gamesCounter;
    //field-counter of sum of all bids
    int totalSum;
    //field of registration date
    LocalDateTime registrationDate;
}
