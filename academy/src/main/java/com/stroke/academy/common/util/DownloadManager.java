package com.stroke.academy.common.util;

import android.os.Handler;
import android.os.Message;

import com.stroke.academy.AcademyApplication;
import com.stroke.academy.R;
import com.stroke.academy.common.constant.Consts;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by yuym on 5/12/15.
 */
public class DownloadManager {
    private boolean isDownloading = false;

    private String downloadUrl = null;

    private String downloadName = null;

    private DownloadHandler downloadHandler = null;

    private DownloadThread downloadThread = null;

    private DownloadListener downloadListener = null;

    private static DownloadManager apkDownloadUtil = null;

    private String audioDownloadDir;

    public final static int PROGRESS_MSG = 1;

    public final static int DOWNLOAD_SUCCESS_MSG = 2;

    public final static int DOWNLOAD_FAIL_MSG = 3;

    public final static int DOWNLOAD_CANCEL_MSG = 4;

    private long mContentSize;

    private DownloadManager() {
    }

    public synchronized static DownloadManager getInstance() {
        if (apkDownloadUtil == null) {
            apkDownloadUtil = new DownloadManager();
        }

        return apkDownloadUtil;
    }

    /**
     * 初始化要下载的安装包信息
     * @param downloadName
     * @param downloadUrl
     */
    public void initAppDownloadInfo(String folderName, String downloadName, String downloadUrl) {
        audioDownloadDir = folderName;
        this.downloadUrl = downloadUrl;
        this.downloadName = downloadName;
        this.downloadHandler = new DownloadHandler(this);
    }

