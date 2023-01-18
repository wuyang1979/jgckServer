package com.ruoyi.rent.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 租金管理对象 t_rent_info
 *
 * @author gubt
 * @date 2023-01-17
 */
public class RentInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 房租id
     */
    private String rentId;

    /**
     * 合同编号
     */
    @Excel(name = "合同编号")
    @ApiModelProperty("合同编号")
    private String contractId;

    /**
     * 空间id
     */
    @Excel(name = "空间id")
    @ApiModelProperty("空间id")
    private String spaceId;

    /**
     * 合同租金
     */
    @Excel(name = "合同租金")
    @ApiModelProperty("合同租金")
    private String contractRent;

    /**
     * 收款期数
     */
    @Excel(name = "收款期数")
    @ApiModelProperty("收款期数")
    private String collectionPeriod;

    /**
     * 租金所属期
     */
    @Excel(name = "租金所属期")
    @ApiModelProperty("租金所属期")
    private String rentalPeriod;

    /**
     * 应收金额
     */
    @Excel(name = "应收金额")
    @ApiModelProperty("应收金额")
    private String receivableMoney;

    /**
     * 应收日期
     */
    @Excel(name = "应收日期")
    @ApiModelProperty("应收日期")
    private String receivableDate;

    /**
     * 实收金额
     */
    @Excel(name = "实收金额")
    @ApiModelProperty("实收金额")
    private String practicalMoney;

    /**
     * 实收日期
     */
    @Excel(name = "实收日期")
    @ApiModelProperty("实收日期")
    private String practicalDate;

    public void setRentId(String rentId) {
        this.rentId = rentId;
    }

    public String getRentId() {
        return rentId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setContractRent(String contractRent) {
        this.contractRent = contractRent;
    }

    public String getContractRent() {
        return contractRent;
    }

    public void setCollectionPeriod(String collectionPeriod) {
        this.collectionPeriod = collectionPeriod;
    }

    public String getCollectionPeriod() {
        return collectionPeriod;
    }

    public void setRentalPeriod(String rentalPeriod) {
        this.rentalPeriod = rentalPeriod;
    }

    public String getRentalPeriod() {
        return rentalPeriod;
    }

    public void setReceivableMoney(String receivableMoney) {
        this.receivableMoney = receivableMoney;
    }

    public String getReceivableMoney() {
        return receivableMoney;
    }

    public void setReceivableDate(String receivableDate) {
        this.receivableDate = receivableDate;
    }

    public String getReceivableDate() {
        return receivableDate;
    }

    public void setPracticalMoney(String practicalMoney) {
        this.practicalMoney = practicalMoney;
    }

    public String getPracticalMoney() {
        return practicalMoney;
    }

    public void setPracticalDate(String practicalDate) {
        this.practicalDate = practicalDate;
    }

    public String getPracticalDate() {
        return practicalDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("rentId", getRentId())
                .append("contractId", getContractId())
                .append("spaceId", getSpaceId())
                .append("contractRent", getContractRent())
                .append("collectionPeriod", getCollectionPeriod())
                .append("rentalPeriod", getRentalPeriod())
                .append("receivableMoney", getReceivableMoney())
                .append("receivableDate", getReceivableDate())
                .append("practicalMoney", getPracticalMoney())
                .append("practicalDate", getPracticalDate())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
