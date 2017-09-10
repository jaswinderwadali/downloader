package video.xdownloader.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

import video.xdownloader.R;
import video.xdownloader.adapters.MainListAdapter;
import video.xdownloader.models.MainModel;
import video.xdownloader.ui.dialog.LikedPageVideos;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainClassFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainClassFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainClassFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    MainListAdapter mainListAdapter;

    private OnFragmentInteractionListener mListener;

    public MainClassFragment() {
        // Required empty public constructor
    }


    public static MainClassFragment newInstance(String param1, String param2) {
        MainClassFragment fragment = new MainClassFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_class, container, false);
    }

    public List<MainModel> mainModelList;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyler_mainList);

        new AsyncTask() {


            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    Document doc = Jsoup.connect("https://sites.google.com/site/androidapksapps/").get();

                    String data = doc.select("tbody").select("p").get(0).text();

                    java.lang.reflect.Type type = new TypeToken<List<MainModel>>() {
                    }.getType();

                    mainModelList = new Gson().fromJson(data, type);
                    MainModel mainModel = new MainModel();
                    mainModel.setTypeName("Your Liked Pages");
                    mainModel.setImage("MyID");
                    mainModelList.add(mainModel);

                    if (getActivity() != null)
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mainListAdapter = new MainListAdapter(mainModelList, new MainListAdapter.SelectedItem() {
                                    @Override
                                    public void selectedOpenList(MainModel mainModel) {
                                        Intent intent = new Intent(getActivity(), LikedPageVideos.class);
                                        intent.putExtra("isMyList", mainModel.getImage().equals("MyID"));
                                        intent.putExtra("dataList", mainModel);
                                        startActivity(intent);

                                    }
                                });
                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                recyclerView.setAdapter(mainListAdapter);
                            }
                        });
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }
        }.execute();


        view.findViewById(R.id.like_pages_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    String mapData = "[{\n" +
            "\t\"typeName\": \"funny\",\n" +
            "\t\"image\": \"https://miscmedia-9gag-fun.9cache.com/images/thumbnail-facebook/1481536354.227_YsUzaZ_220x220.jpg\",\n" +
            "\t\"dataList\": [{\n" +
            "\t\t\t\"pageName\": \"Funny vpageIDeos\",\n" +
            "\t\t\t\"pageID\": \"103741982610\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"pageName\": \"Funny VpageIDeos\",\n" +
            "\t\t\t\"pageID\": \"152451048184611\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"pageName\": \"Hello Funny\",\n" +
            "\t\t\t\"pageID\": \"275358526249038\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"pageName\": \"Funny or Die\",\n" +
            "\t\t\t\"pageID\": \"275358526249038\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"pageName\": \"Funny post\",\n" +
            "\t\t\t\"pageID\": \"753675931309716\"\n" +
            "\n" +
            "\t\t}, {\n" +
            "\t\t\t\"pageName\": \"Thats Sarcasm\",\n" +
            "\t\t\t\"pageID\": \"152451048184611\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"pageName\": \"Funny \",\n" +
            "\t\t\t\"pageID\": \"243950495806947\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"pageName\": \"Funny \",\n" +
            "\t\t\t\"pageID\": \"998095346913887\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"pageName\": \"Laugh VpageIDeos \",\n" +
            "\t\t\t\"pageID\": \"967350833396288\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"pageName\": \"Fun Or Die\",\n" +
            "\t\t\t\"pageID\": \"255207247865023\"\n" +
            "\t\t}\n" +
            "\n" +
            "\n" +
            "\t]\n" +
            "}, {\n" +
            "\t\"typeName\": \"Science\",\n" +
            "\t\"image\": \"https://upload.wikimedia.org/wikipedia/commons/3/3e/Einstein_1921_by_F_Schmutzer_-_restoration.jpg\",\n" +
            "\t\"dataList\": [{\n" +
            "\t\t\"pageID\": \"7557552517\",\n" +
            "\t\t\"pageName\": \"ScienceAlert\"\n" +
            "\t}, {\n" +
            "\t\t\"pageID\": \"96191425588\",\n" +
            "\t\t\"pageName\": \"Science\"\n" +
            "\t}, {\n" +
            "\t\t\"pageID\": \"57242657138\",\n" +
            "\t\t\"pageName\": \"NASA Earth\"\n" +
            "\t}, {\n" +
            "\t\t\"pageID\": \"542971019063265\",\n" +
            "\t\t\"pageName\": \"Brilliant.org\"\n" +
            "\t}, {\n" +
            "\t\t\"pageID\": \"216547678781529\",\n" +
            "\t\t\"pageName\": \"Science Page\"\n" +
            "\t}, {\n" +
            "\t\t\"pageID\": \"172214092811872\",\n" +
            "\t\t\"pageName\": \"The Science Scoop\"\n" +
            "\t}, {\n" +
            "\t\t\"pageID\": \"111815475513565\",\n" +
            "\t\t\"pageName\": \"ScienceDump\"\n" +
            "\t}, {\n" +
            "\t\t\"pageID\": \"126076327412533\",\n" +
            "\t\t\"pageName\": \"Beatrice the Biologist\"\n" +
            "\t}, {\n" +
            "\t\t\"pageID\": \"60342206410\",\n" +
            "\t\t\"pageName\": \"Popular Science\"\n" +
            "\t}, {\n" +
            "\t\t\"pageID\": \"1693692057536686\",\n" +
            "\t\t\"pageName\": \"Sketching Science\"\n" +
            "\t}, {\n" +
            "\t\t\"pageID\": \"167184886633926\",\n" +
            "\t\t\"pageName\": \"The Science Explorer\"\n" +
            "\t}, {\n" +
            "\t\t\"pageID\": \"170493032282\",\n" +
            "\t\t\"pageName\": \"ENGINEERING.com\"\n" +
            "\t}, {\n" +
            "\t\t\"pageID\": \"295830058648\",\n" +
            "\t\t\"pageName\": \"BBC Science News\"\n" +
            "\t}, {\n" +
            "\t\t\"pageID\": \"100864590107\",\n" +
            "\t\t\"pageName\": \"News from Science\"\n" +
            "\t}, {\n" +
            "\t\t\"pageID\": \"184127114954273\",\n" +
            "\t\t\"pageName\": \"ABC Science\"\n" +
            "\t}, {\n" +
            "\t\t\"pageID\": \"105307012882667\",\n" +
            "\t\t\"pageName\": \"The New York Times - Science\"\n" +
            "\t}, {\n" +
            "\t\t\"pageID\": \"1728354754096150\",\n" +
            "\t\t\"pageName\": \"Quora Science\"\n" +
            "\t}]\n" +
            "}]";


}
