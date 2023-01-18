package com.ruoyi.asset.controller;

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
import com.ruoyi.asset.domain.FixedAsset;
import com.ruoyi.asset.service.IFixedAssetService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 固定资产信息Controller
 *
 * @author ruoyi
 * @date 2023-01-18
 */
@Api(tags = "固定资产信息")
@RestController
@RequestMapping("/asset")
public class FixedAssetController extends BaseController {
    @Autowired
    private IFixedAssetService fixedAssetService;

    /**
     * 查询固定资产信息列表
     */
    @ApiOperation("查询固定资产信息列表")
    @PreAuthorize("@ss.hasPermi('asset:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(FixedAsset fixedAsset) {
        startPage();
        List<FixedAsset> list = fixedAssetService.selectFixedAssetList(fixedAsset);
        return getDataTable(list);
    }

    /**
     * 导出固定资产信息列表
     */
    @ApiOperation("导出固定资产信息列表")
    @PreAuthorize("@ss.hasPermi('asset:info:export')")
    @Log(title = "固定资产信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FixedAsset fixedAsset) {
        List<FixedAsset> list = fixedAssetService.selectFixedAssetList(fixedAsset);
        ExcelUtil<FixedAsset> util = new ExcelUtil<FixedAsset>(FixedAsset.class);
        util.exportExcel(response, list, "固定资产信息数据");
    }

    /**
     * 获取固定资产信息详细信息
     */
    @ApiOperation("获取固定资产信息详细信息")
    @PreAuthorize("@ss.hasPermi('asset:info:query')")
    @GetMapping(value = "/{assetId}")
    public AjaxResult getInfo(@PathVariable("assetId") String assetId) {
        return AjaxResult.success(fixedAssetService.selectFixedAssetByAssetId(assetId));
    }

    /**
     * 新增固定资产信息
     */
    @ApiOperation("新增固定资产信息")
    @PreAuthorize("@ss.hasPermi('asset:info:add')")
    @Log(title = "固定资产信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FixedAsset fixedAsset) {
        fixedAsset.setCreateBy(getUsername());
        fixedAsset.setUserId(getUserId().toString());
        fixedAsset.setDeptId(getDeptId().toString());
        fixedAssetService.insertFixedAsset(fixedAsset);
        return toAjax(fixedAsset.getAssetId());
    }

    /**
     * 修改固定资产信息
     */
    @ApiOperation("修改固定资产信息")
    @PreAuthorize("@ss.hasPermi('asset:info:edit')")
    @Log(title = "固定资产信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FixedAsset fixedAsset) {
        fixedAsset.setUpdateBy(getUserId().toString());
        return toAjax(fixedAssetService.updateFixedAsset(fixedAsset));
    }

    /**
     * 删除固定资产信息
     */
    @ApiOperation("删除固定资产信息")
    @PreAuthorize("@ss.hasPermi('asset:info:remove')")
    @Log(title = "固定资产信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{assetIds}")
    public AjaxResult remove(@PathVariable String[] assetIds) {
        return toAjax(fixedAssetService.deleteFixedAssetByAssetIds(assetIds));
    }
}
