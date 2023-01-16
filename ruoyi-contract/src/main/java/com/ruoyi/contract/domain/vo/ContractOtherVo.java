package com.ruoyi.contract.domain.vo;

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
public class ContractOtherVo extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * 客户id（关联租客id）
     */
    @Excel(name = "客户名称")
    private String customerName;

    /**
     * 项目名称
     */
    @Excel(name = "项目名称")
    @ApiModelProperty("项目名称")
    private String projectName;


    /**
     * 空间id
     */
    private String spaceId;


}
