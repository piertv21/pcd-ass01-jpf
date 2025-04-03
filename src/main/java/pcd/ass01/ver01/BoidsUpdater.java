package pcd.ass01.ver01;

import pcd.ass01.common.SimulationMonitor;
import pcd.ass01.common.barrier.CyclicBarrier;
import pcd.ass01.common.boids.Boid;
import pcd.ass01.common.boids.BoidsModel;

import java.util.List;

public class BoidsUpdater extends Thread {

    private final SimulationMonitor monitor;
    private final BoidsModel model;
    private final List<Boid> boids;
    private final CyclicBarrier velBarrier, printBarrier;
    private boolean running;

    public BoidsUpdater(
        List<Boid> boids,
        BoidsModel model,
        CyclicBarrier velBarrier,
        CyclicBarrier printBarrier,
        SimulationMonitor monitor
    ) {
        this.boids = boids;
        this.model = model;
        this.velBarrier = velBarrier;
        this.printBarrier = printBarrier;
        this.monitor = monitor;
        this.running = true;
    }

    @Override
    public void run() {
        while (running) {
            switch (monitor.getState()) {
                case RUNNING -> {
                    awaitBarrier(printBarrier);
                    boids.forEach(boid -> boid.updateVelocity(model));
                    awaitBarrier(velBarrier);
                    boids.forEach(boid -> boid.updatePos(model));
                    awaitBarrier(printBarrier);
                }
                case PAUSED -> {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        running = false;
                    }
                }
                case STOPPED -> running = false;
            }
        }
    }

    private void awaitBarrier(CyclicBarrier barrier) {
        try {
            barrier.hitAndWaitAll();
        } catch (InterruptedException e) {
            running = false;
        }
    }
}
