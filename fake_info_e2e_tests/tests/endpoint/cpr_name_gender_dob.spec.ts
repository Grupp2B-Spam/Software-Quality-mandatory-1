import { test, expect } from '@playwright/test';

test.describe("cpr-name-gender-dob endpoint", ()=> {

  test('Valid GET request returning cpr, name and gender and date of birth', async ({ request }) => {
    const response = await request.get('cpr-name-gender-dob');
    expect(response.status()).toBe(200);
    const data = await response.json();
    expect(data).toMatchObject({
      firstName: expect.any(String),
      lastName: expect.any(String),
      CPR: expect.any(String),
      birthDate: expect.any(String)
    })

  });

    test('Invalid method', async ({ request }) => {
    const response = await request.post('cpr-name-gender-dob');
    expect(response.status()).toBe(405);
    const data = await response.json();
    expect(data['error']).toContain("Method not allowed")

  });

      test('Invalid endpoint', async ({ request }) => {
    const response = await request.post('cprnamegender-dob');
    expect(response.status()).toBe(404);
    const data = await response.json();
    expect(data['error']).toContain("Incorrect API endpoint")

  });


})

