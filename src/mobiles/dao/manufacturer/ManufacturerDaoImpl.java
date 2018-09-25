package mobiles.dao.manufacturer;

import mobiles.ConnectionManager.ConnectionManager;
import mobiles.ConnectionManager.ConnectionManagerMobileDB;
import mobiles.pojo.Manufacturer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManufacturerDaoImpl implements ManufacturerDao {

    private static ConnectionManager connectionManager;
    private static Connection connection;

    public ManufacturerDaoImpl() {
        connectionManager = ConnectionManagerMobileDB.getInstance();
        connection = connectionManager.getConnection();
    }


    @Override
    public boolean add(Manufacturer manufacturer) {
        if (checkObject(manufacturer)) throw new IllegalArgumentException("manufacturer must be not null");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO manufacturer values (DEFAULT, ?, ?) RETURNING id ");
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            ResultSet resultSet = preparedStatement.executeQuery(); // получаем айдишник добавленного объекта
            if (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                System.out.println("The new object Manufacturer in DB was assigned the id: "
                        + id);
                manufacturer.setId(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Manufacturer getById(Integer id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM manufacturer WHERE id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Manufacturer(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateById(Manufacturer manufacturer) {
        if (checkObject(manufacturer)) throw new IllegalArgumentException("manufacturer must be not null");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE manufacturer SET name=?, country=? WHERE id=?");
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.setInt(3, manufacturer.getId());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM manufacturer WHERE id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkObject(Manufacturer manufacturer) {
        return manufacturer == null;
    }
}
