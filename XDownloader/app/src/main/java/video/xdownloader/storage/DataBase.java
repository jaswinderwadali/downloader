package video.xdownloader.storage;

import android.content.Context;

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.util.ArrayList;
import java.util.List;

import video.xdownloader.models.MainModel;

/**
 * Created by jaswinderwadali on 31/07/17.
 */

public class DataBase {
    private static DataBase dataBase = null;

    public static DataBase getInstance() {
        if (dataBase == null)
            dataBase = new DataBase();
        return dataBase;
    }

    private DataBase() {

    }

    String dataKey = "DOWNLOADING_LIST";


    public void insertData(MainModel.DataList commonModels, Context context) {
        try {
            DB snappydb = DBFactory.open(context);
            List<MainModel.DataList> commonModels1 = new ArrayList<>();
            if (snappydb.countKeys(dataKey) == 1) {
                commonModels1 = snappydb.getObject(dataKey, ArrayList.class);
            }
            commonModels1.add(commonModels);
            snappydb.put(dataKey, commonModels1);
            snappydb.close();

        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public List<MainModel.DataList> getDataList(Context context) {
        try {
            DB snappydb = DBFactory.open(context);
            if (snappydb.countKeys(dataKey) == 1) {
                return snappydb.getObject(dataKey, ArrayList.class);
            }
            snappydb.close();

        } catch (SnappydbException e) {

        }
        return null;
    }


}

