import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLClientInfoException;
imoport static android.R.attr.version;
/**
 * Created by Harika Konagala on 3/7/2017.
 */

public class dbhelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "locationdb";
    private static final int DATABASE_VERSION = 1;
    private String CREATE_TABLE = "create table location(ID"
    public dbhelper(Context context){
        super(context,name, null, version);
    }

    public void onCreate()
}
