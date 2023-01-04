package com.ruoyi.space.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.mapper.FileInfoMapper;
import com.ruoyi.common.core.domain.entity.FileInfo;
import com.ruoyi.common.core.service.IFileInfoService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 文件信息Service业务层处理
 *
 * @author gubut
 * @date 2022-12-26
 */
@Service
public class FileInfoServiceImpl implements IFileInfoService {
    @Autowired
    private FileInfoMapper fileInfoMapper;

    /**
     * 查询文件信息
     *
     * @param fileId 文件信息主键
     * @return 文件信息
     */
    @Override
    public FileInfo selectFileInfoByFileId(String fileId) {
        return fileInfoMapper.selectFileInfoByFileId(fileId);
    }

    /**
     * 查询文件信息列表
     *
     * @param fileInfo 文件信息
     * @return 文件信息
     */
    @Override
    public List<FileInfo> selectFileInfoList(FileInfo fileInfo) {
        return fileInfoMapper.selectFileInfoList(fileInfo);
    }

    /**
     * 新增文件信息
     *
     * @param fileInfo 文件信息
     * @return 结果
     */
    @Override
    public int insertFileInfo(FileInfo fileInfo) {
        fileInfo.setCreateTime(DateUtils.getNowDate());
        return fileInfoMapper.insertFileInfo(fileInfo);
    }

    /**
     * 修改文件信息
     *
     * @param fileInfo 文件信息
     * @return 结果
     */
    @Override
    public int updateFileInfo(FileInfo fileInfo) {
        fileInfo.setUpdateTime(DateUtils.getNowDate());
        return fileInfoMapper.updateFileInfo(fileInfo);
    }

    /**
     * 批量删除文件信息
     *
     * @param fileIds 需要删除的文件信息主键
     * @return 结果
     */
    @Override
    public int deleteFileInfoByFileIds(String[] fileIds) {
        return fileInfoMapper.deleteFileInfoByFileIds(fileIds);
    }

    /**
     * 删除文件信息信息
     *
     * @param fileId 文件信息主键
     * @return 结果
     */
    @Override
    public int deleteFileInfoByFileId(String fileId) {
        return fileInfoMapper.deleteFileInfoByFileId(fileId);
    }


    @Override
    @Transactional
    public AjaxResult editByBusinessId(String businessId, String[] fileIds) {

        //todo  绑定新的业务关系之前先将文件绑定清楚
        try {
            fileInfoMapper.emptyBusinessId(businessId);
            for (String fileId : fileIds) {
                FileInfo file = new FileInfo();
                file.setFileId(fileId);
                file.setBusinessId(businessId);
                fileInfoMapper.updateFileInfo(file);
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
        return AjaxResult.success();
    }
}
