package com.ruoyi.fire.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 消防安全对象 t_fire_safety
 *
 * @author gubt
 * @date 2023-01-17
 */
public class FireSafety extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 消防主键
     */
    private String fireId;

    /**
     * 楼层
     */
    @Excel(name = "楼层")
    @ApiModelProperty("楼层")
    private Long floor;

    /**
     * 具体位置
     */
    @Excel(name = "具体位置")
    @ApiModelProperty("具体位置")
    private String address;

    /**
     * 数量
     */
    @Excel(name = "数量")
    @ApiModelProperty("数量")
    private Long quantity;

    /**
     * 生产日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生产日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("生产日期")
    private Date productionTime;

    /**
     * 有效期
     */
    @Excel(name = "有效期")
    @ApiModelProperty("有效期")
    private String validityPeriod;

    /**
     * 采购人
     */
    @Excel(name = "采购人")
    @ApiModelProperty("采购人")
    private String purchaser;

    /**
     * 型号
     */
    @Excel(name = "型号")
    @ApiModelProperty("型号")
    private String size;

    /**
     * 编号
     */
    @Excel(name = "编号")
    @ApiModelProperty("编号")
    private Long number;

    /**
     * 空间id
     */
    @Excel(name = "空间id")
    @ApiModelProperty("空间id")
    private String spaceId;

    /**
     * 用户id
     */
    @Excel(name = "用户id")
    @ApiModelProperty("用户id")
    private String userId;

    /**
     * 部门id
     */
    @Excel(name = "部门id")
    @ApiModelProperty("部门id")
    private String deptId;

    public void setFireId(String fireId) {
        this.fireId = fireId;
    }

    public String getFireId() {
        return fireId;
    }

    public void setFloor(Long floor) {
        this.floor = floor;
    }

    public Long getFloor() {
        return floor;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setProductionTime(Date productionTime) {
        this.productionTime = productionTime;
    }

    public Date getProductionTime() {
        return productionTime;
    }

    public void setValidityPeriod(String validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public String getValidityPeriod() {
        return validityPeriod;
    }

    public void setPurchaser(String purchaser) {
        this.purchaser = purchaser;
    }

    public String getPurchaser() {
        return purchaser;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Long getNumber() {
        return number;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("fireId", getFireId())
                .append("floor", getFloor())
                .append("address", getAddress())
                .append("quantity", getQuantity())
                .append("productionTime", getProductionTime())
                .append("validityPeriod", getValidityPeriod())
                .append("purchaser", getPurchaser())
                .append("size", getSize())
                .append("number", getNumber())
                .append("spaceId", getSpaceId())
                .append("userId", getUserId())
                .append("deptId", getDeptId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
