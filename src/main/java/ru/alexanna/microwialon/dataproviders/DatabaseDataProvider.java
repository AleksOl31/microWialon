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
    private final List<MonitoringObject> wialonObjects;
    private boolean isDeliveryStarted = false;
    private final Map<Integer, String> monitoringObjectMap;

    public DatabaseDataProvider(Map<Integer, String> monitoringObjectMap) {
        this.monitoringObjectMap = monitoringObjectMap;
        wialonObjects = createWialonObjects();
    }

    private List<MonitoringObject> createWialonObjects() {
        List<String> objectIds = new ArrayList<>(monitoringObjectMap.values());
        List<MonitoringObject> wialonObjects = new ArrayList<>();
        objectIds.forEach((str) -> wialonObjects.add(new MonitoringObject(str, null)));
        return wialonObjects;
    }

    @Override
    public void startDataDelivery() {
        isDeliveryStarted = true;
        wialonObjects.forEach(MonitoringObject::startDataTransfer);
        createDBQueryThread().start();
    }

    @Override
    public void stopDataDelivery() {
        isDeliveryStarted = false;
        wialonObjects.forEach(MonitoringObject::stopDataTransfer);
    }

    private Thread createDBQueryThread() {
        return new Thread(() -> {
            while (isDeliveryStarted) {
                Map<Integer, MonitoringData> monDataMap = /*mySQLLookup();*/ safDBLookup();
                monDataMap.forEach((kvartaId, monData) -> {
                String wialonId = monitoringObjectMap.get(kvartaId);
                Optional<MonitoringObject> monObj = wialonObjects.stream()
                        .filter(monitoringObject -> monitoringObject.getId() == wialonId)
                        .findFirst();
                if (monObj.isPresent()) {
                    if (monData != null) {
                        monObj.get().update(monData);
                    }
                }
                });

                try {
                    Thread.sleep(8000);
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

//    private Map<String, Integer> objectMapping() {
//        Map<String, Integer> objMapping = new HashMap<>();
//        objMapping.put("20220001", 2394);
//        objMapping.put("20220002", 2598);
//        objMapping.put("20220003", 2601);
//        objMapping.put("20220004", 403);
//        objMapping.put("20220005", 195);
//        objMapping.put("20220006", 816);
//        objMapping.put("20220007", 85);
//        return objMapping;
//    }
}
