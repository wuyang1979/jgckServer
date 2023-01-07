package com.ruoyi.common.core.domain.vo;


import com.alibaba.fastjson2.JSONObject;
import lombok.Data;

@Data
public class RentConfigVo {
    /**
     * 系统配置键值
     */
    private String configKey;

    /**
     * 系统配置参数
     */
    private JSONObject configValue;

}
