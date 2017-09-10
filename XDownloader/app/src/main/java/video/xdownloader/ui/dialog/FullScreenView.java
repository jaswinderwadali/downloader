package video.xdownloader.ui.dialog;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import video.xdownloader.R;

/**
 * Created by jaswinderwadali on 12/08/16.
 */
public class FullScreenView extends DialogFragment implements View.OnClickListener {
    private View rootView;
    private VideoView videoView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static FullScreenView build(FragmentManager fragmentManager, String url) {
        FullScreenView fullScreenImage = new FullScreenView();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("URL", url);
        fullScreenImage.setArguments(bundle);
        fullScreenImage.show(fragmentTransaction, "showImage");
        return fullScreenImage;
    }

    private WebView webView;


    @Override
    public void onStart() {
        super.onStart();
        Window window2 = getDialog().getWindow();
        if (window2 != null) {
            window2.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        rootView = inflater.inflate(R.layout.full_screen, null);
        Bundle bundle = getArguments();
        String url = bundle.getString("URL");
        videoView = (VideoView) rootView.findViewById(R.id.video_view);
        Uri video = Uri.parse(url);
        videoView.setVideoURI(video);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(false);
                videoView.start();
                rootView.findViewById(R.id.progress_full_screeen).setVisibility(View.GONE);

            }
        });
        return rootView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        dismiss();
    }
}
