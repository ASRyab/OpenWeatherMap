package com.asryab.openweathermap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.asryab.openweathermap.data.CurrentWeather;
import com.asryab.openweathermap.utils.MeteoStation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.Serializable;

public class MapsActivity extends ActionBarActivity implements MeteoStation.MeteoCallback, Serializable {

    private static final String LOG_TAG = "MapsActivity";
    public static int RESULT_CODE_OK =1;
    public static int RESULT_CODE_ERROR =0;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Toolbar mToolbar;
    private CurrentWeather currentWeather=null;
    private TextView mText;
    private ResultReceiver resultReceiver = new ResultReceiver(new Handler()) {
        protected void onReceiveResult(int resultCode, Bundle resultData) {
                if (resultCode==RESULT_CODE_OK){
                    Log.d(LOG_TAG,"success");
                    currentWeather = resultData.getParcelable(MeteoService.BundleKey.CURRENT_WEATHER);
                    setUpMap(currentWeather);
                }
            else if (resultCode==RESULT_CODE_ERROR){
                    Log.d(LOG_TAG,"fail");
                    mText.setText(getResources().getString(R.string.error_city));
                }
        }
    };

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mText =(TextView) findViewById(R.id.text);
        initImageLoader(getApplicationContext());
        initActionBar();
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void initActionBar() {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
//

        actionBar.setCustomView(R.layout.action_bar_search);
        final EditText search = (EditText) actionBar.getCustomView().findViewById(R.id.search_text);
        search.requestFocus();
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Log.d(LOG_TAG, "enter text=" + v.getText().toString());
                    getWeather(v.getText().toString());
//                    queryTextSubmit(v);
                    return true;
                }
                return false;
            }
        });
        ImageButton btnDelText = (ImageButton) actionBar.getCustomView().findViewById(R.id.btn_del_search_text);
        btnDelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setText("");
            }
        });
        actionBar.setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM
                | android.support.v7.app.ActionBar.DISPLAY_SHOW_HOME);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

    }

    private void getWeather(String v) {

        Context context=getApplicationContext();
        Intent intent = new Intent(context, MeteoService.class)
                .setAction(MeteoService.Action.CURRENT_WEATHER_BY_CITY)
                .putExtra(MeteoService.BundleKey.CITY, v)
                .putExtra(MeteoService.BundleKey.CALL_BACK, resultReceiver);
//                .putExtra(MeteoService.BundleKey.COUNTRY, country)
//                .putExtra(MeteoService.BundleKey.PREFERENCE, preferenceKey);
        context.startService(intent);

    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap(CurrentWeather)} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap(currentWeather);
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     * @param currentWeather
     */
    private void setUpMap(CurrentWeather currentWeather) {
        if (currentWeather!=null) {
            Log.d(LOG_TAG, "add marker");
            mText.setText(currentWeather.toStringShow());
            final LatLng latLng = new LatLng(currentWeather.getCoords().getLantitude()
                    , currentWeather.getCoords().getLongitude());
            final MarkerOptions markerOptions =
                    new MarkerOptions().position(latLng).title(currentWeather.toStringShow());
            String urlicon = MeteoStation.ICON_LOAD+"/"+currentWeather.getStateParams().getIcon()+".png";
            Log.d(LOG_TAG,"url to icon="+urlicon);
                    ImageLoader.getInstance().loadImage(urlicon,new SimpleImageLoadingListener(){
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    markerOptions.icon(
                            BitmapDescriptorFactory.fromBitmap(loadedImage));
                    mMap.addMarker(markerOptions);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8.0f));

                }
            });


        }
    }

    @Override
    public void onSuccess(Object result) {
        Log.d(LOG_TAG,"success");
    }

    @Override
    public void onFail(Exception e) {
        Log.d(LOG_TAG, "fail");
    }
}
