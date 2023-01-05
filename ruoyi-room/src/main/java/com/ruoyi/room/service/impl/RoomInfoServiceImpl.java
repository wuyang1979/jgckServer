package com.ruoyi.room.service.impl;

import java.util.List;
import java.util.UUID;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.room.domain.dto.RoomInfoDtoPage;
import com.ruoyi.room.domain.vo.RoomInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.room.mapper.RoomInfoMapper;
import com.ruoyi.room.domain.entiy.RoomInfo;
import com.ruoyi.room.service.IRoomInfoService;

/**
 * 房源基本信息Service业务层处理
 *
 * @author gubt
 * @date 2022-12-05
 */
@Service
public class RoomInfoServiceImpl implements IRoomInfoService {
    @Autowired
    private RoomInfoMapper roomInfoMapper;

    /**
     * 查询房源基本信息
     *
     * @param roomId 房源基本信息主键
     * @return 房源基本信息
     */
    @Override
    public RoomInfo selectRoomInfoByRoomId(String roomId) {
        return roomInfoMapper.selectRoomInfoByRoomId(roomId);
    }

    /**
     * 查询房源基本信息列表
     *
     * @param roomInfoVo 房源基本信息
     * @return 房源基本信息
     */
    @DataScope(userAlias = "r",deptAlias = "r")
    @Override
    public List<RoomInfoDtoPage> selectRoomInfoList(RoomInfoVo roomInfoVo) {
        return roomInfoMapper.selectRoomInfoList(roomInfoVo);
    }


    @Override
    public List<RoomInfoDtoPage> listNoScope(RoomInfoVo roomInfoVo) {
        return roomInfoMapper.selectRoomInfoList(roomInfoVo);
    }

    /**
     * 新增房源基本信息
     *
     * @param roomInfo 房源基本信息
     * @return 结果
     */
    @Override
    public int insertRoomInfo(RoomInfo roomInfo) {
        roomInfo.setRoomId(UUID.randomUUID().toString());
        roomInfo.setRoomStatus(0l);
        roomInfo.setCreateTime(DateUtils.getNowDate());
        return roomInfoMapper.insertRoomInfo(roomInfo);
    }

    /**
     * 修改房源基本信息
     *
     * @param roomInfo 房源基本信息
     * @return 结果
     */
    @Override
    public int updateRoomInfo(RoomInfo roomInfo) {
        roomInfo.setUpdateTime(DateUtils.getNowDate());
        return roomInfoMapper.updateRoomInfo(roomInfo);
    }

    /**
     * 批量删除房源基本信息
     *
     * @param roomIds 需要删除的房源基本信息主键
     * @return 结果
     */
    @Override
    public int deleteRoomInfoByRoomIds(String[] roomIds) {
        return roomInfoMapper.deleteRoomInfoByRoomIds(roomIds);
    }

    /**
     * 删除房源基本信息信息
     *
     * @param roomId 房源基本信息主键
     * @return 结果
     */
    @Override
    public int deleteRoomInfoByRoomId(String roomId) {
        return roomInfoMapper.deleteRoomInfoByRoomId(roomId);
    }
}
