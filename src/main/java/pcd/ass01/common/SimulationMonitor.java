package pcd.ass01.common;

public class SimulationMonitor {

    public enum State {
        RUNNING,
        PAUSED,
        STOPPED
    }

    private State state;

    public SimulationMonitor() {
        state = State.STOPPED;
    }

    public synchronized void start() {
        state = State.RUNNING;
    }

    public synchronized void stop() {
        state = State.STOPPED;
    }

    public synchronized void togglePause() {
        state = state == State.RUNNING ?
                State.PAUSED : State.RUNNING;
    }

    public synchronized State getState() {
        return state;
    }
}
