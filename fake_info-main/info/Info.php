<?php

class Info
{
    public const HOST = 'localhost';
    public const DB_NAME = 'addresses';
    public const USER = 'root';
    public const PASSWORD = '';

    public static function host(): string
    {
        return getenv('DB_HOST') ?: self::HOST;
    }

    public static function dbName(): string
    {
        return getenv('DB_NAME') ?: self::DB_NAME;
    }

    public static function user(): string
    {
        return getenv('DB_USER') ?: self::USER;
    }

    public static function password(): string
    {
        $value = getenv('DB_PASSWORD');
        if ($value === false) {
            return self::PASSWORD;
        }

        return $value;
    }
}