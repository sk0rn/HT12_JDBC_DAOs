package mobiles.dao.manufacturer;

import mobiles.pojo.Manufacturer;

public interface ManufacturerDao {

    boolean add(Manufacturer manufacturer);

    Manufacturer getById(Integer id);

    boolean updateById(Manufacturer manufacturer);

    boolean deleteById(Integer id);
}
