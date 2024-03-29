package com.ruoyi.common.core.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.dto.ContractExpireDto;
import com.ruoyi.common.core.domain.dto.CredentialExpireDto;
import com.ruoyi.common.core.domain.dto.RentStatisticsDTO;
import com.ruoyi.common.core.domain.vo.ContractExpireVo;
import com.ruoyi.common.core.mapper.StatisticsMapper;
import com.ruoyi.common.core.service.IStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文件信息Service业务层处理
 *
 * @author gubut
 * @date 2022-12-26
 */
@Service
public class StatisticsServiceImpl implements IStatisticsService {


    @Autowired
    private StatisticsMapper statisticsMapper;


    @Override
    public AjaxResult getSettleStatistics(String spaceId) {
        AjaxResult ajax = AjaxResult.success();
        ajax.put("shop", statisticsMapper.getSettleStatistics(0l,spaceId));
        ajax.put("office", statisticsMapper.getSettleStatistics(1l,spaceId));
        ajax.put("apartment", statisticsMapper.getSettleStatistics(2l,spaceId));
        return ajax;
    }


    @Override
    public List<ContractExpireDto> getContractExpire(ContractExpireVo contractExpireVo) {
        return statisticsMapper.getContractExpire(contractExpireVo);
    }


    @Override
    public List<CredentialExpireDto> getCredentialExpire(String spaceId) {
        return statisticsMapper.getCredentialExpire(spaceId);
    }

    @Override
    public RentStatisticsDTO getRentStatisticsByYear(String year) {
        return statisticsMapper.getRentStatisticsByYear(year);
    }
}
