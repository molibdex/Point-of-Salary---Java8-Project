package pos.main.controller.devices.output;

import pos.main.controller.devices.Device;
import pos.main.enums.DeviceCategory;


public class Printer extends Device implements OutputDevice {

    @Override
    public void print(String outMsg) {
        System.out.print(outMsg);
    }

    public Printer(String code, String name, String description, DeviceCategory category) {
        super();
        this.name = name;
        this.description = description;
        this.code = code;
        this.category = category;
    }
}
