package com.ruoyi.repair.mapper;

import java.util.List;

import com.ruoyi.repair.domain.entiy.RepairFeedback;
import com.ruoyi.repair.domain.vo.RepairFeedbackVo;

/**
 * 报修反馈基本信息Mapper接口
 *
 * @author gubut
 * @date 2023-01-12
 */
public interface RepairFeedbackMapper {
    /**
     * 查询报修反馈基本信息
     *
     * @param repairId 报修反馈基本信息主键
     * @return 报修反馈基本信息
     */
    public RepairFeedback selectRepairFeedbackByRepairId(String repairId);

    /**
     * 查询报修反馈基本信息列表
     *
     * @param repairFeedbackVo 报修反馈基本信息
     * @return 报修反馈基本信息集合
     */
        public List<RepairFeedback> selectRepairFeedbackList(RepairFeedbackVo repairFeedbackVo);

    /**
     * 新增报修反馈基本信息
     *
     * @param repairFeedbackVo 报修反馈基本信息Vo
     * @return 结果
     */
    public int insertRepairFeedback(RepairFeedbackVo repairFeedbackVo);

    /**
     * 修改报修反馈基本信息
     *
     * @param repairFeedback 报修反馈基本信息
     * @return 结果
     */
    public int updateRepairFeedback(RepairFeedback repairFeedback);

    /**
     * 删除报修反馈基本信息
     *
     * @param repairId 报修反馈基本信息主键
     * @return 结果
     */
    public int deleteRepairFeedbackByRepairId(String repairId);

    /**
     * 批量删除报修反馈基本信息
     *
     * @param repairIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRepairFeedbackByRepairIds(String[] repairIds);
}
