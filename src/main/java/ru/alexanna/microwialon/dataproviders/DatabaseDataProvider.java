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
    private boolean isDelivery = false;
    private final Map<Integer, String> monitoringObjectMap;
    private int dbQueryPause = 8000;

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
        isDelivery = true;
        wialonObjects.forEach(MonitoringObject::startDataTransfer);
        createDBQueryThread().start();
    }

    @Override
    public void stopDataDelivery() {
        isDelivery = false;
        wialonObjects.forEach(MonitoringObject::stopDataTransfer);
    }

    private Thread createDBQueryThread() {
        return new Thread(() -> {
            while (isDelivery) {
                Map<Integer, MonitoringData> dbMonDataMap = /*mySQLLookup();*/  safDBLookup();
                dbMonDataMap.forEach((kvartaId, monData) -> {
                    // TODO выделить в отдельный метод - findObjectInWialonObjects(Integer kvartaId)
                        String wialonId = monitoringObjectMap.get(kvartaId);
                        Optional<MonitoringObject> monObj = wialonObjects.stream()
                                .filter(monitoringObject -> Objects.equals(monitoringObject.getId(), wialonId))
                                .findFirst();
                        if (monObj.isPresent()) {
                            monObj.get().update(monData);
                        }
                });

                try {
                    Thread.sleep(dbQueryPause);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("DBLookup Thread stopped");
        });
    }

    private Map<Integer, MonitoringData> safDBLookup() {
        Map<Integer, MonitoringData> monDataMap = new HashMap<>();
        Properties props = new Properties();
        props.setProperty("user", "SYSDBA");
        props.setProperty("password", "masterkey");
        props.setProperty("encoding", "WIN1251");

        Connection connection = DatabaseConnection.getConnection("jdbc:firebirdsql://10.70.0.150:3051/saf", props);
        System.out.println(connection + " " + new Date());
        String query = "SELECT id, locono, locotime, locodate, speed, latitude, longitude, " +
                "fuel_volume, fuel_dens, modify_dt, fuel_mass" +
                " FROM data_gps";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                MonitoringData monData = new MonitoringData();
                monData.setDate(new Date());
                monData.setLatitude(rs.getDouble("latitude"));
                monData.setLongitude(rs.getDouble("longitude"));
                monData.setSpeed(rs.getInt("speed") / 10);
                monData.setCourse(0);
                monData.setSats(10);
                monData.setFuelVolume(rs.getInt("fuel_volume"));
                monDataMap.put(rs.getInt("locono"), monData);
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

    public int getDbQueryPause() {
        return dbQueryPause;
    }

    public void setDbQueryPause(int dbQueryPause) {
        this.dbQueryPause = dbQueryPause;
    }
}
