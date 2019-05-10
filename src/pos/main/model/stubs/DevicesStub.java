package pos.main.model.stubs;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import pos.main.controller.managers.DeviceManager;
import pos.main.enums.DeviceType;
import pos.main.enums.DeviceCategory;
import pos.main.enums.MessagesEnum;
import pos.main.model.util.PropertyReader;

public class DevicesStub {

    private PrintStream standardOutput;

    public DevicesStub(DeviceManager posDeviceManager, String configFileName) {
        standardOutput = System.out;
        PropertyReader pr = PropertyReader.getInstance();
        String devicesFileName = pr.retrievePropertyFromConfigFile(configFileName, "devicesFileSource");
        if (devicesFileName.length() > 0) {
            loadDevices(devicesFileName, posDeviceManager);
        }
    }

    public void loadDevices(String devicesFileName, DeviceManager posDeviceManager) {
        BufferedReader bf = null;
        FileInputStream fis = null;
        standardOutput.println(MessagesEnum.SYSTEM_BOOT_INFO_START.toString());
        try {
            fis = new FileInputStream(devicesFileName);
            bf = new BufferedReader(new InputStreamReader(fis));
            bf.readLine();//read the line containing the definition of the file
            String deviceString = bf.readLine();
            while (deviceString != null) {
                String[] arrayDeviceInfo = deviceString.split(",");
                if (arrayDeviceInfo.length == 5) {
                    String devId = arrayDeviceInfo[0];
                    String devName = arrayDeviceInfo[1];
                    String devDesc = arrayDeviceInfo[2];
                    String devCategory = arrayDeviceInfo[3];
                    String devType = arrayDeviceInfo[4];
                    createDevice(devId, devName, devDesc, devCategory, devType, posDeviceManager);
                } else {
                    standardOutput.println(MessagesEnum.ERROR_RETRIEVING_DEVICE.toString());
                }
                deviceString = bf.readLine();
            }
            if (posDeviceManager.getSizeInputDevices() > 0) {
                standardOutput.println(MessagesEnum.INPUT_DEVICES_LOADED.toString());
            } else {
                standardOutput.println(MessagesEnum.NO_INPUT_DEVICES_LOADED.toString());
            }
            if (posDeviceManager.getSizeOutputDevices() > 0) {
                standardOutput.println(MessagesEnum.OUTPUT_DEVICES_LOADED.toString());
            } else {
                standardOutput.println(MessagesEnum.NO_OUTPUT_DEVICES_LOADED.toString());
            }

        } catch (FileNotFoundException ex) {
            standardOutput.println(MessagesEnum.INPUT_DEVICES_FILE_NOT_FOUND.toString());
        } catch (IOException ex) {
            standardOutput.println(MessagesEnum.ERROR_RETRIEVING_DEVICE.toString());
        } finally {
            try {
                if (bf != null) bf.close();
                if (fis != null) fis.close();
            } catch (Exception e) {
                standardOutput.println(MessagesEnum.ERROR_CLOSING_SOURCE.toString());
            }
        }

    }

    private void createDevice(String devCode, String devName, String devDesc,
                              String devCategory, String devType, DeviceManager posDeviceManager) {

        DeviceCategory category = DeviceCategory.getCategory(devCategory);
        DeviceType type = DeviceType.getType(devType);
        posDeviceManager.createDevice(devCode, devName, devDesc, category, type);
    }
}
