package com.ruoyi.room.domain.entiy;

import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 房源带看基本信息对象 t_room_look
 *
 * @author gubut
 * @date 2022-12-06
 */
public class RoomLook extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 房源带看ID
     */
    private String lookId;

    /**
     * 房源ID(房源基本信息表主键)
     */
    @Excel(name = "房源ID(房源基本信息表主键)")
    private String roomId;

    /**
     * 带看人账号（系统用户主键id）
     */
    @Excel(name = "带看人账号", readConverterExp = "系=统用户主键id")
    private String userId;

    /**
     * 租客名称
     */
    @Excel(name = "租客名称")
    private String customerName;

    /**
     * 租客手机
     */
    @Excel(name = "租客手机")
    private String customerPhone;

    /**
     * 预约时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "预约时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime appointTime;

    public void setLookId(String lookId) {
        this.lookId = lookId;
    }

    public String getLookId() {
        return lookId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

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

    public LocalDateTime getAppointTime() {
        return appointTime;
    }

    public void setAppointTime(LocalDateTime appointTime) {
        this.appointTime = appointTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("lookId", getLookId())
                .append("roomId", getRoomId())
                .append("userId", getUserId())
                .append("customerName", getCustomerName())
                .append("customerPhone", getCustomerPhone())
                .append("appointTime", getAppointTime())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
