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
import com.ruoyi.space.domain.SpaceInfo;
import com.ruoyi.space.service.ISpaceInfoService;
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
public class SpaceInfoController extends BaseController {
    @Autowired
    private ISpaceInfoService tSpaceInfoService;

    /**
     * 查询空间基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('space:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(SpaceInfo spaceInfo) {
        startPage();
        List<SpaceInfo> list = tSpaceInfoService.selectSpaceInfoList(spaceInfo);
        return getDataTable(list);
    }

    /**
     * 导出空间基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('space:info:export')")
    @Log(title = "空间基本信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SpaceInfo spaceInfo) {
        List<SpaceInfo> list = tSpaceInfoService.selectSpaceInfoList(spaceInfo);
        ExcelUtil<SpaceInfo> util = new ExcelUtil<SpaceInfo>(SpaceInfo.class);
        util.exportExcel(response, list, "空间基本信息数据");
    }

    /**
     * 获取空间基本信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('space:info:query')")
    @GetMapping(value = "/{spaceId}")
    public AjaxResult getInfo(@PathVariable("spaceId") String spaceId) {
        return success(tSpaceInfoService.selectSpaceInfoBySpaceId(spaceId));
    }

    /**
     * 新增空间基本信息
     */
    @PreAuthorize("@ss.hasPermi('space:info:add')")
    @Log(title = "空间基本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SpaceInfo spaceInfo) {
        spaceInfo.setCreateBy(getUsername());
        spaceInfo.setUserId(getUserId().toString());
        spaceInfo.setDeptId(getDeptId().toString());
        tSpaceInfoService.insertSpaceInfo(spaceInfo);
        return toAjax(spaceInfo.getSpaceId());
    }

    /**
     * 修改空间基本信息
     */
    @PreAuthorize("@ss.hasPermi('space:info:edit')")
    @Log(title = "空间基本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SpaceInfo spaceInfo) {
        spaceInfo.setUpdateBy(getUsername());
        return toAjax(tSpaceInfoService.updateSpaceInfo(spaceInfo));
    }

    /**
     * 删除空间基本信息
     */
    @PreAuthorize("@ss.hasPermi('space:info:remove')")
    @Log(title = "空间基本信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{spaceIds}")
    public AjaxResult remove(@PathVariable String[] spaceIds) {
        return toAjax(tSpaceInfoService.deleteSpaceInfoBySpaceIds(spaceIds));
    }
}
