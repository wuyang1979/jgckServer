package com.ruoyi.activity.mapper;

import java.util.List;

import com.ruoyi.activity.domain.ActivityUserRelation;
import com.ruoyi.activity.domain.dto.ActivityPersonDto;
import org.apache.ibatis.annotations.Param;

/**
 * 活动与用户关系Mapper接口
 *
 * @author gubut
 * @date 2022-12-22
 */
public interface ActivityUserRelationMapper {
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
    public int insertActivityUserRelation(ActivityUserRelation activityUserRelation);

    /**
     * 修改活动与用户关系
     *
     * @param activityUserRelation 活动与用户关系
     * @return 结果
     */
    public int updateActivityUserRelation(ActivityUserRelation activityUserRelation);

    /**
     * 删除活动与用户关系
     *
     * @param activityId 活动与用户关系主键
     * @return 结果
     */
    public int deleteActivityUserRelationByActivityId(String activityId);

    /**
     * 批量删除活动与用户关系
     *
     * @param activityIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteActivityUserRelationByActivityIds(String[] activityIds);

    /**
     * 根据活动id和用户id查询关系
     *
     * @param activityId 活动id
     * @param userId     用户id
     * @return
     */
    public int getRelationByActivityIdAndUserId(@Param("activityId") String activityId, @Param("userId") String userId);


    /**
     * 根据活动id查询已报名人数
     *
     * @param activityId 活动id
     * @return
     */
    public int getRelationByActivityId(@Param("activityId") String activityId);

    /**
     * 删除活动与用户关系
     *
     * @param activityId 活动与用户关系主键
     * @return 结果
     */
    public int deleteActivityUserRelationByActivityIdAndUserId(@Param("activityId") String activityId, @Param("userId") String userId);


    /**
     * 根据活动id获取报名列表
     *
     * @param activityId 活动与用户关系主键
     * @return
     */
    List<ActivityPersonDto> selectActivityPersonList(@Param("activityId") String activityId);
}
