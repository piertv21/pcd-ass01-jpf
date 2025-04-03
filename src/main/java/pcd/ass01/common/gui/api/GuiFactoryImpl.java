package pcd.ass01.common.gui.api;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Hashtable;

public class GuiFactoryImpl implements GuiFactory {

    @Override
    public JFrame createFrame(final String title, final int width, final int height) {
        JFrame frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        return frame;
    }

    @Override
    public JButton createButton(final String text, final ActionListener actionListener) {
        var button = new JButton(text);
        button.addActionListener(actionListener);
        return button;
    }

    @Override
    public JTextField createTextField(final int columns) {
        return new JTextField(columns);
    }

    @Override
    public JSlider createSlider(final ChangeListener changeListener) {
        var slider = new JSlider(JSlider.HORIZONTAL, 0, 20, 10);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        Hashtable<Object, Object> labelTable = new Hashtable<>();
        labelTable.put( 0, new JLabel("0") );
        labelTable.put( 10, new JLabel("1") );
        labelTable.put( 20, new JLabel("2") );

        slider.setLabelTable(labelTable);
        slider.setPaintLabels(true);
        slider.addChangeListener(changeListener);
        return slider;
    }
}