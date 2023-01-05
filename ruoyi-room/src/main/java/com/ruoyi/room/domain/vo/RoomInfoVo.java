package com.ruoyi.room.domain.vo;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 房源基本信息对象 t_room_info
 *
 * @author gubt
 * @date 2022-12-05
 */
@Data
public class RoomInfoVo extends BaseEntity {
    private static final long serialVersionUID = 1L;

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
     * 房间号码
     */
    private String roomName;

    /**
     * 房间楼层
     */
    private String floor;

    /**
     * 房间面积
     */
    private String area;

    /**
     * 房间地址
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

    /**
     * 空间名称
     */
    private String spaceName;

    /**
     * 数据过滤用户id
     */
    private String userId;

    /**
     * 数据过滤部门id
     */
    private String deptId;
}
