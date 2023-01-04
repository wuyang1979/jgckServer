package com.ruoyi.room.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.room.domain.dto.RoomLookDtoPage;
import com.ruoyi.room.domain.vo.RoomLookVoPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.room.mapper.RoomLookMapper;
import com.ruoyi.room.domain.entiy.RoomLook;
import com.ruoyi.room.service.IRoomLookService;

/**
 * 房源带看基本信息Service业务层处理
 *
 * @author gubut
 * @date 2022-12-06
 */
@Service
public class RoomLookServiceImpl implements IRoomLookService {
    @Autowired
    private RoomLookMapper roomLookMapper;

    /**
     * 查询房源带看基本信息
     *
     * @param lookId 房源带看基本信息主键
     * @return 房源带看基本信息
     */
    @Override
    public RoomLook selectRoomLookByLookId(String lookId) {
        return roomLookMapper.selectRoomLookByLookId(lookId);
    }

    /**
     * 查询房源带看基本信息列表
     *
     * @param roomLookVoPage 房源带看基本信息
     * @return 房源带看基本信息
     */
    @Override
    public List<RoomLookDtoPage> selectRoomLookList(RoomLookVoPage roomLookVoPage) {
        return roomLookMapper.selectRoomLookList(roomLookVoPage);
    }

    /**
     * 新增房源带看基本信息
     *
     * @param roomLook 房源带看基本信息
     * @return 结果
     */
    @Override
    public int insertRoomLook(RoomLook roomLook) {
        roomLook.setLookId(UUID.randomUUID().toString());
        roomLook.setCreateTime(DateUtils.getNowDate());
        return roomLookMapper.insertRoomLook(roomLook);
    }

    /**
     * 修改房源带看基本信息
     *
     * @param roomLook 房源带看基本信息
     * @return 结果
     */
    @Override
    public int updateRoomLook(RoomLook roomLook) {
        roomLook.setUpdateTime(DateUtils.getNowDate());
        return roomLookMapper.updateRoomLook(roomLook);
    }

    /**
     * 批量删除房源带看基本信息
     *
     * @param lookIds 需要删除的房源带看基本信息主键
     * @return 结果
     */
    @Override
    public int deleteRoomLookByLookIds(String[] lookIds) {
        return roomLookMapper.deleteRoomLookByLookIds(lookIds);
    }

    /**
     * 删除房源带看基本信息信息
     *
     * @param lookId 房源带看基本信息主键
     * @return 结果
     */
    @Override
    public int deleteRoomLookByLookId(String lookId) {
        return roomLookMapper.deleteRoomLookByLookId(lookId);
    }
}
