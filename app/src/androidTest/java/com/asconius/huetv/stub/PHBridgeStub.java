package com.asconius.huetv.stub;

import com.philips.lighting.hue.listener.PHBridgeConfigurationListener;
import com.philips.lighting.hue.listener.PHGroupListener;
import com.philips.lighting.hue.listener.PHHTTPListener;
import com.philips.lighting.hue.listener.PHLightListener;
import com.philips.lighting.hue.listener.PHRuleListener;
import com.philips.lighting.hue.listener.PHSceneListener;
import com.philips.lighting.hue.listener.PHScheduleListener;
import com.philips.lighting.hue.listener.PHSensorListener;
import com.philips.lighting.hue.listener.PHTimeZoneListener;
import com.philips.lighting.hue.sdk.exception.PHHueInvalidAPIException;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeConfiguration;
import com.philips.lighting.model.PHBridgeResourcesCache;
import com.philips.lighting.model.PHGroup;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;
import com.philips.lighting.model.PHScene;
import com.philips.lighting.model.PHSchedule;
import com.philips.lighting.model.rule.PHRule;
import com.philips.lighting.model.sensor.PHSensor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PHBridgeStub implements PHBridge {

    private List<PHLight> lightList;
    private List<PHLightState> lightStateList = new ArrayList<>();

    public PHBridgeStub() {
        PHLight phLight1 = new PHLight("light1", "1", "", "");
        PHLight phLight2 = new PHLight("light2", "2", "", "");
        PHLight phLight3 = new PHLight("light3", "3", "", "");
        PHLight phLight4 = new PHLight("light4", "4", "", "");
        lightList = Arrays.asList(phLight1, phLight2, phLight3, phLight4);
    }

    public List<PHLightState> getLightStateList() {
        return lightStateList;
    }

    @Override
    public PHBridgeResourcesCache getResourceCache() {
        return new PHBridgeResourcesCache() {
            @Override
            public Map<String, PHLight> getLights() {
                return null;
            }

            @Override
            public List<PHLight> getAllLights() {
                return lightList;
            }

            @Override
            public Map<String, PHGroup> getGroups() {
                return null;
            }

            @Override
            public List<PHGroup> getAllGroups() {
                return null;
            }

            @Override
            public List<PHScene> getAllScenes() {
                return null;
            }

            @Override
            public PHBridgeConfiguration getBridgeConfiguration() {
                return new PHBridgeConfiguration() {
                    @Override
                    public String getIpAddress() {
                        return "192.168.0.1";
                    }
                };
            }

            @Override
            public Map<String, PHSchedule> getSchedules() {
                return null;
            }

            @Override
            public Map<String, PHSensor> getSensors() { return null; }

            @Override
            public Map<String, PHRule> getRules() {
                return null;
            }

            @Override
            public Map<String, PHScene> getScenes() {
                return null;
            }

            @Override
            public List<PHSchedule> getAllSchedules(boolean b) {
                return null;
            }

            @Override
            public List<PHSchedule> getAllTimers(boolean b) {
                return null;
            }

            @Override
            public List<PHSensor> getAllSensors() {
                return null;
            }

            @Override
            public List<PHRule> getAllRules() {
                return null;
            }
        };
    }

    @Override
    public void findNewLights(PHLightListener phLightListener) throws PHHueInvalidAPIException {}

    @Override
    public void updateLight(PHLight phLight, PHLightListener phLightListener) throws PHHueInvalidAPIException {}

    @Override
    public void deleteLight(String s, PHLightListener phLightListener) throws PHHueInvalidAPIException {}

    @Override
    public void deleteScene(String s, PHSceneListener phSceneListener) throws PHHueInvalidAPIException {}

    @Override
    public void updateLightState(String s, PHLightState phLightState, PHLightListener phLightListener) {}

    @Override
    public void updateLightState(PHLight phLight, PHLightState phLightState, PHLightListener phLightListener) {
        lightStateList.add(phLightState);
    }

    @Override
    public void updateLightState(PHLight phLight, PHLightState phLightState) {}

    @Override
    public void createGroup(String s, List<String> list, PHGroupListener phGroupListener) throws PHHueInvalidAPIException {}

    @Override
    public void createGroup(PHGroup phGroup, PHGroupListener phGroupListener) throws PHHueInvalidAPIException {}

    @Override
    public void updateGroup(PHGroup phGroup, PHGroupListener phGroupListener) throws PHHueInvalidAPIException {}

    @Override
    public void deleteGroup(String s, PHGroupListener phGroupListener) throws PHHueInvalidAPIException {}

    @Override
    public void setLightStateForGroup(String s, PHLightState phLightState, PHGroupListener phGroupListener) {}

    @Override
    public void setLightStateForDefaultGroup(PHLightState phLightState) {}

    @Override
    public void setLightStateForGroup(String s, PHLightState phLightState) {}

    @Override
    public void updateBridgeConfigurations(PHBridgeConfiguration phBridgeConfiguration, PHBridgeConfigurationListener phBridgeConfigurationListener) throws PHHueInvalidAPIException {}

    @Override
    public void removeUsername(String s, PHBridgeConfigurationListener phBridgeConfigurationListener) throws PHHueInvalidAPIException {}

    @Override
    public void createSchedule(PHSchedule phSchedule, PHScheduleListener phScheduleListener) throws PHHueInvalidAPIException {}

    @Override
    public void updateSchedule(PHSchedule phSchedule, PHScheduleListener phScheduleListener) throws PHHueInvalidAPIException {}

    @Override
    public void removeSchedule(String s, PHScheduleListener phScheduleListener) throws PHHueInvalidAPIException {}

    @Override
    public void updateSoftware(PHBridgeConfigurationListener phBridgeConfigurationListener) throws PHHueInvalidAPIException {}

    @Override
    public void saveScene(PHScene phScene, PHSceneListener phSceneListener) throws PHHueInvalidAPIException {}

    @Override
    public void activateScene(String s, String s1, PHSceneListener phSceneListener) throws PHHueInvalidAPIException {}

    @Override
    public void saveSceneWithCurrentLightStates(PHScene phScene, PHSceneListener phSceneListener) throws PHHueInvalidAPIException {}

    @Override
    public void findNewLightsWithSerials(List<String> list, PHLightListener phLightListener) throws PHHueInvalidAPIException {}

    @Override
    public void saveLightState(PHLightState phLightState, String s, String s1, PHSceneListener phSceneListener) throws PHHueInvalidAPIException {}

    @Override
    public void findNewSensors(PHSensorListener phSensorListener) throws PHHueInvalidAPIException {}

    @Override
    public void findNewSensorsWithSerials(List<String> list, PHSensorListener phSensorListener) throws PHHueInvalidAPIException {}

    @Override
    public void createSensor(PHSensor phSensor, PHSensorListener phSensorListener) throws PHHueInvalidAPIException {}

    @Override
    public void createRule(PHRule phRule, PHRuleListener phRuleListener) throws PHHueInvalidAPIException {}

    @Override
    public void updateSensor(PHSensor phSensor, PHSensorListener phSensorListener) throws PHHueInvalidAPIException {}

    @Override
    public void updateRule(PHRule phRule, PHRuleListener phRuleListener) throws PHHueInvalidAPIException {}

    @Override
    public void deleteSensor(String s, PHSensorListener phSensorListener) throws PHHueInvalidAPIException {}

    @Override
    public void deleteRule(String s, PHRuleListener phRuleListener) throws PHHueInvalidAPIException {}

    @Override
    public void saveSensorState(PHSensor phSensor, PHSensorListener phSensorListener) throws PHHueInvalidAPIException {}

    @Override
    public void saveSensorConfiguration(PHSensor phSensor, PHSensorListener phSensorListener) throws PHHueInvalidAPIException {}

    @Override
    public void getScene(String s, PHSceneListener phSceneListener) {}

    @Override
    public void getSupportedTimeZones(PHTimeZoneListener phTimeZoneListener) {}

    @Override
    public void doHTTPGet(String s, PHHTTPListener phhttpListener) {}

    @Override
    public void doHTTPPut(String s, String s1, PHHTTPListener phhttpListener) {}

    @Override
    public void doHTTPPost(String s, String s1, PHHTTPListener phhttpListener) {}

    @Override
    public void doHTTPDelete(String s, PHHTTPListener phhttpListener) {}
}
