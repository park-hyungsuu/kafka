package com.hyungsuu.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtil {

	public FileUtil() {

	}

	/**
	 * 파일및 디렉토리 목록를 가져온다
	 * @param srcDir        대상 디렉토리
	 * @param subDirFlag  하위 디렉토리 포함여부
	 * @param onlyFile      파일만 검색여부
	 * @return
	 * 예제	현재 디렉토리 파일만 가져올 경우
     *			 result = getList(exchageDir, false, true);
     *			현 디렉토리 하위 폴더 전체 파일만(디렉토리 미포함) 가져올 경우
     *			result = getList(exchageDir, true, true);
     *			현 디렉토리 하위 폴더 전체(디렉토리 포함) 가져올 경우
	 */
	public static ArrayList fileList2(String srcDir ,boolean subDirFlag, boolean onlyFile) {
	
		ArrayList<String[]> resultList = new ArrayList<String[]>();
		String fileInfo[] = null;
		List subResultList = null;
		
		if (srcDir == null)
			return resultList;

		//디렉토리의 파일 및 디렉토리 목록
		File dir = new File(srcDir);
		String fileList [] = dir.list();
		
//		//디렉토리 및 파일 가져오기
		File contentDir = null;
		String dirName = null;
		
		if(fileList != null && fileList.length > 0){
//			dirList = new ArrayList();
			for(int i=0; i<fileList.length; i++){
				if(fileList[i] != null && !"".equals(fileList[i])){
					
//					파일인지 디렉토리인지 구분
					contentDir = new File(srcDir+ File.separator + fileList[i]);
					dirName = srcDir+ File.separator + fileList[i];
//					디렉토리인 경우					
					if(contentDir.isDirectory()) {
// 						파일만 가져올경우에는 디렉토리 포함 안함
						if (onlyFile == false) {
							fileInfo = new String[2];
							fileInfo[0] ="D";
							fileInfo[1] = dirName;
							resultList.add(fileInfo);
						}
// 						하위 디렉토리 검색시
						if (subDirFlag == true) {
							subResultList = fileList2(dirName, true, onlyFile); 
							resultList.addAll(subResultList);
						}
//					파일인 경우						
					} else {
						fileInfo = new String[2];
						fileInfo[0] ="F";
						fileInfo[1] = dirName;
						resultList.add(fileInfo);
					}
					contentDir = null;
				}
			}
		}
		return resultList; 
	}
	
	

	/**
	 * 파일및 디렉토리 목록를 가져온다
	 * @param srcDir        대상 디렉토리
	 * @param subDirFlag  하위 디렉토리 포함여부
	 * @param onlyFile      파일만 검색여부
	 * @return
	 * 예제	현재 디렉토리 파일만 가져올 경우
     *			 result = getList(exchageDir, false, true);
     *			현 디렉토리 하위 폴더 전체 파일만(디렉토리 미포함) 가져올 경우
     *			result = getList(exchageDir, true, true);
     *			현 디렉토리 하위 폴더 전체(디렉토리 포함) 가져올 경우
	 */
	public static List<String> fileList(String srcDir ,boolean subDirFlag, boolean onlyFile) {
	
		List<String> resultList = new ArrayList<String>();
		List subResultList = null;
		
		if (srcDir == null)
			return resultList;

		//디렉토리의 파일 및 디렉토리 목록
		File dir = new File(srcDir);
		String fileList [] = dir.list();
		
//		//디렉토리 및 파일 가져오기
		File contentDir = null;
		String dirName = null;
		
		if(fileList != null && fileList.length > 0){
//			dirList = new ArrayList();
			for(int i=0; i<fileList.length; i++){
				if(fileList[i] != null && !"".equals(fileList[i])){
					
//					파일인지 디렉토리인지 구분
					contentDir = new File(srcDir+ File.separator + fileList[i]);
					dirName = srcDir+ File.separator + fileList[i];
//					디렉토리인 경우					
					if(contentDir.isDirectory()) {
// 						파일만 가져올경우에는 디렉토리 포함 안함
						if (onlyFile == false) {
							resultList.add(dirName);
						}
// 						하위 디렉토리 검색시
						if (subDirFlag == true) {
							subResultList = fileList(dirName, true, onlyFile); 
							resultList.addAll(subResultList);
						}
//					파일인 경우						
					} else {
						resultList.add(dirName);
					}
				}
			}
		}
		return resultList; 
	}	
	public static void readLine(String filePath ,String charSet) {
		
	    BufferedReader br = null;
	    String temStr = new String();
	    String temstr1[] = null;
	    int i=1;
	    try {
	         br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), charSet));
	         while ((temStr = br.readLine()) != null) {
//	             temstr1 = temStr.split("\t");
//	        	 주로 CSV 파일을 읽었어 DB 저장시
	 			System.out.println( i + "번째 라인 ->"+temStr); 	 
	 			i++;
	         } 

	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
	
	public static String readFull(String filePath) throws IOException {


		FileInputStream fis = null;
		byte[] buffer = null;

		try {
			File file = new File(filePath);
			fis = new FileInputStream(file);
			buffer = new byte[fis.available()];
			fis.read(buffer, 0, fis.available());
		} catch (IOException e) {
			throw e;
		} finally {
			if (fis != null)
				fis.close();
		}

		return new String(buffer);
	}

	// buffer 사이즈 바꿈 읽어옴. 주로 파일 다운로드시 많이 사용..
	public static void readBuffer(String filePath) throws IOException {
		FileInputStream fis = null;
//		OutputStream outs = null;
		byte b[] = new byte[1024];
		byte cb[] = null;


		try {
			File file = new File(filePath);
			fis = new FileInputStream(file);
//			outs = response.getOutputStream();
			int read = 0;

			while ((read = fis.read(b)) != -1) {
//				outs.write(b,0,read);
				cb = new byte[read];
				System.arraycopy(b, 0, cb, 0, read);
	 			System.out.println(read+"번째 버퍼 ->"+new String(cb)); 
			}

		} catch (IOException e) {
				throw e;
		} finally {
			try {
//				outs.flush();
//				outs.close();
				fis.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 파일 삭제 함수
	 * @param file
	 * @return
	 */
	public static boolean deleteFile(File file) throws Exception {
		if(file == null){
			return false;
		}
		
		if(file.isDirectory()){
			FileUtils.cleanDirectory(file);
		}
		return file.delete();
	}
	

	/**
	 * 파일 삭제 함수
	 * @param filePath
	 * @return
	 */
	public static boolean deleteFile(String filePath) throws Exception {
		if(filePath == null){
			return false;
		}
		File file = new File(filePath);
		
		if(file.isDirectory()){
			FileUtils.cleanDirectory(file);
		}
		return file.delete();
	}
	
	/**
	 * 파일 이동을 한다.
	 * @param srcFileName 이동 소스 파일명
	 * @param distFileName 이동 목적지 파일명
	 * @return 결과 여부 성공 true , 실패 false
	 * @throws IOException
	 */
	public static boolean moveFile(String srcFileName, String distFileName) throws Exception{
	
		boolean result = false;
		
		if(srcFileName == null){
			throw new NullPointerException("Source must not be null");
		}
		
		if(distFileName == null){
			throw new NullPointerException("Destination must not be null");
		}
		
		File srcFile = new File(srcFileName);
		File destFile = new File(distFileName);
		
		if(!srcFile.exists()){
			throw new FileNotFoundException("Source " + srcFile + "does not exist");
		}
		
		if(srcFile.isDirectory()){
			throw new IOException("Source " + srcFile + "is a directory");
		}
		
		if(destFile.exists()){
			throw new FileNotFoundException("Destination " + destFile + "already exist");
		}
		
		if(destFile.isDirectory()){
			throw new IOException("Destination " + destFile + "is a directory");
		}
		
		boolean rename = srcFile.renameTo(destFile);
		
		if(!rename){
			FileUtils.copyFile(srcFile, destFile);
			
			if(!srcFile.delete()){
				FileUtils.deleteDirectory(destFile);
				
				throw new IOException("Failed to delete original file " + srcFile +" after copy to " + destFile);
			}
		}
		
		result = true;
		
		return result;	
	}
	
	/**
	 * 디렉토리 복사 이동 
	 * 
	 * @param srcDirName 소스 디렉토리 경로명
	 * @param distDirName 목적지 디렉토리 경로명
	 * @return
	 */
	@SuppressWarnings("unused")
	public static boolean moveDir(String srcDirName, String distDirName) throws Exception{
		boolean result = false;
		
		File srcDir = new File(srcDirName);
		File distDir = new File(distDirName, srcDir.getName());
		
		if(srcDir == null){
			throw new NullPointerException("Source must not be null");
		}
		
		if(distDir == null){
			throw new NullPointerException("Source must not be null");
		}
		
		if(!srcDir.exists()){
			throw new FileNotFoundException("Source " + srcDir + "does not exist");
		}
		
		if(!srcDir.isDirectory()){
			throw new IOException("Source " + srcDir + "is not a directory");
		}
		
//		if(distDir.exists()){
//			throw new IOException("Destination " + distDir + "already exists");
//		}
		
		boolean rename = srcDir.renameTo(distDir);
		
		if(!rename){
			FileUtils.copyDirectory(srcDir, distDir);
			FileUtils.deleteDirectory(srcDir);
			
			if(srcDir.exists()){
				throw new IOException("Failed to delete original directory " + srcDir + "after copy to " + distDir);
			}
		}
		
		result = true;
	
		return result;
	}
	
	/**
	 * 특정 디렉토리를 이동한다.
	 * 
	 * @param srcDirName  이동 소스 디렉토리 경로명
	 * @param distDirName 이동 목적지 디렉토리 경로명
	 * @param createDistDir 디렉토리 생성 여부
	 * @return 
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	public static boolean  moveDirToDir(String srcDirName, String distDirName, boolean createDistDir) throws Exception{
		boolean result = false;
		
		File srcDir = new File(srcDirName);
		File distDir = new File(distDirName);
		
		if(srcDir == null){
			throw new NullPointerException("Source must not be null");
		}
		
		if(distDir == null){
			throw new NullPointerException("Source must not be null");
		}
		
		if(!distDir.exists() && createDistDir){
			distDir.mkdirs();
		}
		
		if(!distDir.exists()){
			throw new FileNotFoundException("Destination directory " + distDir + " does not exist (createDistDir=" + createDistDir + ")");
		}
		
		if(!distDir.isDirectory()){
			throw new IOException("Destination " + distDir + " is not a directory");
		}
		
		result = moveDir(srcDirName, distDirName);
		return result;
	}
	
	

	/* 파일카피 */
	public static String fileCopy(String src, String tgt) throws Exception {
		String file_id = "";
		File srcFile = new File(src);
		
		FileInputStream fios = null;
		FileOutputStream fouts = null;
		BufferedInputStream bin = null;
		BufferedOutputStream bout = null;
		
		try {
			fios = new FileInputStream(srcFile);
			fouts = new FileOutputStream(tgt);
			
			bin = new BufferedInputStream(fios);
			bout = new BufferedOutputStream(fouts);
			
			int bytesRead = 0;
			byte[] buffer = new byte[1024];
			while ((bytesRead = bin.read(buffer, 0, 1024)) != -1 ) {
				bout.write(buffer, 0, bytesRead);
			}
			if(tgt.indexOf("/")>=0) file_id = tgt.substring(tgt.lastIndexOf("/")+1,tgt.length());			
		} catch (Exception e) {
			e.printStackTrace();
			//throw e;
		} finally {
			 try {
				 fouts.close();
			 } catch (IOException ie) {}
			try {
				 fios.close();
			 } catch (IOException ie) {}
			 
			 try {
				 bin.close();
			 } catch (IOException ie) {}
			 
			 try {
				 bout.close();
			 } catch (IOException ie) {}
			 
		}
		return file_id;
	}
	
	
	public static void main(String[] args) {
		File file = new File("C:/prj32/workspace/dbsample/src/main/java/com/aeju/util/file/aaaatest.java");
		FileWriter fw;
		try {
			fw = new FileWriter(file, true);

	        fw.write("aaa");

	        fw.flush();



	        // 객체 닫기

	        fw.close(); 


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        

        // 파일안에 문자열 쓰기
         


//		try {
//			String exchageDir 	= "G:\\";
//			List result = null;
//
//			LogUtil logUtil = new LogUtil();		
////			현재 디렉토리 파일만 가져올 경우
////			result = getList(exchageDir, false, true);
////			현 디렉토리 하위 폴더 전체 파일만(디렉토리 미포함) 가져올 경우
////			result = getList(exchageDir, true, true);
////			현 디렉토리 하위 폴더 전체(디렉토리 포함) 가져올 경우
//			System.out.println("파일여부:");
//			result = fileList2(exchageDir, true, false);
//
//			for(int i=0; i<result.size(); i++){
//				logUtil.logWriter("C:\\prj32\\data\\log\\mail_"+Util.getFmtDate("yyyyMMdd")+".log","파일여부:"+ ((java.lang.String[]) result.get(i))[0]+" 파일명" +((java.lang.String[]) result.get(i))[1]);
//				System.out.println("파일여부:"+ ((java.lang.String[]) result.get(i))[0]+" 파일명" +((java.lang.String[]) result.get(i))[1]);
//			}
//			
////			result = fileList(exchageDir, true, false);
////
////			for(int i=0; i<result.size(); i++){
////
////				System.out.println(" 파일명" + result.get(i));
////			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		
//		
////		 fileReadByLine("C:\\prj32\\data\\log\\tomcat.log", "EUC_KR");
//		
//		
////		try {
////			readBuffer("C:\\prj32\\data\\eclipse_code_format.xml");
////
////
//////		String str = readFull("C:\\prj32\\data\\eclipse_code_format.xml");
//////			System.out.println( "번째 라인 ->"+str); 	 
////	} catch (Exception e) {
////		e.printStackTrace();
////	}
	}

}
