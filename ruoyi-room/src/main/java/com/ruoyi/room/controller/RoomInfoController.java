package com.ruoyi.room.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.room.domain.dto.RoomInfoDtoPage;
import com.ruoyi.room.domain.vo.RoomInfoVo;
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
import com.ruoyi.room.domain.entiy.RoomInfo;
import com.ruoyi.room.service.IRoomInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 房源基本信息Controller
 *
 * @author gubt
 * @date 2022-12-05
 */
@RestController
@RequestMapping("/room/info")
public class RoomInfoController extends BaseController {
    @Autowired
    private IRoomInfoService roomInfoService;

    /**
     * 查询房源基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('room:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(RoomInfoVo roomInfoVo) {
        startPage();
        List<RoomInfoDtoPage> list = roomInfoService.selectRoomInfoList(roomInfoVo);
        return getDataTable(list);
    }

    /**
     * 导出房源基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('room:info:export')")
    @Log(title = "房源基本信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RoomInfoVo roomInfoVo) {
        List<RoomInfoDtoPage> list = roomInfoService.selectRoomInfoList(roomInfoVo);
        ExcelUtil<RoomInfoDtoPage> util = new ExcelUtil<RoomInfoDtoPage>(RoomInfoDtoPage.class);
        util.exportExcel(response, list, "房源基本信息数据");
    }

    /**
     * 获取房源基本信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('room:info:query')")
    @GetMapping(value = "/{roomId}")
    public AjaxResult getInfo(@PathVariable("roomId") String roomId) {
        return success(roomInfoService.selectRoomInfoByRoomId(roomId));
    }

    /**
     * 新增房源基本信息
     */
    @PreAuthorize("@ss.hasPermi('room:info:add')")
    @Log(title = "房源基本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RoomInfo roomInfo) {
        roomInfo.setCreateBy(getUsername());
        roomInfoService.insertRoomInfo(roomInfo);
        return toAjax(roomInfo.getRoomId());
    }

    /**
     * 修改房源基本信息
     */
    @PreAuthorize("@ss.hasPermi('room:info:edit')")
    @Log(title = "房源基本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RoomInfo roomInfo) {
        return toAjax(roomInfoService.updateRoomInfo(roomInfo));
    }

    /**
     * 删除房源基本信息
     */
    @PreAuthorize("@ss.hasPermi('room:info:remove')")
    @Log(title = "房源基本信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{roomIds}")
    public AjaxResult remove(@PathVariable String[] roomIds) {
        return toAjax(roomInfoService.deleteRoomInfoByRoomIds(roomIds));
    }
}
