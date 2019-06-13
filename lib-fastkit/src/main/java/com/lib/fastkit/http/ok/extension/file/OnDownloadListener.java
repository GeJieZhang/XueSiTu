package com.lib.fastkit.http.ok.extension.file;

import java.io.File;

public interface OnDownloadListener {
        /**
         * @param file 下载成功后的文件
         */
        void onDownloadSuccess(File file);
 
        /**
         * @param progress 下载进度
         */
        void onDownloading(int progress);
 
        /**
         * @param e 下载异常信息
         */
        void onDownloadFailed(Exception e);
    }

