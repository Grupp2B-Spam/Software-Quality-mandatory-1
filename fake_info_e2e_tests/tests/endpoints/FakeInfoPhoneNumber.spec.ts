import { test, expect } from '@playwright/test';

test.describe('FakeInfoController /phone endpoint', () => {
    const endpoint = 'phone';

    test('should return a valid phone number', async ({ request }) => {
        const response = await request.get(endpoint);

        expect(response.status()).toBe(200);
        expect(response.ok()).toBeTruthy();

        const personData = await response.json();

        expect(personData).not.toBeNull();
        expect(typeof personData).toBe('object');

        // Should contain exactly one field
        expect(Object.keys(personData)).toHaveLength(1);

        // Should contain expected field
        expect(personData).toHaveProperty('phoneNumber');
        expect(personData.phoneNumber).not.toBeNull();

        const phoneNumber = String(personData.phoneNumber);

        // Must be exactly 8 digits
        expect(phoneNumber).toMatch(/^[0-9]{8}$/);

        // Must have a valid Danish prefix
        expect(isValidPhoneNumber(phoneNumber)).toBeTruthy();
    });

    test('should consistently return valid phone numbers over multiple calls', async ({ request }) => {
        for (let i = 0; i < 10; i++) {
            const response = await request.get(endpoint);

            expect(response.status()).toBe(200);

            const personData = await response.json();

            expect(personData).toHaveProperty('phoneNumber');

            const phoneNumber = String(personData.phoneNumber);

            expect(phoneNumber).toMatch(/^[0-9]{8}$/);
            expect(isValidPhoneNumber(phoneNumber)).toBeTruthy();
        }
    });

    test('should return 404 for invalid phone endpoint', async ({ request }) => {
        const response = await request.get('phone-invalid');

        expect(response.status()).toBe(404);

        const body = await response.json();
        expect(body).toHaveProperty('error');
        expect(body.error).toBe('Incorrect API endpoint');
    });
});

function isValidPhoneNumber(phone: string): boolean {
    if (!/^[0-9]{8}$/.test(phone)) {
        return false;
    }

    return phone.startsWith('2')
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

function inRange(phone: string, start: number, end: number): boolean {
    const prefixLength = String(start).length;
    const prefix = Number.parseInt(phone.substring(0, prefixLength), 10);
    return prefix >= start && prefix <= end;
}