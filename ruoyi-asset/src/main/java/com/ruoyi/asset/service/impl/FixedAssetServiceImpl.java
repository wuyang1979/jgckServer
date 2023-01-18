package com.ruoyi.asset.service.impl;

import java.util.List;
import java.util.UUID;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.asset.mapper.FixedAssetMapper;
import com.ruoyi.asset.domain.FixedAsset;
import com.ruoyi.asset.service.IFixedAssetService;

/**
 * 固定资产信息Service业务层处理
 *
 * @author ruoyi
 * @date 2023-01-18
 */
@Service
public class FixedAssetServiceImpl implements IFixedAssetService {
    @Autowired
    private FixedAssetMapper fixedAssetMapper;

    /**
     * 查询固定资产信息
     *
     * @param assetId 固定资产信息主键
     * @return 固定资产信息
     */
    @Override
    public FixedAsset selectFixedAssetByAssetId(String assetId) {
        return fixedAssetMapper.selectFixedAssetByAssetId(assetId);
    }

    /**
     * 查询固定资产信息列表
     *
     * @param fixedAsset 固定资产信息
     * @return 固定资产信息
     */
    @Override
    @DataScope(userAlias = "fa", deptAlias = "fa")
    public List<FixedAsset> selectFixedAssetList(FixedAsset fixedAsset) {
        return fixedAssetMapper.selectFixedAssetList(fixedAsset);
    }

    /**
     * 新增固定资产信息
     *
     * @param fixedAsset 固定资产信息
     * @return 结果
     */
    @Override
    public int insertFixedAsset(FixedAsset fixedAsset) {
        fixedAsset.setAssetId(UUID.randomUUID().toString());
        fixedAsset.setCreateTime(DateUtils.getNowDate());
        return fixedAssetMapper.insertFixedAsset(fixedAsset);
    }

    /**
     * 修改固定资产信息
     *
     * @param fixedAsset 固定资产信息
     * @return 结果
     */
    @Override
    public int updateFixedAsset(FixedAsset fixedAsset) {
        fixedAsset.setUpdateTime(DateUtils.getNowDate());
        return fixedAssetMapper.updateFixedAsset(fixedAsset);
    }

    /**
     * 批量删除固定资产信息
     *
     * @param assetIds 需要删除的固定资产信息主键
     * @return 结果
     */
    @Override
    public int deleteFixedAssetByAssetIds(String[] assetIds) {
        return fixedAssetMapper.deleteFixedAssetByAssetIds(assetIds);
    }

    /**
     * 删除固定资产信息信息
     *
     * @param assetId 固定资产信息主键
     * @return 结果
     */
    @Override
    public int deleteFixedAssetByAssetId(String assetId) {
        return fixedAssetMapper.deleteFixedAssetByAssetId(assetId);
    }
}
