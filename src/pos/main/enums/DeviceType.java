package pos.main.enums;

public enum DeviceType {

    INPUT {
        @Override
        public String toString() {
            return "INPUT";
        }
    }, OUTPUT {
        @Override
        public String toString() {
            return "OUTPUT";
        }
    }, UNDEFINED {
        @Override
        public String toString() {
            return "UNDEFINED";
        }
    };

    public static DeviceType getType(String kind) {
        if (kind.equals(INPUT.toString())) {
            return INPUT;
        } else if (kind.equals(OUTPUT.toString())) {
            return OUTPUT;
        } else {
            return UNDEFINED;
        }
    }
}
