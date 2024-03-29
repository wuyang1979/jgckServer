package com.ruoyi.web.controller.common;


import cn.hutool.json.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.dto.ContractExpireDto;
import com.ruoyi.common.core.domain.dto.CredentialExpireDto;
import com.ruoyi.common.core.domain.dto.RentStatisticsDTO;
import com.ruoyi.common.core.domain.vo.RentConfigVo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.service.IStatisticsService;
import com.ruoyi.system.service.ISysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 首页Controller
 *
 * @author gubut
 * @date 2022-12-28
 */
@CrossOrigin
@RestController
@RequestMapping("/statistics")
@Api("首页")
public class StatisticsController extends BaseController {

    @Autowired
    private IStatisticsService statisticsService;

    @Autowired
    private ISysConfigService sysConfigService;

    @GetMapping("/settle/{spaceId}")
    @ApiOperation("入驻统计")
    public AjaxResult getSettleStatistics(@PathVariable String spaceId) {
        return statisticsService.getSettleStatistics(spaceId);
    }


    @GetMapping("/contract/{spaceId}")
    @ApiOperation("合同到期提醒")
    public TableDataInfo getContractExpire(@PathVariable String spaceId) {
        List<ContractExpireDto> list = sysConfigService.getContractExpire(spaceId);
        return getDataTable(list);
    }

    @GetMapping("/credential/{spaceId}")
    @ApiOperation("证件到期提醒")
    public TableDataInfo getCredentialExpire(@PathVariable String spaceId) {
        List<CredentialExpireDto> list = statisticsService.getCredentialExpire(spaceId);
        return getDataTable(list);
    }

    @PostMapping("/remind/setConfig")
    @ApiOperation("设置到期提醒配置")
    public AjaxResult setRentRmind(@RequestBody RentConfigVo rentConfigVo) {
        return sysConfigService.setRentRmind(rentConfigVo);
    }

    @GetMapping("/rent/{year}")
    @ApiOperation("获取房租统计")
    public AjaxResult getRentStatisticsByYear(@PathVariable String year) {
        RentStatisticsDTO rentStatistics = statisticsService.getRentStatisticsByYear(year);
        return success(rentStatistics);
    }


}
