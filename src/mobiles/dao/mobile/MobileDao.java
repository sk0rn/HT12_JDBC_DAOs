package mobiles.dao.mobile;

import mobiles.pojo.Mobile;

public interface MobileDao {
    boolean add(Mobile mobile);

    Mobile getById(Integer id);

    boolean updateById(Mobile mobile);

    boolean deleteById(Integer id);
}
