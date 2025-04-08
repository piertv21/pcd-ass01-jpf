package pcd.ass01.common.boids;

import pcd.ass01.common.SimulationMonitor;
import pcd.ass01.common.gui.impl.BoidsView;

import java.util.Optional;

public abstract class AbstractBoidsSimulator implements BoidsSimulator {

    protected final SimulationMonitor simulationMonitor;
    protected final BoidsModel model;
    protected Optional<BoidsView> view;
    protected int framerate;

    public AbstractBoidsSimulator(BoidsModel model) {
        this.simulationMonitor = new SimulationMonitor();
        this.model = model;
        this.view = Optional.empty();
    }

    @Override
    public void attachView(BoidsView view) {
        this.view = Optional.of(view);
    }

    @Override
    public void startSimulation(int boidCount) {
        this.model.startSimulation(boidCount);
        this.simulationMonitor.start();
    }

    @Override
    public void pauseSimulation() {
        this.simulationMonitor.togglePause();
    }

    @Override
    public void stopSimulation() {
        this.simulationMonitor.stop();
    }
}