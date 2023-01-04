package com.ruoyi.tenants.domain.vo;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 历史租客基本信息对象 DTO
 *
 * @author gubut
 * @date 2022-12-08
 */
public class TenantsInfoHistoryVo extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * 房源id
     */
    private String roomId;


    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
