package com.kentsong.web.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kentsong.web.support.collection.RequestParams;

public class BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	protected RequestParams loadParameters(HttpServletRequest request) {
		RequestParams params = new RequestParams();
		Enumeration enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String paramKey = (String) enumeration.nextElement();
			params.put(paramKey, request.getParameterValues(paramKey));

			String log = "key:" + paramKey + ", value:";
			String[] paramValues = request.getParameterValues(paramKey);
			for (int i = 0; i < paramValues.length; i++) {
				log = log + paramValues[i] + ", ";
			}
			logger.debug(log);
		}

		return params;
	}
	
	protected RequestParams loadFileParams(HttpServletRequest request) {
		int fileSizeMax = 5000 * 1024; 	// 5000KB default
		return loadFileParams(request, fileSizeMax);
	}
	
	protected RequestParams loadFileParams(HttpServletRequest request, int fileSizeMax) {
		try {
			if(ServletFileUpload.isMultipartContent(request)){
				logger.info("loadFileParameters....");
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setSizeMax(fileSizeMax);
				Map<String, List<FileItem>> paramFileMap = new HashMap<String, List<FileItem>>();
				Map<String, List<String>> paramMap = new HashMap<String, List<String>>();
				
				List items = upload.parseRequest(request);
				
				for(int i=0;i<items.size();i++){
					FileItem item = (FileItem)items.get(i);
					if(!item.isFormField()){
						logger.info("is File....");
						if(paramFileMap.containsKey(item.getFieldName())){
							paramFileMap.get(item.getFieldName()).add(item);
						} else{
							List<FileItem> list = new ArrayList<FileItem>();
							list.add(item);
							paramFileMap.put(item.getFieldName(),list);
						}
						
					}else{
						logger.info("is params....");
						if(paramMap.containsKey(item.getFieldName())){
							List<String> list = paramMap.get(item.getFieldName());
							list.add(new String(item.getString().getBytes("iso-8859-1"),"UTF-8"));
						} else{
							List<String> list = new ArrayList<String>();
							list.add(new String(item.getString().getBytes("iso-8859-1"),"UTF-8"));
							paramMap.put(item.getFieldName(), list);
						}
					}
				}
				logger.info("uploadFiles = " + paramFileMap.size());
				
				RequestParams params = new RequestParams();
				params.setParamFileMap(paramFileMap);
				Iterator<String> it = paramMap.keySet().iterator();
				while(it.hasNext()){
					String key = it.next();
					List<String> list = paramMap.get(key);
					String[] strArray = new String[list.size()]; 
					params.put(key, list.toArray(strArray));
				}
				
				return params;
			} else{
				logger.info("loadFileParams fail!");
				throw new RuntimeException("loadFileParams not MultipartContent");
			}
		}
		catch (Exception e){
			logger.error("",e);
			throw new RuntimeException(e);
		}
		
		
	}

}
