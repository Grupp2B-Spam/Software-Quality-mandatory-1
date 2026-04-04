import { test, expect } from '@playwright/test';

test.describe('FakeInfoController /name-gender endpoint', () => {
    test('should return valid full name and gender data', async ({ request }) => {
        const response = await request.get(process.env.API_URL + 'name-gender');

        expect(response.status()).toBe(200);
        expect(response.ok()).toBeTruthy();

        const personData = await response.json();

        expect(personData).not.toBeNull();
        expect(typeof personData).toBe('object');

        // Must contain exactly the expected fields
        expect(Object.keys(personData)).toHaveLength(3);
        expect(personData).toHaveProperty('firstName');
        expect(personData).toHaveProperty('lastName');
        expect(personData).toHaveProperty('gender');

        // Values must not be null
        expect(personData.firstName).not.toBeNull();
        expect(personData.lastName).not.toBeNull();
        expect(personData.gender).not.toBeNull();

        // Must not contain fields from other endpoints
        expect(personData).not.toHaveProperty('CPR');
        expect(personData).not.toHaveProperty('birthDate');
        expect(personData).not.toHaveProperty('address');
        expect(personData).not.toHaveProperty('phoneNumber');

        // Validate content
        const firstName = String(personData.firstName);
        const lastName = String(personData.lastName);
        const gender = String(personData.gender);

        expect(firstName.trim().length).toBeGreaterThan(0);
        expect(lastName.trim().length).toBeGreaterThan(0);

        expect(firstName).not.toMatch(/[0-9,!@%^$&#]/);
        expect(lastName).not.toMatch(/[0-9,!@%^$&#]/);

        expect(['male', 'female']).toContain(gender);
    });

    test('should consistently respect contract over multiple calls', async ({ request }) => {
        for (let i = 0; i < 10; i++) {
            const response = await request.get(process.env.API_URL + 'name-gender');

            expect(response.status()).toBe(200);
            expect(response.ok()).toBeTruthy();

            const personData = await response.json();

            expect(personData).not.toBeNull();
            expect(typeof personData).toBe('object');

            expect(Object.keys(personData)).toHaveLength(3);
            expect(personData).toHaveProperty('firstName');
            expect(personData).toHaveProperty('lastName');
            expect(personData).toHaveProperty('gender');

            const firstName = String(personData.firstName);
            const lastName = String(personData.lastName);
            const gender = String(personData.gender);

            expect(firstName.trim().length).toBeGreaterThan(0);
            expect(lastName.trim().length).toBeGreaterThan(0);
            expect(firstName).not.toMatch(/[0-9,!@%^$&#]/);
            expect(lastName).not.toMatch(/[0-9,!@%^$&#]/);
            expect(['male', 'female']).toContain(gender);
        }
            //Negative test for invalid endpoint
        test('should return 404 for invalid endpoint', async ({ request }) => {
            const response = await request.get(process.env.API_URL + 'name-gender-wrong');

            expect(response.status()).toBe(404);
        });
    });
});