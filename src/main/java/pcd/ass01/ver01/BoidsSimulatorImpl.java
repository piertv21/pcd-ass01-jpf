package pcd.ass01.ver01;

import pcd.ass01.common.boids.AbstractBoidsSimulator;
import pcd.ass01.common.boids.BoidsModel;
import pcd.ass01.common.SimulationMonitor;
import pcd.ass01.common.barrier.CustomCyclicBarrier;
import pcd.ass01.common.barrier.CyclicBarrier;
import pcd.ass01.common.boids.BoidsUpdater;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static pcd.ass01.common.Context.*;

public class BoidsSimulatorImpl extends AbstractBoidsSimulator {

    private final List<Thread> boidsUpdaters;
    private CyclicBarrier velBarrier, printBarrier;

    public BoidsSimulatorImpl(BoidsModel model) {
        super(model);
        this.boidsUpdaters = new ArrayList<>();
    }

    @Override
    public void startSimulation(int boidCount) {
        super.startSimulation(boidCount);
        new Thread(this::runSimulation).start();
    }

    @Override
    public void stopSimulation() {
        super.stopSimulation();
        this.boidsUpdaters.forEach(Thread::interrupt);
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

        IntStream.range(0, threadsNumber).forEach(i -> {
            int start = i * baseSize + Math.min(i, extra);
            int end = start + baseSize + (i < extra ? 1 : 0);
            var subList = boids.subList(start, end);
            this.boidsUpdaters.add(new BoidsUpdater(subList,
                    model, velBarrier, printBarrier));
        });
        this.boidsUpdaters.forEach(Thread::start);
    }

    private void runSimulation() {
        this.setupBoidsUpdatersAndBarriers();

        for(int i = 0; i < 2; i++) {
            System.out.println("Iterazione " + i);
            //var t0 = System.currentTimeMillis();
            try {
                printBarrier.hitAndWaitAll();
                printBarrier.hitAndWaitAll();
            } catch (InterruptedException e) {
                this.stopSimulation();
            }
            /*view.ifPresent(v -> {
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
            if(simulationMonitor.getState() == SimulationMonitor.State.PAUSED) {
                try {
                    simulationMonitor.waitIfPaused();
                } catch (InterruptedException e) {
                    this.stopSimulation();
                }
            }*/
        }
        this.stopSimulation();
    }
}