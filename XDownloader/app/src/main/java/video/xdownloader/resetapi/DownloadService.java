package video.xdownloader.resetapi;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import video.xdownloader.R;
import video.xdownloader.ui.Navigation;

/**
 * Created by jaswinderwadali on 28/07/17.
 */

public class DownloadService extends IntentService {

    private String name = "wadali";
    private String ID = "";
    private String fileName;



    private static final String SHARED_PROVIDER_AUTHORITY = video.xdownloader.BuildConfig.APPLICATION_ID + ".fileprovider";
    private static final String SHARED_FOLDER = "Download";



    public DownloadService() {
        super("Download Service");
    }

    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    private int totalFileSize;
    private String url;

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {

        super.onStartCommand(intent, flags, startId);

        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    int time;

    @Override
    protected void onHandleIntent(Intent intent) {
        time = 3312;

        url = intent.getStringExtra("URL");
        name = intent.getStringExtra("NAME");
        ID = intent.getStringExtra("id");
        fileName = intent.getStringExtra("path");

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_file_download_black_24dp)
                .setContentTitle(name)
                .setContentText("Downloading...")
                .setAutoCancel(true);
        startForeground(time, notificationBuilder.build());

//        notificationManager.notify(time, notificationBuilder.build());

        initDownload(url);

    }

    void initDownload(String url) {


        ApiServices downloadService = RestAdapter.getInstance(new RestAdapter.ProgressListener() {
            @Override
            public void update(long bytesRead, long contentLength, boolean done) {
                System.out.format("%d%% done\n", (100 * bytesRead) / contentLength);

                notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.drawable.ic_file_download_black_24dp)
                        .setContentTitle(name)
                        .setContentText("Downloading ..." + (100 * bytesRead) / contentLength + " %")
                        .setAutoCancel(true);
                startForeground(time, notificationBuilder.build());

                Download download = new Download();
                download.setTotalFileSize(totalFileSize);
                download.setCurrentFileSize(10);
                download.setProgress((int) ((100 * bytesRead) / contentLength));

                sendIntent(download);

//                notificationManager.notify(time, notificationBuilder.build());


            }
        }).getApiService();
        Call<ResponseBody> call = downloadService.downloadFile(url);

        try {
            downloadFile(call.execute().body());
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            stopSelf();
        }
    }

    private void downloadFile(ResponseBody body) throws IOException {

        int count;
        byte data[] = new byte[1024 * 4];
        long fileSize = body.contentLength();
        InputStream bis = new BufferedInputStream(body.byteStream(), 1024 * 8);


        File sharedFile = new File(fileName);
        sharedFile.createNewFile();



        Log.d("downloadingFile",sharedFile.getAbsolutePath());
//        File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
        OutputStream output = new FileOutputStream(sharedFile);
        long total = 0;
        long startTime = System.currentTimeMillis();
        int timeCount = 1;
        while ((count = bis.read(data)) != -1) {

            total += count;
            totalFileSize = (int) (fileSize / (Math.pow(1024, 2)));
            double current = Math.round(total / (Math.pow(1024, 2)));

            int progress = (int) ((total * 100) / fileSize);

            long currentTime = System.currentTimeMillis() - startTime;

            Download download = new Download();
            download.setTotalFileSize(totalFileSize);

            if (currentTime > 1000 * timeCount) {

                download.setCurrentFileSize((int) current);
                download.setProgress(progress);
                sendNotification(download);
                timeCount++;
            }

            output.write(data, 0, count);
        }
        onDownloadComplete();
        output.flush();
        output.close();
        bis.close();

    }

    private void sendNotification(Download download) {

        sendIntent(download);
        notificationBuilder.setProgress(100, download.getProgress(), false);
        notificationBuilder.setContentText("Downloading file " + download.getCurrentFileSize() + "/" + totalFileSize + " MB");
        startForeground(time, notificationBuilder.build());
//        notificationManager.notify(time, notificationBuilder.build());
    }

    private void sendIntent(Download download) {
        Intent intent = new Intent(Navigation.MESSAGE_PROGRESS);
        intent.putExtra("download", download);
        intent.putExtra("name", name);
        intent.putExtra("id", ID);
        LocalBroadcastManager.getInstance(DownloadService.this).sendBroadcast(intent);
    }

    private void onDownloadComplete() {

        Download download = new Download();
        download.setProgress(100);
        sendIntent(download);

        notificationManager.cancel(time);
        notificationBuilder.setProgress(0, 0, false);
        notificationBuilder.setContentText("File Downloaded");

        startForeground(time, notificationBuilder.build());
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {

    }

}
