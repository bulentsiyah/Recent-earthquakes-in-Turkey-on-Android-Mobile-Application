package com.bulentsiyah.androidturkiyedekisondepremleruygulamasi;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    ProgressAsyncTask progressAsyncTask;

    public static ListView listView;
    static List<RowItemListView> rowItems = new ArrayList<RowItemListView>();
    CustomBaseAdapterListview adapter = null;
    DatabaseHandler database_cls;
    public static List<EarthQuake> earthQuakeList;
    Gson gson;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.veritabaniGecmis:
                Intent i = new Intent(MainActivity.this, GecmisDepremlerActivity.class);
                startActivity(i);

                break;
        }
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database_cls = new DatabaseHandler(this);
        database_cls.onCreate(database_cls.getWritableDatabase());

        listView = (ListView) findViewById(R.id.listView);
        ColorDrawable divcolor = new ColorDrawable(Color.GRAY);
        listView.setDivider(divcolor);
        listView.setDividerHeight(1);

        progressAsyncTask = new ProgressAsyncTask("",
                "", "");
        progressAsyncTask.execute("");

    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();

    }


    class ProgressAsyncTask extends AsyncTask<String, String, String> {

        ProgressDialog dialog;
        private String Content;

        public ProgressAsyncTask(String b, String bb, String bbb) {

        }

        @Override
        protected void onPreExecute() {
            try {
                dialog = new ProgressDialog(MainActivity.this,
                        R.style.CustomProgressDialog);
                dialog.setMessage("Son Depremler alınırken lütfen bekleyiniz...");
                dialog.setCancelable(false);
                dialog.show();

            } catch (Exception exp) {
                exp.toString();
            }
        }

        protected String doInBackground(String... params) {
            try {

                earthQuakeList = new ArrayList<EarthQuake>();

                try {
                    DefaultHttpClient httpclient = new DefaultHttpClient();
                    HttpGet httppost = new HttpGet("http://www.koeri.boun.edu.tr/sismo/zeqmap/xmlt/son24saat.xml");
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity ht = response.getEntity();

                    BufferedHttpEntity buf = new BufferedHttpEntity(ht);

                    InputStream is = buf.getContent();

                    BufferedReader r = new BufferedReader(
                            new InputStreamReader(is));

                    //StringBuilder total = new StringBuilder();
                    String line;

                    while ((line = r.readLine()) != null) {
                        // total.append(line + "\n");
                        try {
                            if (line.length() != 0) {
                                EarthQuake earthQuake = new EarthQuake();
                                String[] tumsatir = line.split("\t");
                                for (String satirinIci : tumsatir) {
                                    if (satirinIci.contains("name")) {
                                        earthQuake.Datetime = satirinIci.split("=")[1].replace("\"", "");
                                    } else if (satirinIci.contains("lokasyon")) {
                                        earthQuake.Subject = satirinIci.split("=")[1].replace("\"", "");
                                    } else if (satirinIci.contains("lat")) {
                                        earthQuake.Latitude = Double.parseDouble(satirinIci.split("=")[1].replace("\"", ""));
                                    } else if (satirinIci.contains("lng")) {
                                        earthQuake.Longitude = Double.parseDouble(satirinIci.split("=")[1].replace("\"", ""));
                                    } else if (satirinIci.contains("mag")) {
                                        earthQuake.Siddeti = Double.parseDouble(satirinIci.split("=")[1].replace("\"", "").split(" ")[0]);
                                    }

                                }
                                if (earthQuake.Subject != null) {
                                    earthQuake.Details = earthQuake.Datetime;

                                    earthQuakeList.add(earthQuake);
                                }

                            }

                        } catch (Exception exp) {
                            exp.toString();
                        }
                    }

                } catch (Exception ex) {
                    ex.getMessage();
                } finally {
                    return "Tamam";
                }


            } catch (Exception e) {
                e.printStackTrace();
                return "Hata";
            }

        }

        protected void onPostExecute(String UzunIslemSonucu) {

            try {
                dialog.dismiss();
                if (UzunIslemSonucu.equals("Hata")) {
                    handler.sendEmptyMessage(1);
                } else if (UzunIslemSonucu.equals("Tamam")) {
                    handler.sendEmptyMessage(2);
                }

            } catch (Exception exp) {
                handler.sendEmptyMessage(1);
            }
        }
    }


    void ToastRed(String textstr) {
        try {
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_layout_red,
                    (ViewGroup) findViewById(R.id.toast_layout_root_red));

            TextView text = (TextView) layout.findViewById(R.id.text_red);
            text.setText(textstr);

            Toast toast = new Toast(getApplicationContext());

            toast.setGravity(Gravity.CENTER | Gravity.FILL_HORIZONTAL, 0, 0);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
        } catch (Exception ex) {

        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
                if (msg.what == 1) {
                    ToastRed("Son deprem listesi alınırken hata oluştu");

                } else if (msg.what == 2) {

                    rowItems = new ArrayList<RowItemListView>();
                    for (int i = 0; i < earthQuakeList.size(); i++) {
                        EarthQuake group = earthQuakeList.get(i);
                        try {
                            List<String[]> getAllTbTest = database_cls.getAllTbTest();
                            gson = new Gson();
                            Boolean benzerlikVarmi = false;
                            for (int j = 0; j < getAllTbTest.size(); j++) {
                                String[] item = getAllTbTest.get(j);
                                String oldEarthQuakestr = item[1];
                                EarthQuake oldEarthQuake = gson.fromJson(oldEarthQuakestr, EarthQuake.class);
                                if (oldEarthQuake.Datetime.equals(group.Datetime)) {
                                    benzerlikVarmi = true;
                                    continue;
                                }
                            }
                            if (!benzerlikVarmi) {
                                database_cls.addTbTest(gson.toJson(group), System.currentTimeMillis());
                            }
                        } catch (Exception exp) {
                            exp.toString();
                        }
                    }
                    for (int i = earthQuakeList.size() - 1; i >= 0; i--) {
                        EarthQuake group = earthQuakeList.get(i);

                        int resource = R.drawable.deprem4menu;
                        try {
                            if (group.Siddeti > 4.0) {
                                resource = R.drawable.deprem4menu;
                            } else if (group.Siddeti > 3.0) {
                                resource = R.drawable.deprem3menu;
                            } else if (group.Siddeti > 2.0) {
                                resource = R.drawable.deprem2menu;
                            } else if (group.Siddeti > 1.0) {
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
