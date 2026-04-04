import { test, expect } from '@playwright/test';

test.describe("phone endpoint", ()=> {

    test('Valid GET request returning phone number', async ({ request }) => {
        const response = await request.get('phone');
        expect(response.status()).toBe(200);
        const data = await response.json();
        expect(data).toMatchObject({
        phoneNumber: expect.any(String)
        })

    });

    test('Invalid method', async ({ request }) => {
        const response = await request.post('phone');
        expect(response.status()).toBe(405);
        const data = await response.json();
        expect(data['error']).toContain("Method not allowed")

    });

    test('Invalid endpoint', async ({ request }) => {
        const response = await request.post('phone');
        expect(response.status()).toBe(404);
        const data = await response.json();
        expect(data['error']).toContain("Incorrect API endpoint")

    });


})

