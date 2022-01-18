package ru.alexanna.microwialon.dataproviders;

import ru.alexanna.microwialon.MonitoringData;
import ru.alexanna.microwialon.MonitoringObject;
import ru.alexanna.microwialon.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DatabaseDataProvider implements DataProvider {
    private final List<MonitoringObject> monitoringObjects;
    private boolean isStart = false;
    private final Map<String, Integer> objectMapping;

    public DatabaseDataProvider(List<MonitoringObject> monitoringObjects) {
        this.monitoringObjects = monitoringObjects;
        objectMapping = objectMapping();
    }

    @Override
    public void startDataDelivery() {
        isStart = true;
        createDBQueryThread().start();
    }

    @Override
    public void stopDataDelivery() {
        isStart = false;
        for (MonitoringObject monitoringObject : monitoringObjects) {
            monitoringObject.stopDataTransfer();
        }
    }

    private Thread createDBQueryThread() {
        return new Thread(() -> {
            while (isStart) {
                Map<Integer, MonitoringData> monDataMap = mySQLLookup(); //safDBLookup();
                for (MonitoringObject monitoringObject : monitoringObjects) {
                    Integer kvartaId = objectMapping.get(monitoringObject.getId());
                    MonitoringData monData = monDataMap.get(kvartaId);
                    if (monData != null) {
                        monitoringObject.update(monData);
                    }
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("finish DB Thread");
        });
    }

    private Map<Integer, MonitoringData> safDBLookup() {
        Map<Integer, MonitoringData> monDataMap = new HashMap<>();
        Properties props = new Properties();
        props.setProperty("user", "SYSDBA");
        props.setProperty("password", "masterkey");
        props.setProperty("encoding", "WIN1251");

        Connection connection = DatabaseConnection.getConnection("jdbc:firebirdsql://10.70.0.150:3051/saf", props);
        System.out.println(connection);
        String query = "SELECT id, locono, locotime, locodate, speed, latitude, longitude, " +
                "fuel_volume, fuel_dens, modify_dt, fuel_mass" +
                " FROM data_gps";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                MonitoringData monData = new MonitoringData();
                monData.setDate(new Date());
                monData.setLatitude(rs.getDouble(6));
                monData.setLongitude(rs.getDouble(7));
                monData.setSpeed(rs.getInt(5) / 10);
                monData.setCourse(0);
                monData.setSats(10);
                monDataMap.put(rs.getInt(2), monData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close(connection);
        }
        return monDataMap;
    }

    private Map<Integer, MonitoringData> mySQLLookup() {
        Map<Integer, MonitoringData> monDataMap = new HashMap<>();
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "fktrctqjktu");

        Connection connection = DatabaseConnection.getConnection("jdbc:mysql://localhost/saf", props);
        System.out.println(connection);
        String query = "SELECT id, locono, locotime, locodate, speed, latitude, longitude, " +
                "fuel_volume, fuel_dens, modify_dt, fuel_mass" +
                " FROM data_gps";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                MonitoringData monData = new MonitoringData();
                monData.setDate(new Date());
                monData.setLatitude(rs.getDouble(6));
                monData.setLongitude(rs.getDouble(7));
                monData.setSpeed(rs.getInt(5));
                monDataMap.put(rs.getInt(2), monData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close(connection);
        }

        return monDataMap;
    }

    private Map<String, Integer> objectMapping() {
        Map<String, Integer> objMapping = new HashMap<>();
        objMapping.put("11223344", 2394);
        objMapping.put("55667788", 2598);
        objMapping.put("56785678", 2601);
        return objMapping;
    }
}
