package pos.main.view;

import java.util.HashMap;

import pos.main.controller.PointOfSale;
import pos.main.enums.DeviceCategory;
import pos.main.enums.DeviceType;
import pos.main.enums.MessagesEnum;
import pos.main.view.devices.input.InputView;
import pos.main.view.devices.output.OutputView;
import pos.main.view.devices.util.ViewInformationConstants;
import pos.main.view.devices.util.ViewMapper;

public class View {

    public static View instance;
    private PointOfSale myPOS;
    private HashMap<String, OutputView> myOutputComponents;
    private HashMap<String, InputView> myInputComponents;

    private View() {
        myInputComponents = new HashMap<String, InputView>();
        myOutputComponents = new HashMap<String, OutputView>();
    }

    public static View getInstance(PointOfSale pos) {
        if (instance == null) {
            instance = new View();
        }
        instance.myPOS = pos;
        return instance;
    }

    public static void dispose() {
        instance = null;
    }

    public int start() {
        System.out.println(MessagesEnum.PRINTING_LCD + ""); //update user on printing output device
        String keyDisplay = isDisplayUnplugged();
        if (keyDisplay.length() == 0) {
            System.out.println(MessagesEnum.NO_DISPLAY_FOUND + "");
            return ViewInformationConstants.NO_DISPLAY_FOUND;
        } else {
            String keyInput = isScannerUnplugged();
            if (keyInput.length() == 0) {
                System.out.println(MessagesEnum.NO_SCANNER_FOUND + "");
                return ViewInformationConstants.NO_SCANNER_FOUND;
            } else {
                return ViewInformationConstants.START;
            }
        }
    }

    public boolean addDevice(String code, String name, String category, String type) {
        switch (DeviceType.getType(type)) {
            case INPUT:
                if (!myInputComponents.containsKey(code)) {
                    myInputComponents.put(code, ViewMapper.toInputView(code, name, category, this));
                    return true;
                } else {
                    return false;
                }
            case OUTPUT:
                if (!myOutputComponents.containsKey(code)) {
                    myOutputComponents.put(code, ViewMapper.toOutputView(code, name, category));
                    return true;
                } else {
                    return false;
                }
            default:
                System.out.println(MessagesEnum.NO_DEVICE_TYPE_FOUND + "");
                return false;
        }
    }

    public int sendBarCode(String barCode) {
        String keyScanner = isScannerUnplugged();
        if (keyScanner.length() == 0) {
            return ViewInformationConstants.NO_SCANNER_FOUND;
        }
        String keyDisplay = "";
        if (!myPOS.isPrintingInvoice(barCode)) {
            keyDisplay = isDisplayUnplugged();
            if (keyDisplay.length() == 0) {
                System.out.println(MessagesEnum.NO_DISPLAY_FOUND + "");
                return ViewInformationConstants.NO_DISPLAY_FOUND_SCANNING_PRODUCT;
            } else {
                String receiveBarcode = myPOS.singleProductSale(barCode);
                myOutputComponents.get(keyDisplay).print(receiveBarcode);
                return ViewInformationConstants.PRODUCT_INFO_DISPLAYED;
            }
        } else {
            keyDisplay = isDisplayUnplugged();
            if (keyDisplay.length() == 0) {
                System.out.println(MessagesEnum.NO_DISPLAY_FOUND + "");
                return ViewInformationConstants.NO_DISPLAY_FOUND_PRINTING_RESULTS;
            } else {
                String results = myPOS.getResults();
                myOutputComponents.get(keyDisplay).print(results);
                String keyPrinter = isPrinterUnplugged();
                if (keyPrinter.length() == 0) {
                    System.out.println(MessagesEnum.NO_PRINTER_FOUND + "");
                    myPOS.finishCurrentOrder();
                    return ViewInformationConstants.NO_PRINTER_FOUND;
                } else {
                    String invoiceResults = myPOS.getInvoiceResults();
                    myOutputComponents.get(keyPrinter).print(invoiceResults);
                    myPOS.finishCurrentOrder();
                    return ViewInformationConstants.RESULTS_PRINTED;
                }
            }
        }
    }

    private String isPrinterUnplugged() {
        for (String key : myOutputComponents.keySet()) {
            if ((myOutputComponents.get(key).getCategory()).equals(DeviceCategory.PRINTER)) {
                return key;
            }
        }
        return "";
    }

    private String isScannerUnplugged() {
        for (String key : myInputComponents.keySet()) {
            if (myInputComponents.get(key).getCategory().equals(DeviceCategory.SCANNER)) {
                return key;
            }
        }
        return "";
    }

    private String isDisplayUnplugged() {
        for (String key : myOutputComponents.keySet()) {
            if ((myOutputComponents.get(key).getCategory()).equals(DeviceCategory.DISPLAY)) {
                return key;
            }
        }
        return "";
    }
}
