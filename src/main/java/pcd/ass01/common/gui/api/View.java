package pcd.ass01.common.gui.api;

import pcd.ass01.common.boids.BoidsModel;

public interface View {

    GuiFactory getGuiFactory();

    BoidsModel getModel();

    void startSimulation(final int boidCount);

    void stopSimulation();

    void pauseSimulation();

    int getWidth();

    int getHeight();

    void update(final int framerate);

}
