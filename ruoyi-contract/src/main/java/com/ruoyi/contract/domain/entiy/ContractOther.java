package com.ruoyi.contract.domain.entiy;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 其他合同对象 t_contract_other
 *
 * @author gubt
 * @date 2023-01-16
 */
public class ContractOther extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 合同主键id
     */
    private String contractId;

    /**
     * 合同编号
     */
    private String contractNumber;

    /**
     * 客户id（关联租客id）
     */
    @Excel(name = "客户id", readConverterExp = "关=联租客id")
    private String customerId;

    /**
     * 项目名称
     */
    @Excel(name = "项目名称")
    @ApiModelProperty("项目名称")
    private String projectName;

    /**
     * 缴纳费用
     */
    @Excel(name = "缴纳费用")
    @ApiModelProperty("缴纳费用")
    private String payMoney;

    /**
     * 缴纳周期
     */
    @Excel(name = "缴纳周期")
    @ApiModelProperty("缴纳周期")
    private Long payPeriod;

    /**
     * 合同开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "合同开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("合同开始时间")
    private Date contractStartTime;

    /**
     * 合同截止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "合同截止时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("合同截止时间")
    private Date contractEndTime;

    /**
     * 签约日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "签约日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("签约日期")
    private Date signTime;

    /**
     * 空间id
     */
    private String spaceId;

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayPeriod(Long payPeriod) {
        this.payPeriod = payPeriod;
    }

    public Long getPayPeriod() {
        return payPeriod;
    }

    public void setContractStartTime(Date contractStartTime) {
        this.contractStartTime = contractStartTime;
    }

    public Date getContractStartTime() {
        return contractStartTime;
    }

    public void setContractEndTime(Date contractEndTime) {
        this.contractEndTime = contractEndTime;
    }

    public Date getContractEndTime() {
        return contractEndTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public String getSpaceId() {
        return spaceId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("contractId", getContractId())
                .append("contractNumber", getContractNumber())
                .append("customerId", getCustomerId())
                .append("projectName", getProjectName())
                .append("payMoney", getPayMoney())
                .append("payPeriod", getPayPeriod())
                .append("contractStartTime", getContractStartTime())
                .append("contractEndTime", getContractEndTime())
                .append("signTime", getSignTime())
                .append("remark", getRemark())
                .append("spaceId", getSpaceId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
