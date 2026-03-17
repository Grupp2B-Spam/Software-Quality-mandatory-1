# Fake Data Generator

## Purpose
Sample PHP object-oriented REST API that generates fake data of nonexistent Danish persons.

## Dependencies

- The fake persons' first name, last name, and gender are extracted from the file `data/person-names.json`.
- The fake persons' postal code and town are extracted from the MariaDB/MySQL database `addresses`.

## Usage
- Start: `docker compose up --build -d`
- Stop: `docker compose down -v`

## API Endpoints
|Method|Endpoint|
|------|--------|
|GET|/cpr|
|GET|/name-gender|
|GET|/name-gender-dob|
|GET|/cpr-name-gender|
|GET|/cpr-name-gender-dob|
|GET|/address|
|GET|/phone|
|GET|/person|
|GET|/person?n=<number_of_fake_persons>|

## API Sample Output
`GET /cpr`
```json
{
    "CPR": "0412489054"
}
```

`GET /person`
```json
{
    "CPR": "0107832911",
    "firstName": "Michelle W.",
    "lastName": "Henriksen",
    "gender": "female",
    "birthDate": "1983-07-01",
    "address": {
        "street": "GYØVCoØMeceOjøtÆgvYrøQQDascNFCHArnSNrxub",
        "number": "521",
        "floor": 74,
        "door": "tv",
        "postal_code": "8670",
        "town_name": "Låsby"
    },
    "phoneNumber": "58676658"
}
```

`GET /person&n=3`
```json
[
    {
        "CPR": "2411576095",
        "firstName": "Laurits S.",
        "lastName": "Kjeldsen",
        "gender": "male",
        "birthDate": "1957-11-24",
        "address": {
            "street": "aÅGgøhIbJXVsRÆøjLnåæFoXtsgU Ø NINFYwBnaø",
            "number": "413",
            "floor": 46,
            "door": "tv",
            "postal_code": "8700",
            "town_name": "Horsens"
        },
        "phoneNumber": "35753186"
    },
    {
        "CPR": "1008114708",
        "firstName": "Tristan M.",
        "lastName": "Christoffersen",
        "gender": "male",
        "birthDate": "2011-08-10",
        "address": {
            "street": "dÅJaKxnRqdRbtxaUyviQBxZÅu JozfbyonuCgNXA",
            "number": "77K",
            "floor": 82,
            "door": 44,
            "postal_code": "3210",
            "town_name": "Vejby"
        },
        "phoneNumber": "69712398"
    },
    {
        "CPR": "0507110046",
        "firstName": "Thomas E.",
        "lastName": "Olsen",
        "gender": "male",
        "birthDate": "1911-07-05",
        "address": {
            "street": "m tfYxXøBxmhadvtIHwWvTWEEIRjOÆglcHigsVjb",
            "number": "184",
            "floor": 3,
            "door": "th",
            "postal_code": "7950",
            "town_name": "Erslev"
        },
        "phoneNumber": "38907752"
    }
]
```

## Class `FakeInfo` - Public methods

```php
- getCPR(): string
- getFullNameAndGender(): array
- getFullNameGenderAndBirthDate(): array
- getCprFullNameAndGender(): array
- getCprFullNameGenderAndBirthDate(): array
- getAddress(): string
- getPhoneNumber(): string
- getFakePerson(): array
- getFakePersons(int $amount): array
```

## Sample Class Output

```php
echo '<pre>';
$fakeInfo = new FakeInfo;
print_r($fakeInfo->getFakePersons());
```

Output
```php
Array
(
    [CPR] => 1909743965
    [firstName] => Anton D.
    [lastName] => Jespersen
    [gender] => male
    [birthDate] => 1974-09-19
    [address] => Array
        (
            [street] => WTquWUqMiHLBKXcEÆnMpqhdGæzlrødfAAAJuGGXø
            [number] => 456
            [floor] => 61
            [door] => th
            [postal_code] => 3650
            [town_name] => Ølstykke
        )
    [phoneNumber] => 55129415
)
```

## Tools
PHP8 / MariaDB

## Author
Arturo Mora-Rioja