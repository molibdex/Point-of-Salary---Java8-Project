package pos.main.controller;

import java.util.ArrayList;

import pos.main.controller.devices.input.BarCodeScanner;
import pos.main.controller.devices.input.InputDevice;
import pos.main.controller.devices.output.DisplayLCD;
import pos.main.controller.devices.output.Printer;
import pos.main.controller.managers.DeviceManager;
import pos.main.controller.managers.ProductsManager;
import pos.main.enums.DeviceCategory;
import pos.main.enums.DeviceType;
import pos.main.enums.MessagesEnum;
import pos.main.model.core.InvoiceDetailsCalculator;
import pos.main.model.core.Product;
import pos.main.model.stubs.DevicesStub;
import pos.main.model.stubs.ProductStub;
import pos.main.view.View;

public class PointOfSale {

    private static final String CONFIG_FILE_NAME = "data/config/config.properties";
    private ProductsManager myProductManager;
    private static DeviceManager myDeviceManager;
    InvoiceDetailsCalculator calculator;
    private ArrayList<Product> currentOrderList;
    private static View myView;
    private static PointOfSale instance;


    private PointOfSale() {
        currentOrderList = new ArrayList<Product>();
        calculator = new InvoiceDetailsCalculator();
        myProductManager = new ProductsManager();
    }

    public static void main(String[] args) {
        PointOfSale myPOS = PointOfSale.getInstance();
        myPOS.setUp();
        myPOS.startView();
    }

    private void setUp() {
        setView(View.getInstance(this));
        setDeviceManager(new DeviceManager());
        setUpDevicesStub();
        setUpProductsStub();
    }

    private void setUpDevicesStub() {
        new DevicesStub(myDeviceManager, CONFIG_FILE_NAME);
    }

    private void setUpProductsStub() {
        new ProductStub(myProductManager, CONFIG_FILE_NAME, calculator);
    }

    public InvoiceDetailsCalculator getInvoiceDetailsCalculator() {
        return calculator;
    }

    public void startView() {
        mapDevicesToView();
        myView.start();
    }

    private void mapDevicesToView() {
        if (myDeviceManager.getSizeOutputDevices() > 0) {
            if (!isDisplayUnplugged()) {
                DisplayLCD display = myDeviceManager.getDisplayLCD();
                myView.addDevice(
                        display.getCode(),
                        display.getName(),
                        display.getCategory() + "", DeviceType.OUTPUT + "");
            }
            if (!isPrinterUnplugged()) {
                Printer printer = myDeviceManager.getPrinter();
                myView.addDevice(printer.getCode(), printer.getName(),
                        printer.getCategory() + "", DeviceType.OUTPUT + "");

            }
        }
        if (myDeviceManager.getSizeInputDevices() > 0) {
            BarCodeScanner scanner = myDeviceManager.getScanner();
            if (!isScannerUnplugged()) {
                myView.addDevice(scanner.getCode(), scanner.getName(),
                        scanner.getCategory() + "", DeviceType.INPUT + "");
            }
        }
    }

    public String getResults() {
        if (currentOrderList.size() > 0) {
            StringBuffer result = new StringBuffer();
            if (calculator.getTax() > 0.0) {
                result.append("\n" + MessagesEnum.DISTANCE_INVOICE_INFO + "Tax %:\t" + calculator.getTax());
                double subtotal = calculator.calculateOrderSubTotal(currentOrderList);
                result.append("\n" + MessagesEnum.DISTANCE_INVOICE_INFO + "Subtotal:\t" + subtotal);
                result.append("\n" + MessagesEnum.DISTANCE_INVOICE_INFO +
                        MessagesEnum.TAX_COLLECTED + "\t" + calculator.calculateTaxCollected(subtotal) + "\n");
            }
            double orderTotal = calculator.calculateOrderTotal(currentOrderList);
            result.append(MessagesEnum.DISTANCE_INVOICE_INFO + "Total: \t" + Math.round(orderTotal * 100.0) / 100.0 + "\n");
            result.append(MessagesEnum.INVOICE_FOOTER + "\n\n");
            if (isDeviceUnplugged(DeviceCategory.PRINTER)) {
                System.out.println(MessagesEnum.NO_PRINTER_FOUND.toString());
            } else {
                System.out.println(MessagesEnum.PRINTING_PRINTER + "\n");//update user on printing output device
            }
            return result.toString();
        } else {
            return "";
        }
    }


    public String getInvoiceResults() {
        return calculator.getInvoiceDetails(currentOrderList);
    }

    public boolean isDeviceUnplugged(DeviceCategory type) {
        boolean unplugged = true;
        if (myDeviceManager == null)
            return true;
        switch (type) {
            case SCANNER:
                unplugged = (myDeviceManager.getScanner() == null);
                break;
            case PRINTER:
                unplugged = (myDeviceManager.getPrinter() == null);
                break;
            case DISPLAY:
                unplugged = (myDeviceManager.getDisplayLCD() == null);
                break;
            default:
                unplugged = true;
                break;
        }
        return unplugged;
    }


    public boolean isPrintingInvoice(String barCode) {
        return (MessagesEnum.PRINT_RECEIPT + "").equals(barCode);
    }

    public String singleProductSale(String barCode) {
        if (barCode.trim().length() == 0) {
            return MessagesEnum.BARCODE_EMPTY + "";
        } else {
            if (myProductManager.isBarCodeValid(barCode)) {
                currentOrderList.add(myProductManager.getProduct(barCode));
                return "" + currentOrderList.get(currentOrderList.size() - 1) + "\n";
            } else {
                return MessagesEnum.BARCODE_NOT_FOUND + "\n";
            }
        }
    }

    public boolean isPrinterUnplugged() {
        return isDeviceUnplugged(DeviceCategory.PRINTER);
    }

    public boolean isDisplayUnplugged() {
        return isDeviceUnplugged(DeviceCategory.DISPLAY);
    }

    public boolean isScannerUnplugged() {
        return isDeviceUnplugged(DeviceCategory.SCANNER);
    }

    public static PointOfSale getInstance() {
        if (instance == null) {
            instance = new PointOfSale();
        }
        return instance;
    }

    public static void dispose() {
        View.dispose();
        myDeviceManager = null;
        instance = null;
    }

    public int addProductsList(ArrayList<Product> listProducts) {
        if (listProducts.size() > 0) {
            return myProductManager.addProducts(listProducts);
        }
        return 0;
    }

    public boolean finishCurrentOrder() {
        if (currentOrderList.size() > 0) {
            storeOrder(currentOrderList);
            System.out.println(MessagesEnum.STARTING_NEW_ORDER + "");
            currentOrderList = new ArrayList<Product>();
            return true;
        } else {
            System.out.println(MessagesEnum.ORDER_LIST_EMPTY + "");
            return false;
        }
    }

    private void storeOrder(ArrayList<Product> listProducts2) {
        if (listProducts2.size() > 0) {
            System.out.println("Storing in database the order");
        }
    }

    public void setDeviceManager(DeviceManager deviceManager) {
        myDeviceManager = deviceManager;
    }

    public void setView(View view) {
        PointOfSale.myView = view;
    }

    public InputDevice getInputDevice() {
        return myDeviceManager.getScanner();
    }
}