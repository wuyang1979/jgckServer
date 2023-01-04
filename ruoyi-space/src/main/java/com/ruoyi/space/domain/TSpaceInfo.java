package com.ruoyi.space.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 空间基本信息对象 t_space_info
 *
 * @author ruoyi
 * @date 2022-12-02
 */
public class TSpaceInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 空间业务id
     */
    @Excel(name = "空间业务id")
    private String spaceId;

    /**
     * 空间名称
     */
    @Excel(name = "空间名称")
    private String spaceName;

    /**
     * 空间地址
     */
    @Excel(name = "空间地址")
    private String spaceAddress;

    @Excel(name = "公司名称")
    private String companyName;

    @Excel(name = "开户银行")
    private String accountBank;

    @Excel(name = "开户账号")
    private String accountNum;

    @Excel(name = "备注")
    private String remark;

    /**
     * 删除标识 0-未删除 1-已删除
     */
    private Long delstatus;

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }

    public String getSpaceName() {
        return spaceName;
    }

    public void setSpaceAddress(String spaceAddress) {
        this.spaceAddress = spaceAddress;
    }

    public String getSpaceAddress() {
        return spaceAddress;
    }

    public void setDelstatus(Long delstatus) {
        this.delstatus = delstatus;
    }

    public Long getDelstatus() {
        return delstatus;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAccountBank() {
        return accountBank;
    }

    public void setAccountBank(String accountBank) {
        this.accountBank = accountBank;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("spaceId", getSpaceId())
                .append("spaceName", getSpaceName())
                .append("spaceAddress", getSpaceAddress())
                .append("componyName", getCompanyName())
                .append("accountBank", getAccountBank())
                .append("accountNum", getAccountNum())
                .append("remark", getRemark())
                .append("delstatus", getDelstatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
