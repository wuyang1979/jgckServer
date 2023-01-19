package com.ruoyi.rent.service.impl;

import java.util.List;
import java.util.UUID;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.contract.domain.dto.ContractInfoPageDto;
import com.ruoyi.contract.domain.vo.ContractInfoPageVo;
import com.ruoyi.contract.service.IContractInfoService;
import com.ruoyi.rent.domain.dto.RentInfoPageDTO;
import com.ruoyi.rent.domain.vo.RentInfoInsertVo;
import com.ruoyi.rent.domain.vo.RentInfoPageVo;
import com.ruoyi.room.service.IRoomInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.rent.mapper.RentInfoMapper;
import com.ruoyi.rent.domain.entiy.RentInfo;
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

    @Autowired
    private IRoomInfoService roomInfoService;

    @Autowired
    private IContractInfoService contractInfoService;

    /**
     * 查询租金管理
     *
     * @param rentId 租金管理主键
     * @return 租金管理
     */
    @Override
    public RentInfoPageDTO selectRentInfoByRentId(String rentId) {
        return rentInfoMapper.selectRentInfoByRentId(rentId);
    }

    /**
     * 查询租金管理列表
     *
     * @param rentInfoPageVo 租金管理
     * @return 租金管理
     */
    @Override
    public List<RentInfoPageDTO> selectRentInfoList(RentInfoPageVo rentInfoPageVo) {
        return rentInfoMapper.selectRentInfoList(rentInfoPageVo);
    }

    /**
     * 新增租金管理
     *
     * @param rentInfoInsertVo 租金管理
     * @return 结果
     */
    @Override
    public AjaxResult insertRentInfo(RentInfoInsertVo rentInfoInsertVo) {
        rentInfoInsertVo.setCreateTime(DateUtils.getNowDate());
        rentInfoInsertVo.setRentId(UUID.randomUUID().toString());
        //todo 获取合同信息
        ContractInfoPageVo contractInfoPageVo = new ContractInfoPageVo();
        contractInfoPageVo.setSpaceId(rentInfoInsertVo.getSpaceId());
        contractInfoPageVo.setRoomId(rentInfoInsertVo.getRoomId());
        contractInfoPageVo.setContractStatus(0l);
        List<ContractInfoPageDto> list = contractInfoService.selectContractInfoList(contractInfoPageVo);
        if (list.size() == 0) {
            return AjaxResult.error("没有查询到相关合同");
        }
        //todo 遍历获取正常状态的合同
        for (ContractInfoPageDto contractInfo : list) {
            boolean b = DateUtils.belongCalendarBefore(contractInfo.getLeaseEndTime());
            if (b) {
                rentInfoInsertVo.setContractId(contractInfo.getContractId());
                break;
            }
        }
        if (!StringUtils.isEmpty(contractInfoPageVo.getContractId())) {
            return AjaxResult.error("没有查询到相关合同");
        }
        rentInfoMapper.insertRentInfo(rentInfoInsertVo);
        return AjaxResult.success(rentInfoInsertVo.getRentId());
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
