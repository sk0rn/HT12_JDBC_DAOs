package mobiles;

import mobiles.dao.mobile.MobileDao;
import mobiles.dao.mobile.MobileDaoImpl;
import mobiles.pojo.Manufacturer;
import mobiles.pojo.Mobile;

public class JDBCDemo {
    public static void main(String[] args) {
        MobileDao mobileDao = new MobileDaoImpl();

        // create
        Manufacturer manufacturer = new Manufacturer(10, null, null);
        Mobile mobile = new Mobile(null, "Redmi 4x", 11000F, manufacturer);
        mobileDao.add(mobile);

        // read
        Mobile newMobile = mobileDao.getById(7);
        System.out.println(newMobile);


        // update
        newMobile.setPrice(5000F);
        mobileDao.updateById(newMobile);
        newMobile = mobileDao.getById(7);
        System.out.println(newMobile); // price = 5000;

        //delete
        mobileDao.deleteById(25);

        ((MobileDaoImpl) mobileDao).closeConnection();

    }
}