    /**
     * 设置下载监听器
     * @param downloadListener
     */
    public void setDownloadListener(DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    /**
     * 判断是否正在下载
     * @return
     */
    public boolean isDownloading() {
        return isDownloading;
    }

    /**
     * 获取下载线程
     * @return
     */
    public DownloadThread getDownloadThread() {
        return downloadThread;
    }

    public void startDownload(boolean isUpdate) {
        downloadThread = null;
        downloadThread = new DownloadThread(isUpdate);
        downloadThread.start();
    }

    public boolean checkDownloading() {
        if (isDownloading) {
            Toaster.show(AcademyApplication.getInstance(), R.string.task_downloading);
            return true;
        }

        return false;
    }

    /**
     * 取消当前下载
     * @param packageName
     */
    public void cancelDownload(String packageName) {
        if (packageName.equals(this.downloadName) && downloadThread != null) {
            downloadThread.interrupt();
        }
    }

    /**
     * 下载线程
     *
     * @author emilyu
     *
     */
    private class DownloadThread extends Thread {
        public static final int TIME_OUT = 30000;

//        private String filePath = null;

        private String filePathTemp = null;

        private String finaFilePath = null;

        private InputStream in = null;

        private OutputStream out = null;

        public DownloadThread(boolean isUpdate) {

            File dir = new File(audioDownloadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            filePathTemp = audioDownloadDir + downloadName + Consts.AUDIO_DOWNLOAD_TEMP;
            finaFilePath = audioDownloadDir + downloadName + Consts.AUDIO_DOWNLOAD_SUF;
            //TODO 暂时修改为有安装包直接打开
            File file = new File(filePathTemp);
            if (file.exists())
                file.delete();
            if (isUpdate) {
                File desFile = new File(finaFilePath);
                if (desFile.exists()) {
                    desFile.delete();
                }
            }
        }

        @Override
        public void run() {
            isDownloading = true;
            try {
                downloadFile();
                isDownloading = false;
                defineFileName();
                downloadHandler.obtainMessage(DOWNLOAD_SUCCESS_MSG, finaFilePath)
                        .sendToTarget();
            } catch (InterruptedException e) {
                isDownloading = false;
                downloadHandler.sendEmptyMessage(DOWNLOAD_CANCEL_MSG);
            } catch (Exception e) {
                isDownloading = false;
                downloadHandler.obtainMessage(DOWNLOAD_FAIL_MSG, e).sendToTarget();
            }

        }

        private void defineFileName() {
            File file = new File(filePathTemp);
            if (file.exists()) {
                file.renameTo(new File(finaFilePath));
            }
        }

        private void downloadFile() throws Exception {

            HttpGet get = new HttpGet(downloadUrl);
            HttpClient client = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(client.getParams(), TIME_OUT);
            HttpConnectionParams.setSoTimeout(client.getParams(), TIME_OUT);
            HttpResponse response = client.execute(get);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200 && statusCode != 206) {
                throw new Exception(response.getStatusLine().toString());
            }
            mContentSize = response.getEntity().getContentLength();
            //如果文件已经存在，并且文件符合，直接打开
            File file = new File(finaFilePath);
            if (file.exists() && file.length() == mContentSize) {
                downloadHandler.obtainMessage(DOWNLOAD_SUCCESS_MSG, finaFilePath).sendToTarget();
                return;
            }
            out = new FileOutputStream(filePathTemp, true);
            in = response.getEntity().getContent();

            byte buffer[] = new byte[1024 * 4];
            int downLength = 0;
            int position = in.read(buffer);
            long time = System.currentTimeMillis();

            while (position > 0 && isAlive()) {
                if (interrupted()) {
                    throw new InterruptedException();
                }
                downLength += position;
                out.write(buffer, 0, position);
                if (System.currentTimeMillis() - time > 500) {// 500ms刷新界面一次
                    time = System.currentTimeMillis();
                    downloadHandler.obtainMessage(PROGRESS_MSG,
                            (int) (((float) downLength) / mContentSize * 100), 0)
                            .sendToTarget();
                }
                position = in.read(buffer);
            }
            out.flush();
            out.close();

        }
    }

    /**
     * 下载线程handler
     *
     * @author emilyu
     *
     */
    private static class DownloadHandler extends Handler {

        private WeakReference<DownloadManager> utilReference = null;

        public DownloadHandler(DownloadManager apkDownloadUtil) {
            utilReference = new WeakReference<DownloadManager>(apkDownloadUtil);
        }

        @Override
        public void handleMessage(Message msg) {
            DownloadManager apkDownloadUtil = utilReference.get();
            switch (msg.what) {
                case DownloadManager.PROGRESS_MSG:
                    if (apkDownloadUtil.downloadListener != null) {
                        apkDownloadUtil.downloadListener.onDownloadProgress(msg.arg1);
                    }
                    break;
                case DownloadManager.DOWNLOAD_SUCCESS_MSG:
                    File src = new File((String) msg.obj);
                    //检查安装包完整性（简单检查安装包大小）
                    if (src.length() != apkDownloadUtil.mContentSize) {
//                        apkDownloadUtil.startDownload(true);
                        src.delete();
                        Toaster.show(AcademyApplication.getInstance(), R.string.download_fail_retry);
                        return;
                    }
                    apkDownloadUtil.downloadThread = null;
                    if (apkDownloadUtil.downloadListener != null) {
                        apkDownloadUtil.downloadListener.onDownloadSuccess();
                    }
                    break;
                case DownloadManager.DOWNLOAD_FAIL_MSG:
                    Exception e = (Exception) msg.obj;
                    if (e instanceof InterruptedIOException
                            || e instanceof SocketException
                            || e instanceof UnknownHostException) {
                        Toaster.show(AcademyApplication.getInstance(), R.string.download_fail_by_network);
                    } else if (e instanceof IOException
                            || e instanceof FileNotFoundException) {
                        Toaster.show(AcademyApplication.getInstance(), R.string.download_fail_by_sdcard);
                    } else {
                        Toaster.show(AcademyApplication.getInstance(), R.string.download_fail_retry);
                    }
                    apkDownloadUtil.downloadThread = null;
                    if (apkDownloadUtil.downloadListener != null) {
                        apkDownloadUtil.downloadListener.onDownloadFail();
                    }
                    break;
                case DownloadManager.DOWNLOAD_CANCEL_MSG:
                    apkDownloadUtil.downloadThread = null;
                    if (apkDownloadUtil.downloadListener != null) {
                        apkDownloadUtil.downloadListener.onDownloadCancel();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public interface DownloadListener {
        /**
         * 下载成功接口
         */
        public void onDownloadSuccess();

        /**
         * 下载失败接口
         */
        public void onDownloadFail();

        /**
         * 取消当前下载接口
         */
        public void onDownloadCancel();

        /**
         * @param position
         * 当前下载进度
         */
        public void onDownloadProgress(int position);
    }
}
