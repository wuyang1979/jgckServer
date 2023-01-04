package com.ruoyi.common.core.mapper;

import java.util.List;

import com.ruoyi.common.core.domain.entity.FileInfo;

/**
 * 文件信息Mapper接口
 *
 * @author gubut
 * @date 2022-12-26
 */
public interface FileInfoMapper {
    /**
     * 查询文件信息
     *
     * @param fileId 文件信息主键
     * @return 文件信息
     */
    public FileInfo selectFileInfoByFileId(String fileId);

    /**
     * 查询文件信息列表
     *
     * @param fileInfo 文件信息
     * @return 文件信息集合
     */
    public List<FileInfo> selectFileInfoList(FileInfo fileInfo);

    /**
     * 新增文件信息
     *
     * @param fileInfo 文件信息
     * @return 结果
     */
    public int insertFileInfo(FileInfo fileInfo);

    /**
     * 修改文件信息
     *
     * @param fileInfo 文件信息
     * @return 结果
     */
    public int updateFileInfo(FileInfo fileInfo);

    /**
     * 删除文件信息
     *
     * @param fileId 文件信息主键
     * @return 结果
     */
    public int deleteFileInfoByFileId(String fileId);

    /**
     * 批量删除文件信息
     *
     * @param fileIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFileInfoByFileIds(String[] fileIds);


    public int emptyBusinessId(String businessId);

}
