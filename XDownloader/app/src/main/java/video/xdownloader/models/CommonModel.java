package video.xdownloader.models;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jaswinderwadali on 15/07/17.
 */

public class CommonModel  implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("created_time")
    @Expose
    private String createdTime;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("source")
    @Expose
    private String source;

    @SerializedName("path")
    @Expose
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDescription() {
        if (!TextUtils.isEmpty(description))
            description = description.trim();
        else
            description = "name less";

        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        if (TextUtils.isEmpty(name))
            name = "";
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

}
