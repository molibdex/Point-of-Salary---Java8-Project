package pos.main.model.core;

import java.util.ArrayList;

import pos.main.enums.MessagesEnum;

public class InvoiceDetailsCalculator {

    private double tax;

    public InvoiceDetailsCalculator() {
        this.tax = 0.0;
    }

    public String getInvoiceDetails(ArrayList<Product> listProducts) {
        if (listProducts.size() > 0) {
            double totalLastInvoice = 0.0;
            double subTotalLastInvoice = 0.0;
            String invoiceDetails = "";
            StringBuffer sb = new StringBuffer(MessagesEnum.INVOICE_FOOTER + "");
            for (int i = 0; i < listProducts.size(); i++) {
                subTotalLastInvoice += listProducts.get(i).getPrice();
                sb.append(MessagesEnum.DISTANCE_INVOICE_INFO + "" + listProducts.get(i) + "\n");
            }
            if (tax != 0.0) {
                subTotalLastInvoice = Math.round(subTotalLastInvoice * 100.0) / 100.0; //round subtotal
                double taxCollected = subTotalLastInvoice * (tax / 100.0);
                taxCollected = Math.round(taxCollected * 100.0) / 100.0; // round tax collected
                sb.append("\n" + MessagesEnum.DISTANCE_INVOICE_INFO + "" + MessagesEnum.TAX + "\t" + tax);
                sb.append("\n" + MessagesEnum.DISTANCE_INVOICE_INFO + "" + MessagesEnum.SUBTOTAL + "\t" + subTotalLastInvoice);
                sb.append("\n" + MessagesEnum.DISTANCE_INVOICE_INFO + "" + MessagesEnum.TAX_COLLECTED + "\t" + taxCollected);
                totalLastInvoice = subTotalLastInvoice + taxCollected;
            } else {
                totalLastInvoice = subTotalLastInvoice;
            }
            totalLastInvoice = Math.round(totalLastInvoice * 100.0) / 100.0; // round total
            sb.append("\n" + MessagesEnum.DISTANCE_INVOICE_INFO + "Total:\t" + totalLastInvoice);
            sb.append(MessagesEnum.INVOICE_FOOTER + "");
            invoiceDetails = sb.toString();
            return invoiceDetails;
        } else {
            return "";
        }
    }

    public double calculateOrderTotal(ArrayList<Product> listProducts) {
        double total = 0;
        for (Product p : listProducts) {
            total += p.getPrice();
        }
        double subtotal = Math.round(total * 100.0) / 100.0;
        subtotal += calculateTaxCollected(subtotal);
        return subtotal;
    }

    public double calculateTaxCollected(double subtotal) {
        double taxCollected = subtotal * tax / 100;
        taxCollected = Math.round(taxCollected * 100.0) / 100.0;
        return taxCollected;
    }

    public double calculateOrderSubTotal(ArrayList<Product> listProducts) {
        double total = 0;
        for (Product p : listProducts) {
            total += p.getPrice();
        }
        return Math.round(total * 100.0) / 100.0;
    }

    public void setTax(double tax) {
        if (tax >= 0.0) {
            this.tax = tax;
        }
    }

    public double getTax() {
        return tax;
    }
}
