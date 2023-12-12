package ParkingManagementSystem.model;

/**
 * ParkingLot 类表示停车场，管理停车位的分配和释放。
 */
public class ParkingLot {
    final int totalSpots; // 停车场总停车位数
    private int availableSpots; // 可用停车位数

    /**
     * 构造方法，创建 ParkingLot 的实例。
     *
     * @param totalSpots 停车场总停车位数。
     */
    public ParkingLot(int totalSpots) {
        this.totalSpots = totalSpots;
        this.availableSpots = totalSpots;
    }

    /**
     * 检查停车场是否已满。
     *
     * @return 如果停车场已满，返回 true；否则，返回 false。
     */
    public boolean isFull() {
        return availableSpots == 0;
    }

    /**
     * 检查停车场是否为空。
     *
     * @return 如果停车场为空，返回 true；否则，返回 false。
     */
    public boolean isEmpty() {
        return availableSpots == totalSpots;
    }

    /**
     * 获取停车场总停车位数。
     *
     * @return 停车场总停车位数。
     */
    public int getTotalSpots() {
        return totalSpots;
    }

    /**
     * 获取当前可用停车位数。
     *
     * @return 当前可用停车位数。
     */
    public int getAvailableSpots() {
        return availableSpots;
    }

    /**
     * 分配停车位。如果停车场已满，返回 false；否则，分配停车位并返回 true。
     *
     * @return 如果成功分配停车位，返回 true；否则，返回 false。
     */
    public boolean assignParkingSpot() {
        if (isFull()) {
            return false; // 停车场已满，无法分配停车位
        }
        availableSpots--; // 分配停车位，可用停车位数减一
        return true;
    }

    /**
     * 释放停车位，将可用停车位数加一。
     */
    public void releaseParkingSpot() {
        availableSpots++; // 释放停车位，可用停车位数加一
    }
}

