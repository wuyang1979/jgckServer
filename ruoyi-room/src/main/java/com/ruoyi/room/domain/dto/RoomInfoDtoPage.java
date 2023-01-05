package com.ruoyi.room.domain.dto;


import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 房源带看列表dto
 */
@Data
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

    private String userId;

    private String deptId;
}
