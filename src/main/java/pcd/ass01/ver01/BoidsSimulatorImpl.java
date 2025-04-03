package pcd.ass01.ver01;

import pcd.ass01.common.SimulationMonitor;
import pcd.ass01.common.barrier.CustomCyclicBarrier;
import pcd.ass01.common.barrier.CyclicBarrier;
import pcd.ass01.common.boids.BoidsModel;
import pcd.ass01.common.boids.BoidsSimulator;
import pcd.ass01.common.gui.impl.BoidsView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static pcd.ass01.common.Context.DYNAMIC_CORE_NUMBER;
import static pcd.ass01.common.Context.FRAMERATE;

public class BoidsSimulatorImpl implements BoidsSimulator {

    private final SimulationMonitor simulationMonitor;
    private final BoidsModel model;
    private Optional<BoidsView> view;
    private int framerate;
    private boolean running;

    private final List<Thread> boidsUpdaters;
    private CyclicBarrier velBarrier, printBarrier;

    public BoidsSimulatorImpl(BoidsModel model) {
        this.simulationMonitor = new SimulationMonitor();
        this.model = model;
        this.view = Optional.empty();
        this.boidsUpdaters = new ArrayList<>();
    }

    @Override
    public void attachView(BoidsView view) {
        this.view = Optional.of(view);
    }

    @Override
    public void startSimulation(int boidCount) {
        this.model.startSimulation(boidCount);
        this.simulationMonitor.start();
        this.running = true;
        this.runSimulation();
    }

    @Override
    public void pauseSimulation() {
        this.simulationMonitor.togglePause();
    }

    @Override
    public void stopSimulation() {
        this.simulationMonitor.stop();
        this.running = false;
        this.velBarrier.breakBarrier();
        this.printBarrier.breakBarrier();
        this.boidsUpdaters.clear();
    }

    private void setupBoidsUpdatersAndBarriers() {
        var boids = model.getBoids();
        var boidCount = boids.size();
        int threadsNumber = Math.min(boidCount, DYNAMIC_CORE_NUMBER);

        this.velBarrier = new CustomCyclicBarrier(threadsNumber);
        this.printBarrier = new CustomCyclicBarrier(threadsNumber + 1);

        int baseSize = boidCount / threadsNumber;
        int extra = boidCount % threadsNumber;

        for(int i = 0; i < threadsNumber; i++) {
            int start = i * baseSize + Math.min(i, extra);
            int end = start + baseSize + (i < extra ? 1 : 0);
            var subList = boids.subList(start, end);
            this.boidsUpdaters.add(new BoidsUpdater(subList,
                    model, velBarrier, printBarrier, simulationMonitor));
        }
        for(Thread t : boidsUpdaters) {
            t.start();
        }
    }

    private void runSimulation() {
        this.setupBoidsUpdatersAndBarriers();

        while (running) {
            switch (simulationMonitor.getState()) {
                case RUNNING:
                    var t0 = System.currentTimeMillis();
                    try {
                        printBarrier.hitAndWaitAll();
                        printBarrier.hitAndWaitAll();
                    } catch (InterruptedException e) {
                        running = false;
                    }
                    view.ifPresent(v -> {
                        v.update(framerate);
                        var t1 = System.currentTimeMillis();
                        var dtElapsed = t1 - t0;
                        var frameRatePeriod = 1000 / FRAMERATE;

                        if (dtElapsed < frameRatePeriod) {
                            try {
                                Thread.sleep(frameRatePeriod - dtElapsed);
                            } catch (Exception ex) {}
                            framerate = FRAMERATE;
                        } else {
                            framerate = (int) (1000 / dtElapsed);
                        }
                    });
                    break;
                case PAUSED:
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        this.stopSimulation();
                    }
                    break;
                case STOPPED:
                    this.stopSimulation();
                    break;
            }
        }
    }
}
