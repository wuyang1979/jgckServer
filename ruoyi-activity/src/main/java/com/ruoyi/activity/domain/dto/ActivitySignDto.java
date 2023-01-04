package com.ruoyi.activity.domain.dto;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 活动是否报名对象 t_activity_info DTO
 *
 * @author gubut
 * @date 2022-12-22
 */
public class ActivitySignDto {

    /**
     * 活动信息（主键id）
     */
    private String activityId;

    private String isSign;

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityId() {
        return activityId;
    }

    public String getIsSign() {
        return isSign;
    }

    public void setIsSign(String isSign) {
        this.isSign = isSign;
    }
}
