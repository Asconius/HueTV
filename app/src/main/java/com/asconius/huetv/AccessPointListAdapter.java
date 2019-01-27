package com.asconius.huetv;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.philips.lighting.hue.sdk.PHAccessPoint;

import java.util.List;

public class AccessPointListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<PHAccessPoint> accessPoints;

    private class BridgeListItem {
        private TextView bridgeIp;
        private TextView bridgeMac;
    }

    public AccessPointListAdapter(Context context, List<PHAccessPoint> accessPoints) {
        mInflater = LayoutInflater.from(context);
        this.accessPoints = accessPoints;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        BridgeListItem item;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.selectbridge_item, null);
            item = new BridgeListItem();
            item.bridgeMac = convertView.findViewById(R.id.bridge_mac);
            item.bridgeIp = convertView.findViewById(R.id.bridge_ip);
            convertView.setTag(item);
        } else {
            item = (BridgeListItem) convertView.getTag();
        }
        PHAccessPoint accessPoint = accessPoints.get(position);
        item.bridgeIp.setText(accessPoint.getIpAddress());
        item.bridgeMac.setText(accessPoint.getMacAddress());
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getCount() {
        return accessPoints.size();
    }

    @Override
    public Object getItem(int position) {
        return accessPoints.get(position);
    }

    public void updateData(List<PHAccessPoint> accessPoints) {
        this.accessPoints = accessPoints;
        notifyDataSetChanged();
    }
}
