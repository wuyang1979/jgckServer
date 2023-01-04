package com.ruoyi.credential.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.credential.domain.dto.CredentialInfoDtoPage;
import com.ruoyi.credential.domain.vo.CredentialInfoVoPage;
import com.ruoyi.credential.domain.vo.CredentialUserIdTypeVo;
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
import com.ruoyi.credential.domain.CredentialInfo;
import com.ruoyi.credential.service.ICredentialInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 证件基本信息Controller
 *
 * @author gubut
 * @date 2022-12-30
 */
@Api("证件管理")
@RestController
@RequestMapping("/credential/info")
public class CredentialInfoController extends BaseController {
    @Autowired
    private ICredentialInfoService credentialInfoService;

    /**
     * 查询证件基本信息列表
     */
    @ApiOperation("查询证件基本信息列表")
    @PreAuthorize("@ss.hasPermi('credential:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(CredentialInfoVoPage credentialInfoVoPage) {
        startPage();
        List<CredentialInfoDtoPage> list = credentialInfoService.selectCredentialInfoList(credentialInfoVoPage);
        return getDataTable(list);
    }

    /**
     * 导出证件基本信息列表
     */
    @ApiOperation("导出证件基本信息列表")
    @PreAuthorize("@ss.hasPermi('credential:info:export')")
    @Log(title = "证件基本信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CredentialInfoVoPage credentialInfoVoPage) {
        List<CredentialInfoDtoPage> list = credentialInfoService.selectCredentialInfoList(credentialInfoVoPage);
        ExcelUtil<CredentialInfoDtoPage> util = new ExcelUtil<CredentialInfoDtoPage>(CredentialInfoDtoPage.class);
        util.exportExcel(response, list, "证件基本信息数据");
    }

    /**
     * 获取证件基本信息详细信息
     */
    @ApiOperation("获取证件基本信息详细信息")
    @PreAuthorize("@ss.hasPermi('credential:info:query')")
    @GetMapping(value = "/{credentialId}")
    public AjaxResult getInfo(@PathVariable("credentialId") String credentialId) {
        return success(credentialInfoService.selectCredentialInfoByCredentialId(credentialId));
    }

    /**
     * 新增证件基本信息
     */
    @ApiOperation("新增证件基本信息")
    @PreAuthorize("@ss.hasPermi('credential:info:add')")
    @Log(title = "证件基本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CredentialInfo credentialInfo) {
        credentialInfo.setCreateBy(getUsername());
        return toAjax(credentialInfoService.insertCredentialInfo(credentialInfo));
    }

    /**
     * 修改证件基本信息
     */
    @ApiOperation("修改证件基本信息")
    @PreAuthorize("@ss.hasPermi('credential:info:edit')")
    @Log(title = "证件基本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CredentialInfo credentialInfo) {
        return toAjax(credentialInfoService.updateCredentialInfo(credentialInfo));
    }

    /**
     * 删除证件基本信息
     */
    @ApiOperation("删除证件基本信息")
    @PreAuthorize("@ss.hasPermi('credential:info:remove')")
    @Log(title = "证件基本信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{credentialIds}")
    public AjaxResult remove(@PathVariable String[] credentialIds) {
        return toAjax(credentialInfoService.deleteCredentialInfoByCredentialIds(credentialIds));
    }


    @ApiOperation("根据用户id及证件类型查询证件基本信息列表")
    @GetMapping("/listByUserIdAndType")
    public TableDataInfo listByUserIdAndType(@RequestBody CredentialUserIdTypeVo credentialUserIdTypeVo) {
        return getDataTable(null);
    }
}
