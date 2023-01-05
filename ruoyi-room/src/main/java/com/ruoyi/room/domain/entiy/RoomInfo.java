package com.ruoyi.room.domain.entiy;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 房源基本信息对象 t_room_info
 *
 * @author gubt
 * @date 2022-12-05
 */
public class RoomInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 房源业务id
     */
    @Excel(name = "房源业务id")
    private String roomId;

    /**
     * 空间业务id(空间基本信息表主键)
     */
    @Excel(name = "空间业务id(空间基本信息表主键)")
    private String spaceId;

    /**
     * 房源类型
     */
    @Excel(name = "房源类型")
    private Long roomType;

    /**
     * 房源状态
     */
    @Excel(name = "房源状态")
    private Long roomStatus;

    /**
     * 房间号码
     */
    @Excel(name = "房间号码")
    private String roomName;

    /**
     * 房间楼层
     */
    @Excel(name = "房间楼层")
    private String floor;

    /**
     * 房间面积
     */
    @Excel(name = "房间面积")
    private String area;

    /**
     * 房间地址
     */
    @Excel(name = "房间地址")
    private String roomAddress;

    /**
     * 出售价格
     */
    @Excel(name = "出售价格")
    private String price;

    /**
     * 最低价格
     */
    @Excel(name = "最低价格")
    private String bottomPrice;

    private String userId;

    private String deptId;

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public Long getRoomType() {
        return roomType;
    }

    public void setRoomType(Long roomType) {
        this.roomType = roomType;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getFloor() {
        return floor;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea() {
        return area;
    }

    public void setRoomAddress(String roomAddress) {
        this.roomAddress = roomAddress;
    }

    public String getRoomAddress() {
        return roomAddress;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setBottomPrice(String bottomPrice) {
        this.bottomPrice = bottomPrice;
    }

    public String getBottomPrice() {
        return bottomPrice;
    }

    public Long getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(Long roomStatus) {
        this.roomStatus = roomStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("roomId", getRoomId())
                .append("spaceId", getSpaceId())
                .append("roomType", getRoomType())
                .append("roomStatus", getRoomStatus())
                .append("roomName", getRoomName())
                .append("floor", getFloor())
                .append("area", getArea())
                .append("remark", getRemark())
                .append("roomAddress", getRoomAddress())
                .append("price", getPrice())
                .append("bottomPrice", getBottomPrice())
                .append("userId", getUserId())
                .append("deptId", getDeptId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
