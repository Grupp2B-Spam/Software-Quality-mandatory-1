import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class FakeInfoController {

    private static final int ERROR_METHOD = 0;
    private static final int ERROR_ENDPOINT = 1;
    private static final int ERROR_PARAMS = 2;

    @GetMapping("cpr")
    public ResponseEntity<?> getCpr() {
        FakeInfo fakePerson = new FakeInfo();
        return ResponseEntity.ok(Collections.singletonMap("CPR", fakePerson.getCpr()));
    }

    @GetMapping("name-gender")
    public ResponseEntity<?> getNameGender() {
        FakeInfo fakePerson = new FakeInfo();
        return ResponseEntity.ok(fakePerson.getFullNameAndGender());
    }

    @GetMapping("name-gender-dob")
    public ResponseEntity<?> getNameGenderDob() {
        FakeInfo fakePerson = new FakeInfo();
        return ResponseEntity.ok(fakePerson.getFullNameGenderAndBirthDate());
    }

    @GetMapping("cpr-name-gender")
    public ResponseEntity<?> getCprNameGender() {
        FakeInfo fakePerson = new FakeInfo();
        return ResponseEntity.ok(fakePerson.getCprFullNameAndGender());
    }

    @GetMapping("cpr-name-gender-dob")
    public ResponseEntity<?> getCprNameGenderDob() {
        FakeInfo fakePerson = new FakeInfo();
        return ResponseEntity.ok(fakePerson.getCprFullNameGenderAndBirthDate());
    }

    @GetMapping("address")
    public ResponseEntity<?> getAddress() {
        FakeInfo fakePerson = new FakeInfo();
        return ResponseEntity.ok(fakePerson.getAddress());
    }

    @GetMapping("phone")
    public ResponseEntity<?> getPhone() {
        FakeInfo fakePerson = new FakeInfo();
        return ResponseEntity.ok(Collections.singletonMap("phoneNumber", fakePerson.getPhoneNumber()));
    }

    @GetMapping("person")
    public ResponseEntity<?> getPerson(@RequestParam(value = "n", defaultValue = "1") int n) {
        FakeInfo fakePerson = new FakeInfo();

        n = Math.abs(n);

        if (n == 0) {
            return reportError(ERROR_PARAMS);
        } else if (n == 1) {
            return ResponseEntity.ok(fakePerson.getFakePerson());
        } else if (n > 1 && n <= 100) {
            return ResponseEntity.ok(fakePerson.getFakePersons(n));
        } else {
            return reportError(ERROR_PARAMS);
        }
    }

    @RequestMapping("*")
    public ResponseEntity<?> handleUnknownEndpoint() {
        return reportError(ERROR_ENDPOINT);
    }

    private ResponseEntity<Map<String, String>> reportError(int error) {
        String message;
        HttpStatus status;

        switch (error) {
            case ERROR_METHOD:
                status = HttpStatus.METHOD_NOT_ALLOWED;
                message = "Incorrect HTTP method";
                break;
            case ERROR_ENDPOINT:
                status = HttpStatus.NOT_FOUND;
                message = "Incorrect API endpoint";
                break;
            case ERROR_PARAMS:
                status = HttpStatus.BAD_REQUEST;
                message = "Incorrect GET parameter value";
                break;
            default:
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                message = "Unknown error";
        }

        return ResponseEntity
                .status(status)
                .body(Collections.singletonMap("error", message));
    }
}