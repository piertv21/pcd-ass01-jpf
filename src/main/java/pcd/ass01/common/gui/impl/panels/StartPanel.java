package pcd.ass01.common.gui.impl.panels;

import pcd.ass01.common.gui.api.View;

import javax.swing.*;
import java.awt.*;

import static pcd.ass01.common.Context.DEFAULT_BOIDS_NUMBER;

public class StartPanel extends JPanel {

    private final JTextField boidInput;

    public StartPanel(final View mainFrame) {
        var factory = mainFrame.getGuiFactory();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel label = new JLabel("Numero di Boids:");
        boidInput = factory.createTextField(10);
        boidInput.setText(DEFAULT_BOIDS_NUMBER);
        JButton startButton = factory.createButton("Start", e -> {
            var boidCount = boidInput.getText();
            if (!boidCount.matches("[0-9]+") || Integer.parseInt(boidCount) <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Inserisci un numero di boids valido o maggiore di 0.",
                        "Errore",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            mainFrame.startSimulation(Integer.parseInt(boidCount));
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(label, gbc);

        gbc.gridx = 1;
        add(boidInput, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(startButton, gbc);
    }
}