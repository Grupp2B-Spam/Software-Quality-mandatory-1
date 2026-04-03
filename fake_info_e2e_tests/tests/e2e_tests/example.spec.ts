import { test, expect } from '@playwright/test';
import { beforeEach } from 'node:test';

test.beforeEach(async ({ page }) => {
  page.goto('');
  await expect(page.getByRole('heading')).toContainText('Fake Personal Data Generator');

});

test('Get CPR', async ({ page }) => {
  await page.getByRole('radio', { name: 'Partial generation:' }).check();
  await expect(page.getByRole('radio', { name: 'Partial generation:' })).toBeChecked();

  await expect(page.locator('#cmbPartialOptions')).toHaveValue('cpr');
  await page.getByRole('button', { name: 'Generate' }).click();
  await expect(page.locator('#output').getByText('CPR:')).toBeVisible();
  /*
    There is an issue with the playwright locator object sometimes getting caught by the snapshot
    seems like the system gets "stuck"
    TODO: find a fix for this issue.
  */
  await expect(page).toHaveScreenshot('cpr visible.png');
});

test('Get name and gender', async ({ page }) => {

});

test('Get name, gender and birthdate', async ({ page }) => {

});

test('Get CPR, name and gender', async ({ page }) => {

});

test('Get CPR, name, gender and birthdate', async ({ page }) => {

});

test('Get Address', async ({ page }) => {

});

test('Get Phonenumber', async ({ page }) => {

});


test('Get full person', async ({ page }) => {

});