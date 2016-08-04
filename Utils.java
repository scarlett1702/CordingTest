package kakaobank.project.com.kakaobankproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by scarlett
 */
public class Utils {

    /**
     *
     * @param imgUrlAddr 가져올 이미지 url
     * @return
     */
    public static Bitmap getImageUrlToBitmap(String imgUrlAddr) {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            URL url = new URL(imgUrlAddr);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true); //url로 input받는 flag 허용
            urlConnection.connect();

            inputStream = urlConnection.getInputStream();
            return BitmapFactory.decodeStream(inputStream);
        } catch(Exception e) {
            Log.w(CommonConfig.TAG, "getImageUrlToBitmap : "+e.toString());
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch(Exception e) {}
        }
        return null;
    } // end -- getImageUrlToBitmap


    /**
     *
     * @param imgUrlAddr 가져올 이미지 url
     * @return
     */
    public static String getImageUrlToString(String imgUrlAddr) {
        ByteArrayOutputStream outputStream = null;

        try {
            Bitmap bitmap = getImageUrlToBitmap(imgUrlAddr);

            outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream) ;
            return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
        } catch(Exception e) {
            Log.w(CommonConfig.TAG, "getImageUrlToString : "+e.toString());
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch(Exception e) {}
        }
        return null;
    } // end -- getImageUrlToString


    /**
     *
     * @param imgStr
     * @return
     */
    public static Bitmap stringToBitmap(String imgStr) {
        byte[] imgByte = Base64.decode(imgStr.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
    }
}
