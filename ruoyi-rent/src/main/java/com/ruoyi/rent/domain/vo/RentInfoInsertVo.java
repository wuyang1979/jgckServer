package com.ruoyi.rent.domain.vo;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 租金管理对象 t_rent_info
 *
 * @author gubt
 * @date 2023-01-17
 */
@Data
public class RentInfoInsertVo extends BaseEntity {
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

    /**
     * 房源id
     */
    @Excel(name = "房源id")
    @ApiModelProperty("房源id")
    private String roomId;

    /**
     * 房源状态
     */
    @Excel(name = "房源状态")
    @ApiModelProperty("房源状态")
    private Long rentStatus;
}
