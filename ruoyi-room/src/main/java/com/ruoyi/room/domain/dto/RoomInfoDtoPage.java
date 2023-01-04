package com.ruoyi.room.domain.dto;


import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 房源带看列表dto
 */
public class RoomInfoDtoPage extends BaseEntity {

    /**
     * 房源业务id
     */
    private String roomId;

    /**
     * 空间业务id(空间基本信息表主键)
     */
    private String spaceId;

    /**
     * 房源类型
     */
    private Long roomType;

    /**
     * 房源状态
     */
    private Long roomStatus;

    /**
     * 房源号
     */
    private String roomName;

    /**
     * 房源楼层
     */
    private String floor;

    /**
     * 房源面积
     */
    private String area;

    /**
     * 房源地址
     */
    private String roomAddress;

    /**
     * 出售价格
     */
    private String price;

    /**
     * 最低价格
     */
    private String bottomPrice;


    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
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

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getRoomAddress() {
        return roomAddress;
    }

    public void setRoomAddress(String roomAddress) {
        this.roomAddress = roomAddress;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBottomPrice() {
        return bottomPrice;
    }

    public void setBottomPrice(String bottomPrice) {
        this.bottomPrice = bottomPrice;
    }

    public Long getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(Long roomStatus) {
        this.roomStatus = roomStatus;
    }
}
