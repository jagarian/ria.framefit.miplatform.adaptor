package lotte.fw.miplatform.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.framework.property.PropertyService;
import lotte.fw.exception.LotteRuntimeException;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
* @Class Name : BusinessException.java
* @Description : File upload/download 관련한 util을 제공한다.
 ** @Modification Information  
* @
* @  수정일      수정자              수정내용
* @ ---------	---------	-------------------------------
* @ 2012.09.10	최초생성
* 
* @author 
* @since 2012.09.10
* @version 1.0
* @see
* 
*  Copyright (C) by LDCC All right reserved.
*/
public class FileService { // NOPMD by gunhlee on 09. 11. 5 오후 2:53
    
    private static final Logger log = Logger.getLogger(FileService.class); // NOPMD by gunhlee on 09. 11. 2 오전 10:21
    
    //file root를  속성으로 가지고 있는 propertyMain
    private PropertyService propertiesService;
    
    public void setPropertiesService(PropertyService propertiesService) {
        this.propertiesService = propertiesService;
    }
    
    /**
     * miplatform httpFile로 전송된 내용을 파일로 저장한다.
     * @param request
     * @param path : 서버에 저장할 path
     * @param filename : 원본 파일명
     * @return 신규 파일명
     * @throws Exception
     */
    public String saveMipUploadedFile(HttpServletRequest request, 
                                    String path,
                                    String filename) throws Exception {
        
        long uploadMaxSize = propertiesService.getLong("upload.size.max", 1000000);
        
        return saveMipUploadedFile(request, path, filename, uploadMaxSize);
        
    }
    
    
    /**
     * miplatform httpFile로 전송된 내용을 파일로 저장한다.
     * @param request
     * @param path : 서버에 저장할 path
     * @param filename : 원본 파일명
     * @param uploadMaxSize : 최대 upload size
     * @return 신규 파일명
     * @throws Exception
     */
    public String saveMipUploadedFile(HttpServletRequest request, 
                                    String path,
                                    String filename,
                                    long uploadMaxSize) throws Exception {
        
        String uploadRoot = propertiesService.getString("upload.root");
        
        //..문자 check
        if(validateURLPath(path) == false ||
        		validateURLPath(filename) == false)  {
            throw new LotteRuntimeException("illegar file upload argument");
        }
        
        StringBuffer sb = new StringBuffer();
        
        //서버에 저장할 파일명으로 변경
//        String newFileName;
//        int lastDotIndex = filename.lastIndexOf('.');
//        if(lastDotIndex > -1) {
//            newFileName = sb.append(filename.substring(0, lastDotIndex)) 
//                            .append("[")
//                            .append(EgovDateUtil.getCurrentDateTimeAsString())
//                            .append("]")
//                            .append(filename.substring(lastDotIndex)).toString();
//        } else {
//            newFileName = sb.append(filename) 
//                            .append("[")
//                            .append(EgovDateUtil.getCurrentDateTimeAsString())
//                            .append("]").toString();
//        }
        
        StringBuffer sb2 = new StringBuffer();
        String fullFileName = sb2.append(uploadRoot)
                                 .append("/").append(path).append("/")
                                 .append(filename).toString();
        
        File file = new File(fullFileName);
        
        if (! file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        
        InputStream  is = request.getInputStream();
        OutputStream os = new FileOutputStream(file);
        try {
            byte buff[] = new byte[8192];
            int size;
            long total = 0;
            while( (size = is.read(buff)) > 0 )  { // NOPMD by gunhlee on 09. 11. 5 오후 2:52
                os.write(buff, 0, size);
                total += size;
                if( total > uploadMaxSize ) {
                    throw new LotteRuntimeException("File Size Limit exceed : " + uploadMaxSize);
                }
            }
        } catch (IOException e) {
            if( file != null ) {
                file.delete();
            }
            throw e;
        } finally {
            os.close();
            is.close();
        }
        
        return filename;
    }
    
    /**
     * multipart로 전송된 파일을 저장한다.
     * @param multiRequest
     * @param response
     * @param serverPath
     * @param uploadMaxSize
     * @return String : 파일을 저장한 경로
     * @throws Exception
     */
    public String saveMultipartFile(MultipartHttpServletRequest multiRequest,
                                      String serverPath,
                                      long uploadMaxSize) throws Exception {
        
        //..문자 check
        if(validateURLPath(serverPath) == false) {
            throw new LotteRuntimeException("illegar file upload argument");
        }
        
        final Map<String, MultipartFile> files = multiRequest.getFileMap();
        
        String uploadRoot = propertiesService.getString("upload.root");
        
        Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
        MultipartFile file;
        
        String uploadPath = uploadRoot + "/" + serverPath;
        
        log.debug("[uploadPath]" + uploadPath);
        
        String filePath;
        
        File uploadDir = new File(uploadPath);
        if(!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        
        while (itr.hasNext()) {
            Entry<String, MultipartFile> entry = itr.next();
         
            file = entry.getValue();
            if (!"".equals(file.getOriginalFilename())) {
                //file size check
                if(file.getSize() > uploadMaxSize) {
                    throw new LotteRuntimeException("File Size Limit exceed : " + uploadMaxSize);
                }
                
                filePath = uploadPath + "/" + file.getOriginalFilename();
                file.transferTo(new File(filePath));
            }
        }
        
        return uploadPath;
    }
    
    /**
     * download에 필요한 response header를 세팅한다.
     * @param req
     * @param res
     * @param fileName
     * @throws Exception
     */
    public void setDownloadResponseHeader(HttpServletRequest req, HttpServletResponse res, 
            String fileName, long fileSize, String charSet) throws Exception {        
        try {            

            res.setContentType("application/octet-stream;charset=" + charSet);
            res.setHeader("Accept-Ranges", "bytes");
                
            String agent = req.getHeader("User-Agent");

            if(agent.indexOf("MSIE 5.5")>-1) {
                  res.setHeader("Content-Disposition", "filename=" + URLEncoder.encode(fileName, charSet) + ";");
                } else {
                  res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, charSet) + ";");
            }
            res.setHeader("Content-Length", String.valueOf(fileSize));
            res.setHeader("Content-Transfer-Encoding", "binary;");
            res.setHeader("Expired", "-1");
            res.setHeader("Pragma",  "nocache");
            
        } catch (Exception e) {
            throw new LotteRuntimeException("exception on set response header", e);
        }        
    }
    
