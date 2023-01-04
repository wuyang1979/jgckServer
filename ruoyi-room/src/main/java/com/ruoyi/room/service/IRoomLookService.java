package com.ruoyi.room.service;

import java.util.List;

import com.ruoyi.room.domain.dto.RoomLookDtoPage;
import com.ruoyi.room.domain.entiy.RoomLook;
import com.ruoyi.room.domain.vo.RoomLookVoPage;

/**
 * 房源带看基本信息Service接口
 *
 * @author gubut
 * @date 2022-12-06
 */
public interface IRoomLookService {
    /**
     * 查询房源带看基本信息
     *
     * @param lookId 房源带看基本信息主键
     * @return 房源带看基本信息
     */
    public RoomLook selectRoomLookByLookId(String lookId);

    /**
     * 查询房源带看基本信息列表
     *
     * @param roomLookVoPage 房源带看基本信息
     * @return 房源带看基本信息集合
     */
    public List<RoomLookDtoPage> selectRoomLookList(RoomLookVoPage roomLookVoPage);

    /**
     * 新增房源带看基本信息
     *
     * @param roomLook 房源带看基本信息
     * @return 结果
     */
    public int insertRoomLook(RoomLook roomLook);

    /**
     * 修改房源带看基本信息
     *
     * @param roomLook 房源带看基本信息
     * @return 结果
     */
    public int updateRoomLook(RoomLook roomLook);

    /**
     * 批量删除房源带看基本信息
     *
     * @param lookIds 需要删除的房源带看基本信息主键集合
     * @return 结果
     */
    public int deleteRoomLookByLookIds(String[] lookIds);

    /**
     * 删除房源带看基本信息信息
     *
     * @param lookId 房源带看基本信息主键
     * @return 结果
     */
    public int deleteRoomLookByLookId(String lookId);
}
