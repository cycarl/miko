package com.xana.acg.com.thread;

import android.os.Looper;

import static java.lang.Thread.MIN_PRIORITY;

/**
 * 获取一个线程的Looper
 */
public class RecycleThread implements Runnable {
    private final Object mLock = new Object();
    private Looper mLooper;

    public RecycleThread(){
        this("");
    }

    public RecycleThread(String name){
        Thread t = new Thread(null, this, name);
        t.setPriority(MIN_PRIORITY);
        t.start();
        synchronized (mLock){
            while (mLooper==null){
                try {
                    mLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public void run() {
        synchronized (mLock){
            Looper.prepare();
            mLooper = Looper.myLooper();
            mLock.notifyAll();
        }
        Looper.loop();
    }
    public Looper getLooper(){
        return mLooper;
    }
    public void quit(){
        mLooper.quit();
    }
}
