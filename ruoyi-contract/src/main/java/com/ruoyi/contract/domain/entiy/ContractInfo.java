package com.ruoyi.contract.domain.entiy;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 合同信息对象 t_contract_info
 *
 * @author gubut
 * @date 2022-12-09
 */
public class ContractInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 合同id
     */
    private String contractId;

    /**
     * 合同编号
     */
    @Excel(name = "合同编号")
    private String contractNumber;

    /**
     * 租赁开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "租赁开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date leaseStartTime;

    /**
     * 租赁结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "租赁结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date leaseEndTime;

    /**
     * 支付周期（单位月）
     */
    @Excel(name = "支付周期", readConverterExp = "单=位月")
    private String paymentPeriod;

    /**
     * 首次支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "首次支付时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date paymentFirstTime;

    /**
     * 首次支付金额（大写）
     */
    @Excel(name = "首次支付金额", readConverterExp = "大=写")
    private String paymentFirstMoney;

    /**
     * 支付期限
     */
    @Excel(name = "支付期限")
    private String paymentDeadline;

    /**
     * 房源id
     */
    @Excel(name = "房源id")
    private String roomId;

    /**
     * 年租金
     */
    @Excel(name = "年租金")
    private String yearMoney;

    /**
     * 保证金
     */
    @Excel(name = "保证金")
    private String securityDeposit;

    /**
     * 租客id
     */
    @Excel(name = "租客id")
    private String tenantsId;

    /**
     * 同住人id
     */
    @Excel(name = "同住人id")
    private String togetherPersonId;

    /**
     * 合同类型
     */
    @Excel(name = "合同类型")
    private Long contractType;

    /**
     * 合同类型
     */
    @Excel(name = "合同状态")
    private Long contractStatus;

    private String userId;

    private String deptId;

    /**
     * 签约时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "签约时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date signTime;

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

    public void setLeaseStartTime(Date leaseStartTime) {
        this.leaseStartTime = leaseStartTime;
    }

    public Date getLeaseStartTime() {
        return leaseStartTime;
    }

    public void setLeaseEndTime(Date leaseEndTime) {
        this.leaseEndTime = leaseEndTime;
    }

    public Date getLeaseEndTime() {
        return leaseEndTime;
    }

    public void setPaymentPeriod(String paymentPeriod) {
        this.paymentPeriod = paymentPeriod;
    }

    public String getPaymentPeriod() {
        return paymentPeriod;
    }

    public void setPaymentFirstTime(Date paymentFirstTime) {
        this.paymentFirstTime = paymentFirstTime;
    }

    public Date getPaymentFirstTime() {
        return paymentFirstTime;
    }

    public void setPaymentFirstMoney(String paymentFirstMoney) {
        this.paymentFirstMoney = paymentFirstMoney;
    }

    public String getPaymentFirstMoney() {
        return paymentFirstMoney;
    }

    public void setPaymentDeadline(String paymentDeadline) {
        this.paymentDeadline = paymentDeadline;
    }

    public String getPaymentDeadline() {
        return paymentDeadline;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setSecurityDeposit(String securityDeposit) {
        this.securityDeposit = securityDeposit;
    }

    public String getSecurityDeposit() {
        return securityDeposit;
    }

    public void setTenantsId(String tenantsId) {
        this.tenantsId = tenantsId;
    }

    public String getTenantsId() {
        return tenantsId;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public Date getSignTime() {
        return signTime;
    }

    public String getYearMoney() {
        return yearMoney;
    }

    public void setYearMoney(String yearMoney) {
        this.yearMoney = yearMoney;
    }

    public String getTogetherPersonId() {
        return togetherPersonId;
    }

    public void setTogetherPersonId(String togetherPersonId) {
        this.togetherPersonId = togetherPersonId;
    }

    public Long getContractType() {
        return contractType;
    }

    public void setContractType(Long contractType) {
        this.contractType = contractType;
    }

    public Long getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(Long contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("contractId", getContractId())
                .append("contractNumber", getContractNumber())
                .append("leaseStartTime", getLeaseStartTime())
                .append("leaseEndTime", getLeaseEndTime())
                .append("paymentPeriod", getPaymentPeriod())
                .append("paymentFirstTime", getPaymentFirstTime())
                .append("paymentFirstMoney", getPaymentFirstMoney())
                .append("paymentDeadline", getPaymentDeadline())
                .append("roomId", getRoomId())
                .append("securityDeposit", getSecurityDeposit())
                .append("tenantsId", getTenantsId())
                .append("signTime", getSignTime())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("yearMoney", getYearMoney())
                .append("togetherPersonId", getTogetherPersonId())
                .append("contractType", getContractType())
                .append("contractStatus", getContractStatus())
                .toString();
    }
}
