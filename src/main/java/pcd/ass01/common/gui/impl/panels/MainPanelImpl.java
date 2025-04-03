package pcd.ass01.common.gui.impl.panels;

import pcd.ass01.common.boids.BoidsModel;
import pcd.ass01.common.gui.api.MainPanel;
import pcd.ass01.common.gui.api.View;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class MainPanelImpl extends JPanel implements ChangeListener, MainPanel {

    private final JSlider cohesionSlider, separationSlider, alignmentSlider;
    private final BoidsPanel boidsPanel;
    private final BoidsModel model;

    public MainPanelImpl(final View mainFrame) {
        var factory = mainFrame.getGuiFactory();
        this.model = mainFrame.getModel();

        LayoutManager layout = new BorderLayout();
        this.setLayout(layout);

        boidsPanel = new BoidsPanel(mainFrame);
        this.add(BorderLayout.CENTER, boidsPanel);

        JPanel slidersPanel = new JPanel();
        cohesionSlider = factory.createSlider(this);
        separationSlider = factory.createSlider(this);
        alignmentSlider = factory.createSlider(this);
        JButton pauseButton = factory.createButton("Pause / Resume", e -> {
            mainFrame.pauseSimulation();
        });
        JButton stopButton = factory.createButton("Stop", e -> {
            mainFrame.stopSimulation();
        });

        slidersPanel.add(new JLabel("Separation"));
        slidersPanel.add(separationSlider);
        slidersPanel.add(new JLabel("Alignment"));
        slidersPanel.add(alignmentSlider);
        slidersPanel.add(new JLabel("Cohesion"));
        slidersPanel.add(cohesionSlider);
        slidersPanel.add(pauseButton);
        slidersPanel.add(stopButton);

        this.add(BorderLayout.SOUTH, slidersPanel);
    }

    @Override
    public void resetSliders() {
        separationSlider.setValue(10);
        cohesionSlider.setValue(10);
        alignmentSlider.setValue(10);
    }

    @Override
    public void update(int frameRate) {
        boidsPanel.setFrameRate(frameRate);
        boidsPanel.repaint();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == separationSlider) {
            var val = separationSlider.getValue();
            this.model.setSeparationWeight(0.1 * val);
        } else if (e.getSource() == cohesionSlider) {
            var val = cohesionSlider.getValue();
            this.model.setCohesionWeight(0.1 * val);
        } else {
            var val = alignmentSlider.getValue();
            this.model.setAlignmentWeight(0.1 * val);
        }
        //System.out.println(e);
    }
}
