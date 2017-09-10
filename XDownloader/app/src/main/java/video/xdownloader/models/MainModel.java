package video.xdownloader.models;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jaswinderwadali on 27/08/17.
 */

public class MainModel implements Serializable {

    @SerializedName("typeName")
    @Expose
    private String typeName;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("dataList")
    @Expose
    private List<DataList> dataList = null;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<DataList> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataList> dataList) {
        this.dataList = dataList;
    }

    public static class DataList extends CommonModel {

        @SerializedName("pageName")
        @Expose
        private String pageName;
        @SerializedName("pageID")
        @Expose
        private String pageID;

        public String getPageName() {
            if (TextUtils.isEmpty(pageName))
                pageName = super.getName();
            return pageName;
        }

        public void setPageName(String pageName) {

            this.pageName = pageName;
        }

        public String getPageID() {
            if (TextUtils.isEmpty(pageID))
                pageID = super.getId();

            return pageID;
        }

        public void setPageID(String pageID) {
            this.pageID = pageID;
        }
    }


}
