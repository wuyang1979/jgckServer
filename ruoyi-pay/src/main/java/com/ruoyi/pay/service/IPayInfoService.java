package com.ruoyi.pay.service;

import java.util.List;

import com.ruoyi.pay.domain.dto.PayInfoPageDTO;
import com.ruoyi.pay.domain.entiy.PayInfo;
import com.ruoyi.pay.domain.vo.PayInfoVo;

/**
 * 缴费基本信息Service接口
 *
 * @author gubt
 * @date 2023-01-14
 */
public interface IPayInfoService {
    /**
     * 查询缴费基本信息
     *
     * @param payId 缴费基本信息主键
     * @return 缴费基本信息
     */
    public PayInfo selectPayInfoByPayId(String payId);

    /**
     * 查询缴费基本信息列表
     *
     * @param payInfoVo 缴费基本信息
     * @return 缴费基本信息集合
     */
    public List<PayInfoPageDTO> selectPayInfoList(PayInfoVo payInfoVo);

    /**
     * 新增缴费基本信息
     *
     * @param payInfo 缴费基本信息
     * @return 结果
     */
    public int insertPayInfo(PayInfo payInfo);

    /**
     * 修改缴费基本信息
     *
     * @param payInfo 缴费基本信息
     * @return 结果
     */
    public int updatePayInfo(PayInfo payInfo);

    /**
     * 批量删除缴费基本信息
     *
     * @param payIds 需要删除的缴费基本信息主键集合
     * @return 结果
     */
    public int deletePayInfoByPayIds(String[] payIds);

    /**
     * 删除缴费基本信息信息
     *
     * @param payId 缴费基本信息主键
     * @return 结果
     */
    public int deletePayInfoByPayId(String payId);
}
