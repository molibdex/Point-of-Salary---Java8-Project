package pos.main.model.core;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Invoice {

    private String invoiceNumber;

    private Date date;

    private String customerId;

    private HashMap<String, Integer> orderMap;

    private double subTotal;

    private double taxCollected;

    private double total;

    public Invoice(String iNumber, Date date, String customerId,
                   List<String> orderList, double subTotal, double taxCollected, double total) {
        this.invoiceNumber = iNumber;
        this.date = date;
        this.customerId = customerId;
        orderMap = getOrderListHashMap(orderList);
        this.subTotal = subTotal;
        this.taxCollected = taxCollected;
        this.total = total;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public Date getDate() {
        return date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public double getTaxCollected() {
        return taxCollected;
    }

    public double getTotal() {
        return total;
    }

    private HashMap<String, Integer> getOrderListHashMap(List<String> orderList) {
        HashMap<String, Integer> orderMap = new HashMap<String, Integer>();
        for (String barcode : orderList) {
            if (orderMap.containsKey(barcode)) {
                orderMap.put(barcode, orderMap.get(barcode) + 1);
            } else {
                orderMap.put(barcode, Integer.valueOf(1));
            }
        }
        return orderMap;
    }

    @Override
    public String toString() {
        return invoiceNumber + "#" + date + "#" + customerId + "#" + orderListToString() +
                "#" + subTotal + "#" + taxCollected + "#" + total;
    }

    private String orderListToString() {
        StringBuffer order = new StringBuffer();
        int cont = 0;
        for (String key : orderMap.keySet()) {
            if (cont == 0) {
                order.append(key + "&" + orderMap.get(key));
            } else {
                order.append("," + key + "&" + orderMap.get(key));
            }
            cont++;
        }
        return order.toString();
    }


}
