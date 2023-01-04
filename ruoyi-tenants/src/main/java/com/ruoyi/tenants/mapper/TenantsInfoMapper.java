package com.ruoyi.tenants.mapper;

import java.util.List;

import com.ruoyi.tenants.domain.dto.TenantsInfoHistory;
import com.ruoyi.tenants.domain.entiy.TenantsInfo;
import com.ruoyi.tenants.domain.vo.TenantsInfoHistoryVo;

/**
 * 租客基本信息Mapper接口
 *
 * @author gubut
 * @date 2022-12-08
 */
public interface TenantsInfoMapper {
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
     * 删除租客基本信息
     *
     * @param tenantsId 租客基本信息主键
     * @return 结果
     */
    public int deleteTenantsInfoByTenantsId(String tenantsId);

    /**
     * 批量删除租客基本信息
     *
     * @param tenantsIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTenantsInfoByTenantsIds(String[] tenantsIds);
}
