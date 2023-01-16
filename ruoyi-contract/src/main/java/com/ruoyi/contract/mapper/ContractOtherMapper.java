package com.ruoyi.contract.mapper;

import java.util.List;

import com.ruoyi.contract.domain.entiy.ContractOther;
import com.ruoyi.contract.domain.vo.ContractOtherVo;

/**
 * 其他合同Mapper接口
 *
 * @author gubt
 * @date 2023-01-16
 */
public interface ContractOtherMapper {
    /**
     * 查询其他合同
     *
     * @param contractId 其他合同主键
     * @return 其他合同
     */
    public ContractOther selectContractOtherByContractId(String contractId);

    /**
     * 查询其他合同列表
     *
     * @param contractOtherVo 其他合同
     * @return 其他合同集合
     */
    public List<ContractOther> selectContractOtherList(ContractOtherVo contractOtherVo);

    /**
     * 新增其他合同
     *
     * @param contractOther 其他合同
     * @return 结果
     */
    public int insertContractOther(ContractOther contractOther);

    /**
     * 修改其他合同
     *
     * @param contractOther 其他合同
     * @return 结果
     */
    public int updateContractOther(ContractOther contractOther);

    /**
     * 删除其他合同
     *
     * @param contractId 其他合同主键
     * @return 结果
     */
    public int deleteContractOtherByContractId(String contractId);

    /**
     * 批量删除其他合同
     *
     * @param contractIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteContractOtherByContractIds(String[] contractIds);
}
