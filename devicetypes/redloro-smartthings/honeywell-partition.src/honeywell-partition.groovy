/**
 *  SmartThings Device Handler: Honeywell Partition
 *
 *  Author: redloro@gmail.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 */
metadata {
  definition (name: "Honeywell Partition", namespace: "platinummassive43262", author: "redloro@gmail.com", vid: "a259c7ac-cf5c-31e3-9ae9-55b118c6f1f0", mnmn: "SmartThingsCommunity") {
    capability "platinummassive43262.alarmState"
    capability "platinummassive43262.longMemo"
    capability "platinummassive43262.disarm"
    capability "platinummassive43262.armAway"
    capability "platinummassive43262.armStay"
    capability "platinummassive43262.armInstant"
    capability "platinummassive43262.armMax"
    capability "platinummassive43262.armNight"
    capability "platinummassive43262.chime"
    capability "platinummassive43262.bypass"
    capability "Health Check"
    capability "Button"
    capability "Alarm"
    capability "Sensor"
    capability "Actuator"
    
    attribute "dscpartition", "enum", ["ready", "notready", "arming", "armedstay", "armedaway", "armedinstant", "armedmax", "alarmcleared", "alarm"]
    
    command "partition"
    command "trigger1"
    command "trigger2"
  }

  tiles(scale: 2) {
    multiAttributeTile(name:"partition", type: "generic", width: 6, height: 4) {
      tileAttribute ("device.dscpartition", key: "PRIMARY_CONTROL") {
        attributeState "ready", label: 'Ready', icon:"st.Home.home2"
        attributeState "notready", label: 'Not Ready', backgroundColor: "#ffcc00", icon:"st.Home.home2"
        attributeState "arming", label: 'Arming', backgroundColor: "#ffcc00", icon:"st.Home.home3"
        attributeState "armedstay", label: 'Armed Stay', backgroundColor: "#79b821", icon:"st.Home.home3"
        attributeState "armedaway", label: 'Armed Away', backgroundColor: "#79b821", icon:"st.Home.home3"
        attributeState "armedinstant", label: 'Armed Instant Stay', backgroundColor: "#79b821", icon:"st.Home.home3"
        attributeState "armedmax", label: 'Armed Instant Away', backgroundColor: "#79b821", icon:"st.Home.home3"
        attributeState "alarmcleared", label: 'Alarm in Memory', backgroundColor: "#ffcc00", icon:"st.Home.home2"
        attributeState "alarm", label: 'Alarm', backgroundColor: "#ff0000", icon:"st.Home.home3"
      }
      tileAttribute ("panelStatus", key: "SECONDARY_CONTROL") {
        attributeState "panelStatus", label:'${currentValue}'
      }
    }

    standardTile("armAwayButton","device.button", width: 2, height: 2, canChangeIcon: true, decoration: "flat") {
      state "default", label: 'Away', action: "armAway", icon: "st.security.alarm.on", backgroundColor: "#79b821"
    }

    standardTile("armStayButton","device.button", width: 2, height: 2, canChangeIcon: true, decoration: "flat") {
      state "default", label: 'Stay', action: "armStay", icon: "st.security.alarm.on", backgroundColor: "#79b821"
    }

    standardTile("armInstantButton","device.button", width: 2, height: 2, canChangeIcon: true, decoration: "flat") {
      state "default", label: 'Instant', action: "armInstant", icon: "st.security.alarm.on", backgroundColor: "#79b821"
    }

    standardTile("disarmButton","device.button", width: 2, height: 2, canChangeIcon: true, decoration: "flat") {
      state "default", label: 'Disarm', action: "disarm", icon: "st.security.alarm.off", backgroundColor: "#C0C0C0"
    }

    standardTile("trigger1Button","device.button", width: 2, height: 2, canChangeIcon: true, decoration: "flat") {
      state "default", label: 'Trigger 1', action: "trigger1", icon: "st.Home.home30"
    }

    standardTile("trigger2Button","device.button", width: 2, height: 2, canChangeIcon: true, decoration: "flat") {
      state "default", label: 'Trigger 2', action: "trigger2", icon: "st.Home.home30"
    }

    standardTile("chimeButton","device.button", width: 2, height: 2, canChangeIcon: true, decoration: "flat") {
      state "default", label: 'Chime', action: "chime", icon: "st.custom.sonos.unmuted"
    }

    standardTile("bypassButton","device.button", width: 2, height: 2, canChangeIcon: true, decoration: "flat") {
      state "default", label: 'Bypass', action: "bypass", icon: "st.locks.lock.unlocked"
    }

    main "partition"

    details(["partition",
             "armAwayButton", "armStayButton", "armInstantButton",
             "disarmButton", "trigger1Button", "trigger2Button",
             "chimeButton", "bypassButton"])
  }

  preferences {
    input name: "bypassZones", type: "text", title: "Bypass Zones", description: "Comma delimited list of zones to bypass", required: false
  }
}

def partition(String state, String alpha) {
  sendEvent (name: "dscpartition", value: "${state}", descriptionText: "${alpha}", displayed: false)
  sendEvent (name: "panelStatus", value: "${alpha}", displayed: false)
  sendEvent (name: "alarmState", value: "${state}", descriptionText: "${alpha}")
  sendEvent (name: "longMemo", value: "${alpha}", displayed: false)
}

def armAway() {
  parent.sendCommandPlugin('/armAway');
}

def armStay() {
  parent.sendCommandPlugin('/armStay');
}

def armInstant() {
  parent.sendCommandPlugin('/armInstant');
}

def disarm() {
  parent.sendCommandPlugin('/disarm');
}

def trigger1() {
  parent.sendCommandPlugin('/trigger/17');
}

def trigger2() {
  parent.sendCommandPlugin('/trigger/18');
}

def chime() {
  parent.sendCommandPlugin('/chime');
}

def bypass() {
  parent.sendCommandPlugin('/bypass/'+settings.bypassZones);
}
