package com.ruoyi.tenants.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.uuid.UUID;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.tenants.domain.entiy.TenantsInfo;
import com.ruoyi.tenants.service.ITenantsInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 租客基本信息Controller
 *
 * @author gubut
 * @date 2022-12-08
 */
@Api(tags = "租客管理")
@RestController
@RequestMapping("/tenants/info")
public class TenantsInfoController extends BaseController {
    @Autowired
    private ITenantsInfoService tenantsInfoService;

    /**
     * 查询租客基本信息列表
     */
    @ApiOperation("查询租客列表")
    @PreAuthorize("@ss.hasPermi('tenants:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(TenantsInfo tenantsInfo) {
        startPage();
        List<TenantsInfo> list = tenantsInfoService.selectTenantsInfoList(tenantsInfo);
        return getDataTable(list);
    }

    /**
     * 查询租客基本信息列表（无数据过滤）
     */
    @ApiOperation("查询租客列表（无数据过滤）")
    @PreAuthorize("@ss.hasPermi('tenants:info:list')")
    @GetMapping("/listNoScope")
    public TableDataInfo listNoScope(TenantsInfo tenantsInfo) {
        startPage();
        List<TenantsInfo> list = tenantsInfoService.listNoScope(tenantsInfo);
        return getDataTable(list);
    }

    /**
     * 导出租客基本信息列表
     */
    @ApiOperation("导出租客列表")
    @PreAuthorize("@ss.hasPermi('tenants:info:export')")
    @Log(title = "租客基本信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TenantsInfo tenantsInfo) {
        List<TenantsInfo> list = tenantsInfoService.selectTenantsInfoList(tenantsInfo);
        ExcelUtil<TenantsInfo> util = new ExcelUtil<TenantsInfo>(TenantsInfo.class);
        util.exportExcel(response, list, "租客基本信息数据");
    }

    /**
     * 获取租客基本信息详细信息
     */
    @ApiOperation("查询租客详情")
    @PreAuthorize("@ss.hasPermi('tenants:info:query')")
    @GetMapping(value = "/{tenantsId}")
    public AjaxResult getInfo(@PathVariable("tenantsId") String tenantsId) {
        return success(tenantsInfoService.selectTenantsInfoByTenantsId(tenantsId));
    }

    /**
     * 新增租客基本信息
     */
    @ApiOperation("新增租客信息")
    @PreAuthorize("@ss.hasPermi('tenants:info:add')")
    @Log(title = "租客基本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TenantsInfo tenantsInfo) {
        Boolean unique = tenantsInfoService.checkPhoneUnique(tenantsInfo.getContactPhone(),tenantsInfo.getSpaceId());
        if (!unique){
            return AjaxResult.error("手机号码不能重复");
        }
        tenantsInfo.setTenantsId(UUID.randomUUID().toString());
        tenantsInfo.setUserId(getUserId().toString());
        tenantsInfo.setDeptId(getDeptId().toString());
        tenantsInfoService.insertTenantsInfo(tenantsInfo);
        return toAjax(tenantsInfo.getTenantsId());
    }

    /**
     * 修改租客基本信息
     */
    @ApiOperation("修改租客信息")
    @PreAuthorize("@ss.hasPermi('tenants:info:edit')")
    @Log(title = "租客基本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TenantsInfo tenantsInfo) {
        return toAjax(tenantsInfoService.updateTenantsInfo(tenantsInfo));
    }

    /**
     * 删除租客基本信息
     */
    @ApiOperation("删除租客信息")
    @PreAuthorize("@ss.hasPermi('tenants:info:remove')")
    @Log(title = "租客基本信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{tenantsIds}")
    public AjaxResult remove(@PathVariable String[] tenantsIds) {
        return toAjax(tenantsInfoService.deleteTenantsInfoByTenantsIds(tenantsIds));
    }

}
