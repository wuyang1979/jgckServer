package com.ruoyi.tenants.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.tenants.domain.dto.TenantsInfoHistory;
import com.ruoyi.tenants.domain.entiy.TenantsInfo;
import com.ruoyi.tenants.domain.vo.TenantsInfoHistoryVo;
import com.ruoyi.tenants.service.ITenantsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 租客基本信息Controller
 *
 * @author gubut
 * @date 2022-12-08
 */
@RestController
@RequestMapping("/tenants/history")
public class TenantsInfoHistoryController extends BaseController {
    @Autowired
    private ITenantsInfoService tenantsInfoService;


    /**
     * 查询租客基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('tenants:history:list')")
    @GetMapping("/list")
    public TableDataInfo list(TenantsInfoHistoryVo tenantsInfoHistoryVo) {
        startPage();
        List<TenantsInfoHistory> list = tenantsInfoService.selectTenantsInfoHistoryList(tenantsInfoHistoryVo);
        return getDataTable(list);
    }

}
