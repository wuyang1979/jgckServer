package com.ruoyi.material.service;

import java.util.List;

import com.ruoyi.material.domain.MaterialInfo;

/**
 * 物料管理Service接口
 *
 * @author gubt
 * @date 2023-01-19
 */
public interface IMaterialInfoService {
    /**
     * 查询物料管理
     *
     * @param materialId 物料管理主键
     * @return 物料管理
     */
    public MaterialInfo selectMaterialInfoByMaterialId(String materialId);

    /**
     * 查询物料管理列表
     *
     * @param materialInfo 物料管理
     * @return 物料管理集合
     */
    public List<MaterialInfo> selectMaterialInfoList(MaterialInfo materialInfo);

    /**
     * 新增物料管理
     *
     * @param materialInfo 物料管理
     * @return 结果
     */
    public int insertMaterialInfo(MaterialInfo materialInfo);

    /**
     * 修改物料管理
     *
     * @param materialInfo 物料管理
     * @return 结果
     */
    public int updateMaterialInfo(MaterialInfo materialInfo);

    /**
     * 批量删除物料管理
     *
     * @param materialIds 需要删除的物料管理主键集合
     * @return 结果
     */
    public int deleteMaterialInfoByMaterialIds(String[] materialIds);

    /**
     * 删除物料管理信息
     *
     * @param materialId 物料管理主键
     * @return 结果
     */
    public int deleteMaterialInfoByMaterialId(String materialId);
}
