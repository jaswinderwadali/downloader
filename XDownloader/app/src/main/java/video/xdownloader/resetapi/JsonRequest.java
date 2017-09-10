package video.xdownloader.resetapi;

import okhttp3.RequestBody;

/**
 * Created by jaswinderwadali on 23/08/16.
 */
public class JsonRequest {
    public static <T> RequestBody format(T jsonObject) {
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject).toString());
    }
}
