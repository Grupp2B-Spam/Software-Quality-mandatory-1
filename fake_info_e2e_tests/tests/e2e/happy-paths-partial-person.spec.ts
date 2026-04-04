import { test, expect } from '@playwright/test';

test.beforeEach(async ({ page }) => {
  await page.goto('');
  await expect(page.getByRole('heading')).toContainText('Fake Personal Data Generator');

  await page.getByRole('radio', { name: 'Partial generation:' }).check();
  await expect(page.getByRole('radio', { name: 'Partial generation:' })).toBeChecked();

  await expect(page.locator('#cmbPartialOptions')).toHaveValue('cpr');
});

test('Get CPR', async ({ page }) => {
  await page.getByRole('button', { name: 'Generate' }).click();

  await expect(page.locator('#output')).toBeVisible();
  await expect(page.getByText('CPR:')).toBeVisible();
  await expect(page.getByText('First name:')).not.toBeVisible();
  await expect(page.getByText('Last name:')).not.toBeVisible();
  await expect(page.getByText('Gender:')).not.toBeVisible();
  await expect(page.getByText('Date of birth:')).not.toBeVisible();
  await expect(page.locator('#output').getByText('Address')).not.toBeVisible();
  await expect(page.getByText('Phone number:')).not.toBeVisible();

  //There is a 0.01 difference allowed in the pixels to account for randomness
  await expect(page).toHaveScreenshot('cpr visible.png');
});

test('Get name and gender', async ({ page }) => {
  await page.locator('#cmbPartialOptions').selectOption('name-gender');
  await expect(page.locator('#cmbPartialOptions')).toHaveValue('name-gender');

  await page.getByRole('button', { name: 'Generate' }).click();

  await expect(page.locator('#output')).toBeVisible();
  await expect(page.getByText('CPR:')).not.toBeVisible();
  await expect(page.getByText('First name:')).toBeVisible();
  await expect(page.getByText('Last name:')).toBeVisible();
  await expect(page.getByText('Gender:')).toBeVisible();
  await expect(page.getByText('Date of birth:')).not.toBeVisible();
  await expect(page.locator('#output').getByText('Address')).not.toBeVisible();
  await expect(page.getByText('Phone number:')).not.toBeVisible();

  //There is a 0.01 difference allowed in the pixels to account for randomness
  await expect(page).toHaveScreenshot('Name-gender visible.png');
});

test('Get name, gender and birthdate', async ({ page }) => {

  await page.locator('#cmbPartialOptions').selectOption('name-gender-dob');
  await expect(page.locator('#cmbPartialOptions')).toHaveValue('name-gender-dob');

  await page.getByRole('button', { name: 'Generate' }).click();

  await expect(page.locator('#output')).toBeVisible();
  await expect(page.getByText('CPR:')).not.toBeVisible();
  await expect(page.getByText('First name:')).toBeVisible();
  await expect(page.getByText('Last name:')).toBeVisible();
  await expect(page.getByText('Gender:')).toBeVisible();
  await expect(page.getByText('Date of birth:')).toBeVisible();
  await expect(page.locator('#output').getByText('Address')).not.toBeVisible();
  await expect(page.getByText('Phone number:')).not.toBeVisible();

  //There is a 0.01 difference allowed in the pixels to account for randomness
  await expect(page).toHaveScreenshot('Name-gender-birth visible.png');
});

test('Get CPR, name and gender', async ({ page }) => {
  await page.locator('#cmbPartialOptions').selectOption('cpr-name-gender');
  await expect(page.locator('#cmbPartialOptions')).toHaveValue('cpr-name-gender');

  await page.getByRole('button', { name: 'Generate' }).click();

  await expect(page.locator('#output')).toBeVisible();
  await expect(page.getByText('CPR:')).toBeVisible();
  await expect(page.getByText('First name:')).toBeVisible();
  await expect(page.getByText('Last name:')).toBeVisible();
  await expect(page.getByText('Gender:')).toBeVisible();
  await expect(page.getByText('Date of birth:')).not.toBeVisible();
  await expect(page.locator('#output').getByText('Address')).not.toBeVisible();
  await expect(page.getByText('Phone number:')).not.toBeVisible();

  //There is a 0.01 difference allowed in the pixels to account for randomness
  await expect(page).toHaveScreenshot('Cpr-Name-gender visible.png');
});

test('Get CPR, name, gender and birthdate', async ({ page }) => {
  await page.locator('#cmbPartialOptions').selectOption('cpr-name-gender-dob');
  await expect(page.locator('#cmbPartialOptions')).toHaveValue('cpr-name-gender-dob');

  await page.getByRole('button', { name: 'Generate' }).click();

  await expect(page.locator('#output')).toBeVisible();
  await expect(page.getByText('CPR:')).toBeVisible();
  await expect(page.getByText('First name:')).toBeVisible();
  await expect(page.getByText('Last name:')).toBeVisible();
  await expect(page.getByText('Gender:')).toBeVisible();
  await expect(page.getByText('Date of birth:')).toBeVisible();
  await expect(page.locator('#output').getByText('Address')).not.toBeVisible();
  await expect(page.getByText('Phone number:')).not.toBeVisible();

  //There is a 0.01 difference allowed in the pixels to account for randomness
  await expect(page).toHaveScreenshot('Cpr-Name-gender-birth visible.png');
});

test('Get Address', async ({ page }) => {
  await page.locator('#cmbPartialOptions').selectOption('address');
  await expect(page.locator('#cmbPartialOptions')).toHaveValue('address');

  await page.getByRole('button', { name: 'Generate' }).click();

  await expect(page.locator('#output')).toBeVisible();
  await expect(page.getByText('CPR:')).not.toBeVisible();
  await expect(page.getByText('First name:')).not.toBeVisible();
  await expect(page.getByText('Last name:')).not.toBeVisible();
  await expect(page.getByText('Gender:')).not.toBeVisible();
  await expect(page.getByText('Date of birth:')).not.toBeVisible();
  await expect(page.locator('#output').getByText('Address')).toBeVisible();
  await expect(page.getByText('Phone number:')).not.toBeVisible();

  //There is a 0.01 difference allowed in the pixels to account for randomness
  await expect(page).toHaveScreenshot('address visible.png');
});

test('Get Phonenumber', async ({ page }) => {
  await page.locator('#cmbPartialOptions').selectOption('phone');
  await expect(page.locator('#cmbPartialOptions')).toHaveValue('phone');

  await page.getByRole('button', { name: 'Generate' }).click();

  await expect(page.locator('#output')).toBeVisible();
  await expect(page.getByText('CPR:')).not.toBeVisible();
  await expect(page.getByText('First name:')).not.toBeVisible();
  await expect(page.getByText('Last name:')).not.toBeVisible();
  await expect(page.getByText('Gender:')).not.toBeVisible();
  await expect(page.getByText('Date of birth:')).not.toBeVisible();
  await expect(page.locator('#output').getByText('Address')).not.toBeVisible();
  await expect(page.getByText('Phone number:')).toBeVisible();


  //There is a 0.01 difference allowed in the pixels to account for randomness
  await expect(page).toHaveScreenshot('phone visible.png');
});