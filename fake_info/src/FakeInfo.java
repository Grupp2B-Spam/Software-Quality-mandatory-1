import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class FakeInfo {

    public static final String GENDER_FEMININE = "female";
    public static final String GENDER_MASCULINE = "male";

    private static final String FILE_PERSON_NAMES = "data/person-names.json";

    private static final String[] PHONE_PREFIXES = {
            "2","30","31","40","41","42","50","51","52","53","60","61","71","81","91","92","93",
            "342","344","345","346","347","348","349","356","357","359","362","365","366",
            "389","398","431","441","462","466","468","472","474","476","478","485","486",
            "488","489","493","494","495","496","498","499","542","543","545","551","552",
            "556","571","572","573","574","577","579","584","586","587","589","597","598",
            "627","629","641","649","658","662","663","664","665","667","692","693","694",
            "697","771","772","782","783","785","786","788","789","826","827","829"
    };

    private static final int MIN_BULK_PERSONS = 2;
    private static final int MAX_BULK_PERSONS = 100;

    private String cpr;
    private String firstName;
    private String lastName;
    private String gender;
    private String birthDate;
    private Map<String, Object> address = new HashMap<>();
    private String phone;

    private final Random random = new Random();

    public FakeInfo() {
        setFullNameAndGender();
        setBirthDate();
        setCpr();
        setAddress();
        setPhone();
    }

    private void setCpr() {

        int finalDigit = random.nextInt(10);

        if (gender.equals(GENDER_FEMININE) && finalDigit % 2 == 1) {
            finalDigit++;
        }

        cpr =
                birthDate.substring(8, 10) +
                birthDate.substring(5, 7) +
                birthDate.substring(2, 4) +
                getRandomDigit() +
                getRandomDigit() +
                getRandomDigit() +
                finalDigit;
    }

    private void setBirthDate() {

        int year = 1900 + random.nextInt(LocalDate.now().getYear() - 1900 + 1);
        int month = 1 + random.nextInt(12);
        int day;

        if (Arrays.asList(1,3,5,7,8,10,12).contains(month)) {
            day = 1 + random.nextInt(31);
        } else if (Arrays.asList(4,6,9,11).contains(month)) {
            day = 1 + random.nextInt(30);
        } else {
            day = 1 + random.nextInt(28);
        }

        birthDate = String.format("%04d-%02d-%02d", year, month, day);
    }

    private void setFullNameAndGender() {

        try {
            String json = new String(Files.readAllBytes(Paths.get(FILE_PERSON_NAMES)));

            JSONObject object = new JSONObject(json);
            JSONArray persons = object.getJSONArray("persons");

            JSONObject person = persons.getJSONObject(random.nextInt(persons.length()));

            firstName = person.getString("firstName");
            lastName = person.getString("lastName");
            gender = person.getString("gender");

        } catch (IOException e) {
            throw new RuntimeException("Error reading names JSON file");
        }
    }

    private void setAddress() {

        address.put("street", getRandomText(40));

        String number = String.valueOf(random.nextInt(999) + 1);

        if (random.nextInt(10) < 3) {
            number += getRandomText(1,false).toUpperCase();
        }

        address.put("number", number);

        if (random.nextInt(10) < 4) {
            address.put("floor","st");
        } else {
            address.put("floor", random.nextInt(99) + 1);
        }

        int doorType = random.nextInt(20) + 1;

        if (doorType < 8) {
            address.put("door","th");
        } else if (doorType < 15) {
            address.put("door","tv");
        } else if (doorType < 17) {
            address.put("door","mf");
        } else if (doorType < 19) {
            address.put("door", random.nextInt(50) + 1);
        } else {

            String[] letters = {
                    "a","b","c","d","e","f","g","h","i","j","k","l","m","n",
                    "o","p","q","r","s","t","u","v","w","x","y","z","ø","æ","å"
            };

            String door = letters[random.nextInt(letters.length)];

            if (doorType == 20) {
                door += "-";
            }

            door += random.nextInt(999) + 1;

            address.put("door", door);
        }

        Town town = new Town();
        Map<String,String> townData = town.getRandomTown();

        address.put("postal_code", townData.get("postal_code"));
        address.put("town_name", townData.get("town_name"));
    }

    private static String getRandomText(int length) {
        return getRandomText(length,true);
    }

    private static String getRandomText(int length, boolean includeDanishCharacters) {

        String characters =
                " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        if (includeDanishCharacters) {
            characters += "æøåÆØÅ";
        }

        Random random = new Random();

        StringBuilder text = new StringBuilder();

        text.append(characters.charAt(random.nextInt(characters.length()-1)+1));

        for (int i = 1; i < length; i++) {
            text.append(characters.charAt(random.nextInt(characters.length())));
        }

        return text.toString();
    }

    private void setPhone() {

        String phone = PHONE_PREFIXES[random.nextInt(PHONE_PREFIXES.length)];

        int prefixLength = phone.length();

        for (int i = 0; i < (8 - prefixLength); i++) {
            phone += getRandomDigit();
        }

        this.phone = phone;
    }

    public String getCpr() {
        return cpr;
    }

    public Map<String,Object> getFullNameAndGender() {

        Map<String,Object> data = new HashMap<>();

        data.put("firstName", firstName);
        data.put("lastName", lastName);
        data.put("gender", gender);

        return data;
    }

    public Map<String,Object> getFullNameGenderAndBirthDate() {

        Map<String,Object> data = new HashMap<>();

        data.put("firstName", firstName);
        data.put("lastName", lastName);
        data.put("gender", gender);
        data.put("birthDate", birthDate);

        return data;
    }

    public Map<String,Object> getCprFullNameAndGender() {

        Map<String,Object> data = new HashMap<>();

        data.put("CPR", cpr);
        data.put("firstName", firstName);
        data.put("lastName", lastName);
        data.put("gender", gender);

        return data;
    }

    public Map<String,Object> getCprFullNameGenderAndBirthDate() {

        Map<String,Object> data = new HashMap<>();

        data.put("CPR", cpr);
        data.put("firstName", firstName);
        data.put("lastName", lastName);
        data.put("gender", gender);
        data.put("birthDate", birthDate);

        return data;
    }

    public Map<String,Object> getAddress() {

        Map<String,Object> data = new HashMap<>();
        data.put("address", address);

        return data;
    }

    public String getPhoneNumber() {
        return phone;
    }

    public Map<String,Object> getFakePerson() {

        Map<String,Object> data = new HashMap<>();

        data.put("CPR", cpr);
        data.put("firstName", firstName);
        data.put("lastName", lastName);
        data.put("gender", gender);
        data.put("birthDate", birthDate);
        data.put("address", address);
        data.put("phoneNumber", phone);

        return data;
    }

    public List<Map<String,Object>> getFakePersons(int amount) {

        if (amount < MIN_BULK_PERSONS) amount = MIN_BULK_PERSONS;
        if (amount > MAX_BULK_PERSONS) amount = MAX_BULK_PERSONS;

        List<Map<String,Object>> persons = new ArrayList<>();

        for (int i = 0; i < amount; i++) {

            FakeInfo fake = new FakeInfo();

            persons.add(fake.getFakePerson());
        }

        return persons;
    }

    private static String getRandomDigit() {
        return String.valueOf(new Random().nextInt(10));
    }
}