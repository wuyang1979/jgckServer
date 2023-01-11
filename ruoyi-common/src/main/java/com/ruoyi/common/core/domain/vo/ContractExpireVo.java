package com.ruoyi.common.core.domain.vo;

import lombok.Data;

@Data
public class ContractExpireVo {

    /**
     * 公寓到期天数
     */
    private String apartmentRemind;

    /**
     * 办公/商铺到期天数
     */
    private String officeShopRemind;

    /**
     * 所属空间
     */
    private String spaceId;

}
