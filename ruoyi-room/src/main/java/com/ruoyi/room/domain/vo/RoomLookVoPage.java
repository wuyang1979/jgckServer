package com.ruoyi.room.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 房源带看列表Vo
 *
 * @author gubut
 * @date 2022-12-06
 */
@Data
public class RoomLookVoPage extends BaseEntity {


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


}
