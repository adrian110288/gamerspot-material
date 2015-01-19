package com.adrianlesniak.gamerspot.utilities;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.adrianlesniak.gamerspot.database.DAO;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by Adrian Lesniak on 19-Jun-14.
 */
public class CommonUtilities {

    private static Context context;
    private static DateFormat df;
    private static String dateFormatString;
    private static DAO dao;
    private static HashMap<String, BitmapDrawable> cachedImages;
    private static HashMap<String, Bitmap> cachedBlurredImages;

    public CommonUtilities(Context c) {

        context = c;
        df = new DateFormat();
        dateFormatString = "dd/MM/yyyy hh:mm";
        dao = new DAO(context);
        cachedImages = new HashMap<String, BitmapDrawable>();
        cachedBlurredImages = new HashMap<String, Bitmap>();
    }

    public static boolean isOnline(Context context) {

        boolean isOnline = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (networkInfo.isConnected()) {
            isOnline = true;
        } else {
            networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (networkInfo.isConnected()) {
                isOnline = true;
            }
        }

        return isOnline;
    }

    public static String getFormattedDate(Date dateIn) {

        return df.format(dateFormatString, dateIn).toString();
    }

    public static DAO getDatabaseAccessor() {
        return dao;
    }

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static int convertDpToPx(Context context, float dp) {
        return Math.round(dp * (context.getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));

    }

    public static int convertPxToDp(float px) {
        return Math.round(px / (Resources.getSystem().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }


}


