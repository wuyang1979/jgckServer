package com.ruoyi.common.core.mapper;

import com.ruoyi.common.core.domain.dto.ContractExpireDto;
import com.ruoyi.common.core.domain.dto.CredentialExpireDto;
import com.ruoyi.common.core.domain.dto.SettleStatisticsDto;
import com.ruoyi.common.core.domain.vo.ContractExpireVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 首页Mapper接口
 *
 * @author gubut
 * @date 2022-12-26
 */
public interface StatisticsMapper {

    List<SettleStatisticsDto> getSettleStatistics(@Param("roomType") Long roomType, @Param("spaceId") String spaceId);

    List<ContractExpireDto> getContractExpire(ContractExpireVo contractExpireVo);

    List<CredentialExpireDto> getCredentialExpire(String spaceId);

}
