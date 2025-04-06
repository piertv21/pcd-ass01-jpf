package pcd.ass01.common;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * SimulationMonitor is a utility class that manages the state of a simulation.
 *
 * It provides methods to start, stop, and pause the simulation, as well as to check its current state.
 */
public class SimulationMonitor {

    public enum State {
        RUNNING,
        PAUSED,
        STOPPED
    }

    private State state;
    private final Lock lock;
    private final Condition notPaused;

    public SimulationMonitor() {
        state = State.STOPPED;
        lock = new ReentrantLock();
        notPaused = lock.newCondition();
    }

    public void start() {
        try {
            lock.lock();
            state = State.RUNNING;
            notPaused.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void stop() {
        try {
            lock.lock();
            state = State.STOPPED;
            notPaused.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void togglePause() {
        try {
            lock.lock();
            if(state == State.RUNNING) {
                state = State.PAUSED;
            } else if(state == State.PAUSED) {
                state = State.RUNNING;
                notPaused.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    public State getState() {
        try {
            lock.lock();
            return state;
        } finally {
            lock.unlock();
        }
    }

    public void waitIfPaused() throws InterruptedException {
        try {
            lock.lock();
            while(state == State.PAUSED) {
                notPaused.await();
            }
        } finally {
            lock.unlock();
        }
    }
}