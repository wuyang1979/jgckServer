package com.ruoyi.activity.domain.dto;

import com.ruoyi.common.annotation.Excel;

import java.time.LocalDateTime;

/**
 * 活动是否报名对象 t_activity_info DTO
 *
 * @author gubut
 * @date 2022-12-22
 */
public class ActivityPersonDto {

    private String userId;

    @Excel(name = "人员名称")
    private String nickName;

    @Excel(name = "手机号码")
    private String phoneNumber;

    @Excel(name = "人员角色", width = 30)
    private String roleName;

    @Excel(name = "性别", readConverterExp = "0=男,1=女,2=未知")
    private Long sex;

    @Excel(name = "人员部门")
    private String deptName;

    @Excel(name = "报名时间", width = 35, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime signTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Long getSex() {
        return sex;
    }

    public void setSex(Long sex) {
        this.sex = sex;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public LocalDateTime getSignTime() {
        return signTime;
    }

    public void setSignTime(LocalDateTime signTime) {
        this.signTime = signTime;
    }
}
