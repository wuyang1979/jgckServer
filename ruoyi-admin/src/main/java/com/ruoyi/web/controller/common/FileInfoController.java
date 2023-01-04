package com.ruoyi.web.controller.common;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.domain.vo.FileInfoEditVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.core.domain.entity.FileInfo;
import com.ruoyi.common.core.service.IFileInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 文件信息Controller
 *
 * @author gubut
 * @date 2022-12-26
 */
@Api("文件服务")
@RestController
@RequestMapping("/file")
public class FileInfoController extends BaseController {
    @Autowired
    private IFileInfoService fileInfoService;

    /**
     * 查询文件信息列表
     */
    @ApiOperation("查询文件信息列表")
//    @PreAuthorize("@ss.hasPermi('space:list')")
    @GetMapping("/list")
    public TableDataInfo list(FileInfo fileInfo) {
        startPage();
        List<FileInfo> list = fileInfoService.selectFileInfoList(fileInfo);
        return getDataTable(list);
    }

    /**
     * 导出文件信息列表
     */
    @ApiOperation("导出文件信息列表")
//    @PreAuthorize("@ss.hasPermi('space:export')")
    @Log(title = "文件信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FileInfo fileInfo) {
        List<FileInfo> list = fileInfoService.selectFileInfoList(fileInfo);
        ExcelUtil<FileInfo> util = new ExcelUtil<FileInfo>(FileInfo.class);
        util.exportExcel(response, list, "文件信息数据");
    }

    /**
     * 获取文件信息详细信息
     */
    @ApiOperation("获取文件信息详细信息")
//    @PreAuthorize("@ss.hasPermi('space:query')")
    @GetMapping(value = "/{fileId}")
    public AjaxResult getInfo(@PathVariable("fileId") String fileId) {
        return success(fileInfoService.selectFileInfoByFileId(fileId));
    }

    /**
     * 新增文件信息
     */
    @ApiOperation("新增文件信息")
//    @PreAuthorize("@ss.hasPermi('space:add')")
    @Log(title = "文件信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FileInfo fileInfo) {
        return toAjax(fileInfoService.insertFileInfo(fileInfo));
    }

    /**
     * 修改文件信息
     */
    @ApiOperation("修改文件信息")
//    @PreAuthorize("@ss.hasPermi('space:edit')")
    @Log(title = "文件信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FileInfo fileInfo) {
        return toAjax(fileInfoService.updateFileInfo(fileInfo));
    }

    /**
     * 删除文件信息
     */
    @ApiOperation("删除文件信息")
//    @PreAuthorize("@ss.hasPermi('space:remove')")
    @Log(title = "文件信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{fileIds}")
    public AjaxResult remove(@PathVariable String[] fileIds) {
        return toAjax(fileInfoService.deleteFileInfoByFileIds(fileIds));
    }

    /**
     * 文件与业务id进行绑定
     *
     * @param editVo
     * @return
     */
    @ApiOperation("文件与业务id进行绑定")
    @PutMapping("/editByBusinessId")
    public AjaxResult editByBusinessId(@RequestBody FileInfoEditVo editVo) {
        return fileInfoService.editByBusinessId(editVo.getBusinessId(), editVo.getFileIds());
    }
}
