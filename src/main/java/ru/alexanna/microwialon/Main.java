package ru.alexanna.microwialon;

import ru.alexanna.microwialon.database.DatabaseConnection;


import java.sql.*;
import java.util.Date;
import java.util.Properties;

// 10.70.0.160
// 82.151.104.72:20332 - Rostelecom;
// Wialon IPS	80.237.26.52	20332

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Monitoring monitoring = new Monitoring();
        monitoring.startMonitoring();

    }

}
