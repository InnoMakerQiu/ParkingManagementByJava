package ParkingManagementSystem.model;

import java.util.List;
import java.util.ArrayList;

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
    public void addVIPUser(String licensePlate, float amount) {
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
    public void topUpBalance(String licensePlate, float amount) throws VIPUserNotFoundException {
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
    private VIPUser getVIPUserByLicensePlate(String licensePlate)
            throws VIPUserNotFoundException {
        for (VIPUser user : vipUsers) {
            if (user.getLicensePlate().equals(licensePlate)) {
                return user;
            }
        }
        throw new VIPUserNotFoundException("未找到VIP用户"); // 如果未找到匹配的VIP用户
    }

    /**
     * 根据车牌号判断是否是VIP用户。
     *
     * @param licensePlate 车牌号
     * @return 是否为VIP用户
     */
    public boolean isVIPUser(String licensePlate){
        for(VIPUser user : vipUsers){
            if(user.getLicensePlate().equals(licensePlate)){
                return true;
            }
        }
        return false;
    }

    /**
     * 减少vip用户余额
     *
     * @param licensePlate 车牌号
     * @param amount 减少用户的余额大小。
     * @return 如果余额充足，为真，否则为假
     */
    public boolean reduceBalance(String licensePlate,float amount){
        try {
            VIPUser vipUser = getVIPUserByLicensePlate(licensePlate);
            if (vipUser.getBalance()>amount){
                vipUser.reduceTheAmount(amount);
                return true;
            }else{
                return false;
            }
        }catch(VIPUserNotFoundException e){
            return false;
        }
    }

    /**
     * 查询指定车牌号VIP用户的余额
     *
     * @param licensePlate 车牌号
     * @return 余额
     */
    public float queryTheAmountOfVIPUser(String licensePlate){
        try{
            VIPUser vipUser = getVIPUserByLicensePlate(licensePlate);
            return vipUser.getBalance();
        } catch (VIPUserNotFoundException e) {
            return 0;
        }
    }


    /**
     * 获取所有VIP用户。
     *
     * @return 包含所有VIP用户的列表。
     */
    public List<VIPUser> getAllVIPUsers() {
        return vipUsers;
    }
}

