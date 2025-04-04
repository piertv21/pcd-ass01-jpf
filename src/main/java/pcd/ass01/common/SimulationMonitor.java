package pcd.ass01.common;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SimulationMonitor {

    public enum State {
        RUNNING,
        PAUSED,
        STOPPED
    }

    private State state;
    private final Lock lock;

    public SimulationMonitor() {
        state = State.STOPPED;
        lock = new ReentrantLock();
    }

    public void start() {
        try {
            lock.lock();
            state = State.RUNNING;
        } finally {
            lock.unlock();
        }
    }

    public void stop() {
        try {
            lock.lock();
            state = State.STOPPED;
        } finally {
            lock.unlock();
        }
    }

    public void togglePause() {
        try {
            lock.lock();
            state = (state == State.RUNNING) ? State.PAUSED : State.RUNNING;
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
}