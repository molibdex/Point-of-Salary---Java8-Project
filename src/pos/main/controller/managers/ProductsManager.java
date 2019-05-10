package pos.main.controller.managers;

import java.util.ArrayList;
import java.util.HashMap;

import pos.main.model.core.Product;

public class ProductsManager {

    private HashMap<String, Product> products;

    public ProductsManager() {
        products = new HashMap<String, Product>();
    }

    public int getSizeProducts() {
        return products.size();
    }

    public boolean isBarCodeValid(String barcode) {
        return products.containsKey(barcode);
    }

    public Product getProduct(String barCode) {
        if (isBarCodeValid(barCode)) {
            return products.get(barCode);
        } else {
            return null;
        }
    }

    public boolean createProduct(String cod, String name, double price, String barCode) {
        if (cod.length() > 0 && name.length() > 0 && price > 0.0 && barCode.length() > 0) {
            Product product = new Product(cod, name, price, barCode);
            return addProduct(product);
        }
        return false;
    }

    public boolean addProduct(Product product) {
        if (products.containsKey(product.getBarCode())) {
            return false;
        } else {
            products.put(product.getBarCode(), product);
            return true;
        }
    }

    public int addProducts(ArrayList<Product> listProducts) {
        int cantAddedProducts = 0;
        for (Product product : listProducts) {
            if (addProduct(product))
                cantAddedProducts++;
        }
        return cantAddedProducts;
    }

}