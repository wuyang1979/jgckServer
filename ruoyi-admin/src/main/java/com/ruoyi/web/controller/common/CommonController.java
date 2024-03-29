package com.ruoyi.web.controller.common;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.FileInfo;
import com.ruoyi.common.core.service.IFileInfoService;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.file.ImageUtils;
import com.ruoyi.framework.config.ServerConfig;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 通用请求处理
 *
 * @author ruoyi
 */
@Api(tags = "通用请求")
@RestController
@RequestMapping("/common")
public class CommonController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private IFileInfoService fileInfoService;

    @Value("${ruoyi.profile-img}")
    private String profileImg;

    @Value("${ruoyi.url-prefix}")
    private String urlPrefix;

    private static final String FILE_DELIMETER = ",";

    private static final String VIDEO_ROUTE = "/www/server/nginx/html/videos/";

//    private static final String VIDEO_ROUTE = "D:/1/nginx-1.7.11.3-Gryphon/html/videos/";

    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete   是否删除
     */
    @GetMapping("/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (!FileUtils.checkAllowDownload(fileName)) {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = RuoYiConfig.getDownloadPath() + fileName;

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete) {
                FileUtils.deleteFile(filePath);
            }
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求（单个）
     */
    @PostMapping("/upload")
    @Transactional
    public AjaxResult uploadFile(MultipartFile file) throws Exception {
        try {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
//            String src = relativeProfile + fileName;
            AjaxResult ajax = AjaxResult.success();
			    //todo 保存文件信息
            FileInfo fileInfo=new FileInfo();
            fileInfo.setCreateBy(getUsername());
            fileInfo.setFileId(UUID.randomUUID().toString());
            fileInfo.setFileName(fileName);
            fileInfo.setNewFileName(FileUtils.getName(fileName));
            fileInfo.setOriginalFileName(file.getOriginalFilename());
            fileInfo.setUrl(url);
            fileInfoService.insertFileInfo(fileInfo);
            ajax.put("url", url);
//            ajax.put("src", src);
            ajax.put("fileName", fileName);
            ajax.put("newFileName", FileUtils.getName(fileName));
            ajax.put("originalFilename", file.getOriginalFilename());
			ajax.put("id",fileInfo.getFileId());
            return ajax;
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 上传图片
     */
    @PostMapping("/upload/img")
    public AjaxResult uploadImg(MultipartFile file) throws Exception {
        try {
            // 上传图片路径
            String filePath = profileImg;
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.uploadImg(filePath, file);
//            String url = urlPrefix + "/images" + fileName;
            String url = urlPrefix  + "/image" + fileName;
            String src = urlPrefix  + "/image" + fileName;
            AjaxResult ajax = AjaxResult.success();
            // 保存文件信息
            FileInfo fileInfo=new FileInfo();
            fileInfo.setCreateBy(getUsername());
            fileInfo.setFileId(UUID.randomUUID().toString());
            fileInfo.setFileName(fileName);
            fileInfo.setNewFileName(FileUtils.getName(fileName));
            fileInfo.setOriginalFileName(file.getOriginalFilename());
            fileInfo.setUrl(url);
            fileInfoService.insertFileInfo(fileInfo);

            ajax.put("url", url);
            ajax.put("src", src);
            ajax.put("fileName", fileName);
            ajax.put("newFileName", FileUtils.getName(fileName));
            ajax.put("originalFilename", file.getOriginalFilename());
            ajax.put("id",fileInfo.getFileId());
			
            return ajax;
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 批量上传图片
     */
    @PostMapping("/uploads")
    public AjaxResult uploadFiles(List<MultipartFile> files) throws Exception {
        try {
            // 上传文件路径
            String filePath = profileImg;
            List<String> urls = new ArrayList<>();
            List<String> fileNames = new ArrayList<>();
            List<String> newFileNames = new ArrayList<>();
            List<String> originalFilenames = new ArrayList<>();
            for (MultipartFile file : files) {
                // 上传并返回新文件名称
                String fileName = FileUploadUtils.uploadImg(filePath, file);
//                String url = urlPrefix + "/images" + fileName;
                String url = urlPrefix  + fileName;
                urls.add(url);
                fileNames.add(fileName);
                newFileNames.add(FileUtils.getName(fileName));
                originalFilenames.add(file.getOriginalFilename());
            }
            AjaxResult ajax = AjaxResult.success();
            ajax.put("urls", StringUtils.join(urls, FILE_DELIMETER));
            ajax.put("fileNames", StringUtils.join(fileNames, FILE_DELIMETER));
            ajax.put("newFileNames", StringUtils.join(newFileNames, FILE_DELIMETER));
            ajax.put("originalFilenames", StringUtils.join(originalFilenames, FILE_DELIMETER));
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 上传视频
     */
    @PostMapping("/uploadVideo")
    public AjaxResult uploadVideo(MultipartFile file) {
        try {
            //获取文件后缀
            String format = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
            // 重构文件名称
            String fileName = ImageUtils.randomFileName() + "." + format;
            //保存视频
            String fileRoute = VIDEO_ROUTE + fileName;
            File videoFile = new File(fileRoute);
            if (!videoFile.exists()) {
                videoFile.createNewFile();
            }
            file.transferTo(videoFile);

            String videoUrl = urlPrefix + "/videos/" + fileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("videoUrl", videoUrl);
            return ajax;
        } catch (Exception e) {
            log.error(e.toString());
            return AjaxResult.error("视频上传错误");
        }
    }


    /**
     * 本地资源通用下载
     */
    @GetMapping("/download/resource")
    public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {
            if (!FileUtils.checkAllowDownload(resource)) {
                throw new Exception(StringUtils.format("资源文件({})非法，不允许下载。 ", resource));
            }
            // 本地资源路径
            String localPath = RuoYiConfig.getProfile();
            // 数据库资源地址
            String downloadPath = localPath + StringUtils.substringAfter(resource, Constants.RESOURCE_PREFIX);
            // 下载名称
            String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, downloadName);
            FileUtils.writeBytes(downloadPath, response.getOutputStream());
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }


    @GetMapping("/download/{id}")
    public void downloadById(@PathVariable String id,HttpServletResponse response,HttpServletRequest request){
        try {
            FileInfo fileInfo = fileInfoService.selectFileInfoByFileId(id);
            String fileName=fileInfo.getFileName();
            if (!FileUtils.checkAllowDownload(fileName)) {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = profileImg + fileName;

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            FileUtils.writeBytes(filePath, response.getOutputStream());
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }
}