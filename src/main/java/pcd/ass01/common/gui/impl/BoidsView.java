package pcd.ass01.common.gui.impl;

import pcd.ass01.common.boids.BoidsModel;
import pcd.ass01.common.boids.BoidsSimulator;
import pcd.ass01.common.gui.api.GuiFactory;
import pcd.ass01.common.gui.api.GuiFactoryImpl;
import pcd.ass01.common.gui.api.MainPanel;
import pcd.ass01.common.gui.api.View;
import pcd.ass01.common.gui.impl.panels.MainPanelImpl;
import pcd.ass01.common.gui.impl.panels.StartPanel;

import javax.swing.*;
import java.awt.*;

import static pcd.ass01.common.Context.TITLE;

public class BoidsView implements View {

    private final CardLayout cardLayout;
    private final JPanel panelContainer;
    private final GuiFactory guiFactory;
    private final BoidsModel boidsModel;
    private final BoidsSimulator boidsSimulator;
    private final int width, height;
    private final MainPanel mainPanel;

    public BoidsView(
        final String title,
        final BoidsModel model,
        final BoidsSimulator boidsSimulator,
        final int width,
        final int height
    ) {
        this.width = width;
        this.height = height;
        this.boidsModel = model;
        this.boidsSimulator = boidsSimulator;
        this.guiFactory = new GuiFactoryImpl();
        JFrame mainFrame = this.guiFactory.createFrame(TITLE + title, this.width, this.height);
        this.cardLayout = new CardLayout();
        this.panelContainer = new JPanel(cardLayout);

        StartPanel startPanel = new StartPanel(this);
        this.mainPanel = new MainPanelImpl(this);
        this.panelContainer.add(startPanel, "Start");
        this.panelContainer.add((Component) mainPanel, "Main");
        mainFrame.setContentPane(this.panelContainer);
        this.setCurrentPanel("Start");

        mainFrame.setVisible(true);
    }

    @Override
    public GuiFactory getGuiFactory() {
        return this.guiFactory;
    }

    @Override
    public BoidsModel getModel() {
        return this.boidsModel;
    }

    @Override
    public void startSimulation(final int boidCount) {
        new Thread(()-> this.boidsSimulator.startSimulation(boidCount)).start();
        this.setCurrentPanel("Main");
    }

    @Override
    public void stopSimulation() {
        this.boidsSimulator.stopSimulation();
        this.setCurrentPanel("Start");
        this.mainPanel.resetSliders();
    }

    @Override
    public void pauseSimulation() {
        this.boidsSimulator.pauseSimulation();
    }

    private void setCurrentPanel(final String panelName) {
        this.cardLayout.show(this.panelContainer, panelName);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void update(int framerate) {
        this.mainPanel.update(framerate);
    }

}