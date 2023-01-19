package com.ruoyi.material.controller;

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
import com.ruoyi.material.domain.MaterialInfo;
import com.ruoyi.material.service.IMaterialInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 物料管理Controller
 *
 * @author gubt
 * @date 2023-01-19
 */
@Api(tags = "物料管理")
@RestController
@RequestMapping("/material")
public class MaterialInfoController extends BaseController {
    @Autowired
    private IMaterialInfoService materialInfoService;

/**
 * 查询物料管理列表
 */
@ApiOperation("查询物料管理列表")
@PreAuthorize("@ss.hasPermi('material:list')")
@GetMapping("/list")
    public TableDataInfo list(MaterialInfo materialInfo) {
        startPage();
        List<MaterialInfo> list = materialInfoService.selectMaterialInfoList(materialInfo);
        return getDataTable(list);
    }

    /**
     * 导出物料管理列表
     */
    @ApiOperation("导出物料管理列表")
    @PreAuthorize("@ss.hasPermi('material:export')")
    @Log(title = "物料管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MaterialInfo materialInfo) {
//        List<MaterialInfo> list = materialInfoService.selectMaterialInfoList(materialInfo);
//        ExcelUtil<MaterialInfo> util = new ExcelUtil<MaterialInfo>(MaterialInfo. class);
//        util.exportExcel(response, list, "物料管理数据");
    }

    /**
     * 获取物料管理详细信息
     */
    @ApiOperation("获取物料管理详细信息")
    @PreAuthorize("@ss.hasPermi('material:query')")
    @GetMapping(value = "/{materialId}")
    public AjaxResult getInfo(@PathVariable("materialId") String materialId) {
        return AjaxResult.success(materialInfoService.selectMaterialInfoByMaterialId(materialId));
    }

    /**
     * 新增物料管理
     */
    @ApiOperation("新增物料管理")
    @PreAuthorize("@ss.hasPermi('material:add')")
    @Log(title = "物料管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MaterialInfo materialInfo) {
        materialInfo.setCreateBy(getUsername());
        return toAjax(materialInfoService.insertMaterialInfo(materialInfo));
    }

    /**
     * 修改物料管理
     */
    @ApiOperation("修改物料管理")
    @PreAuthorize("@ss.hasPermi('material:edit')")
    @Log(title = "物料管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MaterialInfo materialInfo) {
        materialInfo.setUpdateBy(getUsername());
        materialInfoService.updateMaterialInfo(materialInfo);
        return toAjax(materialInfo.getMaterialId());
    }

    /**
     * 删除物料管理
     */
    @ApiOperation("删除物料管理")
    @PreAuthorize("@ss.hasPermi('material:remove')")
    @Log(title = "物料管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{materialIds}")
    public AjaxResult remove(@PathVariable String[] materialIds) {
        return toAjax(materialInfoService.deleteMaterialInfoByMaterialIds(materialIds));
    }
}
