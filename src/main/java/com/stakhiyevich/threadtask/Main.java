package com.stakhiyevich.threadtask;

import com.stakhiyevich.threadtask.entity.TimerThread;
import com.stakhiyevich.threadtask.entity.Truck;
import com.stakhiyevich.threadtask.entity.TruckTask;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {

        List<Truck> trucks = new ArrayList<>();
        trucks.add(getNewTruck(1, 10, 10, false, TruckTask.UNLOAD));
        trucks.add(getNewTruck(2, 0, 300, false, TruckTask.LOAD));
        trucks.add(getNewTruck(3, 10, 10, false, TruckTask.UNLOAD));
        trucks.add(getNewTruck(4, 10, 10, false, TruckTask.UNLOAD));
        trucks.add(getNewTruck(5, 10, 10, false, TruckTask.UNLOAD));
        trucks.add(getNewTruck(6, 10, 10, false, TruckTask.UNLOAD));
        trucks.add(getNewTruck(7, 10, 10, false, TruckTask.UNLOAD));
        trucks.add(getNewTruck(8, 10, 10, false, TruckTask.UNLOAD));
        trucks.add(getNewTruck(9, 10, 10, false, TruckTask.UNLOAD));
        trucks.add(getNewTruck(10, 10, 10, false, TruckTask.UNLOAD));

        Queue<Truck> queue = new ArrayDeque<>(trucks);

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Timer timer = new Timer(true);
        timer.schedule(new TimerThread(), 0, 1000);
        while (!queue.isEmpty()) {
            executorService.submit(queue.poll());
        }
        executorService.shutdown();
    }

    public static Truck getNewTruck(int id, int currentCapacity, int maxCapacity, boolean isPrioritized, TruckTask task) {
        Truck truck = new Truck();
        truck.setId(id);
        truck.setCurrentCapacity(currentCapacity);
        truck.setMaxCapacity(maxCapacity);
        truck.setTask(task);
        return truck;
    }
}
