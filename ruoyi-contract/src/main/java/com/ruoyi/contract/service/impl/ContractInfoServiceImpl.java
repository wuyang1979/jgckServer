package com.ruoyi.contract.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.contract.domain.dto.ContractInfoPageDto;
import com.ruoyi.contract.domain.entiy.ContractInfo;
import com.ruoyi.contract.domain.vo.RentInfoAddVo;
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
    @Transactional
    public int insertContractInfo(ContractInfoPageVo contractInfoPageVo) {
        contractInfoPageVo.setContractId(UUID.randomUUID().toString());
        contractInfoPageVo.setCreateTime(DateUtils.getNowDate());
        contractInfoPageVo.setContractStatus(0l);
        int i = contractInfoMapper.insertContractInfo(contractInfoPageVo);
        //todo 合同创建完毕后按照合同时间新增租金信息
        insertRentByContractId(contractInfoPageVo.getContractId(), contractInfoPageVo.getSpaceId());
        return i;
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

    public void insertRentByContractId(String contractId, String spaceId) {

        //todo  通过合同id查询合同信息
        ContractInfo contractInfo = selectContractInfoByContractId(contractId);
        //todo  获取支付周期
        String paymentPeriod = contractInfo.getPaymentPeriod();
        //todo 获取支付周期间隔月份
        int period = Integer.parseInt(paymentPeriod);
        Integer periodValue = getPeriodValue(period);
        //todo 获取合同开始时间与结束时间
        Date leaseStartTime = contractInfo.getLeaseStartTime();
        Date leaseEndTime = contractInfo.getLeaseEndTime();
        //todo 获取合同年租金
        String yearMoney = contractInfo.getYearMoney();
        int month = DateUtils.getMonth(leaseStartTime, leaseEndTime);
        // todo 合同开始结束的月份小于支付周期 直接生产一条房租记录
        if (month <= periodValue) {
            RentInfoAddVo rentInfoOne = new RentInfoAddVo();
            rentInfoOne.setRentId(UUID.randomUUID().toString());
            rentInfoOne.setCreateBy(contractInfo.getCreateBy());
            rentInfoOne.setContractId(contractId);
            rentInfoOne.setCreateTime(new Date());
            rentInfoOne.setSpaceId(spaceId);
            rentInfoOne.setContractRent(contractInfo.getPaymentFirstMoney());
            rentInfoOne.setCollectionPeriod("第1期");
            rentInfoOne.setRentalPeriod(DateUtils.parseDateToStr("yyyy.MM.dd", leaseStartTime) + "-" + DateUtils.parseDateToStr("yyyy.MM.dd", leaseEndTime));
            rentInfoOne.setReceivableMoney(contractInfo.getPaymentFirstMoney());
            rentInfoOne.setReceivableDate(DateUtils.parseDateToStr("yyyy-MM-dd", contractInfo.getPaymentFirstTime()));
            contractInfoMapper.insertRentInfo(rentInfoOne);
        } else {
            //todo 合同开始结束的月份大于支付周期 生成多条房租记录
            int j = (int) Math.ceil(month / periodValue);
            Date startTime = contractInfo.getLeaseStartTime();
            Date paymentTime = contractInfo.getPaymentFirstTime();
            for (int z = 0; z < j; z++) {
                RentInfoAddVo rentInfoOne = new RentInfoAddVo();
                rentInfoOne.setRentId(UUID.randomUUID().toString());
                rentInfoOne.setCreateBy(contractInfo.getCreateBy());
                rentInfoOne.setContractId(contractId);
                rentInfoOne.setCreateTime(new Date());
                rentInfoOne.setSpaceId(spaceId);
                rentInfoOne.setContractRent(contractInfo.getPaymentFirstMoney());
                rentInfoOne.setCollectionPeriod("第" + (z + 1) + "期");
                rentInfoOne.setRentalPeriod(DateUtils.parseDateToStr("yyyy.MM.dd", startTime) + "-" + DateUtils.parseDateToStr("yyyy.MM.dd", DateUtils.getDay(DateUtils.getMon(startTime, periodValue), -1)));
                rentInfoOne.setReceivableMoney(contractInfo.getPaymentFirstMoney());
                rentInfoOne.setReceivableDate(DateUtils.parseDateToStr("yyyy-MM-dd", paymentTime));
                startTime = DateUtils.getMon(startTime, periodValue);
                paymentTime = DateUtils.getMon(paymentTime, periodValue);
                contractInfoMapper.insertRentInfo(rentInfoOne);
            }
        }
    }


    private int getPeriodValue(int period) {
        int periodValue = 0;
        switch (period) {
            case 0:
                periodValue = 1;
                break;
            case 1:
                periodValue = 3;
                break;
            case 2:
                periodValue = 6;
                break;
            case 3:
                periodValue = 12;
                break;
        }
        return periodValue;
    }

    /**
     * 新增租金管理
     *
     * @param rentInfoInsertVo 租金管理
     * @return 结果
     */
    @Override
    public int insertRentInfo(RentInfoAddVo rentInfoInsertVo) {
        rentInfoInsertVo.setCreateTime(DateUtils.getNowDate());
        rentInfoInsertVo.setRentId(UUID.randomUUID().toString());
        return contractInfoMapper.insertRentInfo(rentInfoInsertVo);
    }
}
