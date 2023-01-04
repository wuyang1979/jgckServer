package com.ruoyi.common.core.domain.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class SysDeptAndUser {

    /**
     * 树id
     */
    private String id;

    /**
     * 树名称
     */
    private String label;

    /**
     * 是否用户
     */
    private Boolean isUser;

    /**
     * 下级集合
     */
    private List<SysDeptAndUser> children;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getUser() {
        return isUser;
    }

    public void setUser(Boolean user) {
        isUser = user;
    }

    public List<SysDeptAndUser> getChildren() {
        return children;
    }

    public void setChildren(List<SysDeptAndUser> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("label", getLabel())
                .append("isUser", getUser())
                .toString();
    }
}
