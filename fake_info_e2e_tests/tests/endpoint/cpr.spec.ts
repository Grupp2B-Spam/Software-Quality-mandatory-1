import { test, expect } from '@playwright/test';

test.describe("cpr endpoint", ()=> {

    test('Valid GET request returning cpr', async ({ request }) => {
        const response = await request.get(process.env.API_URL + 'cpr');
        expect(response.status()).toBe(200);
        const data = await response.json();
        expect(data).toMatchObject({
        CPR: expect.any(String)
        })

    });

    test('Invalid method', async ({ request }) => {
        const response = await request.post(process.env.API_URL + 'cpr');
        expect(response.status()).toBe(405);
        const data = await response.json();
        expect(data['error']).toContain("Method not allowed")

    });

    test('Invalid endpoint', async ({ request }) => {
        const response = await request.post(process.env.API_URL + 'cpr');
        expect(response.status()).toBe(404);
        const data = await response.json();
        expect(data['error']).toContain("Incorrect API endpoint")

    });


})

