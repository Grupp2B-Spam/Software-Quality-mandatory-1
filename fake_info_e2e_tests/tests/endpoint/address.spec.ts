import { test, expect } from '@playwright/test';

test.describe("address endpoint", ()=> {

    test('Valid GET request returning address', async ({ request }) => {
        const response = await request.get(process.env.API_URL + 'address');
        expect(response.status()).toBe(200);
        const data = await response.json();
        expect(data).toMatchObject({
            address: expect.objectContaining({
                number: expect.any(String),
                door: expect.any(String),
                town_name: expect.any(String),
                street: expect.any(String),
                floor: expect.anything(),
                postal_code: expect.any(String)
            })
        })

    });

    test('Invalid method', async ({ request }) => {
        const response = await request.post(process.env.API_URL + 'address');
        expect(response.status()).toBe(405);
        const data = await response.json();
        expect(data['error']).toContain("Method not allowed")

    });

    test('Invalid endpoint', async ({ request }) => {
        const response = await request.post(process.env.API_URL + 'address');
        expect(response.status()).toBe(404);
        const data = await response.json();
        expect(data['error']).toContain("Incorrect API endpoint")

    });


})

