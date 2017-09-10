package video.xdownloader.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;

import video.xdownloader.R;

public class Navigation extends AppCompatActivity {


    public static final String MESSAGE_PROGRESS = "message_progress";
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0, true);
                    return true;
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(1, true);
                    return true;
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(2, true);
                    return true;
            }
            return false;
        }

    };

    ViewPager viewPager;
    private NavigateAdapter navigateAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!android.provider.Settings.System.canWrite(this)) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 2909);
            } else {
                processStart();
            }
        } else {
            processStart();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 2909: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("Permission", "Granted");
                    processStart();
                } else {
                    finish();
                    Log.d("Permission", "Denied");
                }
                return;
            }
        }
    }


    void processStart() {
//        getAllVideoList(this);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    navigateAdapter.refresh();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        navigateAdapter = new NavigateAdapter(getSupportFragmentManager());
        viewPager.setAdapter(navigateAdapter);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content, new MainClassFragment()).commit();

    }


    static class NavigateAdapter extends FragmentStatePagerAdapter {

        private DownloadFragment downloadFragment;

        public NavigateAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new MainClassFragment();
                case 1:
                    return new DownloadFragment();
                case 2:
                    return new Settings();
                default:
                    return new DownloadFragment();
            }
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
            // save the appropriate reference depending on position
            switch (position) {
                case 1:
                    downloadFragment = (DownloadFragment) createdFragment;
            }
            return createdFragment;
        }

        public void refresh() {
            downloadFragment.refresh();
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    interface CallBack {
        void refresh();
    }


}
