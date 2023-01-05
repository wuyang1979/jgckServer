package com.ruoyi.space.service.impl;

import java.util.List;
import java.util.UUID;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.mapper.SysUserSpaceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.space.mapper.SpaceInfoMapper;
import com.ruoyi.space.domain.SpaceInfo;
import com.ruoyi.space.service.ISpaceInfoService;

/**
 * 空间基本信息Service业务层处理
 *
 * @author ruoyi
 * @date 2022-12-02
 */
@Service
public class SpaceInfoServiceImpl implements ISpaceInfoService {
    @Autowired
    private SpaceInfoMapper spaceInfoMapper;

    @Autowired
    private SysUserSpaceMapper spaceMapper;

    /**
     * 查询空间基本信息
     *
     * @param spaceId 空间基本信息主键
     * @return 空间基本信息
     */
    @Override
    public SpaceInfo selectSpaceInfoBySpaceId(String spaceId) {
        return spaceInfoMapper.selectSpaceInfoBySpaceId(spaceId);
    }

    /**
     * 查询空间基本信息列表
     *
     * @param spaceInfo 空间基本信息
     * @return 空间基本信息
     */
    @DataScope(userAlias = "s",deptAlias = "s")
    @Override
    public List<SpaceInfo> selectSpaceInfoList(SpaceInfo spaceInfo) {
        return spaceInfoMapper.selectSpaceInfoList(spaceInfo);
    }

    /**
     * 新增空间基本信息
     *
     * @param spaceInfo 空间基本信息
     * @return 结果
     */
    @Override
    public int insertSpaceInfo(SpaceInfo spaceInfo) {
        spaceInfo.setSpaceId(UUID.randomUUID().toString());
        spaceInfo.setCreateTime(DateUtils.getNowDate());
        return spaceInfoMapper.insertSpaceInfo(spaceInfo);
    }

    /**
     * 修改空间基本信息
     *
     * @param spaceInfo 空间基本信息
     * @return 结果
     */
    @Override
    public int updateSpaceInfo(SpaceInfo spaceInfo) {
        spaceInfo.setUpdateTime(DateUtils.getNowDate());
        return spaceInfoMapper.updateSpaceInfo(spaceInfo);
    }

    /**
     * 批量删除空间基本信息
     *
     * @param spaceIds 需要删除的空间基本信息主键
     * @return 结果
     */
    @Override
    public int deleteSpaceInfoBySpaceIds(String[] spaceIds) {
        return spaceInfoMapper.deleteSpaceInfoBySpaceIds(spaceIds);
    }

    /**
     * 删除空间基本信息信息
     *
     * @param spaceId 空间基本信息主键
     * @return 结果
     */
    @Override
    public int deleteSpaceInfoBySpaceId(String spaceId) {
        return spaceInfoMapper.deleteSpaceInfoBySpaceId(spaceId);
    }


    @Override
    public List<SpaceInfo> selectAllSpace() {
        return spaceInfoMapper.selectSpaceInfoList(new SpaceInfo());
    }


    @Override
    public List<String> selectSpaceListByUserId(Long userId) {
        return spaceMapper.selectSpaceListByUserId(userId);
    }
}
