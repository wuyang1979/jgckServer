package com.ruoyi.pay.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.pay.domain.dto.PayInfoPageDTO;
import com.ruoyi.pay.domain.vo.PayInfoVo;
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
import com.ruoyi.pay.domain.entiy.PayInfo;
import com.ruoyi.pay.service.IPayInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 缴费基本信息Controller
 *
 * @author gubt
 * @date 2023-01-14
 */
@Api(tags = "缴费基本信息")
@RestController
@RequestMapping("/pay")
public class PayInfoController extends BaseController {
    @Autowired
    private IPayInfoService payInfoService;

    /**
     * 查询缴费基本信息列表
     */
    @ApiOperation("查询缴费基本信息列表")
    @PreAuthorize("@ss.hasPermi('pay:list')")
    @GetMapping("/list")
    public TableDataInfo list(PayInfoVo payInfoVo) {
        startPage();
        List<PayInfoPageDTO> list = payInfoService.selectPayInfoList(payInfoVo);
        return getDataTable(list);
    }

    /**
     * 导出缴费基本信息列表
     */
    @ApiOperation("导出缴费基本信息列表")
    @PreAuthorize("@ss.hasPermi('pay:export')")
    @Log(title = "缴费基本信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PayInfo payInfo) {
//        List<PayInfo> list = payInfoService.selectPayInfoList(payInfo);
//        ExcelUtil<PayInfo> util = new ExcelUtil<PayInfo>(PayInfo. class);
//        util.exportExcel(response, list, "缴费基本信息数据");
    }

    /**
     * 获取缴费基本信息详细信息
     */
    @ApiOperation("获取缴费基本信息详细信息")
    @PreAuthorize("@ss.hasPermi('pay:query')")
    @GetMapping(value = "/{payId}")
    public AjaxResult getInfo(@PathVariable("payId") String payId) {
        return AjaxResult.success(payInfoService.selectPayInfoByPayId(payId));
    }

    /**
     * 新增缴费基本信息
     */
    @ApiOperation("新增缴费基本信息")
    @PreAuthorize("@ss.hasPermi('pay:add')")
    @Log(title = "缴费基本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PayInfo payInfo) {
        payInfoService.insertPayInfo(payInfo);
        return toAjax(payInfo.getPayId());
    }

    /**
     * 修改缴费基本信息
     */
    @ApiOperation("修改缴费基本信息")
    @PreAuthorize("@ss.hasPermi('pay:edit')")
    @Log(title = "缴费基本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PayInfo payInfo) {
        return toAjax(payInfoService.updatePayInfo(payInfo));
    }

    /**
     * 删除缴费基本信息
     */
    @ApiOperation("删除缴费基本信息")
    @PreAuthorize("@ss.hasPermi('pay:remove')")
    @Log(title = "缴费基本信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{payIds}")
    public AjaxResult remove(@PathVariable String[] payIds) {
        return toAjax(payInfoService.deletePayInfoByPayIds(payIds));
    }
}
