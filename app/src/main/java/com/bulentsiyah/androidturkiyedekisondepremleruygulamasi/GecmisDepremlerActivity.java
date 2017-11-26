package com.bulentsiyah.androidturkiyedekisondepremleruygulamasi;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class GecmisDepremlerActivity extends AppCompatActivity {

    DatabaseHandler database_cls ;
    public  static ListView listView;
    static List<RowItemListView> rowItems = new ArrayList<RowItemListView>();
    CustomBaseAdapterListview adapter = null;
    public static List<EarthQuake> earthQuakeList;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gecmisdepremlerlayout);
        setTitle("Geçmiş Depremler");

        database_cls = new DatabaseHandler(this);
        database_cls.onCreate(database_cls.getWritableDatabase());

        listView = (ListView) findViewById(R.id.listView2);
        ColorDrawable divcolor = new ColorDrawable(Color.RED);
        listView.setDivider(divcolor);
        listView.setDividerHeight(1);

        try {
            android.support.v7.app.ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);

            actionBar.setDisplayShowHomeEnabled(true);
        } catch (Exception ex) {
            ex.toString();
        }
        handler.sendEmptyMessage(2);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        finish();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
           finish();
        } catch (Exception ex) {
            ex.toString();
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        try {
            if (id == android.R.id.home) {
                onBackPressed();
            }
        } catch (Exception ex) {
            ex.toString();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        try {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                onBackPressed();

                return true;
            }

        } catch (Exception ex) {
            ex.toString();
        }

        return super.onKeyDown(keyCode, event);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
                if (msg.what == 2) {
                    earthQuakeList=new ArrayList<EarthQuake>();
                    rowItems = new ArrayList<RowItemListView>();

                    List<String[]> getAllTbTest=database_cls.getAllTbTest();
                    gson=new Gson();

                    for (int j =0;j< getAllTbTest.size(); j++) {
                        String[] item=getAllTbTest.get(j);
                        String oldEarthQuakestr = item[1];
                        EarthQuake oldEarthQuake = gson.fromJson(oldEarthQuakestr, EarthQuake.class);
                        earthQuakeList.add(oldEarthQuake);
                    }

                    for (int i = earthQuakeList.size()-1; i >= 0; i--) {
                        EarthQuake group = earthQuakeList.get(i);


                        int resource=R.drawable.deprem4menu;
                        try {
                            if(group.Siddeti>4.0){
                                resource=R.drawable.deprem4menu;
                            }else if(group.Siddeti>3.0){
                                resource = R.drawable.deprem3menu;
                            }else if(group.Siddeti>2.0){
                                resource = R.drawable.deprem2menu;
                            }else if(group.Siddeti>1.0){
                                resource = R.drawable.deprem1menu;
                            }
                        } catch (Exception ex) {
                            ex.toString();
                        }


                        RowItemListView item = new RowItemListView(
                                group,
                                resource,
                                group.Subject,
                                group.Details);
                        rowItems.add(item);

                    }

                    //int firstPosition = listView.getFirstVisiblePosition();

                    adapter = new CustomBaseAdapterListview(getApplicationContext(), rowItems);
                    listView.setAdapter(adapter);
                    //listView.setSelection(firstPosition);
                    listView.invalidateViews();

                }
            } catch (Exception exp) {

            }

        }
    };

}