package com.ruoyi.activity.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.activity.domain.dto.ActivityInfoDto;
import com.ruoyi.activity.domain.dto.ActivitySignDto;
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
import com.ruoyi.activity.domain.ActivityInfo;
import com.ruoyi.activity.service.IActivityInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 活动基本信息Controller
 *
 * @author gubut
 * @date 2022-12-22
 */
@Api("活动管理")
@RestController
@RequestMapping("/activity/info")
public class ActivityInfoController extends BaseController {
    @Autowired
    private IActivityInfoService activityInfoService;

    /**
     * 查询活动基本信息列表
     */
    @ApiOperation("查询活动基本信息列表")
    @PreAuthorize("@ss.hasPermi('activity:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(ActivityInfo activityInfo) {
        startPage();
        List<ActivityInfoDto> list = activityInfoService.selectActivityInfoList(activityInfo);
        return getDataTable(list);
    }

    /**
     * 导出活动基本信息列表
     */
    @ApiOperation("导出活动基本信息列表")
    @PreAuthorize("@ss.hasPermi('activity:info:export')")
    @Log(title = "活动基本信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ActivityInfo activityInfo) {
//        List<ActivityInfo> list = activityInfoService.selectActivityInfoList(activityInfo);
//        ExcelUtil<ActivityInfo> util = new ExcelUtil<ActivityInfo>(ActivityInfo.class);
//        util.exportExcel(response, list, "活动基本信息数据");
    }

    /**
     * 获取活动基本信息详细信息
     */
    @ApiOperation("获取活动基本信息详细信息")
    @PreAuthorize("@ss.hasPermi('activity:info:query')")
    @GetMapping(value = "/{activityId}")
    public AjaxResult getInfo(@PathVariable("activityId") String activityId) {
        return success(activityInfoService.selectActivityInfoByActivityId(activityId));
    }

    /**
     * 新增活动基本信息
     */
    @ApiOperation("新增活动基本信息")
    @PreAuthorize("@ss.hasPermi('activity:info:add')")
    @Log(title = "活动基本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ActivityInfo activityInfo) {
        activityInfo.setCreateBy(getUsername());
        return toAjax(activityInfoService.insertActivityInfo(activityInfo));
    }

    /**
     * 修改活动基本信息
     */
    @ApiOperation("修改活动基本信息")
    @PreAuthorize("@ss.hasPermi('activity:info:edit')")
    @Log(title = "活动基本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ActivityInfo activityInfo) {
        return toAjax(activityInfoService.updateActivityInfo(activityInfo));
    }

    /**
     * 删除活动基本信息
     */
    @ApiOperation("删除活动基本信息")
    @PreAuthorize("@ss.hasPermi('activity:info:remove')")
    @Log(title = "活动基本信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{activityIds}")
    public AjaxResult remove(@PathVariable String[] activityIds) {
        return toAjax(activityInfoService.deleteActivityInfoByActivityIds(activityIds));
    }

    /**
     * 获取所有活动报名情况
     *
     * @return
     */
    @ApiOperation("获取所有活动报名情况")
    @GetMapping("/selectSignList")
    public TableDataInfo selectSignList() {
        List<ActivitySignDto> list = activityInfoService.selectSignList(getUserId().toString());
        return getDataTable(list);
    }
}
