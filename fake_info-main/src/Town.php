<?php

require_once 'DB.php';

/**
 * Class Town.
 * It generates random postal codes and town names.
 * 
 * @author  Arturo Mora-Rioja
 * @version 1.0.0 March 2023
 */
class Town extends DB 
{
    private static int $townCount = 0;

    /**
     * The total number of towns is saved in a private property.
     * By making it static, the calculation will be made only once
     */
    public function __construct()
    {
        parent::__construct();
        if (static::$townCount === 0) {
            $sql =<<<'SQL'
                SELECT COUNT(*) AS total
                FROM postal_code;
            SQL;
            $stmt = $this->pdo->prepare($sql);
            $stmt->execute();
            static::$townCount = $stmt->fetch()['total'];
        }
    }

    /**
     * Generates a random postal code and town
     * based on the values in the addresses database
     * 
     * @return array ['postal_code' => value, 'town_name' => value]
     */
    public function getRandomTown(): array
    {
        $randomTown = mt_rand(0, (static::$townCount - 1));
        $sql =<<<SQL
            SELECT cPostalCode AS postal_code, cTownName AS town_name
            FROM postal_code
            LIMIT $randomTown, 1;
        SQL;
        $stmt = $this->pdo->prepare($sql);
        $stmt->execute();

        return $stmt->fetch();
    }
}