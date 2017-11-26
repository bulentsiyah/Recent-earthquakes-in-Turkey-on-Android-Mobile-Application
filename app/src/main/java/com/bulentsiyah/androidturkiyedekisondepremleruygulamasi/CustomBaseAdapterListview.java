package com.bulentsiyah.androidturkiyedekisondepremleruygulamasi;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomBaseAdapterListview extends BaseAdapter {

    Context context;
    List<RowItemListView> rowItems;

    private ArrayList<RowItemListView> arraylist;

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        rowItems.clear();
        if (charText.length() == 0) {
            rowItems.addAll(arraylist);
        } else {

        }
        notifyDataSetChanged();
    }

    public CustomBaseAdapterListview(Context context, List<RowItemListView> items) {
        try {
            this.context = context;
            this.rowItems = items;
            this.arraylist = new ArrayList<RowItemListView>();
            this.arraylist.addAll(rowItems);
        } catch (Exception exp) {

        }

    }

    public CustomBaseAdapterListview(TextWatcher textWatcher,
                                     List<RowItemListView> rowItems2) {
    }

    private class ViewHolder {
        ImageView imageView1;
        TextView text1;
        TextView text2;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        RowItemListView rowItem = (RowItemListView) getItem(position);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_iki_yazi_tek_resim,
                    parent, false);
            holder = new ViewHolder();
            holder.text1 = (TextView) convertView
                    .findViewById(R.id.text1);
            holder.text2 = (TextView) convertView
                    .findViewById(R.id.text2);
            holder.imageView1 = (ImageView) convertView.findViewById(R.id.icon1);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }



        holder.text1.setText(rowItem.text1);
        holder.text2.setText(rowItem.text2);

        try {
            holder.imageView1.setImageResource(rowItem.imageId1);

            if(rowItem.content.Siddeti !=null){
                Bitmap bm = BitmapFactory.decodeResource(convertView.getResources(), rowItem.imageId1);

                Config config = bm.getConfig();
                int width = bm.getWidth();
                int height = bm.getHeight();

                Bitmap newImage = Bitmap.createBitmap(width, height, config);

                Canvas c = new Canvas(newImage);
                c.drawBitmap(bm, 0, 0, null);

                Paint paint = new Paint();
                paint.setColor(Color.parseColor("#ffffff"));
                paint.setStyle(Style.FILL);
                paint.setTextSize(40);
                paint.setTextAlign(Align.CENTER);

                int xPos = (c.getWidth() / 2);
                int yPos = (int) ((c.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2)) ;

                c.drawText(String.valueOf(rowItem.content.Siddeti), xPos, yPos , paint);

                holder.imageView1.setImageBitmap(newImage);
            }
        } catch (Exception exp) {

        }


        return convertView;
    }

    @Override
    public int getCount() {
        try {
            return rowItems.size();
        } catch (Exception exp) {
            return 0;
        }

    }

    @Override
    public RowItemListView getItem(int position) {
        try {

        } catch (Exception exp) {

        }
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }
}

