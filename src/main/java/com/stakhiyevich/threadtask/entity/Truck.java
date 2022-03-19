package com.stakhiyevich.threadtask.entity;

public class Truck implements Runnable {

    private long id;
    private int goodsQuantity;
    private int capacity;
    private boolean isPrioritized;
    private TruckTask task;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getGoodsQuantity() {
        return goodsQuantity;
    }

    public void setGoodsQuantity(int goodsQuantity) {
        this.goodsQuantity = goodsQuantity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isPrioritized() {
        return isPrioritized;
    }

    public void setPrioritized(boolean prioritized) {
        isPrioritized = prioritized;
    }

    public TruckTask getTask() {
        return task;
    }

    public void setTask(TruckTask task) {
        this.task = task;
    }

    @Override
    public void run() {
        LogisticsCenter logisticsCenter = LogisticsCenter.getInstance();
        Terminal terminal = logisticsCenter.getTerminal();
        switch (getTask().getTaskType()) {
            case LOAD -> {
                terminal.loadTruck(this);
            }
            case UNLOAD -> {
                terminal.unloadTruck(this);
            }
        }
        logisticsCenter.releaseTerminal(terminal);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Truck)) return false;

        Truck truck = (Truck) o;

        if (id != truck.id) return false;
        if (getGoodsQuantity() != truck.getGoodsQuantity()) return false;
        if (getCapacity() != truck.getCapacity()) return false;
        if (isPrioritized() != truck.isPrioritized()) return false;
        return getTask() != null ? getTask().equals(truck.getTask()) : truck.getTask() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + getGoodsQuantity();
        result = 31 * result + getCapacity();
        result = 31 * result + (isPrioritized() ? 1 : 0);
        result = 31 * result + (getTask() != null ? getTask().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Truck{");
        sb.append("id=").append(id);
        sb.append(", goodsQuantity=").append(goodsQuantity);
        sb.append(", capacity=").append(capacity);
        sb.append(", isPrioritized=").append(isPrioritized);
        sb.append(", task=").append(task);
        sb.append('}');
        return sb.toString();
    }
}
