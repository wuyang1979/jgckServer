package com.ruoyi.rent.mapper;

import java.util.List;

import com.ruoyi.rent.domain.RentInfo;

/**
 * 租金管理Mapper接口
 *
 * @author gubt
 * @date 2023-01-17
 */
public interface RentInfoMapper {
    /**
     * 查询租金管理
     *
     * @param rentId 租金管理主键
     * @return 租金管理
     */
    public RentInfo selectRentInfoByRentId(String rentId);

    /**
     * 查询租金管理列表
     *
     * @param rentInfo 租金管理
     * @return 租金管理集合
     */
    public List<RentInfo> selectRentInfoList(RentInfo rentInfo);

    /**
     * 新增租金管理
     *
     * @param rentInfo 租金管理
     * @return 结果
     */
    public int insertRentInfo(RentInfo rentInfo);

    /**
     * 修改租金管理
     *
     * @param rentInfo 租金管理
     * @return 结果
     */
    public int updateRentInfo(RentInfo rentInfo);

    /**
     * 删除租金管理
     *
     * @param rentId 租金管理主键
     * @return 结果
     */
    public int deleteRentInfoByRentId(String rentId);

    /**
     * 批量删除租金管理
     *
     * @param rentIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRentInfoByRentIds(String[] rentIds);
}
