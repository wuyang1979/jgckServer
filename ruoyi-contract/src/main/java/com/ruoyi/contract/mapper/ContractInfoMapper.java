package com.ruoyi.contract.mapper;

import java.util.List;

import com.ruoyi.contract.domain.dto.ContractInfoPageDto;
import com.ruoyi.contract.domain.entiy.ContractInfo;
import com.ruoyi.contract.domain.vo.ContractInfoPageVo;

/**
 * 合同信息Mapper接口
 *
 * @author gubut
 * @date 2022-12-09
 */
public interface ContractInfoMapper {
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
     * 删除合同信息
     *
     * @param contractId 合同信息主键
     * @return 结果
     */
    public int deleteContractInfoByContractId(String contractId);

    /**
     * 批量删除合同信息
     *
     * @param contractIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteContractInfoByContractIds(String[] contractIds);
}
