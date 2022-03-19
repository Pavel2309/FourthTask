package com.stakhiyevich.threadtask.entity;

public class TruckTask {

    private TaskType taskType;
    private int quantity;

    public enum TaskType {
        LOAD, UNLOAD
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TruckTask)) return false;

        TruckTask truckTask = (TruckTask) o;

        if (getQuantity() != truckTask.getQuantity()) return false;
        return getTaskType() == truckTask.getTaskType();
    }

    @Override
    public int hashCode() {
        int result = getQuantity();
        result = 31 * result + (getTaskType() != null ? getTaskType().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TruckTask{");
        sb.append("quantity=").append(quantity);
        sb.append(", taskType=").append(taskType);
        sb.append('}');
        return sb.toString();
    }
}
