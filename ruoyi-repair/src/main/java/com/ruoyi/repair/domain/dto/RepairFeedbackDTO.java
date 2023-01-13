package com.ruoyi.repair.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 报修反馈基本信息对象 t_repair_feedback DTO
 *
 * @author gubut
 * @date 2023-01-12
 */
@Data
public class RepairFeedbackDTO extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 报修id
     */
    private String repairId;

    /**
     * 房源号
     */
    private String roomName;

    /**
     * 报修人
     */
    private String repairName;

    /**
     * 报修人手机号
     */
    @Excel(name = "报修人手机号")
    @ApiModelProperty("报修人手机号")
    private String repairMobile;

    /**
     * 报修状态
     */
    @Excel(name = "报修状态")
    @ApiModelProperty("报修状态")
    private Long repairStatus;

    /**
     * 报修类型
     */
    @Excel(name = "报修类型")
    @ApiModelProperty("报修类型")
    private Long repairType;

    /**
     * 处理人id
     */
    @Excel(name = "处理人id")
    @ApiModelProperty("处理人id")
    private String handleName;

    /**
     * 处理时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "处理时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("处理时间")
    private LocalDateTime handlerTime;

    /**
     * 描述
     */
    private String remark;

}
