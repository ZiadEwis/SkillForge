package ui.components;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {

    private int radius = 25;

    public RoundedButton(String text) {
        super(text);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setForeground(Color.WHITE);
        setBackground(new Color(52, 152, 219));
        setFont(new Font("Arial", Font.BOLD, 14));
    }

    public RoundedButton(String text, int radius) {
        this(text);
        this.radius = radius;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        super.paintComponent(g);

        g2.dispose();
    }

    @Override
    public void setContentAreaFilled(boolean b) {
    }
}
