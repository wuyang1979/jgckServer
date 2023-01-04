package com.ruoyi.tenants.domain.dto;

import com.ruoyi.common.core.domain.BaseEntity;

import java.time.LocalDateTime;

/**
 * 历史租客基本信息对象 DTO
 *
 * @author gubut
 * @date 2022-12-08
 */
public class TenantsInfoHistory extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * 租客名称
     */
    private String tenantsName;

    /**
     * 证件号码
     */
    private String cardNumber;

    /**
     * 联系人电话
     */
    private String contactPhone;

    /**
     * 联系人名称
     */
    private String contactName;

    /**
     * 年租金
     **/
    private Long yearMoney;

    /**
     * 租赁开始时间
     **/
    private LocalDateTime leaseStartTime;

    /**
     * 租赁结束时间
     **/
    private LocalDateTime leaseEndTime;

    /**
     * 签约时间
     **/
    private LocalDateTime signTime;

    public String getTenantsName() {
        return tenantsName;
    }

    public void setTenantsName(String tenantsName) {
        this.tenantsName = tenantsName;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Long getYearMoney() {
        return yearMoney;
    }

    public void setYearMoney(Long yearMoney) {
        this.yearMoney = yearMoney;
    }

    public LocalDateTime getLeaseStartTime() {
        return leaseStartTime;
    }

    public void setLeaseStartTime(LocalDateTime leaseStartTime) {
        this.leaseStartTime = leaseStartTime;
    }

    public LocalDateTime getLeaseEndTime() {
        return leaseEndTime;
    }

    public void setLeaseEndTime(LocalDateTime leaseEndTime) {
        this.leaseEndTime = leaseEndTime;
    }

    public LocalDateTime getSignTime() {
        return signTime;
    }

    public void setSignTime(LocalDateTime signTime) {
        this.signTime = signTime;
    }
}
