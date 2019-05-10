package pos.main.controller.managers;

import java.util.HashMap;

import pos.main.controller.devices.Device;
import pos.main.controller.devices.input.BarCodeScanner;
import pos.main.controller.devices.output.DisplayLCD;
import pos.main.controller.devices.output.Printer;
import pos.main.enums.DeviceCategory;
import pos.main.enums.DeviceType;

public class DeviceManager {


    private HashMap<String, Device> inputDevices = new HashMap<String, Device>();
    private HashMap<String, Device> outputDevices = new HashMap<String, Device>();
    private Printer printer;
    private BarCodeScanner scanner;
    private DisplayLCD display;


    public DeviceManager() {
    }

    public boolean createDevice(String code, String name, String description,
                                DeviceCategory category, DeviceType type) {
        switch (type) {
            case INPUT:
                if (code.length() == 0) {
                    return false;
                }
                return createInputDevice(code, name, description, category);
            case OUTPUT:
                if (code.length() == 0) {
                    return false;
                }
                return createOutputDevice(code, name, description, category);
            default:
                return false;
        }
    }

    public boolean createOutputDevice(String code, String name,
                                      String description, DeviceCategory category) {
        if (code.length() == 0) {
            return false;
        }
        Device device = null;
        switch (category) {
            case PRINTER:
                device = new Printer(code, name, description, category);
                if (printer == null) printer = (Printer) device;
                break;
            case DISPLAY:
                device = new DisplayLCD(code, name, description, category);
                if (display == null) display = (DisplayLCD) device;
                break;
            default:
                break;
        }
        if (device != null)
            return addOutputDevice(device);
        return false;
    }


    public boolean createInputDevice(String code, String name,
                                     String description, DeviceCategory category) {
        if (code.length() == 0) {
            return false;
        }
        Device device = null;
        switch (category) {
            case SCANNER:
                device = new BarCodeScanner(code, name, description, category);
                if (scanner == null) scanner = (BarCodeScanner) device;
                break;
            default:
                break;
        }
        if (device != null) {
            return addInputDevice(device);
        } else {
            return false;
        }
    }

    public boolean addInputDevice(Device device) {
        switch (device.getCategory()) {
            case SCANNER:
                if (!inputDevices.containsKey(device.getCode())) {
                    inputDevices.put(device.getCode(), device);
                    return true;
                }
                return false;
            default:
                return false;
        }
    }

    public boolean addOutputDevice(Device device) {
        switch (device.getCategory()) {
            case PRINTER:
                if (!outputDevices.containsKey(device.getCode())) {
                    outputDevices.put(device.getCode(), device);
                    return true;
                }
                return false;
            case DISPLAY:
                if (!outputDevices.containsKey(device.getCode())) {
                    outputDevices.put(device.getCode(), device);
                    return true;
                }
                return false;
            default:
                return false;
        }

    }

    public Printer getPrinter() {
        if (printer == null) {
            for (String key : outputDevices.keySet()) {
                Device dev = outputDevices.get(key);
                if (dev.getCategory().equals(DeviceCategory.PRINTER)) {
                    printer = (Printer) dev;
                }
            }
        }
        return printer;
    }

    public BarCodeScanner getScanner() {
        if (scanner == null) {
            for (String key : inputDevices.keySet()) {
                Device dev = inputDevices.get(key);
                if (dev.getCategory().equals(DeviceCategory.SCANNER)) {
                    scanner = (BarCodeScanner) dev;
                }
            }
        }
        return scanner;
    }

    public DisplayLCD getDisplayLCD() {
        if (display == null) {
            for (String key : outputDevices.keySet()) {
                Device dev = outputDevices.get(key);
                if (dev.getCategory().equals(DeviceCategory.DISPLAY)) {
                    display = (DisplayLCD) dev;
                }
            }
        }
        return display;
    }

    public int getSizeInputDevices() {
        return inputDevices.size();
    }

    public int getSizeOutputDevices() {
        return outputDevices.size();
    }

}
