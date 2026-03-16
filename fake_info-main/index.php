<?php

require_once 'src/FakeInfo.php';

define('POS_ENTITY', 1);

define('ERROR_METHOD', 0);
define('ERROR_ENDPOINT', 1);
define('ERROR_PARAMS', 2);

if ($_SERVER['REQUEST_METHOD'] !== 'GET') {
    reportError(ERROR_METHOD);
}

$url = strtok($_SERVER['REQUEST_URI'], "?");    // GET parameters are removed
// If there is a trailing slash, it is removed, so that it is not taken into account by the explode function
if (substr($url, strlen($url) - 1) == '/') {
    $url = substr($url, 0, strlen($url) - 1);
}
// Everything up to the folder where this file exists is removed.
// This allows the API to be deployed to any directory in the server
$url = substr($url, strpos($url, basename(__DIR__)));

$urlPieces = explode('/', urldecode($url));

header('Content-Type: application/json; charset=utf-8');
header('Access-Control-Allow-Origin: *');
header('Accept-version: v1');

$fakePerson = new FakeInfo;
http_response_code(200);

if (count($urlPieces) === 1) {
    reportError(ERROR_ENDPOINT);
}

switch ($urlPieces[POS_ENTITY]) {
    case 'cpr':
        echo json_encode(['CPR' => $fakePerson->getCpr()]);
        break;
    case 'name-gender':
        echo json_encode($fakePerson->getFullNameAndGender());
        break;
    case 'name-gender-dob':
        echo json_encode($fakePerson->getFullNameGenderAndBirthDate());
        break;
    case 'cpr-name-gender':
        echo json_encode($fakePerson->getCprFullNameAndGender());
        break;
    case 'cpr-name-gender-dob':
        echo json_encode($fakePerson->getCprFullNameGenderAndBirthDate());
        break;
    case 'address':
        echo json_encode($fakePerson->getAddress());
        break;
    case 'phone':
        echo json_encode(['phoneNumber' => $fakePerson->getPhoneNumber()]);
        break;
    case 'person':
        $numPersons = $_GET['n'] ?? 1;
        $numPersons = abs((int)$numPersons);
        switch (true) {
            case $numPersons === 0:
                reportError(ERROR_PARAMS);
                break;
            case $numPersons === 1:
                echo json_encode($fakePerson->getFakePerson());
                break;
            case $numPersons > 1 && $numPersons <= 100:
                echo json_encode($fakePerson->getFakePersons($numPersons), JSON_INVALID_UTF8_SUBSTITUTE);
                break;
            default:
                reportError(ERROR_PARAMS);
        }
        break;
    default:
        reportError(ERROR_ENDPOINT);
}

function reportError(int $error = -1) {
    switch ($error) {
        case ERROR_METHOD:
            http_response_code(405);
            $message = 'Incorrect HTTP method';
            break;
        case ERROR_ENDPOINT:
            http_response_code(404);
            $message = 'Incorrect API endpoint';
            break;
        case ERROR_PARAMS:
            http_response_code(400);
            $message = 'Incorrect GET parameter value';
            break;
        default:
            http_response_code(500);
            $message = 'Unknown error';
    }
    echo json_encode([
        'error' => $message
    ]);
    exit();
}