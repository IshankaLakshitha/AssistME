package project.sliit.assistme.ItemFinder.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DELL on 7/18/2017.
 */

public class DBhandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="AssistME.db";//db name


    String CREATE_Scedule_Table = "CREATE TABLE " + TABLE_SCEDULE + "(" + SCEDULE_DAY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SCEDULE_COLUM_DAY + " TEXT ,"+SCEDULE_COLUM_ITEMS + " TEXT ,"+SCEDULE_COLUM_Location +" TEXT ,"+SCEDULE_COLUM_Time + " TEXT ,"+SCEDULE_COLUM_TMode +" TEXT "+ ");";
    private static final String TABLE_SCEDULE="scedule";//table name
    private static final String SCEDULE_DAY_ID="id";
    private static final String SCEDULE_COLUM_DAY="day";
    private static final String SCEDULE_COLUM_ITEMS="items";
    private static final String SCEDULE_COLUM_Location="Location";
    private static final String SCEDULE_COLUM_Time="Time";
    private static final String SCEDULE_COLUM_TMode="Tmode";


    String CREATE_ITEM_DETAILS = "CREATE TABLE " + TABLE_ITEM + "(" + ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ITEM_NAME + " TEXT ,"+ITEM_MAC +" TEXT ,"+GPS +" TEXT "+ ");";
    private static final String TABLE_ITEM="Item";//table name
    private static final String ITEM_ID="itemId";
    private static final String ITEM_NAME="itemName";
    private static final String ITEM_MAC="itemMac";
    private static final String GPS="itemLocation";

    String CREATE_ALARM_TABLE = "CREATE TABLE " + TABLE_ALARM + "(" + ALARM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT ," + LOCATION + " TEXT ,"+TRANSPORTATION +" TEXT ,"+TIME +" TEXT ,"+DAY +" TEXT ," + STATE + " TEXT ,"+ MANUALALRMITEMS + " TEXT "+ ");";
    private static final String TABLE_ALARM="Alarm";//table name
    private static final String ALARM_ID="alarmId";
    private static final String NAME="name";
    private static final String LOCATION="location";
    private static final String TRANSPORTATION="transportation";
    private static final String TIME="time";
    private static final String DAY="day";
    private static final String STATE="state";
    private static final String MANUALALRMITEMS="MItems";


    String CREATE_Health_Detail_Table = "CREATE TABLE " + TABLE_HEALTH + "(" + HEALTH_DAY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + HEALTH_COLUM_DATE + " TEXT ,"+HEALTH_COLUM_STEPS +" TEXT "+ ");";
    private static final String TABLE_HEALTH="health";//table name
    private static final String HEALTH_DAY_ID="id";
    private static final String HEALTH_COLUM_DATE="date";
    private static final String HEALTH_COLUM_STEPS="steps";


    String CREATE_User_Details_Table = "CREATE TABLE " + TABLE_USER + "(" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_Day + " TEXT ,"+USER_Age +" TEXT ,"+USER_HEIGHT +" TEXT ,"+ USER_WEIGHT + " TEXT ," + USER_BMI + " TEXT " + ");";
    private static final String TABLE_USER="User";//table name
    private static final String USER_ID="uId";
    private static final String USER_Day="date";
    private static final String USER_Age="age";
    private static final String USER_HEIGHT="uHeight";
    private static final String USER_WEIGHT="uWeight";
    private static final String USER_BMI="uBmi";

    String CREATE_User_Name_Table = "CREATE TABLE " + TABLE_nUSER + "(" + USER_iD + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_Name + " TEXT ,"+USER_Gender +" TEXT ,"+USER_NickName +" TEXT ,"+ RedyTime + " TEXT ," + Location + " TEXT " + ");";
    private static final String TABLE_nUSER="nUser"; //table name
    private static final String USER_iD="uId";
    private static final String USER_Name="uname";
    private static final String USER_Gender="gender";
    private static final String USER_NickName="nkname";
    private static final String RedyTime="rtime";
    private static final String Location="locat";

    public DBhandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    //Create table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_Scedule_Table);
        sqLiteDatabase.execSQL(CREATE_ITEM_DETAILS);
        sqLiteDatabase.execSQL(CREATE_ALARM_TABLE);
        sqLiteDatabase.execSQL(CREATE_Health_Detail_Table);
        sqLiteDatabase.execSQL(CREATE_User_Details_Table);
        sqLiteDatabase.execSQL(CREATE_User_Name_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS"+CREATE_Scedule_Table);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS"+CREATE_ITEM_DETAILS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS"+CREATE_ALARM_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS"+CREATE_Health_Detail_Table);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS"+CREATE_User_Details_Table);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS"+CREATE_User_Name_Table);
        onCreate(sqLiteDatabase);
    }

    //Items
    public String databasetostringSedule(String day){
        String dbString="";
        SQLiteDatabase db= getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_SCEDULE + " WHERE day=?";
        Cursor c =db.rawQuery(query,new String[]{day});
        c.moveToFirst();
        while (!c.isAfterLast()) {
            if(c.getString(c.getColumnIndex("id"))!=null) {
                dbString= c.getString(2);
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }

    //Time
    public String databasetostringTimeSedule(String day){
        String dbString="";
        SQLiteDatabase db= getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_SCEDULE + " WHERE day=?";
        Cursor c =db.rawQuery(query,new String[]{day});
        c.moveToFirst();
        while (!c.isAfterLast()) {
            if(c.getString(c.getColumnIndex("id"))!=null) {
                dbString= c.getString(4);
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }

    //Location
    public String databasetostringLocationSedule(String day){
        String dbString="";
        SQLiteDatabase db= getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_SCEDULE + " WHERE day=?";
        Cursor c =db.rawQuery(query,new String[]{day});
        c.moveToFirst();
        while (!c.isAfterLast()) {
            if(c.getString(c.getColumnIndex("id"))!=null) {
                dbString= c.getString(3);
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }

    //update database
    public boolean updateDataSedule(String day, String items,String loc,String Tim,String Mode){
        SQLiteDatabase sq=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(SCEDULE_COLUM_Location,loc);
        values.put(SCEDULE_COLUM_ITEMS,items);
        values.put(SCEDULE_COLUM_Time,Tim);
        values.put(SCEDULE_COLUM_TMode,Mode);
        sq.update(TABLE_SCEDULE,values,"day=?",new String[]{day});
        return true;
    }

    public void addProductSedule(String day)
    {
        ContentValues values= new ContentValues();
        values.put(SCEDULE_COLUM_DAY,day);
        //values.put(SCEDULE_COLUM_Location,loc);
        SQLiteDatabase db= getWritableDatabase();
        db.insert(TABLE_SCEDULE,null,values);
        db.close();
    }

    public void addProductItem(String ItemName,String mac)
    {
        ContentValues values= new ContentValues();
        values.put(ITEM_NAME,ItemName);
        values.put(ITEM_MAC,mac);
        SQLiteDatabase db= getWritableDatabase();
        db.insert(TABLE_ITEM,null,values);
        db.close();
    }

    public boolean updateDataItemLocation(String MAC, String gps){
        SQLiteDatabase sq=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(GPS,gps);
        sq.update(TABLE_ITEM,values,"itemMac=?",new String[]{MAC});
        return true;
    }

    public boolean updateDataItemName(String MAC, String Name){
        SQLiteDatabase sq=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(ITEM_NAME,Name);
        sq.update(TABLE_ITEM,values,"itemMac=?",new String[]{MAC});
        return true;
    }

    public String databasetostringItem(String Mac){
        String dbString="";
        SQLiteDatabase db= getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ITEM + " WHERE itemMac=?";
        Cursor c =db.rawQuery(query,new String[]{Mac});
        c.moveToFirst();
        while (!c.isAfterLast()) {
            if(c.getString(c.getColumnIndex("itemId"))!=null) {
                dbString= c.getString(3);
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }

    public String SelectAllItems(){
        String dbString="";
        SQLiteDatabase db= getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ITEM ;
        Cursor c =db.rawQuery(query,null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            if(c.getString(c.getColumnIndex("itemId"))!=null) {
                dbString= dbString+"#"+c.getString(2);
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }

    public String SelectAllItemsName(){
        String dbString="";
        SQLiteDatabase db= getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ITEM ;
        Cursor c =db.rawQuery(query,null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            if(c.getString(c.getColumnIndex("itemId"))!=null) {
                dbString= dbString+"#"+c.getString(1);
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }

    public void removeSingleContact(String Mac) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DELETE FROM " + TABLE_ITEM + " WHERE " + ITEM_MAC + "= '" + Mac + "'");
        database.close();
    }

    //alrm methods
    public void addAlarm(String Name, String Location,String Transportation, String Time,String Day, String State,String Items)
    {
        ContentValues values= new ContentValues();
        values.put(NAME,Name);
        values.put(LOCATION,Location);
        values.put(TRANSPORTATION,Transportation);
        values.put(TIME,Time);
        values.put(DAY,Day);
        values.put(STATE,State);
        //values.put(MANUALALRMITEMS,Items);
        SQLiteDatabase db= getWritableDatabase();
        db.insert(TABLE_ALARM,null,values);
        db.close();
    }

    public boolean updateAlarmState(String Name, String State){
        SQLiteDatabase sq=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(STATE,State);
        sq.update(TABLE_ALARM,values,"name=?",new String[]{Name});
        return true;
    }

    public String databasetostringAlarmDetails(String Day){
        String dbString=" ";
        SQLiteDatabase db= getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ALARM + " WHERE day=?";
        Cursor c =db.rawQuery(query,new String[]{Day});
        c.moveToFirst();
        while (!c.isAfterLast()) {
            if(c.getString(c.getColumnIndex("alarmId"))!=null) {
                dbString= c.getString(4)+"@"+c.getString(6);
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }

    public String SelectAllAlarmState(){
        String dbString="";
        SQLiteDatabase db= getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ALARM ;
        Cursor c =db.rawQuery(query,null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            if(c.getString(c.getColumnIndex("alarmId"))!=null) {
                dbString= dbString+"#"+c.getString(6);
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }

    public String SelectAllAlarmName(){
        String dbString="";
        SQLiteDatabase db= getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ALARM ;
        Cursor c =db.rawQuery(query,null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            if(c.getString(c.getColumnIndex("alarmId"))!=null) {
                dbString= dbString+"#"+c.getString(1);
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }

    public String databasetostringAlarm(String Day){
        String dbString="";
        SQLiteDatabase db= getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ALARM + " WHERE day=?";
        Cursor c =db.rawQuery(query,new String[]{Day});
        c.moveToFirst();
        while (!c.isAfterLast()) {
            if(c.getString(c.getColumnIndex("alarmId"))!=null) {
                dbString= c.getString(1)+"#"+c.getString(2)+"#"+c.getString(3);
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }



    public void removeAlarm(String Name) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DELETE FROM " + TABLE_ALARM + " WHERE " + NAME + "= '" + Name + "'");
        database.close();
    }

    public String databasetostringHealth(String day){
        String dbString="";
        SQLiteDatabase db= getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_HEALTH + " WHERE date=?";
        Cursor c =db.rawQuery(query,new String[]{day});
        c.moveToFirst();
        while (!c.isAfterLast()) {
            if(c.getString(c.getColumnIndex("id"))!=null) {
                dbString= c.getString(2);
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }



    public void addHealthDetails(String day)
    {
        ContentValues values= new ContentValues();
        values.put(HEALTH_COLUM_DATE,day);
        SQLiteDatabase db= getWritableDatabase();
        db.insert(TABLE_HEALTH,null,values);
        db.close();
    }

    public boolean updateDataHealth(String day, String steps){
        SQLiteDatabase sq=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(HEALTH_COLUM_STEPS,steps);
        sq.update(TABLE_HEALTH,values,"date=?",new String[]{day});
        return true;
    }

    public void adduserDetails(String day,String age,String uHeight,String uWeight,String uBmi)
    {
        ContentValues values= new ContentValues();
        values.put(USER_Day,day);
        values.put(USER_Age,age);
        values.put(USER_HEIGHT,uHeight);
        values.put(USER_WEIGHT,uWeight);
        values.put(USER_BMI,uBmi);
        SQLiteDatabase db= getWritableDatabase();
        db.insert(TABLE_USER,null,values);
        db.close();
    }

    public String databasetostringUser(String day){
        String dbString="";
        SQLiteDatabase db= getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE date=?";
        Cursor c =db.rawQuery(query,new String[]{day});
        c.moveToFirst();
        while (!c.isAfterLast()) {
            if(c.getString(c.getColumnIndex("uId"))!=null) {
                dbString= c.getString(5);
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }

    public String databasetostringUserName(String Name){
        String dbString="";
        SQLiteDatabase db= getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_nUSER + " WHERE uname=?";
        Cursor c =db.rawQuery(query,new String[]{Name});
        c.moveToFirst();
        while (!c.isAfterLast()) {
            if(c.getString(c.getColumnIndex("uId"))!=null) {
                dbString= c.getString(5);
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }




    public void adduserNameDetails(String uname,String gender,String nkname,String rtime,String locat)
    {
        ContentValues values= new ContentValues();
        values.put(USER_Name,uname);
        values.put(USER_Gender,gender);
        values.put(USER_NickName,nkname);
        values.put(RedyTime,rtime);
        values.put(Location,locat);
        SQLiteDatabase db= getWritableDatabase();
        db.insert(TABLE_nUSER,null,values);
        db.close();
    }
    //update database

}
