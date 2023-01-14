package com.ruoyi.repair.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 报修反馈基本信息对象 t_repair_feedback
 *
 * @author gubut
 * @date 2023-01-12
 */
@Data
public class RepairFeedbackVo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 报修id
     */
    private String repairId;

    /**
     * 报修人手机号
     */
    @Excel(name = "报修人id")
    @ApiModelProperty("报修人id号")
    private String repairHandleId;

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
    private String handleId;

    /**
     * 处理时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "处理时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("处理时间")
    private Date handlerTime;

    /**
     * 空间id
     */
    @Excel(name = "空间id")
    @ApiModelProperty("空间id")
    private String spaceId;

    /**
     * 房源id
     */
    @Excel(name = "房源id")
    @ApiModelProperty("房源id")
    private String roomId;

    private String repairName;

    private String handleName;

}
