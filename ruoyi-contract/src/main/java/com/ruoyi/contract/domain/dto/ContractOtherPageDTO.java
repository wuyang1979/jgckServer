package com.ruoyi.contract.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 其他合同对象 t_contract_other
 *
 * @author gubt
 * @date 2023-01-16
 */
@Data
public class ContractOtherPageDTO extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 合同主键id
     */
    private String contractId;

    /**
     * 合同编号
     */
    private String contractNumber;



    /**
     * 项目名称
     */
    @Excel(name = "项目名称")
    @ApiModelProperty("项目名称")
    private String projectName;

    /**
     * 项目名称
     */
    @Excel(name = "项目名称")
    @ApiModelProperty("项目名称")
    private String customerName;

    /**
     * 缴纳费用
     */
    @Excel(name = "缴纳费用")
    @ApiModelProperty("缴纳费用")
    private String payMoney;

    /**
     * 缴纳周期
     */
    @Excel(name = "缴纳周期")
    @ApiModelProperty("缴纳周期")
    private Long payPeriod;

    /**
     * 合同开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "合同开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("合同开始时间")
    private Date contractStartTime;

    /**
     * 合同截止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "合同截止时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("合同截止时间")
    private Date contractEndTime;

    /**
     * 签约日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "签约日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("签约日期")
    private Date signTime;



}
