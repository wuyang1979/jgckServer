package com.ruoyi.space.service.impl;

import java.util.List;
import java.util.UUID;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.mapper.SysUserSpaceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.space.mapper.TSpaceInfoMapper;
import com.ruoyi.space.domain.TSpaceInfo;
import com.ruoyi.space.service.ITSpaceInfoService;

/**
 * 空间基本信息Service业务层处理
 *
 * @author ruoyi
 * @date 2022-12-02
 */
@Service
public class TSpaceInfoServiceImpl implements ITSpaceInfoService {
    @Autowired
    private TSpaceInfoMapper tSpaceInfoMapper;

    @Autowired
    private SysUserSpaceMapper spaceMapper;

    /**
     * 查询空间基本信息
     *
     * @param spaceId 空间基本信息主键
     * @return 空间基本信息
     */
    @Override
    public TSpaceInfo selectTSpaceInfoBySpaceId(String spaceId) {
        return tSpaceInfoMapper.selectTSpaceInfoBySpaceId(spaceId);
    }

    /**
     * 查询空间基本信息列表
     *
     * @param tSpaceInfo 空间基本信息
     * @return 空间基本信息
     */
    @Override
    public List<TSpaceInfo> selectTSpaceInfoList(TSpaceInfo tSpaceInfo) {
        return tSpaceInfoMapper.selectTSpaceInfoList(tSpaceInfo);
    }

    /**
     * 新增空间基本信息
     *
     * @param tSpaceInfo 空间基本信息
     * @return 结果
     */
    @Override
    public int insertTSpaceInfo(TSpaceInfo tSpaceInfo) {
        tSpaceInfo.setSpaceId(UUID.randomUUID().toString());
        tSpaceInfo.setCreateTime(DateUtils.getNowDate());
        return tSpaceInfoMapper.insertTSpaceInfo(tSpaceInfo);
    }

    /**
     * 修改空间基本信息
     *
     * @param tSpaceInfo 空间基本信息
     * @return 结果
     */
    @Override
    public int updateTSpaceInfo(TSpaceInfo tSpaceInfo) {
        tSpaceInfo.setUpdateTime(DateUtils.getNowDate());
        return tSpaceInfoMapper.updateTSpaceInfo(tSpaceInfo);
    }

    /**
     * 批量删除空间基本信息
     *
     * @param spaceIds 需要删除的空间基本信息主键
     * @return 结果
     */
    @Override
    public int deleteTSpaceInfoBySpaceIds(String[] spaceIds) {
        return tSpaceInfoMapper.deleteTSpaceInfoBySpaceIds(spaceIds);
    }

    /**
     * 删除空间基本信息信息
     *
     * @param spaceId 空间基本信息主键
     * @return 结果
     */
    @Override
    public int deleteTSpaceInfoBySpaceId(String spaceId) {
        return tSpaceInfoMapper.deleteTSpaceInfoBySpaceId(spaceId);
    }


    @Override
    public List<TSpaceInfo> selectAllSpace() {
        return tSpaceInfoMapper.selectTSpaceInfoList(new TSpaceInfo());
    }


    @Override
    public List<String> selectSpaceListByUserId(Long userId) {
        return spaceMapper.selectSpaceListByUserId(userId);
    }
}
