package com.ruoyi.credential.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 证件基本信息对象 t_credential_info
 *
 * @author gubut
 * @date 2022-12-30
 */
public class CredentialInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 证件主键id
     */
    private String credentialId;

    /**
     * 用户id
     */
    @Excel(name = "证件绑定用户id")
    private String bindUserId;

    /**
     * 证件类型
     */
    @Excel(name = "证件类型")
    private Long credentialType;

    /**
     * 证件到期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "证件到期时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date credentialExpireTime;

    private String userId;

    private String deptId;

    private String spaceId;

    public void setCredentialId(String credentialId) {
        this.credentialId = credentialId;
    }

    public String getCredentialId() {
        return credentialId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setCredentialType(Long credentialType) {
        this.credentialType = credentialType;
    }

    public Long getCredentialType() {
        return credentialType;
    }

    public void setCredentialExpireTime(Date credentialExpireTime) {
        this.credentialExpireTime = credentialExpireTime;
    }

    public Date getCredentialExpireTime() {
        return credentialExpireTime;
    }

    public String getBindUserId() {
        return bindUserId;
    }

    public void setBindUserId(String bindUserId) {
        this.bindUserId = bindUserId;
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
                .append("credentialId", getCredentialId())
                .append("bindUserId", getBindUserId())
                .append("credentialType", getCredentialType())
                .append("credentialExpireTime", getCredentialExpireTime())
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
