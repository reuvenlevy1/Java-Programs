package com.reuven.healthcare.entity;

import com.reuven.healthcare.model.Sex;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * This class represents the "Patient" table in the database.
 */
@Entity
@Table(name = "patients")
public class Patient {

    // -------------------- Fields --------------------
    @Id // Primary key of the table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long id;

    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    private int age;
    
    /** Medical diagnosis (e.g., "Narcolepsy", "Insomnia", etc.). */
    private String diagnosis;
    
    @Enumerated(EnumType.STRING)
    private Sex sex;
    
    // -------------------- Constructors --------------------
    public Patient() {} // Required by JPA and Jackson

    public Patient(String firstName, String lastName, int age, String diagnosis, Sex sex) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.diagnosis = diagnosis;
        this.sex = sex;
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

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
    
    public Sex getSex() {
		return sex;
	}
    
    public void setSex(Sex sex) {
		this.sex = sex;
	}
}
