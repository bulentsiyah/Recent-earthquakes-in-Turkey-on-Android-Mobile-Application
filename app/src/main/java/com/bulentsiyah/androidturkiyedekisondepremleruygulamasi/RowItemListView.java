package com.bulentsiyah.androidturkiyedekisondepremleruygulamasi;

import java.util.Comparator;

public class RowItemListView {
    public EarthQuake content;
    public int imageId1;
    public String text1;
    public String text2;

    public RowItemListView(EarthQuake id, int imageId1, String text1, String text2) {
        this.content = id;
        this.imageId1 = imageId1;
        this.text1 = text1;
        this.text2 = text2;
    }

    public static Comparator<RowItemListView> FruitNameComparator = new Comparator<RowItemListView>() {

        public int compare(RowItemListView fruit1, RowItemListView fruit2) {

            String fruitName1 = fruit1.text1.toUpperCase();
            String fruitName2 = fruit2.text1.toUpperCase();

            // ascending order
            return fruitName1.compareTo(fruitName2);

            // descending order
            // return fruitName2.compareTo(fruitName1);
        }

    };
}