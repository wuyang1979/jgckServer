package com.ruoyi.fire.service;

import java.util.List;

import com.ruoyi.fire.domain.FireSafety;

/**
 * 消防安全Service接口
 *
 * @author gubt
 * @date 2023-01-17
 */
public interface IFireSafetyService {
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
     * 批量删除消防安全
     *
     * @param fireIds 需要删除的消防安全主键集合
     * @return 结果
     */
    public int deleteFireSafetyByFireIds(String[] fireIds);

    /**
     * 删除消防安全信息
     *
     * @param fireId 消防安全主键
     * @return 结果
     */
    public int deleteFireSafetyByFireId(String fireId);
}
