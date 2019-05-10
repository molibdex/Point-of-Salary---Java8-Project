package pos.main.enums;

public enum DeviceCategory {
    PRINTER {
        @Override
        public String toString() {
            return "PRINTER";
        }
    },
    SCANNER {
        @Override
        public String toString() {
            return "SCANNER";
        }

    },
    DISPLAY {
        @Override
        public String toString() {
            return "DISPLAY";
        }

    },
    UNKNOWN_DEVICE_TYPE {
        @Override
        public String toString() {
            return "UNKNOWN_DEVICE_TYPE";
        }

    };

    public static DeviceCategory getCategory(String type) {
        if (type.equals(PRINTER + "")) {
            return PRINTER;
        } else if (type.equals(SCANNER + "")) {
            return SCANNER;
        } else if (type.equals(DISPLAY + "")) {
            return DISPLAY;
        } else {
            return UNKNOWN_DEVICE_TYPE;
        }
    }
}
