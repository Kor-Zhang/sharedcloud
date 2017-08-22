package com.sharedcloud.pc.front.pageModel;

import java.io.File;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import com.sharedcloud.pc.utils.GConfig;
/**
 * 前台类，接收参数，PageUploadHistory的pagemodel；
 * @author Kor_Zhang
 *
 */
public class FPageUploadHistory extends FPageBase {
	
	
	
	private String id;
	private String fileid;
	private String userid;
	private Timestamp uploadtime;
	private Byte status;
	private String filename;
	private String pId;
	
	//其他属性，取自file类
	private Double filesize;
	private String filepath;
	private Byte isdir;
	
	/**
	 * 上传属性
	 */
	

	/*private int id = -1;*/

	private File upload;
	private String name;
	private List<String> names;
	private String uploadFileName;
	//文件类型
	private String uploadContentType;
	//大文件上传 分块chul
	private int chunk;
	private int chunks;
	
	private String result;
	
	/**
	 * 额外属性
	 */
	
	//文件的后缀
	private String ext;
	
	//前台分类查询的exts
	private String exts;
	
	private String searchKeyWord;
	//该文件夹是否还有子节点
	private Boolean child;
	
	
	
	/**
	 * 
	 * 保存其他用户的文件到自身文件夹
	 */
	//被保存的文件的id
	private String savedUploadHistoryId;
	//保存到的文件的id
	private String selectdUploadHistoryId;
	
	
	
	public String getSavedUploadHistoryId() {
		return savedUploadHistoryId;
	}
	public void setSavedUploadHistoryId(String savedUploadHistoryId) {
		this.savedUploadHistoryId = savedUploadHistoryId;
	}
	public String getSelectdUploadHistoryId() {
		return selectdUploadHistoryId;
	}
	public void setSelectdUploadHistoryId(String selectdUploadHistoryId) {
		this.selectdUploadHistoryId = selectdUploadHistoryId;
	}
	public Boolean getChild() {
		return child;
	}
	public void setChild(Boolean child) {
		this.child = child;
	}
	public String getSearchKeyWord() {
		return searchKeyWord;
	}
	public void setSearchKeyWord(String searchKeyWord) {
		this.searchKeyWord = searchKeyWord;
	}
	public String getExts() {
		return exts;
	}
	public void setExts(String exts) {
		this.exts = exts;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getNames() {
		return names;
	}
	public void setNames(List<String> names) {
		this.names = names;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public String getUploadContentType() {
		return uploadContentType;
	}
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	public int getChunk() {
		return chunk;
	}
	public void setChunk(int chunk) {
		this.chunk = chunk;
	}
	public int getChunks() {
		return chunks;
	}
	public void setChunks(int chunks) {
		this.chunks = chunks;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	@Override
	public String toString() {
		return "FPageUploadHistory [id=" + id + ", fileid=" + fileid
				+ ", userid=" + userid + ", uploadtime=" + uploadtime
				+ ", status=" + status + ", filename=" + filename + ", pId="
				+ pId + ", filesize=" + filesize + ", filepath=" + filepath
				+ ", isdir=" + isdir + "]";
	}
	public FPageUploadHistory() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public FPageUploadHistory(String id, String fileid, String userid,
			Timestamp uploadtime, Byte status, String filename, String pId,
			Double filesize, String filepath, Byte isdir, File upload,
			String name, List<String> names, String uploadFileName,
			String uploadContentType, int chunk, int chunks, String result,
			String ext, String exts, String searchKeyWord, Boolean child,
			String savedUploadHistoryId, String selectdUploadHistoryId) {
		super();
		this.id = id;
		this.fileid = fileid;
		this.userid = userid;
		this.uploadtime = uploadtime;
		this.status = status;
		this.filename = filename;
		this.pId = pId;
		this.filesize = filesize;
		this.filepath = filepath;
		this.isdir = isdir;
		this.upload = upload;
		this.name = name;
		this.names = names;
		this.uploadFileName = uploadFileName;
		this.uploadContentType = uploadContentType;
		this.chunk = chunk;
		this.chunks = chunks;
		this.result = result;
		this.ext = ext;
		this.exts = exts;
		this.searchKeyWord = searchKeyWord;
		this.child = child;
		this.savedUploadHistoryId = savedUploadHistoryId;
		this.selectdUploadHistoryId = selectdUploadHistoryId;
	}
	public FPageUploadHistory(String id, String fileid, String userid,
			Timestamp uploadtime, Byte status, String filename, String pId,
			Double filesize, String filepath, Byte isdir) {
		super();
		this.id = id;
		this.fileid = fileid;
		this.userid = userid;
		this.uploadtime = uploadtime;
		this.status = status;
		this.filename = filename;
		this.pId = pId;
		this.filesize = filesize;
		this.filepath = filepath;
		this.isdir = isdir;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFileid() {
		return fileid;
	}
	public void setFileid(String fileid) {
		this.fileid = fileid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Timestamp getUploadtime() {
		return uploadtime;
	}
	public void setUploadtime(Timestamp uploadtime) {
		this.uploadtime = uploadtime;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public Double getFilesize() {
		return filesize;
	}
	public void setFilesize(Double filesize) {
		this.filesize = filesize;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public Byte getIsdir() {
		return isdir;
	}
	public void setIsdir(Byte isdir) {
		this.isdir = isdir;
	}
	
		
}