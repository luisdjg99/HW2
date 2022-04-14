package dataStructure;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class MyLinkedList {


    private LinkedList<Integer> linkedList;


    private ReentrantReadWriteLock rwl;


    private boolean writing;


    private Condition writersCondition;


    public MyLinkedList() {
        linkedList = new LinkedList<>();
        rwl = new ReentrantReadWriteLock(true);
        writing = false;
        writersCondition = rwl.writeLock().newCondition();
    }


    public void search(String threadName, Integer value) {

        int index;

        System.out.println("[" + threadName + "](param: "+ value + ") - Trying aquire the lock");
        rwl.readLock().lock();
        try {
            System.out.println("[" + threadName + "](param: "+ value + ") - Starting the search operation");

            index = linkedList.indexOf(value);

            if (index > -1) {
                System.out.println("[" + threadName + "](param: "+ value + ") - found, index: " + index + ".");
            }
            else {
                System.out.println("[" + threadName + "](param: "+ value + ") - not found.");
            }
        } finally {
            rwl.readLock().unlock();
        }
    }


    public void insert(String threadName, Integer value) {

        System.out.println("[" + threadName + "](param: "+ value + ") - Trying aquire the lock");
        rwl.writeLock().lock();
        try {
            // Guard condition
            while (writing) {
                System.out.println("[" + threadName + "](param: "+ value + ") - Another thread is writing, await...");
                writersCondition.await();
            }


            writing = true;
            System.out.println("[" + threadName + "](param: "+ value + ") - Starting the insert operation");

        } catch (InterruptedException e) {
            System.err.println("Failure in execute the insert operation, generated Exception:");
            e.printStackTrace();

        } finally {
            rwl.writeLock().unlock();
        }


        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
        linkedList.addLast(value);
        System.out.println("[" + threadName + "](param: "+ value + ") - Value inserted.");


        rwl.writeLock().lock();
        try {
            writing = false;
            writersCondition.signal();
        } finally {
            rwl.writeLock().unlock();
        }
    }


    public void remove(String threadName, Integer value) {

        boolean sucess;

        System.out.println("[" + threadName + "](param: "+ value + ") - Trying aquire the lock");
        rwl.writeLock().lock();

        try {

            while (writing) {
                System.out.println("[" + threadName + "](param: "+ value + ") - Another thread is writing, await...");
                writersCondition.await();
            }


            writing = true;
            System.out.println("[" + threadName + "](param: "+ value + ") - Starting the remove operation");


            Thread.sleep(50);
            sucess = linkedList.remove(value);

            if (sucess) {
                System.out.println("[" + threadName + "](param: "+ value + ") - Value removed.");
            }
            else {
                System.out.println("[" + threadName + "](param: "+ value + ") - Value not removed (probably it wasn't in the list)");
            }

            writing = false;
            writersCondition.signal(); // Awake one awaiting writer

        } catch (InterruptedException e) {
            System.err.println("Failure in execute the remove operation, generated Exception:");
            e.printStackTrace();

        } finally {
            rwl.writeLock().unlock();
        }
    }
}
