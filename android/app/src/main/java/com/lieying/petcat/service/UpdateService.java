/**
 *
 */
package com.lieying.petcat.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.lieying.comlib.bean.DownBean;
import com.lieying.comlib.utils.FileUtil;
import com.lieying.petcat.R;
import com.lieying.petcat.utils.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * ：Update_Test
 * ：UpdateService
 *
 * @description 下载更新服务
 * @author：liyi
 * @dateTime：2017-03-01 下午1:21:28
 */
public class UpdateService extends Service {
    private NotificationManager nm;
    private MyHandler myHandler;
    private Context mContent;

    private NotificationCompat.Builder mBuilder;
    /**
     * Notification的ID
     */
    private int notifyId = 102;

    /**
     * 是否正在下载，如果正在下载，则不下载，防止重复下载
     */
    private boolean isDownloading = false;

    private final int UPDATA_OK = 1;//下载完成
    private final int UPDATA_ING = 2;//准备下载
    private final int UPDATA_ERR = 3;//下载过程异常
    private final int UPDATA_ERR_PERMISSION = 4;//文件读写权限被禁

    private volatile List<DownBean> downLoadList = new ArrayList<>();
    private List<String> downPathList = new ArrayList<>();

    @Override
    public void onCreate() {
        mContent = this;
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            ToastUtil.showToast(getString(R.string.tip_downloadfaild));
            stopSelf();
            return START_NOT_STICKY;
        }
        DownBean downBean = (DownBean) intent.getSerializableExtra("appURL");
        for(String path: downPathList){
            if(path.equals(downBean.getFileName())){
                ToastUtil.showToast(downBean.getName()+"已在下载队列中");
                return super.onStartCommand(intent, flags, startId);
            }
        }
        downPathList.add(downBean.getFileName());
        downLoadList.add(downBean);
        downFile();
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                intent, 0);

        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
                .setContentIntent(contentIntent)
                .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
                .setOngoing(false)// ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_LIGHTS)// 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher);

        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        myHandler = new MyHandler();
        myHandler.sendEmptyMessage(UPDATA_ING);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // 下载更新文件
    private void downFile() {
        if (isDownloading) {
            return;
        }
        if (downLoadList.size() <= 0) {
            stopSelf();
            return;
        }
        final String url = downLoadList.get(0).getDownPath();
        final String filename = downLoadList.get(0).getFileName();
        final String name = downLoadList.get(0).getName();
        isDownloading = true;
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                try {
                    Request request = new Request.Builder().url(url).build();
                    OkHttpClient mOkHttpClient = getUnsafeOkHttpClient();
                    mOkHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();

                            //异步不能弹toast
                            myHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtil.showToast(UpdateService.this, getString(R.string.tip_downloadfaild));
                                    stopSelf();
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            myHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtil.showToast(String.format(getString(R.string.string_download_progress), name));
                                }
                            });
                            InputStream is = null;
                            byte[] buf = new byte[2048];
                            int len = 0;
                            FileOutputStream fos = null;
                            Message msg = new Message();
                            try {
                                is = response.body().byteStream();
                                long total = response.body().contentLength();
                                File file = new File(FileUtil.getRootDir(), filename);
                                if (file.exists()) {
                                    file.delete();
                                }
                                fos = new FileOutputStream(file);
                                long sum = 0;
                                int curProgress = 0;
                                while ((len = is.read(buf)) != -1) {
                                    fos.write(buf, 0, len);
                                    sum += len;
                                    int progress = (int) (sum * 1.0f / total * 100);
                                    if (progress > curProgress) {
                                        emitter.onNext(progress);
                                        curProgress = progress;
                                    }
                                }
                                fos.flush();
                                msg = new Message();
                                msg.obj = file;
                                msg.what = UPDATA_OK;
                                myHandler.sendMessage(msg);
                                emitter.onComplete();
                            } catch (Exception e) {
                                if (!TextUtils.isEmpty(e.getMessage()) && e.getMessage().contains("Permission")) {//权限被禁
                                    msg.what = UPDATA_ERR_PERMISSION;
                                    myHandler.sendMessage(msg);
                                } else {//其他未知原因
                                    msg.what = UPDATA_ERR;
                                    myHandler.sendMessage(msg);
                                }
                                e.printStackTrace();
                            } finally {
                                try {
                                    if (fos != null) {
                                        fos.close();
                                    }
                                    if (is != null) {
                                        is.close();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    isDownloading = false;
                }

            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {

                    @Override
                    public void onError(Throwable throwable) {

                        if (downLoadList.size() > 0) {
                            downPathList.remove(downLoadList.get(0).getFileName());
                            downLoadList.remove(0);
                        }
                        isDownloading = false;
                        downFile();
                        if (mContent != null) {
                            mBuilder.setContentInfo(getString(R.string.tip_downloadfaild));
                        }
                        nm.notify(notifyId, mBuilder.build());
                    }

                    @Override
                    public void onComplete() {
                        if (downLoadList.size() > 0) {
                            downPathList.remove(downLoadList.get(0).getFileName());
                            downLoadList.remove(0);
                        }
                        isDownloading = false;
                        downFile();
                        if (mContent != null) {
                            mBuilder.setContentInfo(getString(R.string.download_s));
                        }
                        nm.notify(notifyId, mBuilder.build());
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer progress) {
                        if (mContent != null) {
                            int rest = downLoadList.size() - 1;
                            mBuilder.setContentTitle(String.format(getString(R.string.string_download_progress), rest > 1 ? name + "(" + rest + "个文件等待下载" : name));
                        }
                        mBuilder.setContentText(progress + " %");
                        mBuilder.setProgress(100, progress, false); // 这个方法是显示进度条
                        nm.notify(notifyId, mBuilder.build());
                    }
                });

    }
    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // 安装下载后的apk文件
    private void Install(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.lieying.socialappstore.fileprovider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        startActivity(intent);
    }

    /* 事件处理类 */
    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case UPDATA_OK:
                        // 下载完成后清除所有下载信息，执行安装提示
//                        nm.cancel(notifyId);
                        Install(mContent, (File) msg.obj);
                        // 停止掉当前的服务
//                        stopSelf();
                        break;
                    case UPDATA_ING:
                        if (mContent != null) {
                            mBuilder.setContentTitle(String.format(getString(R.string.string_download_progress), getString(R.string.app_name)));
                            mBuilder.setContentInfo(getString(R.string.p_download));
                        }
                        nm.notify(notifyId, mBuilder.build());
                        break;
                    case UPDATA_ERR:
                        if (mContent != null) {
                            mBuilder.setContentTitle(getString(R.string.tip_downloadfaild));
                        }
                        mBuilder.setContentInfo("");
                        nm.notify(notifyId, mBuilder.build());
                        stopSelf();
                        break;
                    case UPDATA_ERR_PERMISSION:
                        if (mContent != null) {
                            mBuilder.setContentTitle(getString(R.string.tip_downloadfaild));
                            ToastUtil.showToast(getString(R.string.no_rom_p));
                        }
                        mBuilder.setContentInfo("");
                        nm.notify(notifyId, mBuilder.build());
                        stopSelf();
                        break;
                }
            }
        }
    }
}
