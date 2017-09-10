package video.xdownloader.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import video.xdownloader.R;
import video.xdownloader.models.CommonModel;
import video.xdownloader.models.MainModel;
import video.xdownloader.utils.Utils;

/**
 * Created by jaswinderwadali on 15/07/17.
 */

public class ActiveAdapter extends BaseAdapter {

    List<MainModel.DataList> jsonObjects;
    ActiveAction activeAction;

    public ActiveAdapter(List<MainModel.DataList> jsonObjects, ActiveAction activeAction) {
        this.jsonObjects = jsonObjects;
        this.activeAction = activeAction;
    }

    @Override
    public int getCount() {
        return jsonObjects.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        ViewHolder viewHolder;
        if (convertView == null) {
            View view = LayoutInflater.from(context).inflate(R.layout.downloaded_item, null);
            viewHolder = new ViewHolder(view);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CommonModel commonModel = jsonObjects.get(position);
        viewHolder.textView.setText(commonModel.getDescription());
        Utils.loadLargeFBimage(viewHolder.imageView, jsonObjects.get(position).getId(), true);

        viewHolder.playViewRL.setTag(commonModel);
        viewHolder.playViewRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainModel.DataList commonModel = (MainModel.DataList) v.getTag();
                activeAction.play(commonModel);

            }
        });

        viewHolder.downloadView.setTag(commonModel);
        viewHolder.downloadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainModel.DataList  commonModel = (MainModel.DataList) v.getTag();
                activeAction.download(commonModel);

            }
        });
        convertView  = viewHolder.view;
        convertView.setTag(viewHolder);

        return convertView;
}


static class ViewHolder {
    TextView textView;
    ImageView imageView;
    View playViewRL, downloadView,shareView;
    View view;

    ViewHolder(View view) {
        this.view = view;
        textView = (TextView) view.findViewById(R.id.common_tv_one);
        imageView = (ImageView) view.findViewById(R.id.imageView_common_item);
        playViewRL = view.findViewById(R.id.play_video);
        downloadView = view.findViewById(R.id.download_iv);
        shareView = view.findViewById(R.id.share_ic);
        downloadView.setVisibility(View.VISIBLE);
        shareView.setVisibility(View.GONE);
    }

}

public interface ActiveAction {
    void play(MainModel.DataList commonModel);

    void download(MainModel.DataList commonModel);
}

}
