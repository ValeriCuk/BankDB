package org.example.Entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String username;

    public User(){}

    public User(String username) {
        this.username = username;
    }
}
