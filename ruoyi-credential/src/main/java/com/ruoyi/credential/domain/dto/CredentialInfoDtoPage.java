package com.ruoyi.credential.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 证件基本信息对象 t_credential_info
 *
 * @author gubut
 * @date 2022-12-30
 */
@Data
public class CredentialInfoDtoPage extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 证件主键id
     */
    private String credentialId;

    /**
     * 用户id
     */
    @Excel(name = "用户id")
    private String bindUserId;

    /**
     * 用户名称
     */
    @Excel(name = "用户名称")
    private String userName;

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

}
