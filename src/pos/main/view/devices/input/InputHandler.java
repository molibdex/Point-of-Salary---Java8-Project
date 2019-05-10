/**
 * 
 */
package pos.main.view.devices.input;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import pos.main.view.View;

public class InputHandler implements ActionListener, KeyListener {

	private View view;
	private InputView inputView;

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("InputButton")){
			view.sendBarCode(inputView.capture());
			inputView.clearTextInputField();
		}
	}

	public InputHandler(View view, InputView inputView) {
		this.view = view;
		this.inputView = inputView;
	}

	@Override
	public void keyPressed(KeyEvent paramKeyEvent) {
		if(paramKeyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
			view.sendBarCode(inputView.capture());
			inputView.clearTextInputField();
		}
	}

	@Override
	public void keyReleased(KeyEvent paramKeyEvent) {}

	@Override
	public void keyTyped(KeyEvent paramKeyEvent) {}

}
