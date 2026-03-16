<?php
/**
 * Encapsulates a connection to the database 
 * 
 * @author  Arturo Mora-Rioja
 * @version 1.0.0 August 2020
 * @version 2.0.0 March 2026 Adaptation to Docker
 */
require_once 'info/Info.php';

class DB {    
    protected object $pdo;

    /**
     * Opens a connection to the database
     */
    public function __construct() {
        $dsn = 'mysql:host=' . Info::host() . ';dbname=' . Info::dbName() . ';charset=utf8';
        $options = [
            PDO::ATTR_ERRMODE            => PDO::ERRMODE_EXCEPTION,
            PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC
        ];

        try {
            $this->pdo = @new PDO($dsn, Info::user(), Info::password(), $options); 
        } catch (\PDOException $e) {
            echo 'Connection unsuccessful';
            die('Connection unsuccessful: ' . $e->getMessage());
            exit();
        }
    }

    /**
     * Closes a connection to the database
     */
    public function __destruct() {
        unset($this->pdo);
    }
}
?>