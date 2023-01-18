package com.ruoyi.contract.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.contract.domain.dto.ContractInfoPageDto;
import com.ruoyi.contract.domain.entiy.ContractInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.contract.mapper.ContractInfoMapper;
import com.ruoyi.contract.domain.vo.ContractInfoPageVo;
import com.ruoyi.contract.service.IContractInfoService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 合同信息Service业务层处理
 *
 * @author gubut
 * @date 2022-12-09
 */
@Service
public class ContractInfoServiceImpl implements IContractInfoService {
    @Autowired
    private ContractInfoMapper contractInfoMapper;

    /**
     * 查询合同信息
     *
     * @param contractId 合同信息主键
     * @return 合同信息
     */
    @Override
    public ContractInfo selectContractInfoByContractId(String contractId) {
        return contractInfoMapper.selectContractInfoByContractId(contractId);
    }

    /**
     * 查询合同信息列表
     *
     * @param contractInfoPageVo 合同信息
     * @return 合同信息
     */
    @DataScope(userAlias = "c", deptAlias = "c")
    @Override
    public List<ContractInfoPageDto> selectContractInfoList(ContractInfoPageVo contractInfoPageVo) {
        return contractInfoMapper.selectContractInfoList(contractInfoPageVo);
    }

    /**
     * 新增合同信息
     *
     * @param contractInfoPageVo 合同信息
     * @return 结果
     */
    @Override
    public int insertContractInfo(ContractInfoPageVo contractInfoPageVo) {
        contractInfoPageVo.setContractId(UUID.randomUUID().toString());
        contractInfoPageVo.setCreateTime(DateUtils.getNowDate());
        contractInfoPageVo.setContractStatus(0l);
        return contractInfoMapper.insertContractInfo(contractInfoPageVo);
    }

    /**
     * 修改合同信息
     *
     * @param contractInfoPageVo 合同信息
     * @return 结果
     */
    @Override
    public int updateContractInfo(ContractInfoPageVo contractInfoPageVo) {
        contractInfoPageVo.setUpdateTime(DateUtils.getNowDate());
        return contractInfoMapper.updateContractInfo(contractInfoPageVo);
    }

    /**
     * 批量删除合同信息
     *
     * @param contractIds 需要删除的合同信息主键
     * @return 结果
     */
    @Override
    public int deleteContractInfoByContractIds(String[] contractIds) {
        return contractInfoMapper.deleteContractInfoByContractIds(contractIds);
    }

    /**
     * 删除合同信息信息
     *
     * @param contractId 合同信息主键
     * @return 结果
     */
    @Override
    public int deleteContractInfoByContractId(String contractId) {
        return contractInfoMapper.deleteContractInfoByContractId(contractId);
    }

    /**
     * 合同开始时间是否冲突
     *
     * @param roomId  房源id
     * @param spaceId 空间id
     * @return
     */
    @Override
    public Boolean DateExceed(String roomId, String spaceId, Date leaseStartTime) {
        Boolean isExceed = true;
        Date leaseTime = getLeaseEndTimeByRoomId(roomId, spaceId);
        if (leaseTime != null) {
            int days = DateUtils.differentDaysByMillisecond(leaseTime, leaseStartTime);
            if (days > 0) {
                isExceed = false;
                return isExceed;
            }
        }
        return isExceed;
    }

    /**
     * 根据房源id获取结束时间
     *
     * @param roomId  房源id
     * @param spaceId 空间id
     * @return
     */
    @Override
    public Date getLeaseEndTimeByRoomId(String roomId, String spaceId) {
        return contractInfoMapper.getLeaseEndTimeByRoomId(roomId, spaceId);
    }
}
