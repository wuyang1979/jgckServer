package com.ruoyi.activity.domain;

import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 活动基本信息对象 t_activity_info
 *
 * @author gubut
 * @date 2022-12-22
 */
public class ActivityInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 活动信息（主键id）
     */
    private String activityId;

    /**
     * 活动标题
     */
    @Excel(name = "活动标题")
    private String activityTitle;

    /**
     * 活动内容
     */
    @Excel(name = "活动内容")
    private String activityContent;

    /**
     * 活动状态 0-暂存 1-发布
     */
    @Excel(name = "活动状态 0-暂存 1-发布")
    private String status;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "发布时间", width = 50, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime releaseTime;

    /**
     * 活动开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "活动开始时间", width = 50, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime activityStartTime;

    /**
     * 活动结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "活动结束时间", width = 50, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime activityEndTime;

    /**
     * 活动地址
     */
    @Excel(name = "活动地址")
    private String activityAddress;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "报名截止时间", width = 50, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registerDeadline;

    @Excel(name = "报名人数")
    private Long registerNumber;

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityContent(String activityContent) {
        this.activityContent = activityContent;
    }

    public String getActivityContent() {
        return activityContent;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setReleaseTime(LocalDateTime releaseTime) {
        this.releaseTime = releaseTime;
    }

    public LocalDateTime getReleaseTime() {
        return releaseTime;
    }

    public void setActivityStartTime(LocalDateTime activityStartTime) {
        this.activityStartTime = activityStartTime;
    }

    public LocalDateTime getActivityStartTime() {
        return activityStartTime;
    }

    public void setActivityEndTime(LocalDateTime activityEndTime) {
        this.activityEndTime = activityEndTime;
    }

    public LocalDateTime getActivityEndTime() {
        return activityEndTime;
    }

    public void setActivityAddress(String activityAddress) {
        this.activityAddress = activityAddress;
    }

    public String getActivityAddress() {
        return activityAddress;
    }

    public LocalDateTime getRegisterDeadline() {
        return registerDeadline;
    }

    public void setRegisterDeadline(LocalDateTime registerDeadline) {
        this.registerDeadline = registerDeadline;
    }

    public Long getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(Long registerNumber) {
        this.registerNumber = registerNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("activityId", getActivityId())
                .append("activityTitle", getActivityTitle())
                .append("activityContent", getActivityContent())
                .append("status", getStatus())
                .append("releaseTime", getReleaseTime())
                .append("activityStartTime", getActivityStartTime())
                .append("activityEndTime", getActivityEndTime())
                .append("activityAddress", getActivityAddress())
                .append("registerDeadline", getRegisterDeadline())
                .append("registerNumber", getRegisterNumber())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
