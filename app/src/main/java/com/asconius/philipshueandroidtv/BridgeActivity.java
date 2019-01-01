package com.asconius.philipshueandroidtv;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHMessageType;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHHueError;
import com.philips.lighting.model.PHHueParsingError;

import java.util.List;

public class BridgeActivity extends Activity implements AdapterView.OnItemClickListener {

    private PHHueSDK phHueSDK;
    public static final String TAG = "PhilipsHueAndroidTV";
    private HueSharedPreferences prefs;
    private AccessPointListAdapter adapter;
    private boolean lastSearchWasIPScan = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bridge);
        phHueSDK = PHHueSDK.create();
        phHueSDK.setAppName("PhilipsHueAndroidTV");
        phHueSDK.setDeviceName(android.os.Build.MODEL);
        phHueSDK.getNotificationManager().registerSDKListener(listener);
        adapter = new AccessPointListAdapter(getApplicationContext(), phHueSDK.getAccessPointsFound());
        ListView accessPointList = findViewById(R.id.bridge_list);
        accessPointList.setOnItemClickListener(this);
        accessPointList.setAdapter(adapter);
        prefs = HueSharedPreferences.getInstance(getApplicationContext());
        String lastIpAddress = prefs.getLastConnectedIPAddress();
        String lastUsername = prefs.getUsername();

        if (lastIpAddress !=null && !lastIpAddress.equals("")) {
            PHAccessPoint lastAccessPoint = new PHAccessPoint();
            lastAccessPoint.setIpAddress(lastIpAddress);
            lastAccessPoint.setUsername(lastUsername);

            if (!phHueSDK.isAccessPointConnected(lastAccessPoint)) {
                DialogFactory.getInstance().showProgressDialog(R.string.connecting, BridgeActivity.this);
                phHueSDK.connect(lastAccessPoint);
            }
        } else {
            doBridgeSearch();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.w(TAG, "Inflating home menu");
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.find_new_bridge:
                doBridgeSearch();
                break;
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (listener !=null) {
            phHueSDK.getNotificationManager().unregisterSDKListener(listener);
        }
        phHueSDK.disableAllHeartbeat();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PHAccessPoint accessPoint = (PHAccessPoint) adapter.getItem(position);
        PHBridge connectedBridge = phHueSDK.getSelectedBridge();
        if (connectedBridge != null) {
            String connectedIP = connectedBridge.getResourceCache().getBridgeConfiguration().getIpAddress();
            if (connectedIP != null) {
                phHueSDK.disableHeartbeat(connectedBridge);
                phHueSDK.disconnect(connectedBridge);
            }
        }
        DialogFactory.getInstance().showProgressDialog(R.string.connecting, BridgeActivity.this);
        phHueSDK.connect(accessPoint);
    }

    public void doBridgeSearch() {
        DialogFactory.getInstance().showProgressDialog(R.string.search_progress, BridgeActivity.this);
        PHBridgeSearchManager sm = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
        sm.search(true, true);
    }

    public void startMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private PHSDKListener listener = new PHSDKListener() {
        @Override
        public void onAccessPointsFound(List<PHAccessPoint> accessPoint) {
            Log.w(TAG, "Access Points Found. " + accessPoint.size());
            DialogFactory.getInstance().closeProgressDialog();
            if (accessPoint != null && accessPoint.size() > 0) {
                phHueSDK.getAccessPointsFound().clear();
                phHueSDK.getAccessPointsFound().addAll(accessPoint);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.updateData(phHueSDK.getAccessPointsFound());
                    }
                });
            }
        }

        @Override
        public void onCacheUpdated(List<Integer> arg0, PHBridge bridge) {
            Log.w(TAG, "On CacheUpdated");
        }

        @Override
        public void onBridgeConnected(PHBridge b, String username) {
            phHueSDK.setSelectedBridge(b);
            phHueSDK.enableHeartbeat(b, PHHueSDK.HB_INTERVAL);
            phHueSDK.getLastHeartbeat().put(b.getResourceCache().getBridgeConfiguration() .getIpAddress(), System.currentTimeMillis());
            prefs.setLastConnectedIPAddress(b.getResourceCache().getBridgeConfiguration().getIpAddress());
            prefs.setUsername(username);
            DialogFactory.getInstance().closeProgressDialog();
            startMainActivity();
        }

        @Override
        public void onAuthenticationRequired(PHAccessPoint accessPoint) {
            Log.w(TAG, "Authentication Required.");
            phHueSDK.startPushlinkAuthentication(accessPoint);
            startActivity(new Intent(BridgeActivity.this, PushlinkActivity.class));
        }

        @Override
        public void onConnectionResumed(PHBridge bridge) {
            if (BridgeActivity.this.isFinishing()) {
                return;
            }
            Log.v(TAG, "onConnectionResumed" + bridge.getResourceCache().getBridgeConfiguration().getIpAddress());
            phHueSDK.getLastHeartbeat().put(bridge.getResourceCache().getBridgeConfiguration().getIpAddress(),  System.currentTimeMillis());
            for (int i = 0; i < phHueSDK.getDisconnectedAccessPoint().size(); i++) {
                if (phHueSDK.getDisconnectedAccessPoint().get(i).getIpAddress().equals(bridge.getResourceCache().getBridgeConfiguration().getIpAddress())) {
                    phHueSDK.getDisconnectedAccessPoint().remove(i);
                }
            }
        }

        @Override
        public void onConnectionLost(PHAccessPoint accessPoint) {
            Log.v(TAG, "onConnectionLost : " + accessPoint.getIpAddress());
            if (!phHueSDK.getDisconnectedAccessPoint().contains(accessPoint)) {
                phHueSDK.getDisconnectedAccessPoint().add(accessPoint);
            }
        }

        @Override
        public void onError(int code, final String message) {
            Log.e(TAG, "on Error Called : " + code + ":" + message);

            if (code == PHHueError.NO_CONNECTION) {
                Log.w(TAG, "On No Connection");
            } else if (code == PHHueError.AUTHENTICATION_FAILED || code==PHMessageType.PUSHLINK_AUTHENTICATION_FAILED) {
                DialogFactory.getInstance().closeProgressDialog();
            } else if (code == PHHueError.BRIDGE_NOT_RESPONDING) {
                Log.w(TAG, "Bridge Not Responding . . . ");
                DialogFactory.getInstance().closeProgressDialog();
                BridgeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogFactory.showErrorDialog(BridgeActivity.this, message, R.string.btn_ok);
                    }
                });
            } else if (code == PHMessageType.BRIDGE_NOT_FOUND) {
                if (!lastSearchWasIPScan) {
                    phHueSDK = PHHueSDK.getInstance();
                    PHBridgeSearchManager sm = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
                    sm.search(false, false, true);
                    lastSearchWasIPScan=true;
                } else {
                    DialogFactory.getInstance().closeProgressDialog();
                    BridgeActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogFactory.showErrorDialog(BridgeActivity.this, message, R.string.btn_ok);
                        }
                    });
                }
            }
        }

        @Override
        public void onParsingErrors(List<PHHueParsingError> parsingErrorsList) {
            for (PHHueParsingError parsingError: parsingErrorsList) {
                Log.e(TAG, "ParsingError : " + parsingError.getMessage());
            }
        }
    };
}
