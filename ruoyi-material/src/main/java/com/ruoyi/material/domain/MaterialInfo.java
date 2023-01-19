package com.ruoyi.material.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 物料管理对象 t_material_info
 *
 * @author gubt
 * @date 2023-01-19
 */
public class MaterialInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 物料id
     */
    private String materialId;

    /**
     * 物料名称
     */
    @Excel(name = "物料名称")
    @ApiModelProperty("物料名称")
    private String materialName;

    /**
     * 出入库类型
     */
    @Excel(name = "出入库类型")
    @ApiModelProperty("出入库类型")
    private Long materialStatus;

    /**
     * 物料类型
     */
    @Excel(name = "物料类型")
    @ApiModelProperty("物料类型")
    private Long materialType;

    /**
     * 物料型号
     */
    @Excel(name = "物料型号")
    @ApiModelProperty("物料型号")
    private String materialSize;

    /**
     * 入库数量
     */
    @Excel(name = "入库数量")
    @ApiModelProperty("入库数量")
    private String warehouseIn;

    /**
     * 出库数量
     */
    @Excel(name = "出库数量")
    @ApiModelProperty("出库数量")
    private String warehouseOut;

    /**
     * 结存数量
     */
    @Excel(name = "结存数量")
    @ApiModelProperty("结存数量")
    private String balance;

    /**
     * 入库经手人
     */
    @Excel(name = "入库经手人")
    @ApiModelProperty("入库经手人")
    private String warehouseInUser;

    /**
     * 出库领用人
     */
    @Excel(name = "出库领用人")
    @ApiModelProperty("出库领用人")
    private String warehouseOutUser;

    /**
     * 出库审核人
     */
    @Excel(name = "出库审核人")
    @ApiModelProperty("出库审核人")
    private String warehouseOutAudit;

    /**
     * 入库时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入库时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("入库时间")
    private Date warehouseInTime;

    /**
     * 出库时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出库时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("出库时间")
    private Date warehouseOutTime;

    /**
     * 空间id
     */
    private String spaceId;

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialStatus(Long materialStatus) {
        this.materialStatus = materialStatus;
    }

    public Long getMaterialStatus() {
        return materialStatus;
    }

    public void setMaterialType(Long materialType) {
        this.materialType = materialType;
    }

    public Long getMaterialType() {
        return materialType;
    }

    public void setMaterialSize(String materialSize) {
        this.materialSize = materialSize;
    }

    public String getMaterialSize() {
        return materialSize;
    }

    public void setWarehouseIn(String warehouseIn) {
        this.warehouseIn = warehouseIn;
    }

    public String getWarehouseIn() {
        return warehouseIn;
    }

    public void setWarehouseOut(String warehouseOut) {
        this.warehouseOut = warehouseOut;
    }

    public String getWarehouseOut() {
        return warehouseOut;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBalance() {
        return balance;
    }

    public void setWarehouseInUser(String warehouseInUser) {
        this.warehouseInUser = warehouseInUser;
    }

    public String getWarehouseInUser() {
        return warehouseInUser;
    }

    public void setWarehouseOutUser(String warehouseOutUser) {
        this.warehouseOutUser = warehouseOutUser;
    }

    public String getWarehouseOutUser() {
        return warehouseOutUser;
    }

    public void setWarehouseOutAudit(String warehouseOutAudit) {
        this.warehouseOutAudit = warehouseOutAudit;
    }

    public String getWarehouseOutAudit() {
        return warehouseOutAudit;
    }

    public void setWarehouseInTime(Date warehouseInTime) {
        this.warehouseInTime = warehouseInTime;
    }

    public Date getWarehouseInTime() {
        return warehouseInTime;
    }

    public void setWarehouseOutTime(Date warehouseOutTime) {
        this.warehouseOutTime = warehouseOutTime;
    }

    public Date getWarehouseOutTime() {
        return warehouseOutTime;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public String getSpaceId() {
        return spaceId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("materialId", getMaterialId())
                .append("materialName", getMaterialName())
                .append("materialStatus", getMaterialStatus())
                .append("materialType", getMaterialType())
                .append("materialSize", getMaterialSize())
                .append("warehouseIn", getWarehouseIn())
                .append("warehouseOut", getWarehouseOut())
                .append("balance", getBalance())
                .append("remark", getRemark())
                .append("warehouseInUser", getWarehouseInUser())
                .append("warehouseOutUser", getWarehouseOutUser())
                .append("warehouseOutAudit", getWarehouseOutAudit())
                .append("warehouseInTime", getWarehouseInTime())
                .append("warehouseOutTime", getWarehouseOutTime())
                .append("spaceId", getSpaceId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
