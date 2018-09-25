package mobiles.dao.mobile;

import mobiles.ConnectionManager.ConnectionManager;
import mobiles.ConnectionManager.ConnectionManagerMobileDB;
import mobiles.dao.manufacturer.ManufacturerDao;
import mobiles.dao.manufacturer.ManufacturerDaoImpl;
import mobiles.pojo.Manufacturer;
import mobiles.pojo.Mobile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MobileDaoImpl implements MobileDao {
    private static ConnectionManager connectionManager;
    private static Connection connection;

    public MobileDaoImpl() {
        connectionManager = ConnectionManagerMobileDB.getInstance();
        connection = connectionManager.getConnection();
    }

    @Override
    public boolean add(Mobile mobile) {
        if (checkObject(mobile)) throw new IllegalArgumentException("Objects must be not null");

        // Если id производителя == null, и при этом будут
        // указаны название и страна, то создаем нового производителя в базе
        if (isNewManufacturer(mobile)) {
            ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
            manufacturerDao.add(mobile.getManufacturer());
            ((ManufacturerDaoImpl) manufacturerDao).closeConnection();
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO phones values (DEFAULT, ?, ?, ?) RETURNING id ");
            preparedStatement.setString(1, mobile.getModel());
            preparedStatement.setFloat(2, mobile.getPrice());
            preparedStatement.setInt(3, mobile.getManufacturer().getId());

            ResultSet resultSet = preparedStatement.executeQuery(); // получаем айдишник добавленного объекта
            if (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                System.out.println("The new object Mobile in DB was assigned the id: "
                        + id);
                mobile.setId(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Mobile getById(Integer id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM phones WHERE id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // получаем производителя
                ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
                Manufacturer manufacturer = manufacturerDao.getById(resultSet.getInt(4));
                ((ManufacturerDaoImpl) manufacturerDao).closeConnection();
                return new Mobile(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getFloat(3),
                        manufacturer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateById(Mobile mobile) {
        if (checkObject(mobile)) throw new IllegalArgumentException("Objects must be not null");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE phones SET model=?, price=?, manufacturer_id=? " +
                            "WHERE id=?");
            preparedStatement.setString(1, mobile.getModel());
            preparedStatement.setFloat(2, mobile.getPrice());
            preparedStatement.setInt(3, mobile.getManufacturer().getId());
            preparedStatement.setInt(4, mobile.getId());
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
                    "DELETE FROM phones WHERE id=?");
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

    private boolean isNewManufacturer(Mobile mobile) {
        return mobile.getManufacturer().getId() == null &&
                mobile.getManufacturer().getName() != null &&
                mobile.getManufacturer().getCountry() != null;
    }

    private boolean checkObject(Mobile mobile) {
        return mobile == null || mobile.getManufacturer() == null;
    }
}
