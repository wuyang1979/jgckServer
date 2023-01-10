package com.ruoyi.space.service;

import java.util.List;

import com.ruoyi.space.domain.SpaceInfo;

/**
 * 空间基本信息Service接口
 *
 * @author ruoyi
 * @date 2022-12-02
 */
public interface ISpaceInfoService {
    /**
     * 查询空间基本信息
     *
     * @param spaceId 空间基本信息主键
     * @return 空间基本信息
     */
    public SpaceInfo selectSpaceInfoBySpaceId(String spaceId);

    /**
     * 查询空间基本信息列表
     *
     * @param spaceInfo 空间基本信息
     * @return 空间基本信息集合
     */
    public List<SpaceInfo> selectSpaceInfoList(SpaceInfo spaceInfo);

    /**
     * 查询空间基本信息列表(无数据过滤)
     * @param spaceInfo
     * @return
     */
    public List<SpaceInfo> listNoScope(SpaceInfo spaceInfo);

    /**
     * 新增空间基本信息
     *
     * @param spaceInfo 空间基本信息
     * @return 结果
     */
    public int insertSpaceInfo(SpaceInfo spaceInfo);

    /**
     * 修改空间基本信息
     *
     * @param spaceInfo 空间基本信息
     * @return 结果
     */
    public int updateSpaceInfo(SpaceInfo spaceInfo);

    /**
     * 批量删除空间基本信息
     *
     * @param spaceIds 需要删除的空间基本信息主键集合
     * @return 结果
     */
    public int deleteSpaceInfoBySpaceIds(String[] spaceIds);

    /**
     * 删除空间基本信息信息
     *
     * @param spaceId 空间基本信息主键
     * @return 结果
     */
    public int deleteSpaceInfoBySpaceId(String spaceId);

    /**
     * 查询所有空间
     *
     * @return
     */
    public List<SpaceInfo> selectAllSpace();


    /**
     * 根据用户ID获取空间选择框列表
     *
     * @param userId 用户ID
     * @return 选中空间ID列表
     */
    public List<String> selectSpaceListByUserId(Long userId);
}
