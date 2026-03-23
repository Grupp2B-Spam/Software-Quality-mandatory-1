package com.example.demo.integration;

import com.example.demo.FakeInfo;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FakeInfoPersonTest {

    private static final int NUMBER_OF_FIELDS = 3;

    private static Stream<String> expectedFieldNames() {
        return Stream.of("firstName", "lastName", "gender");
    }

    private static Stream<String> unexpectedFieldNames() {
        return Stream.of("CPR", "birthDate", "address", "phoneNumber");
    }

    @Test
        // Verifies that the method returns a non-null result with exactly the expected number of fields.
    void getFullNameAndGender_shouldReturnNonNullData() {
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFullNameAndGender();

        assertNotNull(personData);
        assertEquals(NUMBER_OF_FIELDS, personData.size());
    }

    @ParameterizedTest
    @MethodSource("expectedFieldNames")
        // Verifies that each required field is present in the result.
    void getFullNameAndGender_shouldContainExpectedFields(String fieldName) {
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFullNameAndGender();

        assertTrue(personData.containsKey(fieldName));
    }

    @ParameterizedTest
    @MethodSource("unexpectedFieldNames")
        // Verifies that fields from other functionalities are not included.
    void getFullNameAndGender_shouldNotContainUnexpectedFields(String fieldName) {
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFullNameAndGender();

        assertFalse(personData.containsKey(fieldName));
    }

    @ParameterizedTest
    @MethodSource("expectedFieldNames")
        // Verifies that all required fields have non-null values.
    void getFullNameAndGender_shouldReturnNoNullValues(String fieldName) {
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFullNameAndGender();

        assertNotNull(personData.get(fieldName));
    }

    @Test
        // Verifies that generated names are not blank.
    void getFullNameAndGender_shouldReturnNonEmptyNames() {
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFullNameAndGender();

        String firstName = personData.get("firstName").toString();
        String lastName = personData.get("lastName").toString();

        assertFalse(firstName.isBlank());
        assertFalse(lastName.isBlank());
    }

    @Test
        // Verifies that gender is within the allowed domain.
    void getFullNameAndGender_shouldReturnValidGender() {
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFullNameAndGender();

        String gender = personData.get("gender").toString();

        assertTrue(gender.equals("male") || gender.equals("female"));
    }

    @Test
        // Verifies that generated names do not contain digits or disallowed special characters.
    void getFullNameAndGender_shouldReturnNamesWithoutDigitsOrSpecialCharacters() {
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFullNameAndGender();

        String firstName = personData.get("firstName").toString();
        String lastName = personData.get("lastName").toString();

        assertFalse(firstName.matches(".*[0-9,!@%^$&#].*"));
        assertFalse(lastName.matches(".*[0-9,!@%^$&#].*"));
    }

    @RepeatedTest(10)
        // Verifies that the output contract is respected across repeated executions.
    void getFullNameAndGender_shouldConsistentlyRespectContract() {
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFullNameAndGender();

        assertNotNull(personData);
        assertEquals(NUMBER_OF_FIELDS, personData.size());

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
}