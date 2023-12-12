package ParkingManagementSystem.model;

public class ParkingLot {
    final int totalSpots;
    private int availableSpots;

    public ParkingLot(int totalSpots) {
        this.totalSpots = totalSpots;
        this.availableSpots = totalSpots;
    }

    public boolean isFull() {
        return availableSpots == 0;
    }

    public boolean isEmpty() {
        return availableSpots == totalSpots;
    }

    public int getTotalSpots() {
        return totalSpots;
    }

    public int getAvailableSpots() {
        return availableSpots;
    }

    // 分配停车位
    public boolean assignParkingSpot() {
        if (isFull()) {
            return false;
        }
        availableSpots--;
        return true;
    }

    // 释放停车位
    public void releaseParkingSpot() {
        availableSpots++;
    }
}
