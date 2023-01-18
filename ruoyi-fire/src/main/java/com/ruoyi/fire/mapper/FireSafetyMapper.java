package com.ruoyi.fire.mapper;

import java.util.List;

import com.ruoyi.fire.domain.FireSafety;

/**
 * 消防安全Mapper接口
 *
 * @author gubt
 * @date 2023-01-17
 */
public interface FireSafetyMapper {
    /**
     * 查询消防安全
     *
     * @param fireId 消防安全主键
     * @return 消防安全
     */
    public FireSafety selectFireSafetyByFireId(String fireId);

    /**
     * 查询消防安全列表
     *
     * @param fireSafety 消防安全
     * @return 消防安全集合
     */
    public List<FireSafety> selectFireSafetyList(FireSafety fireSafety);

    /**
     * 新增消防安全
     *
     * @param fireSafety 消防安全
     * @return 结果
     */
    public int insertFireSafety(FireSafety fireSafety);

    /**
     * 修改消防安全
     *
     * @param fireSafety 消防安全
     * @return 结果
     */
    public int updateFireSafety(FireSafety fireSafety);

    /**
     * 删除消防安全
     *
     * @param fireId 消防安全主键
     * @return 结果
     */
    public int deleteFireSafetyByFireId(String fireId);

    /**
     * 批量删除消防安全
     *
     * @param fireIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFireSafetyByFireIds(String[] fireIds);
}
