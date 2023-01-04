package com.ruoyi.credential.mapper;

import java.util.List;

import com.ruoyi.credential.domain.CredentialInfo;
import com.ruoyi.credential.domain.dto.CredentialInfoDtoPage;
import com.ruoyi.credential.domain.vo.CredentialInfoVoPage;

/**
 * 证件基本信息Mapper接口
 *
 * @author gubut
 * @date 2022-12-30
 */
public interface CredentialInfoMapper {
    /**
     * 查询证件基本信息
     *
     * @param credentialId 证件基本信息主键
     * @return 证件基本信息
     */
    public CredentialInfo selectCredentialInfoByCredentialId(String credentialId);

    /**
     * 查询证件基本信息列表
     *
     * @param credentialInfoVoPage 证件基本信息
     * @return 证件基本信息集合
     */
    public List<CredentialInfoDtoPage> selectCredentialInfoList(CredentialInfoVoPage credentialInfoVoPage);

    /**
     * 新增证件基本信息
     *
     * @param credentialInfo 证件基本信息
     * @return 结果
     */
    public int insertCredentialInfo(CredentialInfo credentialInfo);

    /**
     * 修改证件基本信息
     *
     * @param credentialInfo 证件基本信息
     * @return 结果
     */
    public int updateCredentialInfo(CredentialInfo credentialInfo);

    /**
     * 删除证件基本信息
     *
     * @param credentialId 证件基本信息主键
     * @return 结果
     */
    public int deleteCredentialInfoByCredentialId(String credentialId);

    /**
     * 批量删除证件基本信息
     *
     * @param credentialIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCredentialInfoByCredentialIds(String[] credentialIds);
}
