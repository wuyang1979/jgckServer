package com.ruoyi.tenants.domain.entiy;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 租客基本信息对象 t_tenants_info
 *
 * @author gubut
 * @date 2022-12-08
 */
public class TenantsInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 租客id
     */
    private String tenantsId;

    /**
     * 租客名称
     */
    @Excel(name = "租客名称")
    private String tenantsName;

    /**
     * 证件类型
     */
    @Excel(name = "证件类型")
    private Long cardType;

    /**
     * 证件号码
     */
    @Excel(name = "证件号码")
    private String cardNumber;

    /**
     * 注册地址
     */
    @Excel(name = "注册地址")
    private String registerAddress;

    /**
     * 办公地址
     */
    @Excel(name = "办公地址")
    private String officeAddress;

    /**
     * 电话
     */
    @Excel(name = "电话")
    private String phone;

    /**
     * 传真
     */
    @Excel(name = "传真")
    private String fax;

    /**
     * 邮编
     */
    @Excel(name = "邮编")
    private String email;

    /**
     * 联系人电话
     */
    @Excel(name = "联系人电话")
    private String contactPhone;

    /**
     * 联系人名称
     */
    @Excel(name = "联系人名称")
    private String contactName;

    /**
     * 性别
     */
    @Excel(name = "性别")
    private Long sex;

    /**
     * 职业
     */
    @Excel(name = "职业")
    private String profession;

    /**
     * 通讯地址
     */
    @Excel(name = "通讯地址")
    private String communicationAddress;

    /**
     * 紧急联系人姓名
     */
    @Excel(name = "紧急联系人姓名")
    private String emergencyContactName;

    /**
     * 紧急联系人电话
     */
    @Excel(name = "紧急联系人电话")
    private String emergencyContactPhone;

    private String userId;

    private String deptId;

    private String spaceId;

    public void setTenantsId(String tenantsId) {
        this.tenantsId = tenantsId;
    }

    public String getTenantsId() {
        return tenantsId;
    }

    public void setTenantsName(String tenantsName) {
        this.tenantsName = tenantsName;
    }

    public String getTenantsName() {
        return tenantsName;
    }

    public void setCardType(Long cardType) {
        this.cardType = cardType;
    }

    public Long getCardType() {
        return cardType;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }

    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getFax() {
        return fax;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
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

    public void setSex(Long sex) {
        this.sex = sex;
    }

    public Long getSex() {
        return sex;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getProfession() {
        return profession;
    }

    public void setCommunicationAddress(String communicationAddress) {
        this.communicationAddress = communicationAddress;
    }

    public String getCommunicationAddress() {
        return communicationAddress;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
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

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("tenantsId", getTenantsId())
                .append("tenantsName", getTenantsName())
                .append("cardType", getCardType())
                .append("cardNumber", getCardNumber())
                .append("registerAddress", getRegisterAddress())
                .append("officeAddress", getOfficeAddress())
                .append("phone", getPhone())
                .append("fax", getFax())
                .append("email", getEmail())
                .append("contactPhone", getContactPhone())
                .append("contactName", getContactName())
                .append("sex", getSex())
                .append("profession", getProfession())
                .append("communicationAddress", getCommunicationAddress())
                .append("emergencyContactName", getEmergencyContactName())
                .append("emergencyContactPhone", getEmergencyContactPhone())
                .append("remark", getRemark())
                .append("userId", getUserId())
                .append("deptId", getDeptId())
                .append("spaceId", getSpaceId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
