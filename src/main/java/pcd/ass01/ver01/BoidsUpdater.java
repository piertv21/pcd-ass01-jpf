package pcd.ass01.ver01;

import pcd.ass01.common.boids.BoidsModel;
import pcd.ass01.common.barrier.CyclicBarrier;
import pcd.ass01.common.boids.Boid;

import java.util.List;

public class BoidsUpdater extends Thread {

    private final BoidsModel model;
    private final List<Boid> boids;
    private final CyclicBarrier velBarrier, printBarrier;

    public BoidsUpdater(
        List<Boid> boids,
        BoidsModel model,
        CyclicBarrier velBarrier,
        CyclicBarrier printBarrier
    ) {
        this.boids = boids;
        this.model = model;
        this.velBarrier = velBarrier;
        this.printBarrier = printBarrier;
    }

    @Override
    public void run() {
        while (true) {
            if (!awaitBarrier(printBarrier)) break;
            //boids.forEach(boid -> boid.updateVelocity(model));
            if (!awaitBarrier(velBarrier)) break;
            //boids.forEach(boid -> boid.updatePos(model));
            if (!awaitBarrier(printBarrier)) break;
        }
    }

    private boolean awaitBarrier(CyclicBarrier barrier) {
        try {
            barrier.hitAndWaitAll();
            return true;
        } catch (InterruptedException e) {
            return false;
        }
    }
}