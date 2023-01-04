package com.ruoyi.activity.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 活动与用户关系对象 t_activity_user_relation
 *
 * @author gubut
 * @date 2022-12-22
 */
public class ActivityUserRelation extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 活动主键id
     */
    @Excel(name = "活动主键id")
    private String activityId;

    /**
     * 用户表主键id
     */
    @Excel(name = "用户表主键id")
    private String userId;

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("activityId", getActivityId())
                .append("userId", getUserId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
