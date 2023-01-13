package com.ruoyi.brand.service;

import java.util.List;

import com.ruoyi.brand.domain.BrandCenter;

/**
 * 品牌中心基本信息Service接口
 *
 * @author gubut
 * @date 2023-01-13
 */
public interface IBrandCenterService {
    /**
     * 查询品牌中心基本信息
     *
     * @param brandId 品牌中心基本信息主键
     * @return 品牌中心基本信息
     */
    public BrandCenter selectBrandCenterByBrandId(String brandId);

    /**
     * 查询品牌中心基本信息列表
     *
     * @param brandCenter 品牌中心基本信息
     * @return 品牌中心基本信息集合
     */
    public List<BrandCenter> selectBrandCenterList(BrandCenter brandCenter);

    /**
     * 新增品牌中心基本信息
     *
     * @param brandCenter 品牌中心基本信息
     * @return 结果
     */
    public int insertBrandCenter(BrandCenter brandCenter);

    /**
     * 修改品牌中心基本信息
     *
     * @param brandCenter 品牌中心基本信息
     * @return 结果
     */
    public int updateBrandCenter(BrandCenter brandCenter);

    /**
     * 批量删除品牌中心基本信息
     *
     * @param brandIds 需要删除的品牌中心基本信息主键集合
     * @return 结果
     */
    public int deleteBrandCenterByBrandIds(String[] brandIds);

    /**
     * 删除品牌中心基本信息信息
     *
     * @param brandId 品牌中心基本信息主键
     * @return 结果
     */
    public int deleteBrandCenterByBrandId(String brandId);
}
