package com.lib.utls.upload.initerface;

public interface FileUploadListener {

    void onProgress(int progress);

    void onSuccess(String s);

    void onError(String e);
}
