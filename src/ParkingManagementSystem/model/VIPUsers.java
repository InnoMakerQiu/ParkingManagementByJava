package ParkingManagementSystem.model;

import java.util.List;
import java.util.ArrayList;

class VIPUserNotFoundException extends Exception{
    public VIPUserNotFoundException(String message){ super(message);}
}

class VIPUser {
    private double balance;
    private String licensePlate;

    public VIPUser(String licensePlate, double balance) {
        this.balance = balance;
        this.licensePlate = licensePlate;
    }

    // 获取成员金额
    public double getBalance() {
        return balance;
    }

    // 获取成员车牌号
    public String getLicensePlate() {
        return licensePlate;
    }

    // 充值金额
    public void increaseTheBalance(double amount) {
        balance += amount;
    }

    // 扣费金额
    public void reduceTheAmount(double amount){
        balance -= amount;
    }
}

public class VIPUsers {
    private List<VIPUser> vipUsers;

    public VIPUsers() {
        vipUsers = new ArrayList<>();
    }

    // 添加VIP用户
    public void addVIPUser(String licensePlate,double amount) {
        VIPUser user = new VIPUser(licensePlate,amount);
        vipUsers.add(user);
    }

    public void topUpBalance(String licensePlate,double amount) throws VIPUserNotFoundException {
        VIPUser vipUser = getVIPUserByLicensePlate(licensePlate);
        vipUser.increaseTheBalance(amount);
    }

    // 根据车牌号获取VIP用户
    public VIPUser getVIPUserByLicensePlate(String licensePlate) throws VIPUserNotFoundException {
        for (VIPUser user : vipUsers) {
            if (user.getLicensePlate().equals(licensePlate)) {
                return user;
            }
        }
        throw new VIPUserNotFoundException("未找到vip用户"); // 如果未找到匹配的VIP用户
    }


    // 列出所有VIP用户
    public List<VIPUser> getAllVIPUsers() {
        return vipUsers;
    }
}

