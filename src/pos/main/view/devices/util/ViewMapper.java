package pos.main.view.devices.util;

import pos.main.enums.DeviceCategory;
import pos.main.view.View;
import pos.main.view.devices.input.InputView;
import pos.main.view.devices.output.OutputView;

public class ViewMapper {

	public static OutputView toOutputView(String code, String name, 
			String category){
		return new OutputView(code, name, DeviceCategory.getCategory(category));
	}

	public static InputView toInputView(String code, String name,
			String category, View view) {
		InputView inputView = new InputView (code, name, DeviceCategory.getCategory(category), view);
		return inputView;
	}
}
