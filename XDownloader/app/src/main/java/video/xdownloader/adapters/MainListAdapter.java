package video.xdownloader.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.Profile;

import java.util.Collections;
import java.util.List;

import video.xdownloader.R;
import video.xdownloader.models.MainModel;
import video.xdownloader.utils.Utils;

/**
 * Created by jaswinderwadali on 27/08/17.
 */

public class MainListAdapter extends RecyclerView.Adapter<CommonViewHolder> {

    List<MainModel> mainModelList;
    SelectedItem selectedItem;

    public MainListAdapter(List<MainModel> mainModelList, SelectedItem selectedItem) {
        Collections.reverse(mainModelList);
        this.mainModelList = mainModelList;
        this.selectedItem = selectedItem;

    }


    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        return new CommonViewHolder(mInflater.inflate(R.layout.main_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        MainModel mainModel = mainModelList.get(position);
        List<MainModel.DataList> dataList = mainModel.getDataList();
        String name = mainModel.getTypeName();
        String image = mainModel.getImage();
        holder.jTextView.setText(name);
        if (mainModel.getImage().equals("MyID")) {
            Profile profile = Profile.getCurrentProfile();
            if (profile != null) {
                String id = profile.getId();
                Utils.loadLargeFBimage(holder.imageView, id, true);
            }
        } else
            Utils.findViewAndLoadAnimatedImageUri(holder.imageView, image, true);
        holder.itemView.setTag(mainModel);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItem.selectedOpenList((MainModel) v.getTag());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mainModelList.size();
    }

    public interface SelectedItem {
        void selectedOpenList(MainModel mainModel);

    }


}
