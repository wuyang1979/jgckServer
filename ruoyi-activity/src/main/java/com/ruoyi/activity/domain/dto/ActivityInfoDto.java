package com.ruoyi.activity.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.FieldAlias;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

/**
 * 活动基本信息对象 t_activity_info DTO
 *
 * @author gubut
 * @date 2022-12-22
 */
@Data
public class ActivityInfoDto extends BaseEntity {
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

    @Excel(name = "已报名人数")
    private Long signNumber;

    private String userId;

    private String deptId;
}
