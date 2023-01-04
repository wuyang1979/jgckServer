package com.ruoyi.space.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

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
import com.ruoyi.space.domain.TSpaceInfo;
import com.ruoyi.space.service.ITSpaceInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 空间基本信息Controller
 *
 * @author ruoyi
 * @date 2022-12-02
 */
@RestController
@RequestMapping("/space/info")
public class TSpaceInfoController extends BaseController {
    @Autowired
    private ITSpaceInfoService tSpaceInfoService;

    /**
     * 查询空间基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('space:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(TSpaceInfo tSpaceInfo) {
        startPage();
        List<TSpaceInfo> list = tSpaceInfoService.selectTSpaceInfoList(tSpaceInfo);
        return getDataTable(list);
    }

    /**
     * 导出空间基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('space:info:export')")
    @Log(title = "空间基本信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TSpaceInfo tSpaceInfo) {
        List<TSpaceInfo> list = tSpaceInfoService.selectTSpaceInfoList(tSpaceInfo);
        ExcelUtil<TSpaceInfo> util = new ExcelUtil<TSpaceInfo>(TSpaceInfo.class);
        util.exportExcel(response, list, "空间基本信息数据");
    }

    /**
     * 获取空间基本信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('space:info:query')")
    @GetMapping(value = "/{spaceId}")
    public AjaxResult getInfo(@PathVariable("spaceId") String spaceId) {
        return success(tSpaceInfoService.selectTSpaceInfoBySpaceId(spaceId));
    }

    /**
     * 新增空间基本信息
     */
    @PreAuthorize("@ss.hasPermi('space:info:add')")
    @Log(title = "空间基本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TSpaceInfo tSpaceInfo) {
        tSpaceInfo.setCreateBy(getUsername());
        tSpaceInfoService.insertTSpaceInfo(tSpaceInfo);
        return toAjax(tSpaceInfo.getSpaceId());
    }

    /**
     * 修改空间基本信息
     */
    @PreAuthorize("@ss.hasPermi('space:info:edit')")
    @Log(title = "空间基本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TSpaceInfo tSpaceInfo) {
        tSpaceInfo.setUpdateBy(getUsername());
        return toAjax(tSpaceInfoService.updateTSpaceInfo(tSpaceInfo));
    }

    /**
     * 删除空间基本信息
     */
    @PreAuthorize("@ss.hasPermi('space:info:remove')")
    @Log(title = "空间基本信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{spaceIds}")
    public AjaxResult remove(@PathVariable String[] spaceIds) {
        return toAjax(tSpaceInfoService.deleteTSpaceInfoBySpaceIds(spaceIds));
    }
}
