package pos.main.model.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import pos.main.enums.MessagesEnum;

public class PropertyReader {

    private PrintStream standardOutput;
    private static PropertyReader instance;

    public String retrievePropertyFromConfigFile(String configFileName, String property) {
        FileInputStream inputProperties = null;
        Properties prop = new Properties();
        String productsFileName = "";
        standardOutput = System.out;
        try {
            inputProperties = new FileInputStream(configFileName);
            prop.load(inputProperties);
            inputProperties.close();
            productsFileName = prop.getProperty(property);
            return productsFileName;
        } catch (FileNotFoundException ex) {
            standardOutput.println(MessagesEnum.CONFIG_FILE_NOT_FOUND.toString());
            return productsFileName;
        } catch (IOException ex) {
            standardOutput.println(MessagesEnum.CONFIG_READING_ERORR.toString());
            return productsFileName;
        } finally {
            if (inputProperties != null) {
                try {
                    inputProperties.close();
                } catch (IOException ex) {
                    standardOutput.println(MessagesEnum.CONFIG_READING_ERORR.toString());
                }
            }
        }
    }

    public static PropertyReader getInstance() {
        if (instance == null) {
            instance = new PropertyReader();
        }
        return instance;
    }

    private PropertyReader() {
    }
}
