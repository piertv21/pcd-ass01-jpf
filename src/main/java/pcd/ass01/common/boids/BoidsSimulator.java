package pcd.ass01.common.boids;

import pcd.ass01.common.gui.impl.BoidsView;

public interface BoidsSimulator {

    void attachView(BoidsView view);

    void startSimulation(int boidCount);

    void pauseSimulation();

    void stopSimulation();

}