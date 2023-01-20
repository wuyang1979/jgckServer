package com.ruoyi.common.core.service;

import com.alibaba.fastjson2.JSONObject;
import com.google.gson.JsonObject;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.dto.ContractExpireDto;
import com.ruoyi.common.core.domain.dto.CredentialExpireDto;
import com.ruoyi.common.core.domain.dto.RentStatisticsDTO;
import com.ruoyi.common.core.domain.vo.ContractExpireVo;

import java.util.List;
import java.util.jar.JarEntry;

/**
 * 首页Service接口
 *
 * @author gubut
 * @date 2022-12-28
 */
public interface IStatisticsService {

    public AjaxResult getSettleStatistics(String spaceId);

    public List<ContractExpireDto> getContractExpire(ContractExpireVo contractExpireVo);

    public List<CredentialExpireDto> getCredentialExpire(String spaceId);

    public RentStatisticsDTO getRentStatisticsByYear(String year);


}
