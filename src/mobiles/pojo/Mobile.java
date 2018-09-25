package mobiles.pojo;

public class Mobile {
    private Integer id;
    private String model;
    private Float price;
    private Manufacturer manufacturer;

    public Mobile() {
    }

    public Mobile(Integer id, String model, Float price, Manufacturer manufacturer) {
        this.id = id;
        this.model = model;
        this.price = price;
        this.manufacturer = manufacturer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public String toString() {
        return "Mobile{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", manufacturer=" + manufacturer +
                '}';
    }
}
