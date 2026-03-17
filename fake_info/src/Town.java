import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Town extends DB {

    private static int townCount = 0;
    private final Random random = new Random();

    /**
     * The total number of towns is saved in a private property.
     * By making it static, the calculation will be made only once.
     */
    public Town() {
        super();

        if (townCount == 0) {
            String sql = """
                    SELECT COUNT(*) AS total
                    FROM postal_code;
                    """;

            try (PreparedStatement stmt = connection.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    townCount = rs.getInt("total");
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error counting towns: " + e.getMessage(), e);
            }
        }
    }

    /**
     * Generates a random postal code and town
     * based on the values in the addresses database
     *
     * @return Map with keys: postal_code, town_name
     */
    public Map<String, String> getRandomTown() {
        int randomTown = random.nextInt(townCount);

        String sql = """
                SELECT cPostalCode AS postal_code, cTownName AS town_name
                FROM postal_code
                LIMIT ?, 1;
                """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, randomTown);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Map<String, String> town = new HashMap<>();
                    town.put("postal_code", rs.getString("postal_code"));
                    town.put("town_name", rs.getString("town_name"));
                    return town;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting random town: " + e.getMessage(), e);
        }

        throw new RuntimeException("No town found in database.");
    }
}