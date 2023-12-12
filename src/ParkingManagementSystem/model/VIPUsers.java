package ParkingManagementSystem.model;

import java.util.List;
import java.util.ArrayList;

/**
 * VIPUser 类表示VIP用户，包含用户的余额和车牌号信息。
 */
class VIPUser {
    private double balance; // 用户余额
    private final String licensePlate; // 车牌号

    /**
     * 构造方法，创建 VIPUser 的实例。
     *
     * @param licensePlate 车牌号。
     * @param balance      用户余额。
     */
    public VIPUser(String licensePlate, double balance) {
        this.balance = balance;
        this.licensePlate = licensePlate;
    }

    /**
     * 获取用户余额。
     *
     * @return 用户余额。
     */
    public double getBalance() {
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
    public void increaseTheBalance(double amount) {
        balance += amount;
    }

    /**
     * 扣除用户余额。
     *
     * @param amount 扣费金额。
     */
    public void reduceTheAmount(double amount) {
        balance -= amount;
    }
}

/**
 * VIPUsers 类表示所有VIP用户的集合，提供了添加、充值、获取用户和获取所有用户的方法。
 */
public class VIPUsers {
    private final List<VIPUser> vipUsers; // VIP用户列表

    /**
     * 构造方法，创建 VIPUsers 的实例。
     */
    public VIPUsers() {
        vipUsers = new ArrayList<>();
    }

    /**
     * 添加新的VIP用户。
     *
     * @param licensePlate 车牌号。
     * @param amount       用户初始余额。
     */
    public void addVIPUser(String licensePlate, double amount) {
        VIPUser user = new VIPUser(licensePlate, amount);
        vipUsers.add(user);
    }

    /**
     * 充值指定VIP用户的余额。
     *
     * @param licensePlate 车牌号。
     * @param amount       充值金额。
     * @throws VIPUserNotFoundException 如果未找到匹配的VIP用户。
     */
    public void topUpBalance(String licensePlate, double amount) throws VIPUserNotFoundException {
        VIPUser vipUser = getVIPUserByLicensePlate(licensePlate);
        vipUser.increaseTheBalance(amount);
    }

    /**
     * 根据车牌号获取VIP用户。
     *
     * @param licensePlate 车牌号。
     * @return 匹配的VIP用户。
     * @throws VIPUserNotFoundException 如果未找到匹配的VIP用户。
     */
    VIPUser getVIPUserByLicensePlate(String licensePlate) throws VIPUserNotFoundException {
        for (VIPUser user : vipUsers) {
            if (user.getLicensePlate().equals(licensePlate)) {
                return user;
            }
        }
        throw new VIPUserNotFoundException("未找到VIP用户"); // 如果未找到匹配的VIP用户
    }

    /**
     * 获取所有VIP用户。
     *
     * @return 包含所有VIP用户的列表。
     */
    List<VIPUser> getAllVIPUsers() {
        return vipUsers;
    }
}

