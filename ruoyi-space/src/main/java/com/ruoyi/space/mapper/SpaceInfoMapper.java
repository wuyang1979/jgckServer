package com.ruoyi.space.mapper;

import java.util.List;

import com.ruoyi.space.domain.SpaceInfo;

/**
 * 空间基本信息Mapper接口
 *
 * @author ruoyi
 * @date 2022-12-02
 */
public interface SpaceInfoMapper {
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
     * 查询空间基本信息列表
     *
     * @param spaceInfo 空间基本信息
     * @return 空间基本信息集合
     */
    public List<SpaceInfo> selectSpaceInfoListNoScope(SpaceInfo spaceInfo);

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
     * 删除空间基本信息
     *
     * @param spaceId 空间基本信息主键
     * @return 结果
     */
    public int deleteSpaceInfoBySpaceId(String spaceId);

    /**
     * 批量删除空间基本信息
     *
     * @param spaceIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSpaceInfoBySpaceIds(String[] spaceIds);
}
