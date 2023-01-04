package com.ruoyi.common.core.domain.dto;

import java.time.LocalDateTime;

public class CredentialExpireDto {

    private Long credentialType;

    private String userName;

    private LocalDateTime credentialExpireTime;


    public Long getCredentialType() {
        return credentialType;
    }

    public void setCredentialType(Long credentialType) {
        this.credentialType = credentialType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDateTime getCredentialExpireTime() {
        return credentialExpireTime;
    }

    public void setCredentialExpireTime(LocalDateTime credentialExpireTime) {
        this.credentialExpireTime = credentialExpireTime;
    }
}
