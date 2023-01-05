package com.ruoyi.room.domain.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 房源带看列表dto
 */
@Data
public class RoomLookDtoPage extends BaseEntity {

    /**
     * 房源带看ID
     */
    private String lookId;
    /**
     * 房源带看名称
     */
    private String roomId;

    /**
     * 房源ID(房源基本信息表主键)
     */
    @Excel(name = "房源名称")
    private String roomName;

    /**
     * 带看人账号（系统用户主键id）
     */
    private String bindUserId;

    /**
     * 带看人名称
     */
    @Excel(name = "带看人名称")
    private String nickName;

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
    @Excel(name = "预约时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date appointTime;

   private String userId;

   private String deptId;
}
