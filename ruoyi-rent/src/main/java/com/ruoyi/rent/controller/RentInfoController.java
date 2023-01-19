package com.ruoyi.rent.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.rent.domain.dto.RentInfoPageDTO;
import com.ruoyi.rent.domain.vo.RentInfoInsertVo;
import com.ruoyi.rent.domain.vo.RentInfoPageVo;
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
import com.ruoyi.rent.domain.entiy.RentInfo;
import com.ruoyi.rent.service.IRentInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 租金管理Controller
 *
 * @author gubt
 * @date 2023-01-17
 */
@Api(tags = "租金管理")
@RestController
@RequestMapping("/rent/info")
public class RentInfoController extends BaseController {
    @Autowired
    private IRentInfoService rentInfoService;

    /**
     * 查询租金管理列表
     */
    @ApiOperation("查询租金管理列表")
    @PreAuthorize("@ss.hasPermi('rent:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(RentInfoPageVo rentInfoPageVo) {
        startPage();
        List<RentInfoPageDTO> list = rentInfoService.selectRentInfoList(rentInfoPageVo);
        return getDataTable(list);
    }

    /**
     * 导出租金管理列表
     */
    @ApiOperation("导出租金管理列表")
    @PreAuthorize("@ss.hasPermi('rent:info:export')")
    @Log(title = "租金管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RentInfo rentInfo) {
//        List<RentInfo> list = rentInfoService.selectRentInfoList(rentInfo);
//        ExcelUtil<RentInfo> util = new ExcelUtil<RentInfo>(RentInfo.class);
//        util.exportExcel(response, list, "租金管理数据");
    }

    /**
     * 获取租金管理详细信息
     */
    @ApiOperation("获取租金管理详细信息")
    @PreAuthorize("@ss.hasPermi('rent:info:query')")
    @GetMapping(value = "/{rentId}")
    public AjaxResult getInfo(@PathVariable("rentId") String rentId) {
        return AjaxResult.success(rentInfoService.selectRentInfoByRentId(rentId));
    }

    /**
     * 新增租金管理
     */
    @ApiOperation("新增租金管理")
    @PreAuthorize("@ss.hasPermi('rent:info:add')")
    @Log(title = "租金管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RentInfoInsertVo rentInfoInsertVo) {
        rentInfoInsertVo.setCreateBy(getUsername());
        return rentInfoService.insertRentInfo(rentInfoInsertVo);
    }

    /**
     * 修改租金管理
     */
    @ApiOperation("修改租金管理")
    @PreAuthorize("@ss.hasPermi('rent:info:edit')")
    @Log(title = "租金管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RentInfo rentInfo) {
        return toAjax(rentInfoService.updateRentInfo(rentInfo));
    }

    /**
     * 删除租金管理
     */
    @ApiOperation("删除租金管理")
    @PreAuthorize("@ss.hasPermi('rent:info:remove')")
    @Log(title = "租金管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{rentIds}")
    public AjaxResult remove(@PathVariable String[] rentIds) {
        return toAjax(rentInfoService.deleteRentInfoByRentIds(rentIds));
    }
}
