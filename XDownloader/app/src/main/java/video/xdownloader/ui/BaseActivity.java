package video.xdownloader.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.List;

import video.xdownloader.models.MainModel;

/**
 * Created by jaswinderwadali on 28/07/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    String after;
    protected AccessToken accessToken = AccessToken.getCurrentAccessToken();

    void getVideos(String id) {
        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(after))
            bundle.putString("after", after);
        bundle.putString("fields", "source,description");
        bundle.putString("limit", "200");
        GraphRequest request = GraphRequest.newGraphPathRequest(
                accessToken,
                "/" + id + "/videos",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        try {
//                            Log.d("response", response.getRawResponse());
                            try {
                                after = response.getJSONObject().getJSONObject("paging").getJSONObject("cursors").getString("after");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            JSONArray jsonArray = response.getJSONObject().getJSONArray("data");

                            if (jsonArray == null || jsonArray.length() == 0) {
                                Toast.makeText(BaseActivity.this, "No more videos :(", Toast.LENGTH_SHORT).show();
                            }
                            Type type = new TypeToken<List<MainModel.DataList>>() {
                            }.getType();
                            List<MainModel.DataList> commonModels = new Gson().fromJson(jsonArray.toString(), type);
                            dataCallback(commonModels);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        request.setParameters(bundle);
        request.executeAsync();
    }

    protected abstract void dataCallback(List<MainModel.DataList> commonModels);


}
