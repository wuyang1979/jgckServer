package com.ruoyi.room.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.room.domain.dto.RoomLookDtoPage;
import com.ruoyi.room.domain.vo.RoomLookVoPage;
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
import com.ruoyi.room.domain.entiy.RoomLook;
import com.ruoyi.room.service.IRoomLookService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 房源带看基本信息Controller
 *
 * @author gubut
 * @date 2022-12-06
 */
@RestController
@RequestMapping("/room/look")
public class RoomLookController extends BaseController {
    @Autowired
    private IRoomLookService roomLookService;

    /**
     * 查询房源带看基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('room:look:list')")
    @GetMapping("/list")
    public TableDataInfo list(RoomLookVoPage roomLookVoPage) {
        startPage();
        List<RoomLookDtoPage> list = roomLookService.selectRoomLookList(roomLookVoPage);
        return getDataTable(list);
    }

    /**
     * 导出房源带看基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('room:look:export')")
    @Log(title = "房源带看基本信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RoomLookVoPage roomLookVoPage) {
        List<RoomLookDtoPage> list = roomLookService.selectRoomLookList(roomLookVoPage);
        ExcelUtil<RoomLookDtoPage> util = new ExcelUtil<RoomLookDtoPage>(RoomLookDtoPage.class);
        util.exportExcel(response, list, "房源带看基本信息数据");
    }

    /**
     * 获取房源带看基本信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('room:look:query')")
    @GetMapping(value = "/{lookId}")
    public AjaxResult getInfo(@PathVariable("lookId") String lookId) {
        return success(roomLookService.selectRoomLookByLookId(lookId));
    }

    /**
     * 新增房源带看基本信息
     */
    @PreAuthorize("@ss.hasPermi('room:look:add')")
    @Log(title = "房源带看基本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RoomLook roomLook) {
        roomLook.setCreateBy(getUsername());
        roomLookService.insertRoomLook(roomLook);
        return toAjax(roomLook.getLookId());
    }

    /**
     * 修改房源带看基本信息
     */
    @PreAuthorize("@ss.hasPermi('room:look:edit')")
    @Log(title = "房源带看基本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RoomLook roomLook) {
        return toAjax(roomLookService.updateRoomLook(roomLook));
    }

    /**
     * 删除房源带看基本信息
     */
    @PreAuthorize("@ss.hasPermi('room:look:remove')")
    @Log(title = "房源带看基本信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{lookIds}")
    public AjaxResult remove(@PathVariable String[] lookIds) {
        return toAjax(roomLookService.deleteRoomLookByLookIds(lookIds));
    }
}
