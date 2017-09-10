package video.xdownloader.ui.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import video.xdownloader.R;
import video.xdownloader.adapters.ActiveAdapter;
import video.xdownloader.models.MainModel;
import video.xdownloader.ui.BaseActivity;

public class TimeLineActivity extends BaseActivity implements ActiveAdapter.ActiveAction {

    private String after = "";
    private ListView listView;
    private ActiveAdapter genericAdapter;
    private List<MainModel.DataList> commonModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        listView = (ListView) findViewById(R.id.list);
        genericAdapter = new ActiveAdapter(commonModelList, this);
        listView.setAdapter(genericAdapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (listView.getLastVisiblePosition() == listView.getAdapter().getCount() - 1 &&
                        listView.getChildAt(listView.getChildCount() - 1).getBottom() <= listView.getHeight()) {
                    getTimelineVideos();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        getTimelineVideos();
    }


    GraphRequest nextRequest = null;


    void getTimelineVideos() {
        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(after))
            bundle.putString("after", after);
        bundle.putString("limit", "1000");
        bundle.putString("fields", "source,description,link,id");

        if (nextRequest == null)
            nextRequest = GraphRequest.newGraphPathRequest(
                    accessToken,
                    "/me/feed?fields=attachments", callback);


        nextRequest.setParameters(bundle);
        nextRequest.executeAsync();

    }

    GraphRequest.Callback callback = new GraphRequest.Callback()

    {
        @Override
        public void onCompleted(GraphResponse response) {
            try {
                if (response.getError() == null) {
                    nextRequest = response.getRequestForPagedResults(GraphResponse.PagingDirection.NEXT);
//                                after = response.getJSONObject().getJSONObject("paging").getJSONObject("cursors").getString("after");
                    JSONArray jsonArray = response.getJSONObject().getJSONArray("data");
                    Type type = new TypeToken<List<MainModel.DataList>>() {
                    }.getType();
                    List<MainModel.DataList> commonModels = new Gson().fromJson(jsonArray.toString(), type);
                    for (MainModel.DataList commonModel : commonModels) {
//                        if (!TextUtils.isEmpty(commonModel.getSource()) && commonModel.getSource().contains(".mp4")) {
                            commonModelList.add(commonModel);
//                        }
                    }
                    genericAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(TimeLineActivity.this, "Connectivity Error", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("response", response.getRawResponse());
        }
    };

    @Override
    protected void dataCallback(List<MainModel.DataList> commonModels) {

    }

    @Override
    public void play(MainModel.DataList commonModel) {
        nextRequest.setCallback(callback);
        nextRequest.executeAsync();
    }

    @Override
    public void download(MainModel.DataList commonModel) {

    }
}
