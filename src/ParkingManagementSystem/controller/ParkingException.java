package ParkingManagementSystem.controller;

/**
 * ParkingException 类是自定义异常类，继承自 Java 的 Exception 类。
 * 该异常用于表示与停车场管理相关的异常情况。
 */
public class ParkingException extends Exception {

    /**
     * 构造方法，创建 ParkingException 的实例。
     *
     * @param message 异常的详细描述信息，将会通过父类 Exception 的构造方法传递。
     */
    public ParkingException(String message) {
        // 调用父类 Exception 的构造方法，传递异常描述信息
        super(message);
    }
}
