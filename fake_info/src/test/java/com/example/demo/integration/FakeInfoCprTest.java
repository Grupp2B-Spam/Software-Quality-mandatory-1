package com.example.demo.integration;


import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.FakeInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FakeInfoCprTest {

    @Test
        // Verifies that the method returns a non-null result object.
    void getCpr_shouldReturnNonNullData() {
        FakeInfo person = new FakeInfo();

        String cpr = person.getCpr();

        assertNotNull(cpr);
    }

    @Test
        // test if cpr is 10 digits long
    void getCpr_shouldReturnNonCorrectLength() {
        FakeInfo person = new FakeInfo();

        String cpr = person.getCpr();

        assertEquals(10, cpr.length(),"Expected cpr to be of lengt 10 but it was: " + cpr);
    }

    @Test
        // Verifies that cpr first six digits matches the persons birthdate .
    void getCpr_firstSixDigitsMatchBirthdate() {
        FakeInfo person = new FakeInfo();

        String birthDate = person.getFullNameGenderAndBirthDate().get("birthDate").toString();
        String birthDateFormatted = birthDate.substring(8, 10) +
                birthDate.substring(5, 7) +
                birthDate.substring(2, 4);
        String cprDate = person.getCpr().substring(0, 6);

        assertEquals(cprDate, birthDateFormatted);
    }

    @RepeatedTest(10)
        // Verifies that cpr last digit is uneven when person is male and even when person is female.
    void getCpr_shouldReturnLastDigitThatMatchesGender() {
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFullNameAndGender();

        String cpr = person.getCpr();

        int lastCPRIndex = Character.getNumericValue(cpr.charAt(cpr.length()-1));
        String gender = personData.get("gender").toString();

        //last digit of cpr should be odd for males and even for females
        assertEquals(gender.equals("female") ? 0 : 1, lastCPRIndex % 2, "Expected "+ (lastCPRIndex % 2 == 0 ? "even" : "uneven") +" cpr to be "+ (gender.equals("female") ? "even" : "uneven")+", but was "+cpr);
    }
}
