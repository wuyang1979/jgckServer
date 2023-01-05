package com.ruoyi.credential.service.impl;

import java.util.List;
import java.util.UUID;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.credential.domain.dto.CredentialInfoDtoPage;
import com.ruoyi.credential.domain.vo.CredentialInfoVoPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.credential.mapper.CredentialInfoMapper;
import com.ruoyi.credential.domain.CredentialInfo;
import com.ruoyi.credential.service.ICredentialInfoService;

/**
 * 证件基本信息Service业务层处理
 *
 * @author gubut
 * @date 2022-12-30
 */
@Service
public class CredentialInfoServiceImpl implements ICredentialInfoService {
    @Autowired
    private CredentialInfoMapper credentialInfoMapper;

    /**
     * 查询证件基本信息
     *
     * @param credentialId 证件基本信息主键
     * @return 证件基本信息
     */
    @Override
    public CredentialInfo selectCredentialInfoByCredentialId(String credentialId) {
        return credentialInfoMapper.selectCredentialInfoByCredentialId(credentialId);
    }

    /**
     * 查询证件基本信息列表
     *
     * @param credentialInfoVoPage 证件基本信息
     * @return 证件基本信息
     */
    @DataScope(userAlias = "ci",deptAlias = "ci")
    @Override
    public List<CredentialInfoDtoPage> selectCredentialInfoList(CredentialInfoVoPage credentialInfoVoPage) {
        return credentialInfoMapper.selectCredentialInfoList(credentialInfoVoPage);
    }

    /**
     * 新增证件基本信息
     *
     * @param credentialInfo 证件基本信息
     * @return 结果
     */
    @Override
    public int insertCredentialInfo(CredentialInfo credentialInfo) {
        credentialInfo.setCreateTime(DateUtils.getNowDate());
        credentialInfo.setCredentialId(UUID.randomUUID().toString());
        return credentialInfoMapper.insertCredentialInfo(credentialInfo);
    }

    /**
     * 修改证件基本信息
     *
     * @param credentialInfo 证件基本信息
     * @return 结果
     */
    @Override
    public int updateCredentialInfo(CredentialInfo credentialInfo) {
        credentialInfo.setUpdateTime(DateUtils.getNowDate());
        return credentialInfoMapper.updateCredentialInfo(credentialInfo);
    }

    /**
     * 批量删除证件基本信息
     *
     * @param credentialIds 需要删除的证件基本信息主键
     * @return 结果
     */
    @Override
    public int deleteCredentialInfoByCredentialIds(String[] credentialIds) {
        return credentialInfoMapper.deleteCredentialInfoByCredentialIds(credentialIds);
    }

    /**
     * 删除证件基本信息信息
     *
     * @param credentialId 证件基本信息主键
     * @return 结果
     */
    @Override
    public int deleteCredentialInfoByCredentialId(String credentialId) {
        return credentialInfoMapper.deleteCredentialInfoByCredentialId(credentialId);
    }
}
