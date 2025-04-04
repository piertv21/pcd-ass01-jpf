package pcd.ass01.ver01;

import pcd.ass01.common.boids.BoidsModel;
import pcd.ass01.common.boids.BoidsSimulator;

import static pcd.ass01.common.Context.*;

public class Main {

    public static void main(String[] args) {
        BoidsModel model = new BoidsModel(
                SEPARATION_WEIGHT, ALIGNMENT_WEIGHT, COHESION_WEIGHT,
                ENVIRONMENT_WIDTH, ENVIRONMENT_HEIGHT,
                MAX_SPEED,
                PERCEPTION_RADIUS,
                AVOID_RADIUS);
        BoidsSimulator sim = new BoidsSimulatorImpl(model);
        //var view = new BoidsView("Multi-threaded version", model, sim, SCREEN_WIDTH, SCREEN_HEIGHT);
        //sim.attachView(view);
        sim.startSimulation(3);
    }
}