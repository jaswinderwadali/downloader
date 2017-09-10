package video.xdownloader.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import video.xdownloader.R;
import video.xdownloader.models.MainModel;
import video.xdownloader.utils.Utils;

/**
 * Created by jaswinderwadali on 15/07/17.
 */

public class GenericAdapter extends BaseAdapter {

    private final TYPE type;
    List<MainModel.DataList> jsonObjects;

    public GenericAdapter(List<MainModel.DataList> jsonObjects, TYPE type) {
        this.jsonObjects = jsonObjects;
        this.type = type;
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
        Context context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(type == TYPE.ONE ? R.layout.generic_item : R.layout.final_item, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.common_tv_one);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView_common_item);
        MainModel.DataList commonModel = jsonObjects.get(position);
        if (TextUtils.isEmpty(commonModel.getPageName())) {
            textView.setText(type == TYPE.ONE ? commonModel.getName() : commonModel.getDescription());
            Utils.circleImageFb(imageView, jsonObjects.get(position).getId(), true);
        }else{
            textView.setText(type == TYPE.ONE ? commonModel.getPageName() : commonModel.getDescription());
            Utils.circleImageFb(imageView, jsonObjects.get(position).getPageID(), true);

        }

        return view;
    }

    public enum TYPE {
        ONE, TWO;
    }
}
