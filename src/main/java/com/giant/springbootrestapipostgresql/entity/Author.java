package com.giant.springbootrestapipostgresql.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id")
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="birth_year")
    private int birthYear;

    @Column(name="biography")
    private String biography;

    @Column(name="email")
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "isbn")
    @Column(name = "Value")
    private List<String> isbns = new ArrayList<>();

    public Author() {
    }

    public Author(String firstName, String lastName, int birthYear, String biography, String email, List<String> isbns) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthYear = birthYear;
        this.biography = biography;
        this.email = email;
        this.isbns = isbns;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getIsbns() {
        return isbns;
    }

    public void setIsbns(List<String> isbns) {
        this.isbns = isbns;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthYear=" + birthYear +
                ", biography='" + biography + '\'' +
                ", email='" + email + '\'' +
                ", isbns=" + isbns +
                '}';
    }
}
