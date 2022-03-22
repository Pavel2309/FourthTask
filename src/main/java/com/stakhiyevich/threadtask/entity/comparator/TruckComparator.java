package com.stakhiyevich.threadtask.entity.comparator;

import com.stakhiyevich.threadtask.entity.Truck;

import java.util.Comparator;

public class TruckComparator implements Comparator<Truck> {

    @Override
    public int compare(Truck o1, Truck o2) {
        int result = 0;
        if (o1.isPrioritized() && !o2.isPrioritized()) {
            result = -1;
        } else if (!o1.isPrioritized() && o2.isPrioritized()) {
            result = 1;
        }
        return result;
    }
}
