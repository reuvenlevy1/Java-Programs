package com.reuven.healthcare.entity;

import jakarta.persistence.*;

import com.reuven.healthcare.model.Sex;

/**
 * This class represents the "Doctor" table in the database.
 */
@Entity
@Table(name = "doctors")
public class Doctor {

    // -------------------- Fields --------------------
	@Id // Primary key of the table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long id;

	@Column(nullable = false)
    private String firstName;
	@Column(nullable = false)
    private String lastName;
    private int age;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    /** Medical specialty (e.g., "Neurology", "Internal Medicine", etc.). */
    @Column(nullable = false)
    private String specialty;
    @Column(nullable = false)
    private String phoneNumber;
    private String email;

    // -------------------- Constructors --------------------
    public Doctor() {}

    public Doctor(String firstName, String lastName, Sex sex, String specialty, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.specialty = specialty;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // -------------------- Getters & Setters --------------------
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    
    public void setAge(int age) {
		this.age = age;
	}
    
    public int getAge() {
		return age;
	}

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
