package com.ruoyi.room.service;

import java.util.List;

import com.ruoyi.room.domain.dto.RoomInfoDtoPage;
import com.ruoyi.room.domain.entiy.RoomInfo;
import com.ruoyi.room.domain.vo.RoomInfoVo;

/**
 * 房源基本信息Service接口
 *
 * @author gubt
 * @date 2022-12-05
 */
public interface IRoomInfoService {
    /**
     * 查询房源基本信息
     *
     * @param roomId 房源基本信息主键
     * @return 房源基本信息
     */
    public RoomInfo selectRoomInfoByRoomId(String roomId);

    /**
     * 查询房源基本信息列表
     *
     * @param roomInfoVo 房源基本信息
     * @return 房源基本信息集合
     */
    public List<RoomInfoDtoPage> selectRoomInfoList(RoomInfoVo roomInfoVo);

    /**
     * 查询房源基本信息列表(无数据过滤)
     *
     * @param roomInfoVo 房源基本信息
     * @return 房源基本信息集合
     */
    public List<RoomInfoDtoPage> listNoScope(RoomInfoVo roomInfoVo);

    /**
     * 新增房源基本信息
     *
     * @param roomInfo 房源基本信息
     * @return 结果
     */
    public int insertRoomInfo(RoomInfo roomInfo);

    /**
     * 修改房源基本信息
     *
     * @param roomInfo 房源基本信息
     * @return 结果
     */
    public int updateRoomInfo(RoomInfo roomInfo);

    /**
     * 批量删除房源基本信息
     *
     * @param roomIds 需要删除的房源基本信息主键集合
     * @return 结果
     */
    public int deleteRoomInfoByRoomIds(String[] roomIds);

    /**
     * 删除房源基本信息信息
     *
     * @param roomId 房源基本信息主键
     * @return 结果
     */
    public int deleteRoomInfoByRoomId(String roomId);
}
