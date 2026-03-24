package com.example.demo.integration;

import com.example.demo.FakeInfo;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FakeInfoPersonTest {

    int numberOfPersonFields = 7;

    @Test
        // Verifies that a single fake person contains all expected fields.
    void getFakePerson_shouldContainExpectedFields() {
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFakePerson();

        assertNotNull(personData);
        assertEquals(numberOfPersonFields, personData.size());
        assertTrue(personData.containsKey("CPR"));
        assertTrue(personData.containsKey("firstName"));
        assertTrue(personData.containsKey("lastName"));
        assertTrue(personData.containsKey("gender"));
        assertTrue(personData.containsKey("birthDate"));
        assertTrue(personData.containsKey("address"));
        assertTrue(personData.containsKey("phoneNumber"));
    }

    @Test
        // Verifies that a single fake person does not contain null values in required fields.
    void getFakePerson_shouldReturnNoNullValues() {
        FakeInfo person = new FakeInfo();

        Map<String, Object> personData = person.getFakePerson();

        assertNotNull(personData.get("CPR"));
        assertNotNull(personData.get("firstName"));
        assertNotNull(personData.get("lastName"));
        assertNotNull(personData.get("gender"));
        assertNotNull(personData.get("birthDate"));
        assertNotNull(personData.get("address"));
        assertNotNull(personData.get("phoneNumber"));
    }

    @Test
        // Verifies that bulk generation returns the requested minimum valid amount.
    void getFakePersons_shouldReturnTwoPersons() {
        FakeInfo person = new FakeInfo();

        List<Map<String, Object>> persons = person.getFakePersons(2);

        assertNotNull(persons);
        assertEquals(2, persons.size());
    }

    @Test
        // Verifies that bulk generation returns the requested maximum valid amount.
    void getFakePersons_shouldReturnOneHundredPersons() {
        FakeInfo person = new FakeInfo();

        List<Map<String, Object>> persons = person.getFakePersons(100);

        assertNotNull(persons);
        assertEquals(100, persons.size());
    }

    @Test
        // Verifies that values below the minimum bulk size are normalized to 2.
    void getFakePersons_shouldNormalizeAmountBelowMinimum() {
        FakeInfo person = new FakeInfo();

        List<Map<String, Object>> persons = person.getFakePersons(1);

        assertNotNull(persons);
        assertEquals(2, persons.size());
    }

    @Test
        // Verifies that values above the maximum bulk size are normalized to 100.
    void getFakePersons_shouldNormalizeAmountAboveMaximum() {
        FakeInfo person = new FakeInfo();

        List<Map<String, Object>> persons = person.getFakePersons(101);

        assertNotNull(persons);
        assertEquals(100, persons.size());
    }

    @Test
        // Verifies that each generated person in a bulk result respects the expected contract.
    void getFakePersons_shouldReturnValidPersonObjects() {
        FakeInfo person = new FakeInfo();

        List<Map<String, Object>> persons = person.getFakePersons(5);

        assertEquals(5, persons.size());

        for (Map<String, Object> personData : persons) {
            assertNotNull(personData);
            assertEquals(numberOfPersonFields, personData.size());

            assertTrue(personData.containsKey("CPR"));
            assertTrue(personData.containsKey("firstName"));
            assertTrue(personData.containsKey("lastName"));
            assertTrue(personData.containsKey("gender"));
            assertTrue(personData.containsKey("birthDate"));
            assertTrue(personData.containsKey("address"));
            assertTrue(personData.containsKey("phoneNumber"));

            assertFalse(personData.get("firstName").toString().isBlank());
            assertFalse(personData.get("lastName").toString().isBlank());

            assertFalse(personData.get("firstName").toString().matches(".*[0-9,!@%^$&#].*"));
            assertFalse(personData.get("lastName").toString().matches(".*[0-9,!@%^$&#].*"));

            String gender = personData.get("gender").toString();
            assertTrue(gender.equals("male") || gender.equals("female"));
        }
    }
}