package ParkingManagementSystem;
import ParkingManagementSystem.controller.NetServiceToClientSide;
import ParkingManagementSystem.controller.NetServiceToPeripheralDevice;
import ParkingManagementSystem.controller.ParkingManagement;
import ParkingManagementSystem.controller.SQLBasedFinancialManagement;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {
    final static ParkingManagement parkingManager = new ParkingManagement(100);
    final static SQLBasedFinancialManagement financialManager =
            new SQLBasedFinancialManagement();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        NetServiceToPeripheralDevice netWork = new NetServiceToPeripheralDevice(
                9040, parkingManager,financialManager);
        NetServiceToClientSide netService = new NetServiceToClientSide(
                9010, parkingManager,financialManager,netWork);


        executorService.submit(netWork); // 提交 netWorkServer 线程
        executorService.submit(netService); // 提交 netServerThread 线程

        // 关闭 executorService，防止新任务提交
        executorService.shutdown();
    }
}

