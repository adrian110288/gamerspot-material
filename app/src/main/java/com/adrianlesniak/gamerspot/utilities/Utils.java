package com.adrianlesniak.gamerspot.utilities;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.util.Date;

/**
 * Created by Adrian Lesniak on 19-Jun-14.
 */
public class Utils {

    private static DateFormat sDf;
    private static String sDateFormatString;
    private static ConnectivityManager sConnectivityManager;
    private static NetworkInfo sNetworkInfo;
    private static Picasso sPicasso;
    private static OkHttpClient sOkHttpClient;

    static {
        sDf = new DateFormat();
        sDateFormatString = "dd/MM/yyyy hh:mm";
    }

    public static boolean isOnline(Context context) {

        boolean isOnline = false;
        sConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        sNetworkInfo = sConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (sNetworkInfo.isConnected()) {
            isOnline = true;
        } else {
            sNetworkInfo = sConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (sNetworkInfo.isConnected()) {
                isOnline = true;
            }
        }

        return isOnline;
    }

    public static OkHttpClient getsOkHttpClient() {

        if (sOkHttpClient == null) {
            sOkHttpClient = new OkHttpClient();
        }

        return sOkHttpClient;
    }

    public static Picasso getPicassoInstance(Context context) {

        if (sPicasso == null) {
            sPicasso = new Picasso.Builder(context).downloader(new OkHttpDownloader(getsOkHttpClient())).build();
        }

        return sPicasso;
    }

    public static String getFormattedDate(Date dateIn) {

        return sDf.format(sDateFormatString, dateIn).toString();
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


