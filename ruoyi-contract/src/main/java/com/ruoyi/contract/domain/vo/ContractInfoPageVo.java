package com.ruoyi.contract.domain.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 合同信息对象 t_contract_info
 *
 * @author gubut
 * @date 2022-12-09
 */
@Data
public class ContractInfoPageVo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 合同id
     */
    private String contractId;

    /**
     * 合同编号
     */
    @Excel(name = "合同编号")
    private String contractNumber;

    /**
     * 租赁开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "租赁开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date leaseStartTime;

    /**
     * 租赁结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "租赁结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date leaseEndTime;

    /**
     * 支付方式
     */
    @Excel(name = "支付方式")
    private Long paymentType;

    /**
     * 支付周期（单位月）
     */
    @Excel(name = "支付周期", readConverterExp = "单=位月")
    private String paymentPeriod;

    /**
     * 首次支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "首次支付时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date paymentFirstTime;

    /**
     * 首次支付金额（大写）
     */
    @Excel(name = "首次支付金额", readConverterExp = "大=写")
    private String paymentFirstMoney;

    /**
     * 支付期限
     */
    @Excel(name = "支付期限")
    private String paymentDeadline;

    /**
     * 房源id
     */
    @Excel(name = "房源id")
    private String roomId;

    /**
     * 年租金
     */
    @Excel(name = "年租金")
    private String yearMoney;

    /**
     * 保证金
     */
    @Excel(name = "保证金")
    private String securityDeposit;

    /**
     * 租客id
     */
    @Excel(name = "租客id")
    private String tenantsId;

    /**
     * 同住人id
     */
    @Excel(name = "同住人id")
    private String togetherPersonId;

    /**
     * 合同类型
     */
    @Excel(name = "合同类型")
    private Long contractType;

    /**
     * 合同状态
     */
    @Excel(name = "合同状态")
    private Long contractStatus;

    /**
     * 签约时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "签约时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date signTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date signTimeStart;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date signTimeEnd;

    private String userId;

    private String deptId;

    private String spaceId;
}
