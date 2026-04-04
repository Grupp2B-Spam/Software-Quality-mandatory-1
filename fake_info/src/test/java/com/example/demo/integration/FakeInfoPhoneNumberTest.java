package com.example.demo.integration;

import com.example.demo.FakeInfo;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FakeInfoPhoneNumberTest {

    private static final String PHONE_NUMBER = "phoneNumber";

    @Test
        // Verifies that the method returns a non-null result object.
    void getPhoneNumber_shouldReturnNonNullData(){
        FakeInfo person = new FakeInfo();

        String phoneNumber = person.getPhoneNumber();

        assertNotNull(phoneNumber);
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
