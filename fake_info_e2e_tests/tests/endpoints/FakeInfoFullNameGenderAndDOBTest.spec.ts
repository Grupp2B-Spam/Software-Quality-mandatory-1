import { test, expect } from '@playwright/test';

test.describe('FakeInfoController /name-gender-dob endpoint', () => {
    const endpoint = process.env.API_URL + 'name-gender-dob';
    const expectedFields = ['firstName', 'lastName', 'gender', 'birthDate'];

    test('should return valid full name, gender and birthDate data', async ({ request }) => {
        const response = await request.get(endpoint);

        expect(response.status()).toBe(200);
        expect(response.ok()).toBeTruthy();

        const personData = await response.json();

        // Basic checks
        expect(personData).not.toBeNull();
        expect(typeof personData).toBe('object');

        // Must contain exactly 4 fields
        expect(Object.keys(personData)).toHaveLength(4);

        // Required fields
        for (const field of expectedFields) {
            expect(personData).toHaveProperty(field);
            expect(personData[field]).not.toBeNull();
        }

        // Must NOT contain unrelated fields
        expect(personData).not.toHaveProperty('CPR');
        expect(personData).not.toHaveProperty('address');
        expect(personData).not.toHaveProperty('phoneNumber');

        // Validate names
        const firstName = String(personData.firstName);
        const lastName = String(personData.lastName);
        const gender = String(personData.gender);
        const birthDate = String(personData.birthDate);

        expect(firstName.trim().length).toBeGreaterThan(0);
        expect(lastName.trim().length).toBeGreaterThan(0);

        expect(firstName).not.toMatch(/[0-9,!@%^$&#]/);
        expect(lastName).not.toMatch(/[0-9,!@%^$&#]/);

        // Validate gender
        expect(['male', 'female']).toContain(gender);

        // Validate birthDate format (YYYY-MM-DD)
        const parts = birthDate.split('-');
        expect(parts.length).toBe(3);
        expect(parts[0]).toMatch(/^\d{4}$/); // year
        expect(parts[1]).toMatch(/^\d{2}$/); // month
        expect(parts[2]).toMatch(/^\d{2}$/); // day
    });

    test('should consistently respect contract over multiple calls', async ({ request }) => {
        for (let i = 0; i < 10; i++) {
            const response = await request.get(endpoint);

            expect(response.status()).toBe(200);

            const personData = await response.json();

            expect(Object.keys(personData)).toHaveLength(4);
            expect(personData).toHaveProperty('firstName');
            expect(personData).toHaveProperty('lastName');
            expect(personData).toHaveProperty('gender');
            expect(personData).toHaveProperty('birthDate');

            const firstName = String(personData.firstName);
            const lastName = String(personData.lastName);
            const gender = String(personData.gender);
            const birthDate = String(personData.birthDate);

            expect(firstName.trim().length).toBeGreaterThan(0);
            expect(lastName.trim().length).toBeGreaterThan(0);
            expect(['male', 'female']).toContain(gender);

            const parts = birthDate.split('-');
            expect(parts.length).toBe(3);
        }

        test('name-gender-dob should return 404 for invalid endpoint', async ({ request }) => {
            const response = await request.get(process.env.API_URL + 'name-gender-dob-wrong');

            expect(response.status()).toBe(404);

            const body = await response.json();
            expect(body).toHaveProperty('error');
            expect(body.error).toBe('Incorrect API endpoint');
        });
    });
});