package com.example.demo.integration;

import com.example.demo.FakeInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FakeInfoPersonBulkTest {

    private static final int NUMBER_OF_PERSON_FIELDS = 7;

    private List<String> expectedFields = Stream.of(
            "CPR","firstName","lastName","gender","birthDate","address","phoneNumber").toList();
    private static final String CPR = "CPR";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String GENDER = "gender";
    private static final String BIRTH_DATE = "birthDate";
    private static final String ADDRESS = "address";
    private static final String PHONE_NUMBER = "phoneNumber";

    static Stream<Integer> validBulkAmounts() {
        return Stream.of(2, 5, 10);
    }

    static Stream<AmountCase> standardBulkNormalizationCases() {
        return Stream.of(
                new AmountCase(1, 2),
                new AmountCase(2, 2),
                new AmountCase(5, 5)
        );
    }

    static Stream<AmountCase> upperBoundaryBulkNormalizationCases() {
        return Stream.of(
                new AmountCase(100, 100),
                new AmountCase(101, 100)
        );
    }

    @ParameterizedTest
    @MethodSource("standardBulkNormalizationCases")
        // Verifies that bulk generation normalizes standard edge cases correctly.
    void getFakePersons_shouldReturnExpectedAmount_forStandardCases(AmountCase amountCase) {
        FakeInfo person = new FakeInfo();

        List<Map<String, Object>> persons = person.getFakePersons(amountCase.requestedAmount());

        assertNotNull(persons);
        assertEquals(amountCase.expectedAmount(), persons.size(),
                "Expected " + amountCase.expectedAmount()
                        + " persons for request " + amountCase.requestedAmount());
    }

    @ParameterizedTest
    @MethodSource("upperBoundaryBulkNormalizationCases")
        // Verifies that bulk generation normalizes upper-boundary cases correctly.
    void getFakePersons_shouldReturnExpectedAmount_forLargeCases(AmountCase amountCase) {
        FakeInfo person = new FakeInfo();

        List<Map<String, Object>> persons = person.getFakePersons(amountCase.requestedAmount());

        assertNotNull(persons);
        assertEquals(amountCase.expectedAmount(), persons.size(),
                "Expected " + amountCase.expectedAmount()
                        + " persons for request " + amountCase.requestedAmount());
    }

    @ParameterizedTest
    @MethodSource("validBulkAmounts")
        // Verifies that valid bulk requests return the requested number of persons.
    void getFakePersons_shouldReturnRequestedAmountForValidInputs(int requestedAmount) {
        FakeInfo person = new FakeInfo();

        List<Map<String, Object>> persons = person.getFakePersons(requestedAmount);

        assertNotNull(persons);
        assertEquals(requestedAmount, persons.size(),
                "Expected " + requestedAmount + " persons");
    }

    @ParameterizedTest
    @MethodSource("validBulkAmounts")
        // Verifies that each generated person in a bulk result respects the basic expected structure.
    void getFakePersons_shouldReturnStructurallyValidPersonObjects(int requestedAmount) {
        FakeInfo person = new FakeInfo();

        List<Map<String, Object>> persons = person.getFakePersons(requestedAmount);

        assertNotNull(persons);
        assertEquals(requestedAmount, persons.size());

        for (Map<String, Object> personData : persons) {
            assertNotNull(personData);
            assertEquals(NUMBER_OF_PERSON_FIELDS, personData.size());

            assertTrue(personData.containsKey(CPR));
            assertTrue(personData.containsKey(FIRST_NAME));
            assertTrue(personData.containsKey(LAST_NAME));
            assertTrue(personData.containsKey(GENDER));
            assertTrue(personData.containsKey(BIRTH_DATE));
            assertTrue(personData.containsKey(ADDRESS));
            assertTrue(personData.containsKey(PHONE_NUMBER));

            assertNotNull(personData.get(CPR));
            assertNotNull(personData.get(FIRST_NAME));
            assertNotNull(personData.get(LAST_NAME));
            assertNotNull(personData.get(GENDER));
            assertNotNull(personData.get(BIRTH_DATE));
            assertNotNull(personData.get(ADDRESS));
            assertNotNull(personData.get(PHONE_NUMBER));

            String cpr = personData.get(CPR).toString();
            String firstName = personData.get(FIRST_NAME).toString();
            String lastName = personData.get(LAST_NAME).toString();
            String gender = personData.get(GENDER).toString();
            String birthDate = personData.get(BIRTH_DATE).toString();
            String phoneNumber = personData.get(PHONE_NUMBER).toString();

            assertFalse(firstName.isBlank());
            assertFalse(lastName.isBlank());

            assertFalse(firstName.matches(".*[0-9,!@%^$&#].*"));
            assertFalse(lastName.matches(".*[0-9,!@%^$&#].*"));

            assertTrue(gender.equals("male") || gender.equals("female"));

            assertTrue(cpr.matches("[0-9]{10}"), "Expected CPR to contain exactly 10 digits, but was " + cpr);

            assertTrue(birthDate.matches("\\d{4}-\\d{2}-\\d{2}"), "Expected birthDate format yyyy-MM-dd, but was " + birthDate);

            assertTrue(phoneNumber.matches("[0-9]{8}"), "Expected phone number to contain exactly 8 digits, but was " + phoneNumber);
        }
    }

    void getFakePersons_shouldReturnStructurallyValidPersonObjects_improvement(int requestedAmount) {
        FakeInfo person = new FakeInfo();

        List<Map<String, Object>> persons = person.getFakePersons(requestedAmount);

        assertNotNull(persons);
        assertEquals(requestedAmount, persons.size());

        for (Map<String, Object> personData : persons) {
            assertNotNull(personData);
            // every expected field is present and the list is the same size.
            assertEquals(expectedFields.size(), personData.size());
            assertTrue(personData.keySet().containsAll(expectedFields), "Expected all required fields to be present");

            for (Map.Entry<String, Object> entry : personData.entrySet()) {
                String fieldName = entry.getKey();
                Object value = entry.getValue();
                assertNotNull(value, "Expected the value for field " + fieldName + " to not be null");
                assertFalse(value.toString().isBlank(), "Expected field " + fieldName + " to not be blank");
            }

            String cpr = personData.get(CPR).toString();
            String firstName = personData.get(FIRST_NAME).toString();
            String lastName = personData.get(LAST_NAME).toString();
            String gender = personData.get(GENDER).toString();
            String birthDate = personData.get(BIRTH_DATE).toString();
            String phoneNumber = personData.get(PHONE_NUMBER).toString();

            assertTrue(persons.stream()
                            .map(data -> data.get(CPR).toString())
                            .distinct()
                            .count() > 1,
                    "Expected generated persons to not all be identical"
            );

            assertFalse(firstName.matches(".*[0-9,!@%^$&#].*"));
            assertFalse(lastName.matches(".*[0-9,!@%^$&#].*"));

            assertTrue(gender.equals("male") || gender.equals("female"),"We only operate with male and female not: "+gender);

            assertTrue(cpr.matches("[0-9]{10}"), "Expected CPR to contain exactly 10 digits, but was " + cpr);

            assertTrue(birthDate.matches("\\d{4}-\\d{2}-\\d{2}"), "Expected birthDate format yyyy-MM-dd, but was " + birthDate);

            assertTrue(phoneNumber.matches("[0-9]{8}"), "Expected phone number to contain exactly 8 digits, but was " + phoneNumber);
        }
    }

    record AmountCase(int requestedAmount, int expectedAmount) {}
}