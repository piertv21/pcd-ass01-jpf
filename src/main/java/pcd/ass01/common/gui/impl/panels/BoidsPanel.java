package pcd.ass01.common.gui.impl.panels;

import pcd.ass01.common.boids.BoidsModel;
import pcd.ass01.common.gui.api.View;

import javax.swing.*;
import java.awt.*;

public class BoidsPanel extends JPanel {

    private final View view;
    private final BoidsModel model;
    private int framerate;

    public BoidsPanel(final View mainFrame) {
        this.view = mainFrame;
        this.model = this.view.getModel();
    }

    public void setFrameRate(int framerate) {
        this.framerate = framerate;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);

        var w = view.getWidth();
        var h = view.getHeight();
        var envWidth = model.getWidth();
        var xScale = w/envWidth;
        // var envHeight = model.getHeight();
        // var yScale = h/envHeight;

        var boids = model.getBoids();

        g.setColor(Color.BLUE);
        boids.forEach(boid -> {
            var x = boid.getPos().x();
            var y = boid.getPos().y();
            int px = (int)(w/2 + x*xScale);
            int py = (int)(h/2 - y*xScale);
            g.fillOval(px,py, 5, 5);
        });

        g.setColor(Color.BLACK);
        g.drawString("Num. Boids: " + boids.size(), 10, 25);
        g.drawString("Framerate: " + framerate, 10, 40);
    }
}