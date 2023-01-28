package com.ruoyi.brand.service.impl;

import java.util.List;
import java.util.UUID;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.brand.mapper.BrandCenterMapper;
import com.ruoyi.brand.domain.BrandCenter;
import com.ruoyi.brand.service.IBrandCenterService;

/**
 * 品牌中心基本信息Service业务层处理
 *
 * @author gubut
 * @date 2023-01-13
 */
@Service
public class BrandCenterServiceImpl implements IBrandCenterService {
    @Autowired
    private BrandCenterMapper brandCenterMapper;

    /**
     * 查询品牌中心基本信息
     *
     * @param brandId 品牌中心基本信息主键
     * @return 品牌中心基本信息
     */
    @Override
    public BrandCenter selectBrandCenterByBrandId(String brandId) {
        return brandCenterMapper.selectBrandCenterByBrandId(brandId);
    }

    /**
     * 查询品牌中心基本信息列表
     *
     * @param brandCenter 品牌中心基本信息
     * @return 品牌中心基本信息
     */
    @DataScope(userAlias = "u",deptAlias = "d")
    @Override
    public List<BrandCenter> selectBrandCenterList(BrandCenter brandCenter) {
        return brandCenterMapper.selectBrandCenterList(brandCenter);
    }

    /**
     * 新增品牌中心基本信息
     *
     * @param brandCenter 品牌中心基本信息
     * @return 结果
     */
    @Override
    public int insertBrandCenter(BrandCenter brandCenter) {
        brandCenter.setCreateTime(DateUtils.getNowDate());
        brandCenter.setBrandId(UUID.randomUUID().toString());
        return brandCenterMapper.insertBrandCenter(brandCenter);
    }

    /**
     * 修改品牌中心基本信息
     *
     * @param brandCenter 品牌中心基本信息
     * @return 结果
     */
    @Override
    public int updateBrandCenter(BrandCenter brandCenter) {
        brandCenter.setUpdateTime(DateUtils.getNowDate());
        return brandCenterMapper.updateBrandCenter(brandCenter);
    }

    /**
     * 批量删除品牌中心基本信息
     *
     * @param brandIds 需要删除的品牌中心基本信息主键
     * @return 结果
     */
    @Override
    public int deleteBrandCenterByBrandIds(String[] brandIds) {
        return brandCenterMapper.deleteBrandCenterByBrandIds(brandIds);
    }

    /**
     * 删除品牌中心基本信息信息
     *
     * @param brandId 品牌中心基本信息主键
     * @return 结果
     */
    @Override
    public int deleteBrandCenterByBrandId(String brandId) {
        return brandCenterMapper.deleteBrandCenterByBrandId(brandId);
    }
}
