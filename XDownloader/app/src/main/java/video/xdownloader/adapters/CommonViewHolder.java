package video.xdownloader.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import video.xdownloader.R;
import video.xdownloader.utils.JTextView;

/**
 * Created by jaswinderwadali on 27/08/17.
 */

public class CommonViewHolder extends RecyclerView.ViewHolder  {

    JTextView jTextView;
    ImageView imageView;
    public CommonViewHolder(View itemView) {
        super(itemView);
        jTextView = (JTextView) itemView.findViewById(R.id.jtextView);
        imageView= (ImageView) itemView.findViewById(R.id.imageView);

    }
}
