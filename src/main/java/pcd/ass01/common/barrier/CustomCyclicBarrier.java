package pcd.ass01.common.barrier;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Custom Barrier implementation.
 *
 * This class is a custom implementation of the CyclicBarrier,
 * exposing a hitAndWaitAll() method instead of the await() method.
 */
public class CustomCyclicBarrier implements CyclicBarrier {

    private final int nTotal;
    private int nArrivedSoFar;
    private int barrierGeneration;
    private final Lock lock;
    private final Condition condition;

    public CustomCyclicBarrier(int nTotal) {
        this.nTotal = nTotal;
        this.nArrivedSoFar = 0;
        this.barrierGeneration = 0;
        this.lock = new ReentrantLock();
        this.condition = lock.newCondition();
    }

    @Override
    public void hitAndWaitAll() throws InterruptedException {
        try {
            lock.lock();
            int generation = this.barrierGeneration;
            this.nArrivedSoFar++;

            if (this.nArrivedSoFar == this.nTotal) {
                this.nArrivedSoFar = 0;
                this.barrierGeneration++;
                condition.signalAll();
            } else {
                while (this.nArrivedSoFar < this.nTotal &&
                        this.barrierGeneration == generation) {
                    condition.await();
                }
            }
        } finally {
            lock.unlock();
        }
    }
}