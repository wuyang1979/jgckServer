package com.ruoyi.fire.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

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
import com.ruoyi.fire.domain.FireSafety;
import com.ruoyi.fire.service.IFireSafetyService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 消防安全Controller
 *
 * @author gubt
 * @date 2023-01-17
 */
@Api(tags = "消防安全")
@RestController
@RequestMapping("/fire")
public class FireSafetyController extends BaseController {
    @Autowired
    private IFireSafetyService fireSafetyService;

    /**
     * 查询消防安全列表
     */
    @ApiOperation("查询消防安全列表")
    @PreAuthorize("@ss.hasPermi('fire:list')")
    @GetMapping("/list")
    public TableDataInfo list(FireSafety fireSafety) {
        startPage();
        List<FireSafety> list = fireSafetyService.selectFireSafetyList(fireSafety);
        return getDataTable(list);
    }

    /**
     * 导出消防安全列表
     */
    @ApiOperation("导出消防安全列表")
    @PreAuthorize("@ss.hasPermi('fire:export')")
    @Log(title = "消防安全", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FireSafety fireSafety) {
        List<FireSafety> list = fireSafetyService.selectFireSafetyList(fireSafety);
        ExcelUtil<FireSafety> util = new ExcelUtil<FireSafety>(FireSafety.class);
        util.exportExcel(response, list, "消防安全数据");
    }

    /**
     * 获取消防安全详细信息
     */
    @ApiOperation("获取消防安全详细信息")
    @PreAuthorize("@ss.hasPermi('fire:query')")
    @GetMapping(value = "/{fireId}")
    public AjaxResult getInfo(@PathVariable("fireId") String fireId) {
        return AjaxResult.success(fireSafetyService.selectFireSafetyByFireId(fireId));
    }

    /**
     * 新增消防安全
     */
    @ApiOperation("新增消防安全")
    @PreAuthorize("@ss.hasPermi('fire:add')")
    @Log(title = "消防安全", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FireSafety fireSafety) {
        fireSafety.setCreateBy(getUsername());
        fireSafety.setUserId(getUserId().toString());
        fireSafety.setDeptId(getDeptId().toString());
        return toAjax(fireSafetyService.insertFireSafety(fireSafety));
    }

    /**
     * 修改消防安全
     */
    @ApiOperation("修改消防安全")
    @PreAuthorize("@ss.hasPermi('fire:edit')")
    @Log(title = "消防安全", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FireSafety fireSafety) {
        return toAjax(fireSafetyService.updateFireSafety(fireSafety));
    }

    /**
     * 删除消防安全
     */
    @ApiOperation("删除消防安全")
    @PreAuthorize("@ss.hasPermi('fire:remove')")
    @Log(title = "消防安全", businessType = BusinessType.DELETE)
    @DeleteMapping("/{fireIds}")
    public AjaxResult remove(@PathVariable String[] fireIds) {
        return toAjax(fireSafetyService.deleteFireSafetyByFireIds(fireIds));
    }
}
