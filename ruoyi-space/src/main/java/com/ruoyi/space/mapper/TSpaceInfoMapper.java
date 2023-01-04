package com.ruoyi.space.mapper;

import java.util.List;

import com.ruoyi.space.domain.TSpaceInfo;

/**
 * 空间基本信息Mapper接口
 *
 * @author ruoyi
 * @date 2022-12-02
 */
public interface TSpaceInfoMapper {
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
     * 删除空间基本信息
     *
     * @param spaceId 空间基本信息主键
     * @return 结果
     */
    public int deleteTSpaceInfoBySpaceId(String spaceId);

    /**
     * 批量删除空间基本信息
     *
     * @param spaceIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTSpaceInfoBySpaceIds(String[] spaceIds);
}
