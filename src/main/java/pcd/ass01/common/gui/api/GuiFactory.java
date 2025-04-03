package pcd.ass01.common.gui.api;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionListener;

public interface GuiFactory {

    JFrame createFrame(final String title, final int width, final int height);

    JButton createButton(final String text, final ActionListener actionListener);

    JTextField createTextField(final int columns);

    JSlider createSlider(final ChangeListener changeListener);

}
