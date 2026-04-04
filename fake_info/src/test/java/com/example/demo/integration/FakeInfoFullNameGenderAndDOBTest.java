package com.example.demo.integration;


import com.example.demo.FakeInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FakeInfoFullNameGenderAndDOBTest {

    private static final String BIRTH_DATE = "birthDate";

    int numberOfFields = 4;

    @Test
        // Verifies that the method returns a non-null result object.
    void getFullNameGenderAndDOB_shouldReturnNonNullData(){
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFullNameGenderAndBirthDate();

        assertNotNull(personData);
        assertEquals(numberOfFields, personData.size());
    }

    @Test
        // Verifies that all required fields have non-null values.
    void getFullNameGenderAndDOB_shouldReturnNonNullValues(){
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFullNameGenderAndBirthDate();

        assertNotNull(personData.get("firstName"));
        assertNotNull(personData.get("lastName"));
        assertNotNull(personData.get("gender"));
        assertNotNull(personData.get("birthDate"));
    }

    @Test
        // Verifies that the result contains exactly the expected fields.
    void getFullNameGenderAndDOB_shouldContainExpectedFields(){
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFullNameGenderAndBirthDate();
        assertEquals(numberOfFields, personData.size());
        assertTrue(personData.containsKey("firstName"));
        assertTrue(personData.containsKey("lastName"));
        assertTrue(personData.containsKey("gender"));
        assertTrue(personData.containsKey("birthDate"));
    }

    @Test
        // test for if the returned names are not an empty string
    void getFullNameGenderAndDOB_shouldReturnNonEmptyNames(){
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFullNameAndGender();

        String firstName = personData.get("firstName").toString();
        String lastName = personData.get("lastName").toString();

        assertFalse(firstName.isBlank());
        assertFalse(lastName.isBlank());
    }

     @Test
         // test to make sure the gender field matches expected patterns
    void getFullNameGenderAndDOB_shouldReturnValidGender(){
         FakeInfo person = new FakeInfo();

         Map<String, Object> personData = person.getFullNameAndGender();

         String gender = personData.get("gender").toString();

         assertTrue(gender.equals("male") || gender.equals("female"));
     }

     @Test
         // Test to make check for no special characters
    void getFullNameGenderAndDOB_shouldReturnNamesWithoutDigitsOrSpecialCharacters(){
         FakeInfo person = new FakeInfo();

         Map<String, Object> personData = person.getFullNameAndGender();

         String firstName = personData.get("firstName").toString();
         String lastName = personData.get("lastName").toString();

         assertFalse(firstName.matches(".*[0-9,!@%^$&#].*"));
         assertFalse(lastName.matches(".*[0-9,!@%^$&#].*"));
     }

    @Test
        // Verifies that birthDate is present.
    void getFullNameGenderAndDOB_shouldReturnValidBirthDate(){
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFakePerson();

        String birthDate = personData.get(BIRTH_DATE).toString();

        String[] birthDateParts = birthDate.split("-");

        assertEquals(3, birthDateParts.length,
                "Expected birthDate format YYYY-MM-DD, but was " + birthDate);
    }

}
