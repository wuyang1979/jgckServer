package com.ruoyi.contract.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.contract.domain.dto.ContractInfoPageDto;
import com.ruoyi.contract.domain.vo.ContractInfoPageVo;
import com.ruoyi.contract.service.IContractInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 合同信息Controller
 *
 * @author gubut
 * @date 2022-12-09
 */
@Api(tags = "合同管理")
@RestController
@RequestMapping("/contract/info")
public class ContractInfoController extends BaseController {
    @Autowired
    private IContractInfoService contractInfoService;

    /**
     * 查询合同信息列表
     */
    @ApiOperation("查询合同基本信息列表")
    @PreAuthorize("@ss.hasPermi('contract:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(ContractInfoPageVo contractInfoPageVo) {
        startPage();
        List<ContractInfoPageDto> list = contractInfoService.selectContractInfoList(contractInfoPageVo);
        return getDataTable(list);
    }

    /**
     * 导出合同信息列表
     */
    @ApiOperation("导出合同信息列表")
    @PreAuthorize("@ss.hasPermi('contract:info:export')")
    @Log(title = "合同信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ContractInfoPageVo contractInfoPageVo) {
        List<ContractInfoPageDto> list = contractInfoService.selectContractInfoList(contractInfoPageVo);
        ExcelUtil<ContractInfoPageDto> util = new ExcelUtil<ContractInfoPageDto>(ContractInfoPageDto.class);
        util.exportExcel(response, list, "合同信息数据");
    }

    /**
     * 获取合同信息详细信息
     */
    @ApiOperation("获取合同信息详细信息")
    @PreAuthorize("@ss.hasPermi('contract:info:query')")
    @GetMapping(value = "/{contractId}")
    public AjaxResult getInfo(@PathVariable("contractId") String contractId) {
        return success(contractInfoService.selectContractInfoByContractId(contractId));
    }

    /**
     * 新增合同信息
     */
    @ApiOperation("新增合同信息")
    @PreAuthorize("@ss.hasPermi('contract:info:add')")
    @Log(title = "合同信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ContractInfoPageVo contractInfoPageVo) {
        //todo  校验合同开始日期是否与房源的上个合同交叉
        Boolean isExceed = contractInfoService.DateExceed(contractInfoPageVo.getRoomId(), contractInfoPageVo.getSpaceId(),contractInfoPageVo.getLeaseStartTime());
        if (!isExceed){
            return AjaxResult.error("合同开始时间不能再上个合同时间之内");
        }
        contractInfoPageVo.setCreateBy(getUsername());
        contractInfoPageVo.setUserId(getUserId().toString());
        contractInfoPageVo.setDeptId(getDeptId().toString());
        return toAjax(contractInfoService.insertContractInfo(contractInfoPageVo));
    }

    /**
     * 修改合同信息
     */
    @ApiOperation("修改合同信息")
    @PreAuthorize("@ss.hasPermi('contract:info:edit')")
    @Log(title = "合同信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ContractInfoPageVo contractInfoPageVo) {
        contractInfoPageVo.setUpdateBy(getUsername());
        return toAjax(contractInfoService.updateContractInfo(contractInfoPageVo));
    }

    /**
     * 删除合同信息
     */
    @ApiOperation("删除合同信息")
    @PreAuthorize("@ss.hasPermi('contract:info:remove')")
    @Log(title = "合同信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{contractIds}")
    public AjaxResult remove(@PathVariable String[] contractIds) {
        return toAjax(contractInfoService.deleteContractInfoByContractIds(contractIds));
    }

}
