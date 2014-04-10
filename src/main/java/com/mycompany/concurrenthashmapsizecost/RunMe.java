package com.mycompany.concurrenthashmapsizecost;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Demonstrates the time cost for querying the size of a HashMap in Java.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class RunMe
{
    static Map<Integer, Integer> regular = new HashMap<>();
    static Map<Integer, Integer> concurrent = new ConcurrentHashMap<>();
    
    static long startNano;
    static String test;
    
    public static void main(String... ignored) throws InterruptedException
    {
        final int N = 10_000_000;
        
        doTest("regular", regular, N);
        doTest("concurrent", concurrent, N);
    }
    
    public static void doTest(String test, Map<Integer, Integer> theMap, int count) throws InterruptedException
    {
        System.out.println("Running test: " + test);
        
        start(test + ": fill elements");
        
        while (count-- > 0)
            theMap.put(count, count);
        
        end();
        
        TimeUnit.SECONDS.sleep(3);
        
        start(test + ": query size");
        
        int size = theMap.size();
        System.out.println("Proof of size read: " + size);
        
        end();
    }
    
    public static void start(String test) {
        RunMe.test = test;
        startNano = System.nanoTime();
    }
    
    public static void end()
    {
        final long nanos = System.nanoTime() - startNano;
        final long ms = TimeUnit.MILLISECONDS.convert(nanos, TimeUnit.NANOSECONDS);
        System.out.printf("%s, took %s nanoseconds (%s ms).%n", test, nanos, ms);
    }
}