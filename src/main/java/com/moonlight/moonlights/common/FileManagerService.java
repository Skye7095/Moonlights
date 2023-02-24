package com.moonlight.moonlights.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class FileManagerService {
	
	public static final String FILE_UPLOAD_PATH = "D:\\java web develop\\springProject\\upload\\moonlights";
	
	private static Logger logger = LoggerFactory.getLogger(FileManagerService.class);
	
	public static String saveFile(int userId, MultipartFile file) {
		
		String directoryName = "/" + userId + "_" + System.currentTimeMillis() + "/";
		
		String directoryPath = FILE_UPLOAD_PATH + directoryName;
		File directory = new File(directoryPath);
		if(directory.mkdir() == false) {
			// 디렉토리 생성 실패 로그 추가
			logger.error("savefile: 디렉토리 생성 실패" + directoryPath);
			return null;
		}
		
		
		try {
			byte[] bytes = file.getBytes();
			
			String filePath = directoryPath + file.getOriginalFilename();
			Path path = Paths.get(filePath);
			Files.write(path, bytes);
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
			return null;
		}
		
		
		
		return "/moonlights" + directoryName + file.getOriginalFilename();
		
	}
	
	// 파일 삭제 메소드
		public static boolean removeFile(String filePath) {
			// 삭제 경로 /memo를 제거하고
			// 실제 파일 저장 경로에 이어 붙여 준다.
			String realFilePath = FILE_UPLOAD_PATH + filePath.replace("/moonlights", "");
			Path path = Paths.get(realFilePath);
			
			// 파일이 존재하는지 먼저 확인하고 삭제해야함
			if(Files.exists(path)) {
				try {
					Files.delete(path);
				} catch (IOException e) {
					
					e.printStackTrace();
					
					return false;
				}
			}
			
			// 파일을 저장하는 폴더도 같이 삭제해야함
			// 디렉토리 경로
			Path dirPath = path.getParent();
			// 디렉토리 존재여부
			if(Files.exists(dirPath)) {
				try {
					Files.delete(path);
				} catch (IOException e) {
					
					e.printStackTrace();
					
					return false;
				}
			}
			
			return true;
		}
}
