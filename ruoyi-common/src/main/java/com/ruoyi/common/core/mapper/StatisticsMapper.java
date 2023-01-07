package com.ruoyi.common.core.mapper;

import com.ruoyi.common.core.domain.dto.ContractExpireDto;
import com.ruoyi.common.core.domain.dto.CredentialExpireDto;
import com.ruoyi.common.core.domain.dto.SettleStatisticsDto;
import com.ruoyi.common.core.domain.vo.ContractExpireVo;

import java.util.List;

/**
 * 首页Mapper接口
 *
 * @author gubut
 * @date 2022-12-26
 */
public interface StatisticsMapper {

    List<SettleStatisticsDto> getSettleStatistics(Long roomType);

    List<ContractExpireDto> getContractExpire(ContractExpireVo contractExpireVo);

    List<CredentialExpireDto> getCredentialExpire();

}
