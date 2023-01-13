package com.ruoyi.brand.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 品牌中心基本信息对象 t_brand_center
 *
 * @author gubut
 * @date 2023-01-13
 */
public class BrandCenter extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 品牌id
     */
    private String brandId;

    /**
     * 文字
     */
    @Excel(name = "文字")
    @ApiModelProperty("文字")
    private String brandContent;

    /**
     * 视频
     */
    @Excel(name = "视频")
    @ApiModelProperty("视频")
    private String brandVideoUrl;

    /**
     * 公益形象
     */
    @Excel(name = "公益形象")
    @ApiModelProperty("公益形象")
    private String publicWelfare;

    /**
     * 空间名称
     */
    @Excel(name = "空间名称")
    @ApiModelProperty("空间名称")
    private String spaceId;

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandContent(String brandContent) {
        this.brandContent = brandContent;
    }

    public String getBrandContent() {
        return brandContent;
    }

    public void setBrandVideoUrl(String brandVideoUrl) {
        this.brandVideoUrl = brandVideoUrl;
    }

    public String getBrandVideoUrl() {
        return brandVideoUrl;
    }

    public void setPublicWelfare(String publicWelfare) {
        this.publicWelfare = publicWelfare;
    }

    public String getPublicWelfare() {
        return publicWelfare;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public String getSpaceId() {
        return spaceId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("brandId", getBrandId())
                .append("brandContent", getBrandContent())
                .append("brandVideoUrl", getBrandVideoUrl())
                .append("publicWelfare", getPublicWelfare())
                .append("spaceId", getSpaceId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
