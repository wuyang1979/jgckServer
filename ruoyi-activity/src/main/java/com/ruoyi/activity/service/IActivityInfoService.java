package com.ruoyi.activity.service;

import java.util.List;

import com.ruoyi.activity.domain.ActivityInfo;
import com.ruoyi.activity.domain.dto.ActivityInfoDto;
import com.ruoyi.activity.domain.dto.ActivitySignDto;

/**
 * 活动基本信息Service接口
 *
 * @author gubut
 * @date 2022-12-22
 */
public interface IActivityInfoService {
    /**
     * 查询活动基本信息
     *
     * @param activityId 活动基本信息主键
     * @return 活动基本信息
     */
    public ActivityInfo selectActivityInfoByActivityId(String activityId);

    /**
     * 查询活动基本信息列表
     *
     * @param activityInfo 活动基本信息
     * @return 活动基本信息集合
     */
    public List<ActivityInfoDto> selectActivityInfoList(ActivityInfo activityInfo);

    /**
     * 新增活动基本信息
     *
     * @param activityInfo 活动基本信息
     * @return 结果
     */
    public int insertActivityInfo(ActivityInfo activityInfo);

    /**
     * 修改活动基本信息
     *
     * @param activityInfo 活动基本信息
     * @return 结果
     */
    public int updateActivityInfo(ActivityInfo activityInfo);

    /**
     * 批量删除活动基本信息
     *
     * @param activityIds 需要删除的活动基本信息主键集合
     * @return 结果
     */
    public int deleteActivityInfoByActivityIds(String[] activityIds);

    /**
     * 删除活动基本信息信息
     *
     * @param activityId 活动基本信息主键
     * @return 结果
     */
    public int deleteActivityInfoByActivityId(String activityId);

    /**
     * 根据登录用户获取是否参加活动list
     *
     * @return
     */
    List<ActivitySignDto> selectSignList(String userId);
}
