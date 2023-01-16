package com.ruoyi.contract.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.contract.domain.vo.ContractOtherVo;
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
import com.ruoyi.contract.domain.entiy.ContractOther;
import com.ruoyi.contract.service.IContractOtherService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 其他合同Controller
 *
 * @author gubt
 * @date 2023-01-16
 */
@Api(tags = "其他合同")
@RestController
@RequestMapping("/contract/other")
public class ContractOtherController extends BaseController {
    @Autowired
    private IContractOtherService contractOtherService;

    /**
     * 查询其他合同列表
     */
    @ApiOperation("查询其他合同列表")
    @PreAuthorize("@ss.hasPermi('contract:other:list')")
    @GetMapping("/list")
    public TableDataInfo list(ContractOtherVo contractOtherVo) {
        startPage();
        List<ContractOther> list = contractOtherService.selectContractOtherList(contractOtherVo);
        return getDataTable(list);
    }

    /**
     * 导出其他合同列表
     */
    @ApiOperation("导出其他合同列表")
    @PreAuthorize("@ss.hasPermi('contract:other:export')")
    @Log(title = "其他合同", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ContractOther contractOther) {
//        List<ContractOther> list = contractOtherService.selectContractOtherList(contractOther);
//        ExcelUtil<ContractOther> util = new ExcelUtil<ContractOther>(ContractOther.class);
//        util.exportExcel(response, list, "其他合同数据");
    }

    /**
     * 获取其他合同详细信息
     */
    @ApiOperation("获取其他合同详细信息")
    @PreAuthorize("@ss.hasPermi('contract:other:query')")
    @GetMapping(value = "/{contractId}")
    public AjaxResult getInfo(@PathVariable("contractId") String contractId) {
        return AjaxResult.success(contractOtherService.selectContractOtherByContractId(contractId));
    }

    /**
     * 新增其他合同
     */
    @ApiOperation("新增其他合同")
    @PreAuthorize("@ss.hasPermi('contract:other:add')")
    @Log(title = "其他合同", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ContractOther contractOther) {
        contractOther.setCreateBy(getUsername());
        contractOtherService.insertContractOther(contractOther);
        return toAjax(contractOther.getContractId());
    }

    /**
     * 修改其他合同
     */
    @ApiOperation("修改其他合同")
    @PreAuthorize("@ss.hasPermi('contract:other:edit')")
    @Log(title = "其他合同", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ContractOther contractOther) {
        return toAjax(contractOtherService.updateContractOther(contractOther));
    }

    /**
     * 删除其他合同
     */
    @ApiOperation("删除其他合同")
    @PreAuthorize("@ss.hasPermi('contract:other:remove')")
    @Log(title = "其他合同", businessType = BusinessType.DELETE)
    @DeleteMapping("/{contractIds}")
    public AjaxResult remove(@PathVariable String[] contractIds) {
        return toAjax(contractOtherService.deleteContractOtherByContractIds(contractIds));
    }
}