    /**
     * 전송된 request/response와 file정보를 이용하여 file download를 수행한다.
     * @param request
     * @param response
     * @param uploadPath
     * @param fullName
     * @param localFullName
     * @param onlySizeCheck
     * @param charSet
     * @throws Exception
     */
    public void downloadFile(HttpServletRequest request,  // NOPMD by gunhlee on 09. 11. 5 오후 2:54
                            HttpServletResponse response, 
                            String uploadPath, 
                            String fullName, 
                            String localName, 
                            String onlySizeCheck, 
                            String charSet) throws Exception {
        
        //전체 upload root directory
        String uploadRoot = propertiesService.getString("upload.root");
        
        //..문자 check
        if(validateURLPath(uploadPath) == false || validateURLPath(fullName) == false) {
            throw new LotteRuntimeException("illegar file download argument");
        }
        
        String newlocalName = localName;
        
        //locaName이 전달되지 않은경우 서버 파일명에서 생성한다.
        if(newlocalName == null || "".equals(newlocalName.trim())) {
            int lastDirIndex = Math.max(fullName.lastIndexOf('/'), fullName.lastIndexOf('\\'));
            
            if(lastDirIndex > -1) {
                newlocalName = fullName.substring(lastDirIndex+1);
            } else {
                newlocalName = fullName;
            }
            
            int lastCloseBraceIndex = newlocalName.indexOf(']');
            int lastOpenBraceIndex = newlocalName.indexOf('[');
            
            if(lastCloseBraceIndex > -1 && lastOpenBraceIndex > -1
                    && lastCloseBraceIndex > lastOpenBraceIndex) {
                
                newlocalName = newlocalName.substring(0, lastOpenBraceIndex)
                             + newlocalName.substring(lastCloseBraceIndex + 1);
            }
        }
        
        String fullFilePath = uploadRoot + "/" + uploadPath + "/" + fullName;
        log.debug("fullfilePath=" + fullFilePath);
        File file = new File(fullFilePath);
        if( !file.isFile() ) {
            throw new LotteRuntimeException("'" + fullFilePath + "' does not exist.");
        }
        
        //size check 인 경우 file size만 cookie에 세팅하여 보내줌
        if( "true".equalsIgnoreCase(onlySizeCheck) ) {
            response.setContentType("text/html; charset=" + charSet);
            
            Cookie rtnCookie = new Cookie("FileParam", URLEncoder.encode("fileSize="+file.length(), charSet));
            response.addCookie(rtnCookie);
            
        } else {
            
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            
            try {
                //header setting
                setDownloadResponseHeader(request, response, newlocalName, file.length(), charSet);
                
                Cookie rtnCookie = new Cookie("FileParam", URLEncoder.encode("fileSize="+file.length(), charSet));
                response.addCookie(rtnCookie);
                
                bis = new BufferedInputStream(new FileInputStream(file));
                bos = new BufferedOutputStream(response.getOutputStream());
                
                int read = 0;
                byte []buff = new byte[8192];
                while ((read = bis.read(buff)) != -1) { // NOPMD by gunhlee on 09. 11. 5 오후 2:52
                    bos.write(buff, 0, read);
                }
                bos.flush();
                
            } catch(Exception e) {
                log.debug(e);
                
            } finally {
                if( bis != null ) bis.close(); // NOPMD by gunhlee on 09. 11. 5 오후 2:52
                if( bos != null ) bos.close(); // NOPMD by gunhlee on 09. 11. 5 오후 2:52
            }
        }
    }
    
    
    private boolean validateURLPath(String path) {
    	if(path == null || path.indexOf("..") >= 0) {
    		return false;
    	}
    	return true;
    }
    
}


























