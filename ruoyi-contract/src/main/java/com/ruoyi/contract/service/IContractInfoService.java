package com.ruoyi.contract.service;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.contract.domain.dto.ContractInfoPageDto;
import com.ruoyi.contract.domain.entiy.ContractInfo;
import com.ruoyi.contract.domain.vo.ContractInfoPageVo;
import org.apache.commons.math3.analysis.function.Abs;

/**
 * 合同信息Service接口
 *
 * @author gubut
 * @date 2022-12-09
 */
public interface IContractInfoService {
    /**
     * 查询合同信息
     *
     * @param contractId 合同信息主键
     * @return 合同信息
     */
    public ContractInfo selectContractInfoByContractId(String contractId);

    /**
     * 查询合同信息列表
     *
     * @param contractInfoPageVo 合同信息
     * @return 合同信息集合
     */
    public List<ContractInfoPageDto> selectContractInfoList(ContractInfoPageVo contractInfoPageVo);

    /**
     * 新增合同信息
     *
     * @param contractInfoPageVo 合同信息
     * @return 结果
     */
    public int insertContractInfo(ContractInfoPageVo contractInfoPageVo);

    /**
     * 修改合同信息
     *
     * @param contractInfoPageVo 合同信息
     * @return 结果
     */
    public int updateContractInfo(ContractInfoPageVo contractInfoPageVo);

    /**
     * 批量删除合同信息
     *
     * @param contractIds 需要删除的合同信息主键集合
     * @return 结果
     */
    public int deleteContractInfoByContractIds(String[] contractIds);

    /**
     * 删除合同信息信息
     *
     * @param contractId 合同信息主键
     * @return 结果
     */
    public int deleteContractInfoByContractId(String contractId);


    public Boolean DateExceed(String roomId,String spaceId,Date leaseEndTime);


    public Date getLeaseEndTimeByRoomId(String roomId,String spaceId);


}
