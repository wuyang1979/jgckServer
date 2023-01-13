package com.ruoyi.repair.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.repair.domain.vo.RepairFeedbackVo;
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
import com.ruoyi.repair.domain.entiy.RepairFeedback;
import com.ruoyi.repair.service.IRepairFeedbackService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 报修反馈基本信息Controller
 *
 * @author gubut
 * @date 2023-01-12
 */
@Api(tags = "报修反馈基本信息")
@RestController
@RequestMapping("/repair")
public class RepairFeedbackController extends BaseController {
    @Autowired
    private IRepairFeedbackService repairFeedbackService;

/**
 * 查询报修反馈基本信息列表
 */
@ApiOperation("查询报修反馈基本信息列表")
@PreAuthorize("@ss.hasPermi('repair:list')")
@GetMapping("/list")
    public TableDataInfo list(RepairFeedbackVo repairFeedbackVo) {
        startPage();
        List<RepairFeedback> list = repairFeedbackService.selectRepairFeedbackList(repairFeedbackVo);
        return getDataTable(list);
    }

    /**
     * 导出报修反馈基本信息列表
     */
    @ApiOperation("导出报修反馈基本信息列表")
    @PreAuthorize("@ss.hasPermi('repair:export')")
    @Log(title = "报修反馈基本信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RepairFeedbackVo repairFeedbackVo) {
        List<RepairFeedback> list = repairFeedbackService.selectRepairFeedbackList(repairFeedbackVo);
        ExcelUtil<RepairFeedback> util = new ExcelUtil<RepairFeedback>(RepairFeedback. class);
        util.exportExcel(response, list, "报修反馈基本信息数据");
    }

    /**
     * 获取报修反馈基本信息详细信息
     */
    @ApiOperation("获取报修反馈基本信息详细信息")
    @PreAuthorize("@ss.hasPermi('repair:query')")
    @GetMapping(value = "/{repairId}")
    public AjaxResult getInfo(@PathVariable("repairId") String repairId) {
        return AjaxResult.success(repairFeedbackService.selectRepairFeedbackByRepairId(repairId));
    }

    /**
     * 新增报修反馈基本信息
     */
    @ApiOperation("新增报修反馈基本信息")
    @PreAuthorize("@ss.hasPermi('repair:add')")
    @Log(title = "报修反馈基本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RepairFeedbackVo repairFeedbackVo) {
        repairFeedbackVo.setCreateBy(getUsername());
        repairFeedbackService.insertRepairFeedback(repairFeedbackVo);
        return toAjax(repairFeedbackVo.getRepairId());
    }

    /**
     * 修改报修反馈基本信息
     */
    @ApiOperation("修改报修反馈基本信息")
    @PreAuthorize("@ss.hasPermi('repair:edit')")
    @Log(title = "报修反馈基本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RepairFeedback repairFeedback) {
        return toAjax(repairFeedbackService.updateRepairFeedback(repairFeedback));
    }

    /**
     * 删除报修反馈基本信息
     */
    @ApiOperation("删除报修反馈基本信息")
    @PreAuthorize("@ss.hasPermi('repair:remove')")
    @Log(title = "报修反馈基本信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{repairIds}")
    public AjaxResult remove(@PathVariable String[] repairIds) {
        return toAjax(repairFeedbackService.deleteRepairFeedbackByRepairIds(repairIds));
    }
}
