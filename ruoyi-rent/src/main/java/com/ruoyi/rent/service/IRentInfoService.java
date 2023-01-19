package com.ruoyi.rent.service;

import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.rent.domain.dto.RentInfoPageDTO;
import com.ruoyi.rent.domain.entiy.RentInfo;
import com.ruoyi.rent.domain.vo.RentInfoInsertVo;
import com.ruoyi.rent.domain.vo.RentInfoPageVo;

/**
 * 租金管理Service接口
 *
 * @author gubt
 * @date 2023-01-17
 */
public interface IRentInfoService {
    /**
     * 查询租金管理
     *
     * @param rentId 租金管理主键
     * @return 租金管理
     */
    public RentInfoPageDTO selectRentInfoByRentId(String rentId);

    /**
     * 查询租金管理列表
     *
     * @param rentInfoPageVo 租金管理
     * @return 租金管理集合
     */
    public List<RentInfoPageDTO> selectRentInfoList(RentInfoPageVo rentInfoPageVo);

    /**
     * 新增租金管理
     *
     * @param rentInfoInsertVo 租金管理
     * @return 结果
     */
    public AjaxResult insertRentInfo(RentInfoInsertVo rentInfoInsertVo);

    /**
     * 修改租金管理
     *
     * @param rentInfo 租金管理
     * @return 结果
     */
    public int updateRentInfo(RentInfo rentInfo);

    /**
     * 批量删除租金管理
     *
     * @param rentIds 需要删除的租金管理主键集合
     * @return 结果
     */
    public int deleteRentInfoByRentIds(String[] rentIds);

    /**
     * 删除租金管理信息
     *
     * @param rentId 租金管理主键
     * @return 结果
     */
    public int deleteRentInfoByRentId(String rentId);
}
