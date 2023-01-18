package com.ruoyi.asset.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 固定资产信息对象 t_fixed_asset
 *
 * @author ruoyi
 * @date 2023-01-18
 */
@Data
public class FixedAsset extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 固定资产id
     */
    private String assetId;

    /**
     * 资产编码
     */
    @Excel(name = "资产编码")
    @ApiModelProperty("资产编码")
    private String assetNo;

    /**
     * 空间业务id(空间基本信息表主键)
     */
    @Excel(name = "空间业务id(空间基本信息表主键)")
    @ApiModelProperty("空间业务id(空间基本信息表主键)")
    private String spaceId;

    /**
     * 资产名称
     */
    @Excel(name = "资产名称")
    @ApiModelProperty("资产名称")
    private String assetName;

    /**
     * 资产型号
     */
    @Excel(name = "资产型号")
    @ApiModelProperty("资产型号")
    private String assetModel;

    /**
     * 使用人id
     */
    @Excel(name = "使用人id")
    @ApiModelProperty("使用人id")
    private String userTenantsId;

    /**
     * 使用人姓名
     */
    @Excel(name = "使用人姓名")
    @ApiModelProperty("使用人姓名")
    private String userTenantsName;

    /**
     * 领用人id
     */
    @Excel(name = "领用人id")
    @ApiModelProperty("领用人id")
    private String receiverTenantsId;

    /**
     * 领用人姓名
     */
    @Excel(name = "领用人姓名")
    @ApiModelProperty("领用人姓名")
    private String receiverTenantsName;

    /**
     * 存放地点
     */
    @Excel(name = "存放地点")
    @ApiModelProperty("存放地点")
    private String storageLocation;

    /**
     * 保管部门
     */
    @Excel(name = "保管部门")
    @ApiModelProperty("保管部门")
    private String custodyDepartment;

    /**
     * 采购时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "采购时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("采购时间")
    private Date purchaseTime;

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
}
