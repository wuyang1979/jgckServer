package com.ruoyi.brand.controller;

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
import com.ruoyi.brand.domain.BrandCenter;
import com.ruoyi.brand.service.IBrandCenterService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 品牌中心基本信息Controller
 *
 * @author gubut
 * @date 2023-01-13
 */
@Api(tags = "品牌中心基本信息")
@RestController
@RequestMapping("/brand")
public class BrandCenterController extends BaseController {
    @Autowired
    private IBrandCenterService brandCenterService;

/**
 * 查询品牌中心基本信息列表
 */
@ApiOperation("查询品牌中心基本信息列表")
@PreAuthorize("@ss.hasPermi('brand:list')")
@GetMapping("/list")
    public TableDataInfo list(BrandCenter brandCenter) {
        startPage();
        List<BrandCenter> list = brandCenterService.selectBrandCenterList(brandCenter);
        return getDataTable(list);
    }

    /**
     * 导出品牌中心基本信息列表
     */
    @ApiOperation("导出品牌中心基本信息列表")
    @PreAuthorize("@ss.hasPermi('brand:export')")
    @Log(title = "品牌中心基本信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BrandCenter brandCenter) {
        List<BrandCenter> list = brandCenterService.selectBrandCenterList(brandCenter);
        ExcelUtil<BrandCenter> util = new ExcelUtil<BrandCenter>(BrandCenter. class);
        util.exportExcel(response, list, "品牌中心基本信息数据");
    }

    /**
     * 获取品牌中心基本信息详细信息
     */
    @ApiOperation("获取品牌中心基本信息详细信息")
    @PreAuthorize("@ss.hasPermi('brand:query')")
    @GetMapping(value = "/{brandId}")
    public AjaxResult getInfo(@PathVariable("brandId") String brandId) {
        return AjaxResult.success(brandCenterService.selectBrandCenterByBrandId(brandId));
    }

    /**
     * 新增品牌中心基本信息
     */
    @ApiOperation("新增品牌中心基本信息")
    @PreAuthorize("@ss.hasPermi('brand:add')")
    @Log(title = "品牌中心基本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BrandCenter brandCenter) {
        brandCenter.setCreateBy(getUsername());
        brandCenter.setUserId(getUserId().toString());
        brandCenter.setDeptId(getDeptId().toString());
        brandCenterService.insertBrandCenter(brandCenter);
        return toAjax(brandCenter.getBrandId());
    }

    /**
     * 修改品牌中心基本信息
     */
    @ApiOperation("修改品牌中心基本信息")
    @PreAuthorize("@ss.hasPermi('brand:edit')")
    @Log(title = "品牌中心基本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BrandCenter brandCenter) {
        return toAjax(brandCenterService.updateBrandCenter(brandCenter));
    }

    /**
     * 删除品牌中心基本信息
     */
    @ApiOperation("删除品牌中心基本信息")
    @PreAuthorize("@ss.hasPermi('brand:remove')")
    @Log(title = "品牌中心基本信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{brandIds}")
    public AjaxResult remove(@PathVariable String[] brandIds) {
        return toAjax(brandCenterService.deleteBrandCenterByBrandIds(brandIds));
    }
}
