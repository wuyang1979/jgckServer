package com.ruoyi.tenants.service;

import java.util.List;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.tenants.domain.dto.TenantsInfoHistory;
import com.ruoyi.tenants.domain.entiy.TenantsInfo;
import com.ruoyi.tenants.domain.vo.TenantsInfoHistoryVo;

/**
 * 租客基本信息Service接口
 *
 * @author gubut
 * @date 2022-12-08
 */
public interface ITenantsInfoService {
    /**
     * 查询租客基本信息
     *
     * @param tenantsId 租客基本信息主键
     * @return 租客基本信息
     */
    public TenantsInfo selectTenantsInfoByTenantsId(String tenantsId);

    /**
     * 查询历史租客基本信息列表
     *
     * @param tenantsInfoHistory 租客基本信息
     * @return 租客基本信息集合
     */
    public List<TenantsInfoHistory> selectTenantsInfoHistoryList(TenantsInfoHistoryVo tenantsInfoHistoryVo);

    /**
     * 查询租客基本信息列表
     *
     * @param tenantsInfo 租客基本信息
     * @return 租客基本信息集合
     */
    public List<TenantsInfo> selectTenantsInfoList(TenantsInfo tenantsInfo);

    /**
     * 查询租客基本信息列表(无数据过滤)
     *
     * @param tenantsInfo 租客基本信息
     * @return 租客基本信息集合
     */
    public List<TenantsInfo> listNoScope(TenantsInfo tenantsInfo);

    /**
     * 新增租客基本信息
     *
     * @param tenantsInfo 租客基本信息
     * @return 结果
     */
    public int insertTenantsInfo(TenantsInfo tenantsInfo);

    /**
     * 修改租客基本信息
     *
     * @param tenantsInfo 租客基本信息
     * @return 结果
     */
    public int updateTenantsInfo(TenantsInfo tenantsInfo);

    /**
     * 批量删除租客基本信息
     *
     * @param tenantsIds 需要删除的租客基本信息主键集合
     * @return 结果
     */
    public int deleteTenantsInfoByTenantsIds(String[] tenantsIds);

    /**
     * 删除租客基本信息信息
     *
     * @param tenantsId 租客基本信息主键
     * @return 结果
     */
    public int deleteTenantsInfoByTenantsId(String tenantsId);
}
