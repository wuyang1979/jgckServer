package com.ruoyi.pay.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 缴费基本信息对象 t_pay_info
 *
 * @author gubt
 * @date 2023-01-14
 */
@Data
public class PayInfoPageDTO extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 缴费id
     */
    private String payId;

    /**
     * 月份
     */
    @JsonFormat(pattern = "yyyy-MM")
    @Excel(name = "月份", width = 30, dateFormat = "yyyy-MM")
    @ApiModelProperty("月份")
    private Date month;

    /**
     * 上月参数
     */
    @Excel(name = "上月参数")
    @ApiModelProperty("上月参数")
    private String lastMonthParameter;

    /**
     * 本月参数
     */
    @Excel(name = "本月参数")
    @ApiModelProperty("本月参数")
    private String thisMonthParameter;

    /**
     * 单价
     */
    @Excel(name = "单价")
    @ApiModelProperty("单价")
    private String unitPrice;

    /**
     * 本月使用
     */
    @Excel(name = "本月使用")
    @ApiModelProperty("本月使用")
    private String thisMonthUse;

    /**
     * 应缴金额
     */
    @Excel(name = "应缴金额")
    @ApiModelProperty("应缴金额")
    private String payableMoney;

    /**
     * 缴费状态
     */
    @Excel(name = "缴费状态")
    @ApiModelProperty("缴费状态")
    private Long payStatus;

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
    @ApiModelProperty("空间id")
    private String roomId;

    /**
     * 缴费类型
     */
    @Excel(name = "缴费类型")
    @ApiModelProperty("缴费类型")
    private Long payType;

    /**
     * 房源号
     */
    private String roomName;

}
