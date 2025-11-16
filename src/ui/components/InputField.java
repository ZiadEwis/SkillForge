package ui.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InputField extends JTextField {

    public InputField() {
        super();
        setFont(new Font("Arial", Font.PLAIN, 14));
        setBorder(new EmptyBorder(8, 10, 8, 10));
    }

    public InputField(int columns) {
        super(columns);
        setFont(new Font("Arial", Font.PLAIN, 14));
        setBorder(new EmptyBorder(8, 10, 8, 10));
    }
}
