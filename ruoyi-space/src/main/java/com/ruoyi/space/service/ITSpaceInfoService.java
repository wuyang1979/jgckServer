package com.ruoyi.space.service;

import java.util.List;

import com.ruoyi.space.domain.TSpaceInfo;

/**
 * 空间基本信息Service接口
 *
 * @author ruoyi
 * @date 2022-12-02
 */
public interface ITSpaceInfoService {
    /**
     * 查询空间基本信息
     *
     * @param spaceId 空间基本信息主键
     * @return 空间基本信息
     */
    public TSpaceInfo selectTSpaceInfoBySpaceId(String spaceId);

    /**
     * 查询空间基本信息列表
     *
     * @param tSpaceInfo 空间基本信息
     * @return 空间基本信息集合
     */
    public List<TSpaceInfo> selectTSpaceInfoList(TSpaceInfo tSpaceInfo);

    /**
     * 新增空间基本信息
     *
     * @param tSpaceInfo 空间基本信息
     * @return 结果
     */
    public int insertTSpaceInfo(TSpaceInfo tSpaceInfo);

    /**
     * 修改空间基本信息
     *
     * @param tSpaceInfo 空间基本信息
     * @return 结果
     */
    public int updateTSpaceInfo(TSpaceInfo tSpaceInfo);

    /**
     * 批量删除空间基本信息
     *
     * @param spaceIds 需要删除的空间基本信息主键集合
     * @return 结果
     */
    public int deleteTSpaceInfoBySpaceIds(String[] spaceIds);

    /**
     * 删除空间基本信息信息
     *
     * @param spaceId 空间基本信息主键
     * @return 结果
     */
    public int deleteTSpaceInfoBySpaceId(String spaceId);

    /**
     * 查询所有空间
     *
     * @return
     */
    public List<TSpaceInfo> selectAllSpace();


    /**
     * 根据用户ID获取空间选择框列表
     *
     * @param userId 用户ID
     * @return 选中空间ID列表
     */
    public List<String> selectSpaceListByUserId(Long userId);
}
