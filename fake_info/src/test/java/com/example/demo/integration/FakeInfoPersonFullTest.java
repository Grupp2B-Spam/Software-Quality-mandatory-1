package com.example.demo.integration;

import com.example.demo.FakeInfo;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FakeInfoPersonFullTest {

    private static final int NUMBER_OF_PERSON_FIELDS = 7;

    private static final String CPR = "CPR";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String GENDER = "gender";
    private static final String BIRTH_DATE = "birthDate";
    private static final String ADDRESS = "address";
    private static final String PHONE_NUMBER = "phoneNumber";

    static Stream<String> expectedPersonFields() {
        return Stream.of(
                CPR,
                FIRST_NAME,
                LAST_NAME,
                GENDER,
                BIRTH_DATE,
                ADDRESS,
                PHONE_NUMBER
        );
    }

    static Stream<String> unexpectedPersonFields() {
        return Stream.of(
                "street",
                "number",
                "floor",
                "door",
                "postal_code",
                "town_name"
        );
    }

    @Test
        // Verifies that the generated person contains exactly the expected number of top-level fields.
    void getFakePerson_shouldContainExpectedNumberOfFields() {
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFakePerson();

        assertNotNull(personData);
        assertEquals(NUMBER_OF_PERSON_FIELDS, personData.size());
    }

    @ParameterizedTest
    @MethodSource("expectedPersonFields")
        // Verifies that each required top-level field is present.
    void getFakePerson_shouldContainExpectedField(String fieldName) {
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFakePerson();

        assertTrue(personData.containsKey(fieldName),
                "Expected field missing: " + fieldName);
    }

    @ParameterizedTest
    @MethodSource("expectedPersonFields")
        // Verifies that each required top-level field has a non-null value.
    void getFakePerson_shouldReturnNonNullValueForExpectedField(String fieldName) {
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFakePerson();

        assertNotNull(personData.get(fieldName),
                "Expected non-null value for field: " + fieldName);
    }

    @ParameterizedTest
    @MethodSource("unexpectedPersonFields")
        // Verifies that address subfields are not exposed as top-level fields.
    void getFakePerson_shouldNotContainUnexpectedTopLevelField(String fieldName) {
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFakePerson();

        assertFalse(personData.containsKey(fieldName),
                "Unexpected top-level field found: " + fieldName);
    }

    @Test
        // Verifies that first name and last name are not blank and do not contain digits or disallowed special characters.
    void getFakePerson_shouldReturnValidNames() {
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFakePerson();

        String firstName = personData.get(FIRST_NAME).toString();
        String lastName = personData.get(LAST_NAME).toString();

        assertFalse(firstName.isBlank());
        assertFalse(lastName.isBlank());

        assertFalse(firstName.matches(".*[0-9,!@%^$&#].*"));
        assertFalse(lastName.matches(".*[0-9,!@%^$&#].*"));
    }

    @Test
        // Verifies that gender is within the allowed domain.
    void getFakePerson_shouldReturnValidGender() {
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFakePerson();

        String gender = personData.get(GENDER).toString();

        assertTrue(gender.equals("male") || gender.equals("female"));
    }

    @Test
        // Verifies that CPR consists of exactly 10 digits and that the final digit matches the expected gender parity.
    void getFakePerson_shouldReturnValidCprMatchingGender() {
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFakePerson();

        String cpr = personData.get(CPR).toString();
        String gender = personData.get(GENDER).toString();

        assertTrue(cpr.matches("[0-9]{10}"),
                "Expected CPR to contain exactly 10 digits, but was " + cpr);

        int lastCprDigit = Character.getNumericValue(cpr.charAt(cpr.length() - 1));

        if (gender.equals("female")) {
            assertEquals(0, lastCprDigit % 2,
                    "Expected CPR to be even for female, but was " + cpr);
        }

        if (gender.equals("male")) {
            assertEquals(1, lastCprDigit % 2,
                    "Expected CPR to be odd for male, but was " + cpr);
        }
    }

    @Test
        // Verifies that birthDate is present and matches the date encoded in the first six digits of the CPR.
    void getFakePerson_shouldReturnBirthDateMatchingCpr() {
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFakePerson();

        String cpr = personData.get(CPR).toString();
        String birthDate = personData.get(BIRTH_DATE).toString();

        String expectedDay = cpr.substring(0, 2);
        String expectedMonth = cpr.substring(2, 4);
        String expectedYear = cpr.substring(4, 6);

        String[] birthDateParts = birthDate.split("-");

        assertEquals(3, birthDateParts.length,
                "Expected birthDate format yyyy-MM-dd, but was " + birthDate);

        String actualYear = birthDateParts[0].substring(2, 4);
        String actualMonth = birthDateParts[1];
        String actualDay = birthDateParts[2];

        assertEquals(expectedDay, actualDay,
                "Expected birth day to match CPR, but got " + birthDate);
        assertEquals(expectedMonth, actualMonth,
                "Expected birth month to match CPR, but got " + birthDate);
        assertEquals(expectedYear, actualYear,
                "Expected birth year to match CPR, but got " + birthDate);
    }

    @Test
        // Verifies that address is present and contains all expected subfields.
    void getFakePerson_shouldReturnValidAddressStructure() {
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFakePerson();

        Object addressObject = personData.get(ADDRESS);

        assertNotNull(addressObject);
        assertInstanceOf(Map.class, addressObject);

        @SuppressWarnings("unchecked")
        Map<String, Object> address = (Map<String, Object>) addressObject;

        assertTrue(address.containsKey("street"));
        assertTrue(address.containsKey("number"));
        assertTrue(address.containsKey("floor"));
        assertTrue(address.containsKey("door"));
        assertTrue(address.containsKey("postal_code"));
        assertTrue(address.containsKey("town_name"));

        assertNotNull(address.get("street"));
        assertNotNull(address.get("number"));
        assertNotNull(address.get("floor"));
        assertNotNull(address.get("door"));
        assertNotNull(address.get("postal_code"));
        assertNotNull(address.get("town_name"));
    }

    @Test
        // Verifies that phoneNumber has valid format and an allowed prefix from the assignment requirements.
    void getFakePerson_shouldReturnValidPhoneNumber() {
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFakePerson();

        String phoneNumber = personData.get(PHONE_NUMBER).toString();

        assertTrue(isValidPhoneNumber(phoneNumber),
                "Invalid phone number: " + phoneNumber);
    }

    @RepeatedTest(5)
        // Verifies across repeated executions that the generated person respects the expected overall contract.
    void getFakePerson_shouldConsistentlyRespectContract() {
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFakePerson();

        assertNotNull(personData);
        assertEquals(NUMBER_OF_PERSON_FIELDS, personData.size());

        for (String field : expectedPersonFields().toList()) {
            assertTrue(personData.containsKey(field),
                    "Expected field missing: " + field);
            assertNotNull(personData.get(field),
                    "Expected non-null value for field: " + field);
        }

        String cpr = personData.get(CPR).toString();
        String firstName = personData.get(FIRST_NAME).toString();
        String lastName = personData.get(LAST_NAME).toString();
        String gender = personData.get(GENDER).toString();
        String phoneNumber = personData.get(PHONE_NUMBER).toString();

        assertFalse(firstName.isBlank());
        assertFalse(lastName.isBlank());
        assertTrue(gender.equals("male") || gender.equals("female"));

        assertTrue(cpr.matches("[0-9]{10}"),
                "Expected CPR to contain exactly 10 digits, but was " + cpr);

        int lastCprDigit = Character.getNumericValue(cpr.charAt(cpr.length() - 1));
        if (gender.equals("female")) {
            assertEquals(0, lastCprDigit % 2);
        }
        if (gender.equals("male")) {
            assertEquals(1, lastCprDigit % 2);
        }

        assertTrue(isValidPhoneNumber(phoneNumber),
                "Invalid phone number: " + phoneNumber);
    }

    private boolean isValidPhoneNumber(String phone) {
        if (!phone.matches("[0-9]{8}")) {
            return false;
        }

        return phone.startsWith("2")
                || inRange(phone, 30, 31)
                || inRange(phone, 40, 42)
                || inRange(phone, 50, 53)
                || inRange(phone, 60, 61)
                || inRange(phone, 71, 71)
                || inRange(phone, 81, 81)
                || inRange(phone, 91, 93)
                || inRange(phone, 342, 342)
                || inRange(phone, 344, 349)
                || inRange(phone, 356, 357)
                || inRange(phone, 359, 359)
                || inRange(phone, 362, 362)
                || inRange(phone, 365, 366)
                || inRange(phone, 389, 389)
                || inRange(phone, 398, 398)
                || inRange(phone, 431, 431)
                || inRange(phone, 441, 441)
                || inRange(phone, 462, 462)
                || inRange(phone, 466, 466)
                || inRange(phone, 468, 468)
                || inRange(phone, 472, 472)
                || inRange(phone, 474, 474)
                || inRange(phone, 476, 476)
                || inRange(phone, 478, 478)
                || inRange(phone, 485, 486)
                || inRange(phone, 488, 489)
                || inRange(phone, 493, 496)
                || inRange(phone, 498, 499)
                || inRange(phone, 542, 543)
                || inRange(phone, 545, 545)
                || inRange(phone, 551, 552)
                || inRange(phone, 556, 556)
                || inRange(phone, 571, 574)
                || inRange(phone, 577, 577)
                || inRange(phone, 579, 579)
                || inRange(phone, 584, 584)
                || inRange(phone, 586, 587)
                || inRange(phone, 589, 589)
                || inRange(phone, 597, 598)
                || inRange(phone, 627, 627)
                || inRange(phone, 629, 629)
                || inRange(phone, 641, 641)
                || inRange(phone, 649, 649)
                || inRange(phone, 658, 658)
                || inRange(phone, 662, 665)
                || inRange(phone, 667, 667)
                || inRange(phone, 692, 694)
                || inRange(phone, 697, 697)
                || inRange(phone, 771, 772)
                || inRange(phone, 782, 783)
                || inRange(phone, 785, 786)
                || inRange(phone, 788, 789)
                || inRange(phone, 826, 827)
                || inRange(phone, 829, 829);
    }

    private boolean inRange(String phone, int start, int end) {
        int prefixLength = String.valueOf(start).length();
        int prefix = Integer.parseInt(phone.substring(0, prefixLength));
        return prefix >= start && prefix <= end;
    }
}