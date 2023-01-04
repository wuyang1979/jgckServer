package com.ruoyi.room.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 房源带看列表Vo
 *
 * @author gubut
 * @date 2022-12-06
 */
public class RoomLookVoPage {


    /**
     * 房源ID(房源基本信息表主键)
     */
    private String roomName;

    /**
     * 带看人名称
     */
    private String nickName;

    /**
     * 租客名称
     */
    private String customerName;

    /**
     * 租客手机
     */
    private String customerPhone;

    /**
     * 预约开始时间
     */
    private String appointTimeStart;

    /**
     * 预约结束时间
     */

    private String appointTimeEnd;


    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAppointTimeStart() {
        return appointTimeStart;
    }

    public void setAppointTimeStart(String appointTimeStart) {
        this.appointTimeStart = appointTimeStart;
    }

    public String getAppointTimeEnd() {
        return appointTimeEnd;
    }

    public void setAppointTimeEnd(String appointTimeEnd) {
        this.appointTimeEnd = appointTimeEnd;
    }
}
