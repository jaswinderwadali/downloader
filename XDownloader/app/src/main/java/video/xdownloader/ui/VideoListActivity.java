package video.xdownloader.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import video.xdownloader.R;
import video.xdownloader.adapters.ActiveAdapter;
import video.xdownloader.models.MainModel;
import video.xdownloader.resetapi.DownloadService;
import video.xdownloader.storage.DataBase;
import video.xdownloader.utils.Utils;
import video.xdownloader.utils.player.PlayerActivity;

public class VideoListActivity extends BaseActivity implements ActiveAdapter.ActiveAction {

    private static final String TAG = "VideoListActivity";
    private static final int PERMISSION_REQUEST_CODE = 3312;
    private ListView listView;
    private MainModel.DataList commonModel;
    private List<MainModel.DataList> commonModels = new ArrayList<>();
    private ActiveAdapter genericAdapter;
    private int totalFileSize;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        Bundle bundle = getIntent().getExtras();
        commonModel = (MainModel.DataList) bundle.getSerializable("Data");
        Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar);
        ImageView profileIv = (ImageView) findViewById(R.id.profileImage);
        TextView nameTv = (TextView) findViewById(R.id.user_name);
        nameTv.setText(commonModel.getPageName());
        Profile profile = Profile.getCurrentProfile();

        if (commonModel.getId() != null && commonModel.getId().equals(profile.getId())) {
            userId = commonModel.getId();
        } else {
            userId = commonModel.getId() != null ? commonModel.getId() : commonModel.getPageID();

        }
        Log.d("pageid", ">" + userId);
        Utils.circleImageFb(profileIv, userId, false);
        setSupportActionBar(toolbar);
        findViewById(R.id.backbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listView = (ListView) findViewById(R.id.list);
        genericAdapter = new ActiveAdapter(commonModels, this);
        listView.setAdapter(genericAdapter);

        getVideos(userId);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (listView.getLastVisiblePosition() == listView.getAdapter().getCount() - 1 &&
                        listView.getChildAt(listView.getChildCount() - 1).getBottom() <= listView.getHeight()) {
                    getVideos(userId);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

    }

    @Override
    protected void dataCallback(List<MainModel.DataList> commonModels) {
        this.commonModels.addAll(commonModels);
        genericAdapter.notifyDataSetChanged();
    }

    @Override
    public void play(MainModel.DataList commonModel) {

        Intent intent = new Intent(this, PlayerActivity.class);
        intent.setData(Uri.parse(commonModel.getSource()));
        intent.setAction(PlayerActivity.ACTION_VIEW);
        startActivity(intent);
//        FullScreenView.build(getSupportFragmentManager(), commonModel.getSource());
    }

    @Override
    public void download(MainModel.DataList commonModel) {

        Toast.makeText(this, "Downloading .....", Toast.LENGTH_LONG).show();
        final File sharedFolder = new File(getFilesDir(), "Download");
        if (!sharedFolder.exists())
            sharedFolder.mkdirs();
        final File sharedFile;
        try {
            sharedFile = File.createTempFile(userId + commonModel.getName(), ".mp4", sharedFolder);
            sharedFile.createNewFile();

            commonModel.setPath(sharedFile.getPath());
            DataBase.getInstance().insertData(commonModel, getApplicationContext());
            if (checkPermission()) {
                Intent intent = new Intent(getApplicationContext(), DownloadService.class);
                intent.putExtra("URL", commonModel.getSource());
                intent.putExtra("NAME", commonModel.getDescription());
                intent.putExtra("id", userId);
                intent.putExtra("path", commonModel.getPath());


                startService(intent);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {

            return true;

        } else {

            return false;
        }
    }


}
