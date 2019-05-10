package pos.main.view.devices.input;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pos.main.enums.DeviceCategory;
import pos.main.enums.MessagesEnum;
import pos.main.view.View;

public class InputView extends JFrame implements InputCapable {

    private static final long serialVersionUID = -1062394476785328056L;
    private String name;
    private String code;
    private DeviceCategory category;
    private JButton send;
    private JTextField inputField;
    private InputHandler handler;
    private int xIni;
    private int yIni;
    private int width;
    private int height;

    public InputView(String code, String name, DeviceCategory category, View view) {

        super(name);
        this.name = name;
        this.code = code;
        this.category = category;
        this.inputField = new JTextField();
        this.inputField.setColumns(35);
        this.inputField.setFont(new Font("Verdana", Font.BOLD, 15));
        this.send = new JButton(MessagesEnum.SEND_INPUT + "");
        this.send.setActionCommand("InputButton");
        handler = new InputHandler(view, this);
        send.addActionListener(handler);
        inputField.setActionCommand("TextInput");
        inputField.addKeyListener(handler);
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new FlowLayout());
        this.setLayout(new BorderLayout());

        myPanel.add(inputField);
        myPanel.add(send);
        this.getContentPane().add(myPanel, BorderLayout.CENTER);

        JLabel lable = new JLabel(MessagesEnum.WAITING_MESSAGE + "");
        this.getContentPane().add(lable, BorderLayout.NORTH);

        this.setDefaultCloseOperation(closeWindow());
        JFrame.setDefaultLookAndFeelDecorated(true);
        xIni = 455;
        yIni = 620;
        width = 91;
        height = 11;

        this.setBounds(xIni, yIni, width, height);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public String capture() {
        return inputField.getText();
    }

    public void clearTextInputField() {
        inputField.setText("");
    }

    public void setTextInputField(String text) {
        inputField.setText(text);
    }

    private int closeWindow() {
        return JFrame.EXIT_ON_CLOSE;
    }

    public DeviceCategory getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

}
