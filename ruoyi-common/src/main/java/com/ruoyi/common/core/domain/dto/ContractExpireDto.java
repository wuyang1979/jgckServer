package com.ruoyi.common.core.domain.dto;

import java.time.LocalDateTime;

public class ContractExpireDto {

    private String roomName;

    private Long roomType;

    private LocalDateTime leaseEndTime;


    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Long getRoomType() {
        return roomType;
    }

    public void setRoomType(Long roomType) {
        this.roomType = roomType;
    }

    public LocalDateTime getLeaseEndTime() {
        return leaseEndTime;
    }

    public void setLeaseEndTime(LocalDateTime leaseEndTime) {
        this.leaseEndTime = leaseEndTime;
    }
}
