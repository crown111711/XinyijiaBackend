package com.xinyijia.backend.controller;

import com.xinyijia.backend.common.BaseConsant;
import com.xinyijia.backend.common.BusinessResponseCode;
import com.xinyijia.backend.domain.AttachmentInfo;
import com.xinyijia.backend.param.AttachmentInfluence;
import com.xinyijia.backend.param.BusinessResponse;
import com.xinyijia.backend.param.response.BaseResponse;
import com.xinyijia.backend.service.AttachmentService;
import com.xinyijia.backend.utils.FilePathUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/16 11:28
 */
@Api("附件管理相关Api")
@RestController
@RequestMapping(value = BaseConsant.BASE_PATH + "/attachment")
@CrossOrigin("*")
@Slf4j
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @ApiOperation(value = "文件上传", notes = "")
    @RequestMapping(value = "uploadFile", method = RequestMethod.POST)
    public String uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request,
                             @RequestParam(name = "type", required = false) String type,
                             @RequestParam(name = "accessToken", required = false) String accessToken,
                             @RequestParam(name = "category", required = false) String category) throws Exception {
//,
        System.out.println(request.getSession().getId());
        System.out.println(type);
        //保存附件
        if (!file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();
            long pre = System.currentTimeMillis();
            String fileName = pre + originalFilename;
            String filePath = FilePathUtil.getImagePath(fileName);
            File destFile = new File(FilePathUtil.getRootPath(), fileName);
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            //request.setAttribute("fileName", fileName);
            file.transferTo(new File(filePath));

            int index = fileName.indexOf(".");
            AttachmentInfo attachment = new AttachmentInfo();
            attachment.setOldName(originalFilename);
            attachment.setCategory(category);
            attachment.setFileSize(String.valueOf(file.getSize()));
            attachment.setFileAddress(filePath);
            attachment.setFileName(fileName);
            attachment.setFileType(fileName.substring(index + 1));
            attachmentService.saveAttachment(attachment);
            if (!StringUtils.isEmpty(type)) {
                AttachmentInfluence influence = new AttachmentInfluence();
                influence.setFileName(fileName);
                influence.setType(type);
                influence.setAccessToken(accessToken);
                attachmentService.catToRelative(influence);
                attachmentService.catToRelative(influence);
            }
            return BaseConsant.SHOW_IMAGE + fileName;
        }
        return null;
    }


    @ApiOperation(value = "文件上传", notes = "")
    @RequestMapping(value = "uploadMultipartFile", method = RequestMethod.POST)
    public String uploadMultiFile(HttpServletRequest request, HttpServletResponse response)
            throws IllegalStateException, IOException {
        request.getSession().getServletContext();
        CommonsMultipartResolver multiResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (multiResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iterator = multiRequest.getFileNames();
            ArrayList<String> listNames = new ArrayList<>();
            while (iterator.hasNext()) {

                MultipartFile file = multiRequest.getFile(iterator.next());
                if (file != null) {
                    String fileName = file.getOriginalFilename();
                    String originName = file.getOriginalFilename();

                    String path = FilePathUtil.getRootPath();
                    System.out.println(path);
                    long pre = System.currentTimeMillis();
                    fileName = pre + fileName;
                    File filePath = new File(path, fileName);
                    if (!filePath.getParentFile().exists()) {
                        filePath.getParentFile().mkdirs();
                    }
                    listNames.add(fileName);

                    file.transferTo(new File(FilePathUtil.getImagePath(fileName)));

                    //通过request取得参数
                    String accessToken = request.getParameter("accessToken");
                    String type = request.getParameter("type");
                    String category = request.getParameter("category");

                    int index = fileName.indexOf(".");
                    System.out.println(index);
                    //String fileType=fileName.substring(index);
                    AttachmentInfo attachment = new AttachmentInfo();
                    attachment.setOldName(originName);
                    attachment.setFileSize(String.valueOf(file.getSize()));
                    attachment.setFileAddress(path);
                    attachment.setFileName(fileName);
                    attachment.setFileType(fileName.substring(index + 1));
                    attachment.setCategory(category);
                    attachmentService.saveAttachment(attachment);

                    if (!StringUtils.isEmpty(type)) {
                        AttachmentInfluence influence = new AttachmentInfluence();
                        influence.setFileName(fileName);
                        influence.setType(type);
                        influence.setAccessToken(accessToken);
                        attachmentService.catToRelative(influence);
                    }
                }
            }
            //request.setAttribute("listNames", listNames);
        }
        return null;

    }

    @ApiOperation(value = "文件展示，解决vue图片展示路径问题", notes = "")
    @RequestMapping(value = "/showImage/{imageName}", method = RequestMethod.GET)
    public String showFile(@PathVariable String imageName, HttpServletResponse response) throws Exception {
        if (StringUtils.isEmpty(imageName)) {
            return null;
        }
        if (imageName.startsWith(BaseConsant.SHOW_IMAGE)) {
            imageName = imageName.substring(imageName.indexOf(BaseConsant.SHOW_IMAGE) + 1);
        }
        ServletOutputStream outputStream = null;
        FileInputStream in = null;

        try {
            in = new FileInputStream(new File(FilePathUtil.getImagePath(imageName)));
            response.setContentType("multipart/form-data");
            outputStream = response.getOutputStream();
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = in.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
        } catch (Exception e) {
            log.error("传输图片错误", e);
        } finally {
            in.close();
            outputStream.close();
        }
        return null;
    }


    @ApiOperation(value = "文件下载", notes = "")
    @RequestMapping(value = "/getDownFile", method = RequestMethod.GET)
    public BaseResponse getDownFile() {
        try {
            BaseResponse baseResponse = BaseResponse.success();
            baseResponse.setData(attachmentService.getDownFile());
            return baseResponse;
        } catch (Exception e) {
            return new BaseResponse(BusinessResponseCode.ERROR);
        }
    }

    @ApiOperation(value = "文件删除", notes = "")
    @RequestMapping(value = "/deleteFile", method = RequestMethod.GET)
    public BaseResponse deleteFile(@RequestParam("fileName")String fileName){
        try{
            BaseResponse baseResponse = BaseResponse.success();
            attachmentService.deleteAttachment(fileName);
            return baseResponse;
        }catch (Exception e){
            return new BaseResponse(BusinessResponseCode.ERROR);
        }
    }
//updateFile
    @ApiOperation(value = "文件修改", notes = "")
    @RequestMapping(value = "/updateFile", method = RequestMethod.POST)
    public BaseResponse updateFile(@RequestBody AttachmentInfo attachmentInfo){
        try{
            BaseResponse baseResponse = BaseResponse.success();
            attachmentService.updateFile(attachmentInfo);
            return baseResponse;
        }catch (Exception e){
            return new BaseResponse(BusinessResponseCode.ERROR);
        }
    }


}
