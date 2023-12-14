package ParkingManagementSystem.model;

/**
 * VIPUser 类表示VIP用户，包含用户的余额和车牌号信息。
 */
class VIPUser {
    private float balance; // 用户余额
    private final String licensePlate; // 车牌号

    /**
     * 构造方法，创建 VIPUser 的实例。
     *
     * @param licensePlate 车牌号。
     * @param balance      用户余额。
     */
    public VIPUser(String licensePlate, float balance) {
        this.balance = balance;
        this.licensePlate = licensePlate;
    }

    /**
     * 获取用户余额。
     *
     * @return 用户余额。
     */
    public float getBalance() {
        return balance;
    }

    /**
     * 获取用户车牌号。
     *
     * @return 用户车牌号。
     */
    public String getLicensePlate() {
        return licensePlate;
    }

    /**
     * 充值用户余额。
     *
     * @param amount 充值金额。
     */
    public void increaseTheBalance(float amount) {
        balance += amount;
    }

    /**
     * 扣除用户余额。
     *
     * @param amount 扣费金额。
     */
    public void reduceTheAmount(float amount) {
        balance -= amount;
    }

    public String toString(){
        return "licensePlate:"+licensePlate+",balance:"+balance;
    }
}
