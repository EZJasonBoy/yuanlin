package com.ninetowns.library.util;

import java.io.File;
import java.text.DecimalFormat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class FileFolderUtils {
	
	private static final DecimalFormat DOUBLE_DECIMAL_FORMAT = new DecimalFormat("0.##");
    private static final int MB_2_BYTE = 1024 * 1024;
    private static final int KB_2_BYTE = 1024;

    
	/**
     *  根据路径删除指定的目录或文件，无论存在与否
     *@param path  要删除的目录或文件
     */
    public  boolean deleteFileOrFolder(String path) {
        File fileOrFolder = new File(path);
        // 判断目录或文件是否存在
        if (fileOrFolder.exists()) {
            // 判断是否为文件
            if (fileOrFolder.isFile()) {  // 为文件时调用删除文件方法
                return deleteFile(path);
            } else {  // 为目录时调用删除目录方法
                return deleteFolder(path);
            }
        } else {
        	return false;
        }
    }
    
    
    /**
     * 删除单个文件
     * @param   filePath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
   public  static  boolean deleteFile(String filePath) {
        File file = new File(filePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            return true;
        } else {
        	return false;
        }
        
    }
    
    
    
    /**
     * 删除目录（文件夹）以及目录下的文件
     * @param   folderPath 被删除目录的文件路径
     * @return  目录删除成功返回true，否则返回false
     */
    private boolean deleteFolder(String folderPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!folderPath.endsWith(File.separator)) {
        	folderPath = folderPath + File.separator;
        }
        File folder = new File(folderPath);
        //如果folder对应的文件不存在，或者不是一个目录，则退出
        if (!folder.exists() || !folder.isDirectory()) {
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] filesOrFolders = folder.listFiles();
        for (int i = 0; i < filesOrFolders.length; i++) {
            //删除子文件
            if (filesOrFolders[i].isFile()) {
                flag = deleteFile(filesOrFolders[i].getAbsolutePath());
                if(!flag) break;
            } else {
            	//删除子目录
               flag = deleteFolder(filesOrFolders[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (folder.delete()) {
            return true;
        } else {
            return false;
        }
    }
    
    
    
    /**
     * @param size
     * @return
     */
    public static String getAppSize(long size) {
        if (size <= 0) {
            return "0M";
        }

        if (size >= MB_2_BYTE) {
            return new StringBuilder(16).append(DOUBLE_DECIMAL_FORMAT.format((double)size / MB_2_BYTE)).append("M").toString();
        } else if (size >= KB_2_BYTE) {
            return new StringBuilder(16).append(DOUBLE_DECIMAL_FORMAT.format((double)size / KB_2_BYTE)).append("K").toString();
        } else {
            return size + "B";
        }
    }

    /**
     * 获取百分比
     * @param progress
     * @param max
     * @return
     */
    public static String getNotiPercent(long progress, long max) {
        int rate = 0;
        if (progress <= 0 || max <= 0) {
            rate = 0;
        } else if (progress > max) {
            rate = 100;
        } else {
            rate = (int)((double)progress / max * 100);
        }
        return new StringBuilder(16).append(rate).append("%").toString();
    }

    //安装apk
	public void installApp(Context context, String filePath) {
	      Intent intent = new Intent(Intent.ACTION_VIEW);
	      File file = new File(filePath);
	      if (file != null && file.length() > 0 && file.exists() && file.isFile()) {
	      	intent.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
	      	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	          context.startActivity(intent);
	      }
	  }
    
}
