package com.ruoyi.asset.mapper;

import java.util.List;

import com.ruoyi.asset.domain.FixedAsset;

/**
 * 固定资产信息Mapper接口
 *
 * @author ruoyi
 * @date 2023-01-18
 */
public interface FixedAssetMapper {
    /**
     * 查询固定资产信息
     *
     * @param assetId 固定资产信息主键
     * @return 固定资产信息
     */
    public FixedAsset selectFixedAssetByAssetId(String assetId);

    /**
     * 查询固定资产信息列表
     *
     * @param fixedAsset 固定资产信息
     * @return 固定资产信息集合
     */
    public List<FixedAsset> selectFixedAssetList(FixedAsset fixedAsset);

    /**
     * 新增固定资产信息
     *
     * @param fixedAsset 固定资产信息
     * @return 结果
     */
    public int insertFixedAsset(FixedAsset fixedAsset);

    /**
     * 修改固定资产信息
     *
     * @param fixedAsset 固定资产信息
     * @return 结果
     */
    public int updateFixedAsset(FixedAsset fixedAsset);

    /**
     * 删除固定资产信息
     *
     * @param assetId 固定资产信息主键
     * @return 结果
     */
    public int deleteFixedAssetByAssetId(String assetId);

    /**
     * 批量删除固定资产信息
     *
     * @param assetIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFixedAssetByAssetIds(String[] assetIds);
}
