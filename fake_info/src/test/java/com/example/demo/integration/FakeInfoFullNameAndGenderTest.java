package com.example.demo.integration;


import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.FakeInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FakeInfoFullNameAndGenderTest {

    int numberOfFields = 3;

    @Test
        // Verifies that the method returns a non-null result object.
    void getFullNameAndGender_shouldReturnNonNullData() {
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFullNameAndGender();

        assertNotNull(personData);
        assertEquals(numberOfFields, personData.size());
    }

    @Test
        // Verifies that all required fields have non-null values.
    void getFullNameAndGender_shouldReturnNoNullValues() {
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFullNameAndGender();

        assertNotNull(personData.get("firstName"));
        assertNotNull(personData.get("lastName"));
        assertNotNull(personData.get("gender"));
    }

    @Test
        // Verifies that the result contains exactly the expected fields.
    void getFullNameAndGender_shouldContainExpectedFields() {
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFullNameAndGender();

        assertEquals(numberOfFields, personData.size());
        assertTrue(personData.containsKey("firstName"));
        assertTrue(personData.containsKey("lastName"));
        assertTrue(personData.containsKey("gender"));
    }

    @Test
        // Even though the above test reduces this tests value, I choose to include it we have specific fields that must never appear
    void getFullNameAndGender_shouldNotContainUnexpectedFields() {
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFullNameAndGender();

        // Other known fields from other endpoints we don't want here
        assertFalse(personData.containsKey("CPR"));
        assertFalse(personData.containsKey("birthDate"));
        assertFalse(personData.containsKey("address"));
        assertFalse(personData.containsKey("phoneNumber"));
    }

    @Test
        // test for if the returned names are not an empty string
    void getFullNameAndGender_shouldReturnNonEmptyNames() {
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFullNameAndGender();

        String firstName = personData.get("firstName").toString();
        String lastName = personData.get("lastName").toString();

        assertFalse(firstName.isBlank());
        assertFalse(lastName.isBlank());
    }

    @Test
        // test to make sure the gender field matches expected patterns
    void getFullNameAndGender_shouldReturnValidGender() {
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFullNameAndGender();

        String gender = personData.get("gender").toString();

        assertTrue(gender.equals("male") || gender.equals("female"));
    }

    @Test
        // Test to make check for no special characters
    void getFullNameAndGender_shouldReturnNamesWithoutDigitsOrSpecialCharacters() {
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFullNameAndGender();

        String firstName = personData.get("firstName").toString();
        String lastName = personData.get("lastName").toString();

        assertFalse(firstName.matches(".*[0-9,!@%^$&#].*"));
        assertFalse(lastName.matches(".*[0-9,!@%^$&#].*"));
    }

    @RepeatedTest(10)
        // stress test to make sure the system can deliver valid data multiple times.
    void getFullNameAndGender_shouldConsistentlyRespectContract() {
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFullNameAndGender();

        assertNotNull(personData);
        assertTrue(personData.containsKey("firstName"));
        assertTrue(personData.containsKey("lastName"));
        assertTrue(personData.containsKey("gender"));

        String firstName = personData.get("firstName").toString();
        String lastName = personData.get("lastName").toString();
        String gender = personData.get("gender").toString();

        assertFalse(firstName.isBlank());
        assertFalse(lastName.isBlank());
        assertTrue(gender.equals("male") || gender.equals("female"));
        assertFalse(firstName.matches(".*[0-9,!@%^$&#].*"));
        assertFalse(lastName.matches(".*[0-9,!@%^$&#].*"));
    }



//    @Test
//    // test to make sure that within one test scenario, multiple generated persons should all be valid
//    void getFullNameAndGender_shouldReturnValidResultsForSeveralGeneratedPersons() {
//        int numberOfPersons = 5;
//        ArrayList<FakeInfo> personList = new ArrayList<>();
//
//        for (int i = 0; i < numberOfPersons; i++) {
//            personList.add(new FakeInfo());
//        }
//
//        for (FakeInfo person : personList) {
//            Map<String, Object> personData = person.getFullNameAndGender();
//
//            assertNotNull(personData);
//            assertTrue(personData.containsKey("firstName"));
//            assertTrue(personData.containsKey("lastName"));
//            assertTrue(personData.containsKey("gender"));
//
//            String firstName = personData.get("firstName").toString();
//            String lastName = personData.get("lastName").toString();
//            String gender = personData.get("gender").toString();
//
//            assertFalse(firstName.isBlank());
//            assertFalse(lastName.isBlank());
//            assertTrue(gender.equals("male") || gender.equals("female"));
//
//            assertFalse(firstName.matches(".*[0-9,!@%^$&#].*"));
//            assertFalse(lastName.matches(".*[0-9,!@%^$&#].*"));
//        }
//    }
}
