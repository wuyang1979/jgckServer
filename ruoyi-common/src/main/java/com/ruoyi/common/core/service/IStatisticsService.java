package com.ruoyi.common.core.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.dto.ContractExpireDto;
import com.ruoyi.common.core.domain.dto.CredentialExpireDto;

import java.util.List;

/**
 * 首页Service接口
 *
 * @author gubut
 * @date 2022-12-28
 */
public interface IStatisticsService {

    public AjaxResult getSettleStatistics();

    public List<ContractExpireDto> getContractExpire();

    public List<CredentialExpireDto> getCredentialExpire();


}
