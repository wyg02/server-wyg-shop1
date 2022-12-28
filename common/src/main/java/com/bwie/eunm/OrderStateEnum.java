package com.bwie.eunm;

public enum OrderStateEnum {
    //订单状态0：未支付1：已支付 2：取消支付 3：已发货
    KILL_NO_PAY(0,"未支付"),
    KILL_YES_PAY(1,"已支付"),
    KILL_CANCE_PAY(2,"取消支付"),
    KILL__SHIPPED(3,"已发货");

    //成员
    private Integer code;
    private String name;

    private OrderStateEnum(int code, String name) {
        this.code=code;
        this.name=name;
    }

    //通过code码获取对应name
    public static String getName(int code){
        for (OrderStateEnum value : OrderStateEnum.values()) {
            if (value.code==code){
                return value.name;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }
}