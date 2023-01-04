package com.ruoyi.activity.service.impl;

import java.util.List;

import com.ruoyi.activity.domain.ActivityInfo;
import com.ruoyi.activity.domain.dto.ActivityPersonDto;
import com.ruoyi.activity.mapper.ActivityInfoMapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.activity.mapper.ActivityUserRelationMapper;
import com.ruoyi.activity.domain.ActivityUserRelation;
import com.ruoyi.activity.service.IActivityUserRelationService;

/**
 * 活动与用户关系Service业务层处理
 *
 * @author gubut
 * @date 2022-12-22
 */
@Service
public class ActivityUserRelationServiceImpl implements IActivityUserRelationService {
    @Autowired
    private ActivityUserRelationMapper activityUserRelationMapper;

    @Autowired
    private ActivityInfoMapper activityInfoMapper;

    /**
     * 查询活动与用户关系
     *
     * @param activityId 活动与用户关系主键
     * @return 活动与用户关系
     */
    @Override
    public ActivityUserRelation selectActivityUserRelationByActivityId(String activityId) {
        return activityUserRelationMapper.selectActivityUserRelationByActivityId(activityId);
    }

    /**
     * 查询活动与用户关系列表
     *
     * @param activityUserRelation 活动与用户关系
     * @return 活动与用户关系
     */
    @Override
    public List<ActivityUserRelation> selectActivityUserRelationList(ActivityUserRelation activityUserRelation) {
        return activityUserRelationMapper.selectActivityUserRelationList(activityUserRelation);
    }

    /**
     * 新增活动与用户关系
     *
     * @param activityUserRelation 活动与用户关系
     * @return 结果
     */
    @Override
    public AjaxResult insertActivityUserRelation(ActivityUserRelation activityUserRelation) {
        boolean exceed = isExceed(activityUserRelation.getActivityId());
        if (exceed) {
            return AjaxResult.error("报名人数已满，请选择其他活动进行报名！");
        }
        boolean sign = isSign(activityUserRelation.getActivityId(), activityUserRelation.getUserId());
        if (sign) {
            return AjaxResult.error("已报名，无需重复报名！");
        }
        activityUserRelation.setCreateTime(DateUtils.getNowDate());
        activityUserRelationMapper.insertActivityUserRelation(activityUserRelation);
        return AjaxResult.success();
    }

    /**
     * 修改活动与用户关系
     *
     * @param activityUserRelation 活动与用户关系
     * @return 结果
     */
    @Override
    public int updateActivityUserRelation(ActivityUserRelation activityUserRelation) {
        activityUserRelation.setUpdateTime(DateUtils.getNowDate());
        return activityUserRelationMapper.updateActivityUserRelation(activityUserRelation);
    }

    /**
     * 批量删除活动与用户关系
     *
     * @param activityIds 需要删除的活动与用户关系主键
     * @return 结果
     */
    @Override
    public int deleteActivityUserRelationByActivityIds(String[] activityIds) {
        return activityUserRelationMapper.deleteActivityUserRelationByActivityIds(activityIds);
    }

    /**
     * 删除活动与用户关系信息
     *
     * @param activityId 活动与用户关系主键
     * @return 结果
     */
    @Override
    public int deleteActivityUserRelationByActivityId(String activityId) {
        return activityUserRelationMapper.deleteActivityUserRelationByActivityId(activityId);
    }

    /**
     * 根据活动id和用户id查询关系
     *
     * @param activityId 活动id
     * @param userId     用户id
     * @return
     */
    @Override
    public int getRelationByActivityIdAndUserId(String activityId, String userId) {
        return activityUserRelationMapper.getRelationByActivityIdAndUserId(activityId, userId);
    }


    @Override
    public int getRelationByActivityId(String activityId) {
        return activityUserRelationMapper.getRelationByActivityId(activityId);
    }


    // 判断用户是否已经报名参加活动
    private boolean isSign(String activityId, String userId) {
        boolean isSign = false;
        int relationNum = activityUserRelationMapper.getRelationByActivityIdAndUserId(activityId, userId);
        if (relationNum > 0) {
            isSign = true;
        }
        return isSign;
    }

    //判断是否超过报名人数限制
    private boolean isExceed(String activityId) {
        boolean isExceed = false;
        ActivityInfo activityInfo = activityInfoMapper.selectActivityInfoByActivityId(activityId);
        int relationNum = activityUserRelationMapper.getRelationByActivityId(activityId);
        if (relationNum >= activityInfo.getRegisterNumber()) {
            isExceed = true;
        }
        return isExceed;
    }


    @Override
    public int deleteActivityUserRelationByActivityIdAndUserId(String activityId, String userId) {
        return activityUserRelationMapper.deleteActivityUserRelationByActivityIdAndUserId(activityId, userId);
    }

    @Override
    public int deleteActivityUserRelationByActivity(String activityId) {
        return activityUserRelationMapper.deleteActivityUserRelationByActivityId(activityId);
    }

    @Override
    public List<ActivityPersonDto> selectActivityPersonList(String activityId) {
        return activityUserRelationMapper.selectActivityPersonList(activityId);
    }
}
