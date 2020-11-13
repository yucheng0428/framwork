package com.qyai.beaconlib.bean;

import java.util.ArrayList;

public class PositioningDataBean {
    private ArrayList<SensorEventBean> gsensorInfos = new ArrayList<>();
    private ArrayList<ArrayList<Beacon>> rssiData = new ArrayList<>();
    private int direction;

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void addBeaconList(ArrayList<Beacon> beacons) {
        rssiData.add(beacons);
    }

    public void addGsensorEvents(ArrayList<SensorEventBean> events) {
        gsensorInfos.addAll(events);
    }

    public ArrayList<SensorEventBean> getSensorEvents() {
        return gsensorInfos;
    }

    public ArrayList<ArrayList<Beacon>> getBeaconLists() {
        return rssiData;
    }

    public int getBeaconListsSize() {
        return rssiData.size();
    }

    public void removeBeaconList(int index) {
        if (rssiData.size() > index) {
            rssiData.remove(index);
        }
    }
}
