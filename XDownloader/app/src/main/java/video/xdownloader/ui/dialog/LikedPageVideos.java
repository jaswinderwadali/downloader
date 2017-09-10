package video.xdownloader.ui.dialog;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import video.xdownloader.R;
import video.xdownloader.ui.GenericFragment;

public class LikedPageVideos extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_page_videos);
        FragmentManager fragmentManager = getSupportFragmentManager();
        GenericFragment genericFragment = new GenericFragment();
        genericFragment.isLikedVideos = getIntent().getBooleanExtra("isMyList",false);
        genericFragment.setArguments(getIntent().getExtras());
        fragmentManager.beginTransaction().add(R.id.container, genericFragment).commit();

    }


}
