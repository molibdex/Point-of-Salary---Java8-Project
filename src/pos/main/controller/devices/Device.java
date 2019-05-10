package pos.main.controller.devices;

import pos.main.enums.DeviceCategory;

public abstract class Device {
    protected String name;
    protected String code;
    protected String description;
    protected DeviceCategory category;

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public DeviceCategory getCategory() {
        return category;
    }
}
