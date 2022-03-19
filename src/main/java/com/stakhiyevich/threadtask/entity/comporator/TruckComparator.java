package com.stakhiyevich.threadtask.entity.comporator;

import com.stakhiyevich.threadtask.entity.Truck;

import java.util.Comparator;

public class TruckComparator implements Comparator<Truck> {

    @Override
    public int compare(Truck o1, Truck o2) {
        if (o1.isPrioritized() && !o2.isPrioritized()) {
            return -1;
        } else if (!o1.isPrioritized() && o2.isPrioritized()) {
            return 1;
        }
        return 0;
    }
}
