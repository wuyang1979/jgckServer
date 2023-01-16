package com.ruoyi.pay.domain.entiy;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 缴费基本信息对象 t_pay_info
 *
 * @author gubt
 * @date 2023-01-14
 */
public class PayInfo extends BaseEntity {
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
    private Long lastMonthParameter;

    /**
     * 本月参数
     */
    @Excel(name = "本月参数")
    @ApiModelProperty("本月参数")
    private Long thisMonthParameter;

    /**
     * 单价
     */
    @Excel(name = "单价")
    @ApiModelProperty("单价")
    private Float unitPrice;

    /**
     * 本月使用
     */
    @Excel(name = "本月使用")
    @ApiModelProperty("本月使用")
    private Long thisMonthUse;

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

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getPayId() {
        return payId;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Date getMonth() {
        return month;
    }

    public void setLastMonthParameter(Long lastMonthParameter) {
        this.lastMonthParameter = lastMonthParameter;
    }

    public Long getLastMonthParameter() {
        return lastMonthParameter;
    }

    public void setThisMonthParameter(Long thisMonthParameter) {
        this.thisMonthParameter = thisMonthParameter;
    }

    public Long getThisMonthParameter() {
        return thisMonthParameter;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setThisMonthUse(Long thisMonthUse) {
        this.thisMonthUse = thisMonthUse;
    }

    public Long getThisMonthUse() {
        return thisMonthUse;
    }

    public void setPayableMoney(String payableMoney) {
        this.payableMoney = payableMoney;
    }

    public String getPayableMoney() {
        return payableMoney;
    }

    public void setPayStatus(Long payStatus) {
        this.payStatus = payStatus;
    }

    public Long getPayStatus() {
        return payStatus;
    }

    public Long getPayType() {
        return payType;
    }

    public void setPayType(Long payType) {
        this.payType = payType;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("payId", getPayId())
                .append("month", getMonth())
                .append("lastMonthParameter", getLastMonthParameter())
                .append("thisMonthParameter", getThisMonthParameter())
                .append("unitPrice", getUnitPrice())
                .append("thisMonthUse", getThisMonthUse())
                .append("payableMoney", getPayableMoney())
                .append("payStatus", getPayStatus())
                .append("payType", getPayType())
                .append("spaceId", getSpaceId())
                .append("roomId", getRoomId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
