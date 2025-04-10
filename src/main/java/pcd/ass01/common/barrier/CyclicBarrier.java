package pcd.ass01.common.barrier;

/**
 * CyclicBarrier interface.
 */
public interface CyclicBarrier {

    void hitAndWaitAll() throws InterruptedException;

}