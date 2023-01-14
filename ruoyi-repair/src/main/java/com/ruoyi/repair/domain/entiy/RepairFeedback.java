package com.ruoyi.repair.domain.entiy;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 报修反馈基本信息对象 t_repair_feedback
 *
 * @author gubut
 * @date 2023-01-12
 */
public class RepairFeedback extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 报修id
     */
    private String repairId;

    /**
     * 报修人手机号
     */
    @Excel(name = "报修人手机号")
    @ApiModelProperty("报修人手机号")
    private String repairHandleId;

    /**
     * 报修状态
     */
    @Excel(name = "报修状态")
    @ApiModelProperty("报修状态")
    private Long repairStatus;

    /**
     * 报修类型
     */
    @Excel(name = "报修类型")
    @ApiModelProperty("报修类型")
    private Long repairType;

    /**
     * 处理人id
     */
    @Excel(name = "处理人id")
    @ApiModelProperty("处理人id")
    private String handleId;

    /**
     * 处理时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "处理时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("处理时间")
    private Date handlerTime;

    private String spaceId;

    private String roomId;

    public void setRepairId(String repairId) {
        this.repairId = repairId;
    }

    public String getRepairId() {
        return repairId;
    }

    public String getRepairHandleId() {
        return repairHandleId;
    }

    public void setRepairHandleId(String repairHandleId) {
        this.repairHandleId = repairHandleId;
    }

    public void setRepairStatus(Long repairStatus) {
        this.repairStatus = repairStatus;
    }

    public Long getRepairStatus() {
        return repairStatus;
    }

    public void setRepairType(Long repairType) {
        this.repairType = repairType;
    }

    public Long getRepairType() {
        return repairType;
    }

    public void setHandleId(String handleId) {
        this.handleId = handleId;
    }

    public String getHandleId() {
        return handleId;
    }

    public void setHandlerTime(Date handlerTime) {
        this.handlerTime = handlerTime;
    }

    public Date getHandlerTime() {
        return handlerTime;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("repairId", getRepairId())
                .append("repairHandleId", getRepairHandleId())
                .append("roomId", getRoomId())
                .append("repairStatus", getRepairStatus())
                .append("repairType", getRepairType())
                .append("remark", getRemark())
                .append("handleId", getHandleId())
                .append("handlerTime", getHandlerTime())
                .append("spaceId", getSpaceId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
