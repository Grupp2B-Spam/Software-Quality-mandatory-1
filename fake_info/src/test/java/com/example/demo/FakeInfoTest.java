package com.example.demo;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FakeInfoTest {


    @RepeatedTest(20)
    void getCprFullNameAndGender() {
        FakeInfo person = new FakeInfo();

        Map<String,Object> personData = person.getCprFullNameAndGender();

        String cpr = personData.get("CPR").toString();
        String firstName = personData.get("firstName").toString();
        String lastName = personData.get("lastName").toString();
        String gender = personData.get("gender").toString();
        int lastCPRIndex = Character.getNumericValue(cpr.charAt(cpr.length()-1));

        int cprDate = Integer.parseInt(cpr.substring(0,2));
        int cprMonth = Integer.parseInt(cpr.substring(2,4));
        int cprYear = Integer.parseInt(cpr.substring(4,6));

        //Assertions
        assertNotNull(personData);

        //CPR needs to be only 10 character.
        assertTrue(cpr.matches("[0-9]{10}"), "Expected CPR to be 10 numbers, but got: " + cpr + " \nCpr length: " + cpr.length());
        assertTrue(gender.equals("male") || gender.equals("female"));

        //Name can't contain the numbers of characters in the regex
        assertFalse(firstName.matches("[0-9,!@%^$&#]"));
        assertFalse(lastName.matches("[0-9,!@%^$&#]"));

        //last digit of cpr should be odd for males and even for females
        if (gender.equals("female")) {
            assertEquals(0,lastCPRIndex % 2, "Expected "+firstName+" cpr to be even, but was "+cpr);

        }

        if (gender.equals("male")) {
            assertEquals(1,lastCPRIndex % 2, "Expected "+firstName+" cpr to be odd, but was "+cpr);
        }

            //First 2 digits should be the day
        assertTrue(32> cprDate && cprDate > 0, "Expected date to be valid, but was "+cprDate);
        //Third and fourth digit should be the month
        assertTrue(12>= cprMonth && cprMonth > 0, "Expected month to be valid, but was "+cprMonth);
        //Fifth and sixth digit should be the year
        assertTrue(100 > cprYear && cprYear >= 0, "Expected year to be valid, but was "+cprYear);

    }

    @RepeatedTest(5)
    void getCprFullNameGenderAndBirthDate() {

        FakeInfo person = new FakeInfo();

        Map<String,Object> personData = person.getCprFullNameGenderAndBirthDate();
        String cpr = personData.get("CPR").toString();
        String firstName = personData.get("firstName").toString();
        String lastName = personData.get("lastName").toString();
        String gender = personData.get("gender").toString();
        String birthDate = personData.get("birthDate").toString();
        int lastCPRIndex = Character.getNumericValue(cpr.charAt(cpr.length()-1));

        int cprDate = Integer.parseInt(cpr.substring(0,2));
        int cprMonth = Integer.parseInt(cpr.substring(2,4));
        int cprYear = Integer.parseInt(cpr.substring(4,6));

        //Assertions
        assertNotNull(personData);

        //CPR needs to be only 10 character.
        assertTrue(cpr.matches("[0-9]{10}"), "Expected CPR to be 10 numbers, but got: " + cpr + " \nCpr length: " + cpr.length());
        assertTrue(gender.equals("male") || gender.equals("female"));

        //Name can't contain the numbers of characters in the regex
        assertFalse(firstName.matches("[0-9,!@%^$&#]"));
        assertFalse(lastName.matches("[0-9,!@%^$&#]"));

        //last digit of cpr should be odd for males and even for females
        if (gender.equals("female")) {
            assertEquals(0,lastCPRIndex % 2, "Expected "+firstName+" cpr to be even, but was "+cpr);

        }

        if (gender.equals("male")) {
            assertEquals(1,lastCPRIndex % 2, "Expected "+firstName+" cpr to be odd, but was "+cpr);
        }

            //First 2 digits should be the day
        assertTrue(32> cprDate && cprDate > 0, "Expected date to be valid, but was "+cprDate);
        //Third and fourth digit should be the month
        assertTrue(12>= cprMonth && cprMonth > 0, "Expected month to be valid, but was "+cprMonth);
        //Fifth and sixth digit should be the year
        assertTrue(100 > cprYear && cprYear >= 0, "Expected year to be valid, but was "+cprYear);

        assertTrue(birthDate.matches("\\d{4}-\\d{2}-\\d{2}"), "Birthdate is expected to be YYY-MM-DD, but was "+birthDate);
    }
}
