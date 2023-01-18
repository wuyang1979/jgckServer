package com.ruoyi.rent.service.impl;

import java.util.List;
import java.util.UUID;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.rent.mapper.RentInfoMapper;
import com.ruoyi.rent.domain.RentInfo;
import com.ruoyi.rent.service.IRentInfoService;

/**
 * 租金管理Service业务层处理
 *
 * @author gubt
 * @date 2023-01-17
 */
@Service
public class RentInfoServiceImpl implements IRentInfoService {
    @Autowired
    private RentInfoMapper rentInfoMapper;

    /**
     * 查询租金管理
     *
     * @param rentId 租金管理主键
     * @return 租金管理
     */
    @Override
    public RentInfo selectRentInfoByRentId(String rentId) {
        return rentInfoMapper.selectRentInfoByRentId(rentId);
    }

    /**
     * 查询租金管理列表
     *
     * @param rentInfo 租金管理
     * @return 租金管理
     */
    @Override
    public List<RentInfo> selectRentInfoList(RentInfo rentInfo) {
        return rentInfoMapper.selectRentInfoList(rentInfo);
    }

    /**
     * 新增租金管理
     *
     * @param rentInfo 租金管理
     * @return 结果
     */
    @Override
    public int insertRentInfo(RentInfo rentInfo) {
        rentInfo.setCreateTime(DateUtils.getNowDate());
        rentInfo.setRentId(UUID.randomUUID().toString());
        return rentInfoMapper.insertRentInfo(rentInfo);
    }

    /**
     * 修改租金管理
     *
     * @param rentInfo 租金管理
     * @return 结果
     */
    @Override
    public int updateRentInfo(RentInfo rentInfo) {
        rentInfo.setUpdateTime(DateUtils.getNowDate());
        return rentInfoMapper.updateRentInfo(rentInfo);
    }

    /**
     * 批量删除租金管理
     *
     * @param rentIds 需要删除的租金管理主键
     * @return 结果
     */
    @Override
    public int deleteRentInfoByRentIds(String[] rentIds) {
        return rentInfoMapper.deleteRentInfoByRentIds(rentIds);
    }

    /**
     * 删除租金管理信息
     *
     * @param rentId 租金管理主键
     * @return 结果
     */
    @Override
    public int deleteRentInfoByRentId(String rentId) {
        return rentInfoMapper.deleteRentInfoByRentId(rentId);
    }
}
