import { test, expect } from '@playwright/test';

test.beforeEach(async ({ page }) => {
  await page.goto('');
  await expect(page.getByRole('heading')).toContainText('Fake Personal Data Generator');

  await expect(page.getByRole('radio', { name: 'person(s)' })).toBeChecked();
});

test('Get full person', async ({ page }) => {
    await page.locator('#txtNumberPersons').fill('1');

  await page.getByRole('button', { name: 'Generate' }).click();

  await expect(page.locator('#output')).toBeVisible();
  await expect(page.getByText('CPR:')).toBeVisible();
  await expect(page.getByText('CPR:')).toHaveCount(1);

  await expect(page.getByText('First name:')).toBeVisible();
  await expect(page.getByText('First name:')).toHaveCount(1);

  await expect(page.getByText('Last name:')).toBeVisible();
  await expect(page.getByText('Last name:')).toHaveCount(1);

  await expect(page.getByText('Gender:')).toBeVisible();
  await expect(page.getByText('Gender:')).toHaveCount(1);

  await expect(page.getByText('Date of birth:')).toBeVisible();
  await expect(page.getByText('Date of birth:')).toHaveCount(1);

  await expect(page.locator('#output').getByText('Address')).toBeVisible();
  await expect(page.locator('#output').getByText('Address')).toHaveCount(1);

  await expect(page.getByText('Phone number:')).toBeVisible();
  await expect(page.getByText('Phone number:')).toHaveCount(1);

  //There is a 0.01 difference allowed in the pixels to account for randomness
  await expect(page).toHaveScreenshot('person visible.png');
});

test('Get random number of full people', async ({ page }) => {

    let amount = Math.floor(Math.random() * 100) + 1;
    await page.locator('#txtNumberPersons').fill(amount.toString());

    await expect(page.locator('#txtNumberPersons')).toHaveValue(amount.toString());
    await page.getByRole('button', { name: 'Generate' }).click();

    await expect(page.getByText('CPR:').filter({visible: true})).toHaveCount(amount);

    await expect(page.getByText('First name:').filter({visible: true})).toHaveCount(amount);

    await expect(page.getByText('Last name:').filter({visible: true})).toHaveCount(amount);

    await expect(page.getByText('Gender:').filter({visible: true})).toHaveCount(amount);

    await expect(page.getByText('Date of birth:').filter({visible: true})).toHaveCount(amount);

    await expect(page.locator('#output').getByText('Address').filter({visible: true})).toHaveCount(amount);

    await expect(page.getByText('Phone number:').filter({visible: true})).toHaveCount(amount);
});