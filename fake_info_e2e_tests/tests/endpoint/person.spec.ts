import { test, expect } from '@playwright/test';

test.describe("person endpoint", ()=> {

    test('Valid GET request returning 1 person', async ({ request }) => {
        const response = await request.get(process.env.API_URL + 'person');
        expect(response.status()).toBe(200);
        const data = await response.json();
        expect(data).toMatchObject({
            firstName: expect.any(String),
            lastName: expect.any(String),
            CPR: expect.any(String),
            phoneNumber: expect.any(String),
            address: expect.objectContaining({
                number: expect.any(String),
                door: expect.anything(),
                town_name: expect.any(String),
                street: expect.any(String),
                floor: expect.anything(),
                postal_code: expect.any(String)
            })
        })
    });


    test('Valid GET request returning 2 people', async ({ request }) => {
        const response = await request.get(process.env.API_URL + 'person?n=2');
        expect(response.status()).toBe(200);
        const data = await response.json();
        expect(data).toEqual(
            expect.arrayContaining([
                expect.objectContaining({
                    firstName: expect.any(String),
                    lastName: expect.any(String),
                    CPR: expect.any(String),
                    phoneNumber: expect.any(String),
                    address: expect.objectContaining({
                        number: expect.any(String),
                        door: expect.anything(),
                        town_name: expect.any(String),
                        street: expect.any(String),
                        floor: expect.anything(),
                        postal_code: expect.any(String)
                    })
                })
            ])
        )
        expect(data).toHaveLength(2)
    });

    test('Valid GET request returning 99 people', async ({ request }) => {
        const response = await request.get(process.env.API_URL + 'person?n=99');
        expect(response.status()).toBe(200);
        const data = await response.json();
        expect(data).toEqual(
            expect.arrayContaining([
                expect.objectContaining({
                    firstName: expect.any(String),
                    lastName: expect.any(String),
                    CPR: expect.any(String),
                    phoneNumber: expect.any(String),
                    address: expect.objectContaining({
                        number: expect.any(String),
                        door: expect.anything(),
                        town_name: expect.any(String),
                        street: expect.any(String),
                        floor: expect.anything(),
                        postal_code: expect.any(String)
                    })
                })
            ])
        )
        expect(data).toHaveLength(99)
    });

    test('Valid GET request returning 100 people', async ({ request }) => {
        const response = await request.get(process.env.API_URL + 'person?n=100');
        expect(response.status()).toBe(200);
        const data = await response.json();
        expect(data).toEqual(
            expect.arrayContaining([
                expect.objectContaining({
                    firstName: expect.any(String),
                    lastName: expect.any(String),
                    CPR: expect.any(String),
                    phoneNumber: expect.any(String),
                    address: expect.objectContaining({
                        number: expect.any(String),
                        door: expect.anything(),
                        town_name: expect.any(String),
                        street: expect.any(String),
                        floor: expect.anything(),
                        postal_code: expect.any(String)
                    })
                })
            ])
        )
        expect(data).toHaveLength(100)
    });

    test('Valid GET request returning -1 person', async ({ request }) => {
        const response = await request.get(process.env.API_URL + 'person?n=-1');
        expect(response.status()).toBe(200);
        const data = await response.json();
        expect(data).toMatchObject({
            firstName: expect.any(String),
            lastName: expect.any(String),
            CPR: expect.any(String),
            phoneNumber: expect.any(String),
            address: expect.objectContaining({
                number: expect.any(String),
                door: expect.anything(),
                town_name: expect.any(String),
                street: expect.any(String),
                floor: expect.anything(),
                postal_code: expect.any(String)
            })
        })
    });

    test('Valid GET request returning -2 people', async ({ request }) => {
        const response = await request.get(process.env.API_URL + 'person?n=-2');
        expect(response.status()).toBe(200);
        const data = await response.json();
        expect(data).toEqual(
            expect.arrayContaining([
                expect.objectContaining({
                    firstName: expect.any(String),
                    lastName: expect.any(String),
                    CPR: expect.any(String),
                    phoneNumber: expect.any(String),
                    address: expect.objectContaining({
                        number: expect.any(String),
                        door: expect.anything(),
                        town_name: expect.any(String),
                        street: expect.any(String),
                        floor: expect.anything(),
                        postal_code: expect.any(String)
                    })
                })
            ])
        )
        expect(data).toHaveLength(2)
    });


    test('Invalid parameter n > 100', async ({ request }) => {
        const response = await request.get(process.env.API_URL + 'person?n=101');
        expect(response.status()).toBe(400);
        const data = await response.json();
        expect(data['error']).toContain("Incorrect GET parameter value")
    });

    test('Invalid parameter n = 0', async ({ request }) => {
        const response = await request.get(process.env.API_URL + 'person?n=0');
        expect(response.status()).toBe(400);
        const data = await response.json();
        expect(data['error']).toContain("Incorrect GET parameter value")
    });

    test('Invalid parameter not handled by the controller, n = abc', async ({ request }) => {
        const response = await request.get(process.env.API_URL + 'person?n=abc');
        expect(response.status()).toBe(400);
        const data = await response.json();
        expect(data['error']).toContain("Bad Request")
    });
    
    test('Invalid method', async ({ request }) => {
        const response = await request.post(process.env.API_URL + 'person');
        expect(response.status()).toBe(405);
        const data = await response.json();
        expect(data['error']).toContain("Method not allowed")
    });

    test('Invalid endpoint', async ({ request }) => {
        const response = await request.get(process.env.API_URL + 'persons');
        expect(response.status()).toBe(404);
        const data = await response.json();
        expect(data['error']).toContain("Incorrect API endpoint")
    });
})

