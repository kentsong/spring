package com.kentsong.web.support.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 檔案處理單元
 * @author 635_kentsong
 */
public class FileUtil {

	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	public static void uploadFile(FileItem fileItem, String fileDir, String fileName) {
		uploadFile(fileItem, fileDir+fileName);
		
	}
	
	public static void uploadFile(FileItem fileItem, String filePath) {
		try {
			logger.info("上傳路徑 = " + filePath);
			fileItem.write(new File(filePath));
		} catch (FileNotFoundException e) {
			logger.error("uploadFile FileNotFoundException: ", e);
			throw new RuntimeException(e);
		} catch (IOException e) {
			logger.error("uploadFile IOException: ", e);
			throw new RuntimeException(e);
		} catch (Exception e){
			logger.error("uploadFile Exception: ", e);
			throw new RuntimeException(e);
		}
		
	}
	
	
	
	
}
