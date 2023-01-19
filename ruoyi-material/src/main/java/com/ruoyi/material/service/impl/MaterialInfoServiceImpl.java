package com.ruoyi.material.service.impl;

import java.util.List;
import java.util.UUID;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.material.mapper.MaterialInfoMapper;
import com.ruoyi.material.domain.MaterialInfo;
import com.ruoyi.material.service.IMaterialInfoService;

/**
 * 物料管理Service业务层处理
 *
 * @author gubt
 * @date 2023-01-19
 */
@Service
public class MaterialInfoServiceImpl implements IMaterialInfoService {
    @Autowired
    private MaterialInfoMapper materialInfoMapper;

    /**
     * 查询物料管理
     *
     * @param materialId 物料管理主键
     * @return 物料管理
     */
    @Override
    public MaterialInfo selectMaterialInfoByMaterialId(String materialId) {
        return materialInfoMapper.selectMaterialInfoByMaterialId(materialId);
    }

    /**
     * 查询物料管理列表
     *
     * @param materialInfo 物料管理
     * @return 物料管理
     */
    @Override
    public List<MaterialInfo> selectMaterialInfoList(MaterialInfo materialInfo) {
        return materialInfoMapper.selectMaterialInfoList(materialInfo);
    }

    /**
     * 新增物料管理
     *
     * @param materialInfo 物料管理
     * @return 结果
     */
    @Override
    public int insertMaterialInfo(MaterialInfo materialInfo) {
        materialInfo.setCreateTime(DateUtils.getNowDate());
        materialInfo.setMaterialId(UUID.randomUUID().toString());
        return materialInfoMapper.insertMaterialInfo(materialInfo);
    }

    /**
     * 修改物料管理
     *
     * @param materialInfo 物料管理
     * @return 结果
     */
    @Override
    public int updateMaterialInfo(MaterialInfo materialInfo) {
        materialInfo.setUpdateTime(DateUtils.getNowDate());
        return materialInfoMapper.updateMaterialInfo(materialInfo);
    }

    /**
     * 批量删除物料管理
     *
     * @param materialIds 需要删除的物料管理主键
     * @return 结果
     */
    @Override
    public int deleteMaterialInfoByMaterialIds(String[] materialIds) {
        return materialInfoMapper.deleteMaterialInfoByMaterialIds(materialIds);
    }

    /**
     * 删除物料管理信息
     *
     * @param materialId 物料管理主键
     * @return 结果
     */
    @Override
    public int deleteMaterialInfoByMaterialId(String materialId) {
        return materialInfoMapper.deleteMaterialInfoByMaterialId(materialId);
    }
}
