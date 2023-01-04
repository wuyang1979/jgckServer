package com.ruoyi.activity.mapper;

import java.util.List;

import com.ruoyi.activity.domain.ActivityInfo;
import com.ruoyi.activity.domain.dto.ActivityInfoDto;
import com.ruoyi.activity.domain.dto.ActivitySignDto;
import org.apache.ibatis.annotations.Param;

/**
 * 活动基本信息Mapper接口
 *
 * @author gubut
 * @date 2022-12-22
 */
public interface ActivityInfoMapper {
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
     * 删除活动基本信息
     *
     * @param activityId 活动基本信息主键
     * @return 结果
     */
    public int deleteActivityInfoByActivityId(String activityId);

    /**
     * 批量删除活动基本信息
     *
     * @param activityIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteActivityInfoByActivityIds(String[] activityIds);


    public List<ActivitySignDto> selectSignList(@Param("userId") String userId);
}
