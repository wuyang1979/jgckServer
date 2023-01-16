package com.ruoyi.pay.service.impl;

import java.util.List;
import java.util.UUID;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.pay.domain.dto.PayInfoPageDTO;
import com.ruoyi.pay.domain.vo.PayInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.pay.mapper.PayInfoMapper;
import com.ruoyi.pay.domain.entiy.PayInfo;
import com.ruoyi.pay.service.IPayInfoService;

/**
 * 缴费基本信息Service业务层处理
 *
 * @author gubt
 * @date 2023-01-14
 */
@Service
public class PayInfoServiceImpl implements IPayInfoService {
    @Autowired
    private PayInfoMapper payInfoMapper;

    /**
     * 查询缴费基本信息
     *
     * @param payId 缴费基本信息主键
     * @return 缴费基本信息
     */
    @Override
    public PayInfo selectPayInfoByPayId(String payId) {
        return payInfoMapper.selectPayInfoByPayId(payId);
    }

    /**
     * 查询缴费基本信息列表
     *
     * @param payInfoVo 缴费基本信息
     * @return 缴费基本信息
     */
    @Override
    public List<PayInfoPageDTO> selectPayInfoList(PayInfoVo payInfoVo) {
        return payInfoMapper.selectPayInfoList(payInfoVo);
    }

    /**
     * 新增缴费基本信息
     *
     * @param payInfo 缴费基本信息
     * @return 结果
     */
    @Override
    public int insertPayInfo(PayInfo payInfo) {
        payInfo.setCreateTime(DateUtils.getNowDate());
        payInfo.setPayId(UUID.randomUUID().toString());
        return payInfoMapper.insertPayInfo(payInfo);
    }

    /**
     * 修改缴费基本信息
     *
     * @param payInfo 缴费基本信息
     * @return 结果
     */
    @Override
    public int updatePayInfo(PayInfo payInfo) {
        payInfo.setUpdateTime(DateUtils.getNowDate());
        return payInfoMapper.updatePayInfo(payInfo);
    }

    /**
     * 批量删除缴费基本信息
     *
     * @param payIds 需要删除的缴费基本信息主键
     * @return 结果
     */
    @Override
    public int deletePayInfoByPayIds(String[] payIds) {
        return payInfoMapper.deletePayInfoByPayIds(payIds);
    }

    /**
     * 删除缴费基本信息信息
     *
     * @param payId 缴费基本信息主键
     * @return 结果
     */
    @Override
    public int deletePayInfoByPayId(String payId) {
        return payInfoMapper.deletePayInfoByPayId(payId);
    }
}
