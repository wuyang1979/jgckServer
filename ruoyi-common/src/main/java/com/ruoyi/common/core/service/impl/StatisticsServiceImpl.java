package com.ruoyi.common.core.service.impl;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.dto.ContractExpireDto;
import com.ruoyi.common.core.domain.dto.CredentialExpireDto;
import com.ruoyi.common.core.domain.entity.FileInfo;
import com.ruoyi.common.core.mapper.FileInfoMapper;
import com.ruoyi.common.core.mapper.StatisticsMapper;
import com.ruoyi.common.core.service.IFileInfoService;
import com.ruoyi.common.core.service.IStatisticsService;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public AjaxResult getSettleStatistics() {
        AjaxResult ajax = AjaxResult.success();
        ajax.put("shop", statisticsMapper.getSettleStatistics(0l));
        ajax.put("office", statisticsMapper.getSettleStatistics(1l));
        ajax.put("apartment", statisticsMapper.getSettleStatistics(2l));
        return ajax;
    }


    @Override
    public List<ContractExpireDto> getContractExpire() {
        return statisticsMapper.getContractExpire();
    }


    @Override
    public List<CredentialExpireDto> getCredentialExpire() {
        return statisticsMapper.getCredentialExpire();
    }
}
