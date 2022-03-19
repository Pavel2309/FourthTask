package com.stakhiyevich.threadtask.entity;

import com.stakhiyevich.threadtask.entity.comporator.TruckComparator;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class TruckQueue {

    private final Queue<Truck> truckQueue;
    
    public TruckQueue() {
        truckQueue = new PriorityQueue<>(new TruckComparator());
    }

    public void addTruck(Truck truck) {
        truckQueue.add(truck);
    }

    public void addTrucksFromList(List<Truck> trucks) {
        truckQueue.addAll(trucks);
    }

    public Queue<Truck> getTruckQueue() {
        return truckQueue;
    }

    public Truck pollTruck() {
        return truckQueue.poll();
    }

    public boolean isEmpty() {
        return truckQueue.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TruckQueue)) return false;

        TruckQueue that = (TruckQueue) o;

        return truckQueue != null ? truckQueue.equals(that.truckQueue) : that.truckQueue == null;
    }

    @Override
    public int hashCode() {
        return truckQueue != null ? truckQueue.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TruckQueue{");
        sb.append("truckQueue=").append(truckQueue);
        sb.append('}');
        return sb.toString();
    }
}
