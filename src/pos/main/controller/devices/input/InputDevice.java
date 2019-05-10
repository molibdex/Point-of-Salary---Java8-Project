
package pos.main.controller.devices.input;

import java.io.InputStream;


public interface InputDevice {

    public String capture(InputStream in);
}
