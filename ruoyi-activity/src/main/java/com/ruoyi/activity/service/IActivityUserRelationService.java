package com.ruoyi.activity.service;

import java.util.List;

import com.ruoyi.activity.domain.ActivityUserRelation;
import com.ruoyi.activity.domain.dto.ActivityPersonDto;
import com.ruoyi.common.core.domain.AjaxResult;

/**
 * 活动与用户关系Service接口
 *
 * @author gubut
 * @date 2022-12-22
 */
public interface IActivityUserRelationService {
    /**
     * 查询活动与用户关系
     *
     * @param activityId 活动与用户关系主键
     * @return 活动与用户关系
     */
    public ActivityUserRelation selectActivityUserRelationByActivityId(String activityId);

    /**
     * 查询活动与用户关系列表
     *
     * @param activityUserRelation 活动与用户关系
     * @return 活动与用户关系集合
     */
    public List<ActivityUserRelation> selectActivityUserRelationList(ActivityUserRelation activityUserRelation);

    /**
     * 新增活动与用户关系
     *
     * @param activityUserRelation 活动与用户关系
     * @return 结果
     */
    public AjaxResult insertActivityUserRelation(ActivityUserRelation activityUserRelation);

    /**
     * 修改活动与用户关系
     *
     * @param activityUserRelation 活动与用户关系
     * @return 结果
     */
    public int updateActivityUserRelation(ActivityUserRelation activityUserRelation);

    /**
     * 批量删除活动与用户关系
     *
     * @param activityIds 需要删除的活动与用户关系主键集合
     * @return 结果
     */
    public int deleteActivityUserRelationByActivityIds(String[] activityIds);

    /**
     * 删除活动与用户关系信息
     *
     * @param activityId 活动与用户关系主键
     * @return 结果
     */
    public int deleteActivityUserRelationByActivityId(String activityId);


    /**
     * 根据活动id和用户id查询关系
     *
     * @param activityId 活动id
     * @param userId     用户id
     * @return
     */
    public int getRelationByActivityIdAndUserId(String activityId, String userId);

    /**
     * 根据活动id查询已报名人数
     *
     * @param activityId 活动id
     * @return
     */
    public int getRelationByActivityId(String activityId);


    /**
     * 删除活动与用户关系信息
     *
     * @param activityId 活动与用户关系主键
     * @return 结果
     */
    public int deleteActivityUserRelationByActivityIdAndUserId(String activityId, String userId);

    /**
     * 根据活动id删除所有报名人员
     *
     * @param activityId 活动与用户关系主键
     * @return
     */
    public int deleteActivityUserRelationByActivity(String activityId);

    /**
     * 根据活动id获取报名人员列表
     *
     * @param activityId 活动与用户关系主键
     * @return
     */
    List<ActivityPersonDto> selectActivityPersonList(String activityId);
}
