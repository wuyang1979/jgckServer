package com.ruoyi.contract.service.impl;

import java.util.List;
import java.util.UUID;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.contract.domain.vo.ContractOtherVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.contract.mapper.ContractOtherMapper;
import com.ruoyi.contract.domain.entiy.ContractOther;
import com.ruoyi.contract.service.IContractOtherService;

/**
 * 其他合同Service业务层处理
 *
 * @author gubt
 * @date 2023-01-16
 */
@Service
public class ContractOtherServiceImpl implements IContractOtherService {
    @Autowired
    private ContractOtherMapper contractOtherMapper;

    /**
     * 查询其他合同
     *
     * @param contractId 其他合同主键
     * @return 其他合同
     */
    @Override
    public ContractOther selectContractOtherByContractId(String contractId) {
        return contractOtherMapper.selectContractOtherByContractId(contractId);
    }

    /**
     * 查询其他合同列表
     *
     * @param contractOtherVo 其他合同
     * @return 其他合同
     */
    @Override
    public List<ContractOther> selectContractOtherList(ContractOtherVo contractOtherVo) {
        return contractOtherMapper.selectContractOtherList(contractOtherVo);
    }

    /**
     * 新增其他合同
     *
     * @param contractOther 其他合同
     * @return 结果
     */
    @Override
    public int insertContractOther(ContractOther contractOther) {
        contractOther.setCreateTime(DateUtils.getNowDate());
        contractOther.setContractId(UUID.randomUUID().toString());
        return contractOtherMapper.insertContractOther(contractOther);
    }

    /**
     * 修改其他合同
     *
     * @param contractOther 其他合同
     * @return 结果
     */
    @Override
    public int updateContractOther(ContractOther contractOther) {
        contractOther.setUpdateTime(DateUtils.getNowDate());
        return contractOtherMapper.updateContractOther(contractOther);
    }

    /**
     * 批量删除其他合同
     *
     * @param contractIds 需要删除的其他合同主键
     * @return 结果
     */
    @Override
    public int deleteContractOtherByContractIds(String[] contractIds) {
        return contractOtherMapper.deleteContractOtherByContractIds(contractIds);
    }

    /**
     * 删除其他合同信息
     *
     * @param contractId 其他合同主键
     * @return 结果
     */
    @Override
    public int deleteContractOtherByContractId(String contractId) {
        return contractOtherMapper.deleteContractOtherByContractId(contractId);
    }
}
