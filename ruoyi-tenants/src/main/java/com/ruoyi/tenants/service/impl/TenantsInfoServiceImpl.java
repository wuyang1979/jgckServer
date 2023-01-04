package com.ruoyi.tenants.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.tenants.domain.dto.TenantsInfoHistory;
import com.ruoyi.tenants.domain.vo.TenantsInfoHistoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.tenants.mapper.TenantsInfoMapper;
import com.ruoyi.tenants.domain.entiy.TenantsInfo;
import com.ruoyi.tenants.service.ITenantsInfoService;

/**
 * 租客基本信息Service业务层处理
 *
 * @author gubut
 * @date 2022-12-08
 */
@Service
public class TenantsInfoServiceImpl implements ITenantsInfoService {
    @Autowired
    private TenantsInfoMapper tenantsInfoMapper;

    /**
     * 查询租客基本信息
     *
     * @param tenantsId 租客基本信息主键
     * @return 租客基本信息
     */
    @Override
    public TenantsInfo selectTenantsInfoByTenantsId(String tenantsId) {
        return tenantsInfoMapper.selectTenantsInfoByTenantsId(tenantsId);
    }

    /**
     * 查询历史租客基本信息列表
     *
     * @param tenantsInfoHistory 租客基本信息
     * @return 租客基本信息
     */
    @Override
    public List<TenantsInfoHistory> selectTenantsInfoHistoryList(TenantsInfoHistoryVo tenantsInfoHistoryVo) {
        return tenantsInfoMapper.selectTenantsInfoHistoryList(tenantsInfoHistoryVo);
    }

    /**
     * 查询租客基本信息列表
     *
     * @param tenantsInfo 租客基本信息
     * @return 租客基本信息
     */
    @Override
    public List<TenantsInfo> selectTenantsInfoList(TenantsInfo tenantsInfo) {
        return tenantsInfoMapper.selectTenantsInfoList(tenantsInfo);
    }

    /**
     * 新增租客基本信息
     *
     * @param tenantsInfo 租客基本信息
     * @return 结果
     */
    @Override
    public int insertTenantsInfo(TenantsInfo tenantsInfo) {
        tenantsInfo.setCreateTime(DateUtils.getNowDate());
        return tenantsInfoMapper.insertTenantsInfo(tenantsInfo);
    }

    /**
     * 修改租客基本信息
     *
     * @param tenantsInfo 租客基本信息
     * @return 结果
     */
    @Override
    public int updateTenantsInfo(TenantsInfo tenantsInfo) {
        tenantsInfo.setUpdateTime(DateUtils.getNowDate());
        return tenantsInfoMapper.updateTenantsInfo(tenantsInfo);
    }

    /**
     * 批量删除租客基本信息
     *
     * @param tenantsIds 需要删除的租客基本信息主键
     * @return 结果
     */
    @Override
    public int deleteTenantsInfoByTenantsIds(String[] tenantsIds) {
        return tenantsInfoMapper.deleteTenantsInfoByTenantsIds(tenantsIds);
    }

    /**
     * 删除租客基本信息信息
     *
     * @param tenantsId 租客基本信息主键
     * @return 结果
     */
    @Override
    public int deleteTenantsInfoByTenantsId(String tenantsId) {
        return tenantsInfoMapper.deleteTenantsInfoByTenantsId(tenantsId);
    }
}
