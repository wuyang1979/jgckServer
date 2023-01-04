package com.ruoyi.activity.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.activity.domain.dto.ActivityPersonDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.activity.domain.ActivityUserRelation;
import com.ruoyi.activity.service.IActivityUserRelationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 活动与用户关系Controller
 *
 * @author gubut
 * @date 2022-12-22
 */
@RestController
@RequestMapping("/activity/relation")
public class ActivityUserRelationController extends BaseController {
    @Autowired
    private IActivityUserRelationService activityUserRelationService;

    /**
     * 查询活动与用户关系列表
     */
    @PreAuthorize("@ss.hasPermi('activity:relation:list')")
    @GetMapping("/list")
    public TableDataInfo list(ActivityUserRelation activityUserRelation) {
        startPage();
        List<ActivityUserRelation> list = activityUserRelationService.selectActivityUserRelationList(activityUserRelation);
        return getDataTable(list);
    }

    /**
     * 导出活动与用户关系列表
     */
    @PreAuthorize("@ss.hasPermi('activity:relation:export')")
    @Log(title = "活动与用户关系", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ActivityUserRelation activityUserRelation) {
        List<ActivityPersonDto> list = activityUserRelationService.selectActivityPersonList(activityUserRelation.getActivityId());
        ExcelUtil<ActivityPersonDto> util = new ExcelUtil<ActivityPersonDto>(ActivityPersonDto.class);
        util.exportExcel(response, list, "活动列表数据");
    }

    /**
     * 获取活动与用户关系详细信息
     */
    @PreAuthorize("@ss.hasPermi('activity:relation:query')")
    @GetMapping(value = "/{activityId}")
    public AjaxResult getInfo(@PathVariable("activityId") String activityId) {
        return success(activityUserRelationService.selectActivityUserRelationByActivityId(activityId));
    }

    /**
     * 新增活动与用户关系(报名参与活动)
     */
    @PreAuthorize("@ss.hasPermi('activity:relation:add')")
    @Log(title = "活动与用户关系", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ActivityUserRelation activityUserRelation) {
        activityUserRelation.setUserId(getUserId().toString());
        activityUserRelation.setCreateBy(getUsername());
        return activityUserRelationService.insertActivityUserRelation(activityUserRelation);
    }

    /**
     * 修改活动与用户关系
     */
    @PreAuthorize("@ss.hasPermi('activity:relation:edit')")
    @Log(title = "活动与用户关系", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ActivityUserRelation activityUserRelation) {
        return toAjax(activityUserRelationService.updateActivityUserRelation(activityUserRelation));
    }

    /**
     * 删除活动与用户关系
     */
    @PreAuthorize("@ss.hasPermi('activity:relation:remove')")
    @Log(title = "活动与用户关系", businessType = BusinessType.DELETE)
    @DeleteMapping("/{activityIds}")
    public AjaxResult remove(@PathVariable String[] activityIds) {
        return toAjax(activityUserRelationService.deleteActivityUserRelationByActivityIds(activityIds));
    }


    @PreAuthorize("@ss.hasPermi('activity:relation:remove')")
    @Log(title = "活动与用户关系", businessType = BusinessType.DELETE)
    @DeleteMapping()
    public AjaxResult removeByActivityIdAndUserId(@RequestParam(name = "activityId") String activityId) {
        return toAjax(activityUserRelationService.deleteActivityUserRelationByActivityIdAndUserId(activityId, getUserId().toString()));
    }

    @PreAuthorize("@ss.hasPermi('activity:relation:remove')")
    @Log(title = "活动与用户关系", businessType = BusinessType.DELETE)
    @DeleteMapping("/all")
    public AjaxResult removeByActivityId(@RequestParam(name = "activityId") String activityId) {
        activityUserRelationService.deleteActivityUserRelationByActivity(activityId);
        return success();
    }

    @GetMapping("/person")
    public TableDataInfo selectActivityPersonList(@RequestParam(name = "activityId") String activityId) {
        List<ActivityPersonDto> list = activityUserRelationService.selectActivityPersonList(activityId);
        return getDataTable(list);
    }
}
