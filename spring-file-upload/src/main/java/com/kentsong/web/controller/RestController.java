package com.kentsong.web.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kentsong.web.support.collection.RequestParams;
import com.kentsong.web.support.file.FileUtil;

@Controller
public class RestController extends BaseController {
	
	//上傳路徑
	private final String FILE_PATH = "D://test/";
	@Autowired
	protected ServletContext context;

	private static final Logger logger = LoggerFactory.getLogger(RestController.class);
  
	@RequestMapping({ "/", "/index"})
	public String index(ModelMap modelMap) {
		return "index";
	}
	
    //URL: http://localhost:8080/spring-file-upload/download?fileName=xxx.zip
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(HttpServletRequest request, HttpServletResponse response) {
		try {
			File file = new File(FILE_PATH + request.getParameter("fileName"));
			FileCopyUtils.copy(FileUtils.readFileToByteArray(file), response.getOutputStream());
		} catch (IOException e) {
			logger.error("IOException error: ", e);
		}
	}

  
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody boolean upload(HttpServletRequest request) {

		RequestParams requestParams = this.loadFileParams(request);
		FileItem fileItem = requestParams.getFileParameter("file1Name");
		logger.info("fileItem.getFieldName() = " + fileItem.getFieldName());
		logger.info("fileItem.getName() = " + fileItem.getName());
		logger.info("fileItem.getContentType() = " + fileItem.getContentType());
		logger.info("fileItem.getSize() = " + fileItem.getSize());
		// logger.info(fileItem.getInputStream());

		String fullPath = context.getRealPath("/WEB-INF/upload/"+ fileItem.getName());
		logger.info("fullPath = " + fullPath);
		FileUtil.uploadFile(fileItem, FILE_PATH + fileItem.getName());

		return true;
	}
	
  		
}

