package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysUserRole;
import com.ruoyi.system.domain.SysUserSpace;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户与空间关联表 数据层
 *
 * @author ruoyi
 */
public interface SysUserSpaceMapper {
    /**
     * 通过用户ID删除用户和空间关联
     *
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteUserSpaceByUserId(Long userId);

    /**
     * 批量删除用户和空间关联
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUserSpace(Long[] ids);

    /**
     * 通过空间ID查询空间使用数量
     *
     * @param spaceId 空间ID
     * @return 结果
     */
    public int countUserSpaceBySpaceId(Long spaceId);

    /**
     * 批量新增用户空间信息
     *
     * @param userSpaceList 用户空间列表
     * @return 结果
     */
    public int batchUserSpace(List<SysUserSpace> userSpaceList);

    /**
     * 删除用户和空间关联信息
     *
     * @param userSpace 用户和角色关联信息
     * @return 结果
     */
    public int deleteUserSpaceInfo(SysUserSpace userSpace);

    /**
     * 批量取消授权用户角色
     *
     * @param spaceId 角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
    public int deleteUserSpaceInfos(@Param("spaceId") String spaceId, @Param("userIds") Long[] userIds);

    /**
     * 根据用户ID获取空间选择框列表
     *
     * @param userId 用户ID
     * @return 选中空间ID列表
     */
    List<String> selectSpaceListByUserId(Long userId);
}
