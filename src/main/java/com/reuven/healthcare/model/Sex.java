package com.reuven.healthcare.model;

/**
 * Represents biological sex with two valid values: MALE and FEMALE.
 * This enum is used in domain entities like Patient and Doctor.
 * It ensures type safety and restricts input to known constants.
 */
public enum Sex {
    MALE,
    FEMALE
}
