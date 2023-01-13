package com.ruoyi.repair.service.impl;

import java.util.List;
import java.util.UUID;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.repair.domain.vo.RepairFeedbackVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.repair.mapper.RepairFeedbackMapper;
import com.ruoyi.repair.domain.entiy.RepairFeedback;
import com.ruoyi.repair.service.IRepairFeedbackService;

/**
 * 报修反馈基本信息Service业务层处理
 *
 * @author gubut
 * @date 2023-01-12
 */
@Service
public class RepairFeedbackServiceImpl implements IRepairFeedbackService {
    @Autowired
    private RepairFeedbackMapper repairFeedbackMapper;

    /**
     * 查询报修反馈基本信息
     *
     * @param repairId 报修反馈基本信息主键
     * @return 报修反馈基本信息
     */
    @Override
    public RepairFeedback selectRepairFeedbackByRepairId(String repairId) {
        return repairFeedbackMapper.selectRepairFeedbackByRepairId(repairId);
    }

    /**
     * 查询报修反馈基本信息列表
     *
     * @param repairFeedbackVo 报修反馈基本信息
     * @return 报修反馈基本信息
     */
    @Override
    public List<RepairFeedback> selectRepairFeedbackList(RepairFeedbackVo repairFeedbackVo) {
        return repairFeedbackMapper.selectRepairFeedbackList(repairFeedbackVo);
    }

    /**
     * 新增报修反馈基本信息
     *
     * @param repairFeedbackVo 报修反馈基本信息Vo
     * @return 结果
     */
    @Override
    public int insertRepairFeedback(RepairFeedbackVo repairFeedbackVo) {
        repairFeedbackVo.setCreateTime(DateUtils.getNowDate());
        repairFeedbackVo.setRepairId(UUID.randomUUID().toString());
        return repairFeedbackMapper.insertRepairFeedback(repairFeedbackVo);
    }

    /**
     * 修改报修反馈基本信息
     *
     * @param repairFeedback 报修反馈基本信息
     * @return 结果
     */
    @Override
    public int updateRepairFeedback(RepairFeedback repairFeedback) {
        repairFeedback.setUpdateTime(DateUtils.getNowDate());
        return repairFeedbackMapper.updateRepairFeedback(repairFeedback);
    }

    /**
     * 批量删除报修反馈基本信息
     *
     * @param repairIds 需要删除的报修反馈基本信息主键
     * @return 结果
     */
    @Override
    public int deleteRepairFeedbackByRepairIds(String[] repairIds) {
        return repairFeedbackMapper.deleteRepairFeedbackByRepairIds(repairIds);
    }

    /**
     * 删除报修反馈基本信息信息
     *
     * @param repairId 报修反馈基本信息主键
     * @return 结果
     */
    @Override
    public int deleteRepairFeedbackByRepairId(String repairId) {
        return repairFeedbackMapper.deleteRepairFeedbackByRepairId(repairId);
    }
}
