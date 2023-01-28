package com.ruoyi.fire.service.impl;

import java.util.List;
import java.util.UUID;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fire.mapper.FireSafetyMapper;
import com.ruoyi.fire.domain.FireSafety;
import com.ruoyi.fire.service.IFireSafetyService;

/**
 * 消防安全Service业务层处理
 *
 * @author gubt
 * @date 2023-01-17
 */
@Service
public class FireSafetyServiceImpl implements IFireSafetyService {
    @Autowired
    private FireSafetyMapper fireSafetyMapper;

    /**
     * 查询消防安全
     *
     * @param fireId 消防安全主键
     * @return 消防安全
     */
    @Override
    public FireSafety selectFireSafetyByFireId(String fireId) {
        return fireSafetyMapper.selectFireSafetyByFireId(fireId);
    }

    /**
     * 查询消防安全列表
     *
     * @param fireSafety 消防安全
     * @return 消防安全
     */
    @DataScope(userAlias = "u",deptAlias = "d")
    @Override
    public List<FireSafety> selectFireSafetyList(FireSafety fireSafety) {
        return fireSafetyMapper.selectFireSafetyList(fireSafety);
    }

    /**
     * 新增消防安全
     *
     * @param fireSafety 消防安全
     * @return 结果
     */
    @Override
    public int insertFireSafety(FireSafety fireSafety) {
        fireSafety.setCreateTime(DateUtils.getNowDate());
        fireSafety.setFireId(UUID.randomUUID().toString());
        return fireSafetyMapper.insertFireSafety(fireSafety);
    }

    /**
     * 修改消防安全
     *
     * @param fireSafety 消防安全
     * @return 结果
     */
    @Override
    public int updateFireSafety(FireSafety fireSafety) {
        fireSafety.setUpdateTime(DateUtils.getNowDate());
        return fireSafetyMapper.updateFireSafety(fireSafety);
    }

    /**
     * 批量删除消防安全
     *
     * @param fireIds 需要删除的消防安全主键
     * @return 结果
     */
    @Override
    public int deleteFireSafetyByFireIds(String[] fireIds) {
        return fireSafetyMapper.deleteFireSafetyByFireIds(fireIds);
    }

    /**
     * 删除消防安全信息
     *
     * @param fireId 消防安全主键
     * @return 结果
     */
    @Override
    public int deleteFireSafetyByFireId(String fireId) {
        return fireSafetyMapper.deleteFireSafetyByFireId(fireId);
    }
}
