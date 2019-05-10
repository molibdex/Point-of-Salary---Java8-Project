package pos.main.model.core;

public class Product {
    private String id;
    private double price;
    private String name;
    private String barCode;

    public Product(String id, String name, double price, String barCode) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.barCode = barCode;
    }

    public String getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getBarCode() {
        return barCode;
    }

    @Override
    public String toString() {
        return this.name + ":\t" + this.price;
    }
}
