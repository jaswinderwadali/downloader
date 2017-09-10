package video.xdownloader.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import video.xdownloader.R;
import video.xdownloader.models.CommonModel;
import video.xdownloader.models.MainModel;
import video.xdownloader.utils.Utils;
import video.xdownloader.utils.player.PlayerActivity;

/**
 * Created by jaswinderwadali on 08/05/17.
 */

public class GenericAdapterRecycler extends RecyclerView.Adapter<GenericAdapterRecycler.ViewHolder> {

    private LayoutInflater mInflater;
    private List<MainModel.DataList> mItems;
    private Dialog availableItemsDialog;
    boolean isPlan;
    RelativeLayout secondTool;
    boolean openHighLight = false;
    AnimationSet animationSet;
    TYPE type;

    private static final String SHARED_PROVIDER_AUTHORITY = video.xdownloader.BuildConfig.APPLICATION_ID;
    private static final String SHARED_FOLDER = "Download";


    public GenericAdapterRecycler(Context context, List<MainModel.DataList> mItems, TYPE type) {
        this.mItems = mItems;


    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(mInflater.inflate(R.layout.downloaded_item, null));


    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        CommonModel commonModel = mItems.get(position);
        if (type == TYPE.MAIN) {
            viewHolder.textView.setText(commonModel.getDescription());
            Utils.loadLargeFBimage(viewHolder.imageView, commonModel.getId(), true);
        } else {
            viewHolder.textView.setText(commonModel.getDescription());
            Utils.loadLargeFBimage(viewHolder.imageView, commonModel.getId(), true);
        }

        viewHolder.shareIc.setTag(commonModel);
        viewHolder.itemView.setTag(commonModel);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonModel commonModel = (CommonModel) v.getTag();
                Context context = v.getContext();
                try {

                    Log.d("path", commonModel.getPath());
                    Intent intent = new Intent(context, PlayerActivity.class);
                    intent.setData(Uri.parse(commonModel.getPath()));
                    intent.setAction(PlayerActivity.ACTION_VIEW);
                    context.startActivity(intent);

                } catch (Exception e) {
                    e.getStackTrace();
                    Toast.makeText(context, "Sorry we are still working on some issues", Toast.LENGTH_SHORT).show();
                    Log.d("Exception", commonModel.getPath());

                }
            }
        });

        viewHolder.shareIc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonModel commonModel = (CommonModel) v.getTag();
                Context context = v.getContext();
                try {

                    Log.d("path", commonModel.getPath());
                    final File sharedFile = new File(commonModel.getPath());
                    Uri fileUri = FileProvider.getUriForFile(context, SHARED_PROVIDER_AUTHORITY, sharedFile);
                    context.grantUriPermission(context.getPackageName(), fileUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    ShareCompat.IntentBuilder intentBuilder = ShareCompat.IntentBuilder.from((Activity) context).setType("video/*").addStream(fileUri);
                    Intent intent = intentBuilder.createChooserIntent();

                    final PackageManager packageManager = context.getPackageManager();
                    final List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                    for (ResolveInfo resolvedIntentInfo : activities) {
                        final String packageName = resolvedIntentInfo.activityInfo.packageName;
                        context.grantUriPermission(packageName, fileUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                    v.getContext().startActivity(intent);


                } catch (Exception e) {
                    e.getStackTrace();
                    Toast.makeText(context, "Sorry we are still working on some issues", Toast.LENGTH_SHORT).show();
                    Log.d("Exception", commonModel.getPath());

                }
                Log.d("final", commonModel.getPath());

            }
        });

    }

    boolean secondOpen = false;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView, shareIc;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.common_tv_one);
            imageView = (ImageView) view.findViewById(R.id.imageView_common_item);
            shareIc = (ImageView) view.findViewById(R.id.share_ic);
            shareIc.setVisibility(View.VISIBLE);
        }
    }

    public enum TYPE {
        MAIN,
        SUB;
    }
}
