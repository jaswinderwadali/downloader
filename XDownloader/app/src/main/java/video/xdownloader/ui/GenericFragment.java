package video.xdownloader.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.AccessToken;
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
import video.xdownloader.adapters.GenericAdapter;
import video.xdownloader.models.MainModel;

/**
 * Created by jaswinderwadali on 15/07/17.
 */

public class GenericFragment extends Fragment {

    AccessToken accessToken = AccessToken.getCurrentAccessToken();
    List<MainModel.DataList> jsonObjects = new ArrayList<>();


    public boolean isLikedVideos;
    ListView listView;
    private GenericAdapter genericAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_view, container, false);
        listView = rootView.findViewById(R.id.list);
        genericAdapter = new GenericAdapter(jsonObjects, GenericAdapter.TYPE.ONE);
        listView.setAdapter(genericAdapter);
        View view = inflater.inflate(R.layout.progress, null);
        listView.addFooterView(view);
        if (isLikedVideos)
            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    if (listView.getLastVisiblePosition() == listView.getAdapter().getCount() - 1 &&
                            listView.getChildAt(listView.getChildCount() - 1).getBottom() <= listView.getHeight()) {
                        getLikes();
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), VideoListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Data", jsonObjects.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        if (isLikedVideos)
            getLikes();
        else
            xRender();
        return rootView;
    }

    String after;

    void getLikes() {
        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(after))
            bundle.putString("after", after);
        bundle.putString("limit", "200");
        GraphRequest request = GraphRequest.newGraphPathRequest(
                accessToken,
                "/me/likes",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        try {
                            if (response.getError() == null) {
                                after = response.getJSONObject().getJSONObject("paging").getJSONObject("cursors").getString("after");
                                JSONArray jsonArray = response.getJSONObject().getJSONArray("data");
                                Type type = new TypeToken<List<MainModel.DataList>>() {
                                }.getType();
                                List<MainModel.DataList> commonModels = new Gson().fromJson(jsonArray.toString(), type);
                                jsonObjects.addAll(commonModels);
                                genericAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getActivity(), "Connectivity Error", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("response", response.getRawResponse());

                    }
                });

        request.setParameters(bundle);
        request.executeAsync();
    }

    void xRender() {
        try {
            MainModel mainModel = (MainModel) getActivity().getIntent().getSerializableExtra("dataList");
            jsonObjects.addAll(mainModel.getDataList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        genericAdapter.notifyDataSetChanged();
    }


    String xID = "[{\n" +
            "\t\"name\": \"Funny videos\",\n" +
            "\t\"id\": \"103741982610\",\n" +
            "\t\"created_time\": \"2017-05-19T07:35:50+0000\"\n" +
            "}, {\n" +
            "\t\"name\": \"Funny Videos\",\n" +
            "\t\"id\": \"152451048184611\",\n" +
            "\t\"created_time\": \"2017-05-14T14:40:00+0000\"\n" +
            "}, {\n" +
            "\t\"name\": \"Hello Funny\",\n" +
            "\t\"id\": \"275358526249038\",\n" +
            "\t\"created_time\": \"2017-05-14T11:06:42+0000\"\n" +
            "}, {\n" +
            "\t\"name\": \"Funny or Die\",\n" +
            "\t\"id\": \"275358526249038\",\n" +
            "\t\"created_time\": \"2017-05-14T11:06:42+0000\"\n" +
            "}, {\n" +
            "\t\"name\": \"Funny post\",\n" +
            "\t\"id\": \"753675931309716\",\n" +
            "\t\"created_time\": \"2017-05-14T11:06:42+0000\"\n" +
            "}, {\n" +
            "\t\"name\": \"Thats Sarcasm\",\n" +
            "\t\"id\": \"152451048184611\",\n" +
            "\t\"created_time\": \"2017-05-14T11:06:42+0000\"\n" +
            "}, {\n" +
            "\t\"name\": \"Funny \",\n" +
            "\t\"id\": \"243950495806947\",\n" +
            "\t\"created_time\": \"2017-05-14T11:06:42+0000\"\n" +
            "}, {\n" +
            "\t\"name\": \"Funny \",\n" +
            "\t\"id\": \"998095346913887\",\n" +
            "\t\"created_time\": \"2017-05-14T11:06:42+0000\"\n" +
            "}, {\n" +
            "\t\"name\": \"Laugh Videos \",\n" +
            "\t\"id\": \"967350833396288\",\n" +
            "\t\"created_time\": \"2017-05-14T11:06:42+0000\"\n" +
            "}, {\n" +
            "\t\"name\": \"Fun Or Die\",\n" +
            "\t\"id\": \"255207247865023\",\n" +
            "\t\"created_time\": \"2017-05-14T11:06:42+0000\"\n" +
            "}]";
}
