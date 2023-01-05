package com.ruoyi.activity.service.impl;

import java.util.List;

import com.ruoyi.activity.domain.dto.ActivityInfoDto;
import com.ruoyi.activity.domain.dto.ActivitySignDto;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.uuid.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.activity.mapper.ActivityInfoMapper;
import com.ruoyi.activity.domain.ActivityInfo;
import com.ruoyi.activity.service.IActivityInfoService;

/**
 * 活动基本信息Service业务层处理
 *
 * @author gubut
 * @date 2022-12-22
 */
@Service
public class ActivityInfoServiceImpl implements IActivityInfoService {
    @Autowired
    private ActivityInfoMapper activityInfoMapper;

    /**
     * 查询活动基本信息
     *
     * @param activityId 活动基本信息主键
     * @return 活动基本信息
     */
    @Override
    public ActivityInfo selectActivityInfoByActivityId(String activityId) {
        return activityInfoMapper.selectActivityInfoByActivityId(activityId);
    }

    /**
     * 查询活动基本信息列表
     *
     * @param activityInfo 活动基本信息
     * @return 活动基本信息
     */
    @DataScope(deptAlias = "ai", userAlias = "ai")
    @Override
    public List<ActivityInfoDto> selectActivityInfoList(ActivityInfo activityInfo) {
        return activityInfoMapper.selectActivityInfoList(activityInfo);
    }

    /**
     * 新增活动基本信息
     *
     * @param activityInfo 活动基本信息
     * @return 结果
     */
    @Override
    public int insertActivityInfo(ActivityInfo activityInfo) {
        activityInfo.setActivityId(UUID.randomUUID().toString());
        activityInfo.setCreateTime(DateUtils.getNowDate());
        return activityInfoMapper.insertActivityInfo(activityInfo);
    }

    /**
     * 修改活动基本信息
     *
     * @param activityInfo 活动基本信息
     * @return 结果
     */
    @Override
    public int updateActivityInfo(ActivityInfo activityInfo) {
        activityInfo.setUpdateTime(DateUtils.getNowDate());
        return activityInfoMapper.updateActivityInfo(activityInfo);
    }

    /**
     * 批量删除活动基本信息
     *
     * @param activityIds 需要删除的活动基本信息主键
     * @return 结果
     */
    @Override
    public int deleteActivityInfoByActivityIds(String[] activityIds) {
        return activityInfoMapper.deleteActivityInfoByActivityIds(activityIds);
    }

    /**
     * 删除活动基本信息信息
     *
     * @param activityId 活动基本信息主键
     * @return 结果
     */
    @Override
    public int deleteActivityInfoByActivityId(String activityId) {
        return activityInfoMapper.deleteActivityInfoByActivityId(activityId);
    }

    /**
     * 根据登录用户获取是否参加活动list
     *
     * @return
     */
    @Override
    public List<ActivitySignDto> selectSignList(String userId) {
        return activityInfoMapper.selectSignList(userId);
    }
}
