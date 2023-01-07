package com.ruoyi.web.controller.common;


import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.dto.ContractExpireDto;
import com.ruoyi.common.core.domain.dto.CredentialExpireDto;
import com.ruoyi.common.core.domain.vo.RentConfigVo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.service.IStatisticsService;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 首页Controller
 *
 * @author gubut
 * @date 2022-12-28
 */
@RestController
@RequestMapping("/statistics")
@Api("首页")
public class StatisticsController extends BaseController {

    @Autowired
    private IStatisticsService statisticsService;

    @Autowired
    private ISysConfigService sysConfigService;

    @GetMapping("/settle")
    @ApiOperation("入驻统计")
    public AjaxResult getSettleStatistics() {
        return statisticsService.getSettleStatistics();
    }


    @GetMapping("/contract")
    @ApiOperation("合同到期提醒")
    public TableDataInfo getContractExpire() {
        List<ContractExpireDto> list = sysConfigService.getContractExpire();
        return getDataTable(list);
    }

    @GetMapping("/credential")
    @ApiOperation("证件到期提醒")
    public TableDataInfo getCredentialExpire() {
        List<CredentialExpireDto> list = statisticsService.getCredentialExpire();
        return getDataTable(list);
    }

    @PostMapping("/remind/setConfig")
    @ApiOperation("设置到期提醒配置")
    public AjaxResult setRentRmind(@RequestBody RentConfigVo rentConfigVo) {
        return sysConfigService.setRentRmind(rentConfigVo);
    };



}
