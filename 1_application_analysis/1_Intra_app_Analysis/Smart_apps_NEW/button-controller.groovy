/*****************************************************************************************
 *  Copyright 2015 SmartThings
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 *	Button Controller
 *
 *	Author: SmartThings
 *	Date: 2014-5-21
 ********************************************************************************/
definition(
    name: "Button Controller",
    namespace: "smartthings",
    author: "SmartThings",
    description: "Control devices with buttons like the Aeon Labs Minimote",
    category: "Convenience",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/MyApps/Cat-MyApps.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/MyApps/Cat-MyApps@2x.png"
)

preferences {
	
	section("") {
			input "buttonDevice", "capability.button", title: "Button", multiple: false, required: true
		}
	
	section( "title: More options, hidden: hideOptionsSection(), hideable: true") {

			def timeLabel = timeIntervalLabel()

			href "timeIntervalInput", title: "Only during a certain time", description: timeLabel ?: "Tap to set", state: timeLabel ? "complete" : null

			input "days", "enum", title: "Only on certain days of the week", multiple: true, required: false,
				options: ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]

			input "modes", "mode", title: "Only when mode is", multiple: true, required: false
		}
	

		section("Lights") {
			input "lights_1_pushed", "capability.switch", title: "Pushed", multiple: true, required: false
			input "lights_1_held", "capability.switch", title: "Held", multiple: true, required: false
		}
		section("Locks") {
			input "locks_1_pushed", "capability.lock", title: "Pushed", multiple: true, required: false
			input "locks_1_held", "capability.lock", title: "Held", multiple: true, required: false
		}
		section("Sonos") {
			input "sonos_1_pushed", "capability.musicPlayer", title: "Pushed", multiple: true, required: false
			input "sonos_1_held", "capability.musicPlayer", title: "Held", multiple: true, required: false
		}
		section("Modes") {
			input "mode_1_pushed", "mode", title: "Pushed", required: false
			input "mode_1_held", "mode", title: "Held", required: false
		}

			section("Hello Home Actions") {
				log.trace phrases
				input "phrase_1_pushed", "enum", title: "Pushed", required: false, options: phrases
				input "phrase_1_held", "enum", title: "Held", required: false, options: phrases
			}

        section("Sirens") {
            input "sirens_1_pushed","capability.alarm" ,title: "Pushed", multiple: true, required: false
            input "sirens_1_held", "capability.alarm", title: "Held", multiple: true, required: false
        }

		section("Custom Message") {
			input "textMessage_1", "text", title: "Message", required: false
		}

        section("Push Notifications") {
            input "notifications_1_pushed","bool" ,title: "Pushed", required: false, defaultValue: false
            input "notifications_1_held", "bool", title: "Held", required: false, defaultValue: false
        }

        section("Sms Notifications") {
            input "phone_1_pushed","phone" ,title: "Pushed", required: false
            input "phone_1_held", "phone", title: "Held", required: false
        }



	section("Lights") {
			input "lights_2_pushed", "capability.switch", title: "Pushed", multiple: true, required: false
			input "lights_2_held", "capability.switch", title: "Held", multiple: true, required: false
		}
		section("Locks") {
			input "locks_2_pushed", "capability.lock", title: "Pushed", multiple: true, required: false
			input "locks_2_held", "capability.lock", title: "Held", multiple: true, required: false
		}
		section("Sonos") {
			input "sonos_2_pushed", "capability.musicPlayer", title: "Pushed", multiple: true, required: false
			input "sonos_2_held", "capability.musicPlayer", title: "Held", multiple: true, required: false
		}
		section("Modes") {
			input "mode_2_pushed", "mode", title: "Pushed", required: false
			input "mode_2_held", "mode", title: "Held", required: false
		}


			section("Hello Home Actions") {
				log.trace phrases
				input "phrase_2_pushed", "enum", title: "Pushed", required: false, options: phrases
				input "phrase_2_held", "enum", title: "Held", required: false, options: phrases
			}

        section("Sirens") {
            input "sirens_2_pushed","capability.alarm" ,title: "Pushed", multiple: true, required: false
            input "sirens_2_held", "capability.alarm", title: "Held", multiple: true, required: false
        }

		section("Custom Message") {
			input "textMessage_2", "text", title: "Message", required: false
		}

        section("Push Notifications") {
            input "notifications_2_pushed","bool" ,title: "Pushed", required: false, defaultValue: false
            input "notifications_2_held", "bool", title: "Held", required: false, defaultValue: false
        }

        section("Sms Notifications") {
            input "phone_2_pushed","phone" ,title: "Pushed", required: false
            input "phone_2_held", "phone", title: "Held", required: false
        }


		section("Lights") {
			input "lights_3_pushed", "capability.switch", title: "Pushed", multiple: true, required: false
			input "lights_3_held", "capability.switch", title: "Held", multiple: true, required: false
		}
		section("Locks") {
			input "locks_3_pushed", "capability.lock", title: "Pushed", multiple: true, required: false
			input "locks_3_held", "capability.lock", title: "Held", multiple: true, required: false
		}
		section("Sonos") {
			input "sonos_3_pushed", "capability.musicPlayer", title: "Pushed", multiple: true, required: false
			input "sonos_3_held", "capability.musicPlayer", title: "Held", multiple: true, required: false
		}
		section("Modes") {
			input "mode_3_pushed", "mode", title: "Pushed", required: false
			input "mode_3_held", "mode", title: "Held", required: false
		}

			section("Hello Home Actions") {
				log.trace phrases
				input "phrase_3_pushed", "enum", title: "Pushed", required: false, options: phrases
				input "phrase_3_held", "enum", title: "Held", required: false, options: phrases
			}

        section("Sirens") {
            input "sirens_3_pushed","capability.alarm" ,title: "Pushed", multiple: true, required: false
            input "sirens_3_held", "capability.alarm", title: "Held", multiple: true, required: false
        }

		section("Custom Message") {
			input "textMessage_3", "text", title: "Message", required: false
		}

        section("Push Notifications") {
            input "notifications_3_pushed","bool" ,title: "Pushed", required: false, defaultValue: false
            input "notifications_3_held", "bool", title: "Held", required: false, defaultValue: false
        }

        section("Sms Notifications") {
            input "phone_3_pushed","phone" ,title: "Pushed", required: false
            input "phone_3_held", "phone", title: "Held", required: false
        }


		section("Lights") {
			input "lights_4_pushed", "capability.switch", title: "Pushed", multiple: true, required: false
			input "lights_4_held", "capability.switch", title: "Held", multiple: true, required: false
		}
		section("Locks") {
			input "locks_4_pushed", "capability.lock", title: "Pushed", multiple: true, required: false
			input "locks_4_held", "capability.lock", title: "Held", multiple: true, required: false
		}
		section("Sonos") {
			input "sonos_4_pushed", "capability.musicPlayer", title: "Pushed", multiple: true, required: false
			input "sonos_4_held", "capability.musicPlayer", title: "Held", multiple: true, required: false
		}
		section("Modes") {
			input "mode_4_pushed", "mode", title: "Pushed", required: false
			input "mode_4_held", "mode", title: "Held", required: false
		}

			section("Hello Home Actions") {
				log.trace phrases
				input "phrase_4_pushed", "enum", title: "Pushed", required: false, options: phrases
				input "phrase_4_held", "enum", title: "Held", required: false, options: phrases
			}

        section("Sirens") {
            input "sirens_4_pushed","capability.alarm" ,title: "Pushed", multiple: true, required: false
            input "sirens_4_held", "capability.alarm", title: "Held", multiple: true, required: false
        }

		section("Custom Message") {
			input "textMessage_4", "text", title: "Message", required: false
		}

        section("Push Notifications") {
            input "notifications_4_pushed","bool" ,title: "Pushed", required: false, defaultValue: false
            input "notifications_4_held", "bool", title: "Held", required: false, defaultValue: false
        }

        section("Sms Notifications") {
            input "phone_4_pushed","phone" ,title: "Pushed", required: false
            input "phone_4_held", "phone", title: "Held", required: false
        }

		section("") {
			input "starting", "time", title: "Starting", required: false
			input "ending", "time", title: "Ending", required: false
		}
}


def installed() {
	initialize()
}

def updated() {
	unsubscribe()
	initialize()
}

def initialize() {
	subscribe(buttonDevice, "button", buttonEvent)
}

def configured() {
	return buttonDevice || buttonConfigured(1) || buttonConfigured(2) || buttonConfigured(3) || buttonConfigured(4)
}

def buttonConfigured(idx) {
	return settings["lights_$idx_pushed"] ||
		settings["locks_$idx_pushed"] ||
		settings["sonos_$idx_pushed"] ||
		settings["mode_$idx_pushed"] ||
        settings["notifications_$idx_pushed"] ||
        settings["sirens_$idx_pushed"] ||
        settings["notifications_$idx_pushed"]   ||
        settings["phone_$idx_pushed"]
}

def buttonEvent(evt){
	if(allOk) {
		def buttonNumber = evt.data // why doesn't jsonData work? always returning [:]
		def value = evt.value
		log.debug "buttonEvent: $evt.name = $evt.value ($evt.data)"
		log.debug "button: $buttonNumber, value: $value"

		def recentEvents = buttonDevice.eventsSince(new Date(now() - 3000)).findAll{it.value == evt.value && it.data == evt.data}
		log.debug "Found ${recentEvents.size()?:0} events in past 3 seconds"
		executeHandlers(1, value)
		executeHandlers(2, value)
		executeHandlers(3, value)
		executeHandlers(4, value)

		if(recentEvents.size <= 1){
			switch(buttonNumber) {
				case ~/.*1.*/:
					
					break
				case ~/.*2.*/:
					
					break
				case ~/.*3.*/:
					
					break
				case ~/.*4.*/:
					
					break
			}
		} else {
			log.debug "Found recent button press events for $buttonNumber with value $value"
		}
	}
}

def executeHandlers(buttonNumber, value) { // value on, off 
	log.debug "executeHandlers: $buttonNumber - $value"

	def lights = find('lights', buttonNumber, value)
	if (lights != null) toggle(lights)
	log.debug "toggle: $devices = ${devices*.currentValue('switch')}"

	if (devices*.currentValue('switch').contains('on')) {
		devices.off()
		switch1.off()
	}
	

	else if (devices*.currentValue('switch').contains('off')) {
		devices.on()
		switch1.on()
	}
	

	else if (devices*.currentValue('lock').contains('locked')) {
		devices.unlock()
		lock.unlock()
	}
	

	else if (devices*.currentValue('lock').contains('unlocked')) {
		devices.lock()
		lock.lock()
	}
	
	else if (devices*.currentValue('alarm').contains('off')) {
        devices.siren()
		alarm.siren()
    }
	

	else {
		devices.on()
	}



	def locks = find('locks', buttonNumber, value)
	if (locks != null) toggle(locks)
	log.debug "toggle: $devices = ${devices*.currentValue('switch')}"

	if (devices*.currentValue('switch').contains('on')) {
		devices.off()
	}
	else if (devices*.currentValue('switch').contains('off')) {
		devices.on()
	}
	else if (devices*.currentValue('lock').contains('locked')) {
		devices.unlock()
	}
	else if (devices*.currentValue('lock').contains('unlocked')) {
		devices.lock()
	}
	else if (devices*.currentValue('alarm').contains('off')) {
        devices.siren()
    }
	else {
		devices.on()
	}


	def sonos = find('sonos', buttonNumber, value)
	if (sonos != null) toggle(sonos)
	log.debug "toggle: $devices = ${devices*.currentValue('switch')}"

	if (devices*.currentValue('switch').contains('on')) {
		devices.off()
	}
	else if (devices*.currentValue('switch').contains('off')) {
		devices.on()
	}
	else if (devices*.currentValue('lock').contains('locked')) {
		devices.unlock()
	}
	else if (devices*.currentValue('lock').contains('unlocked')) {
		devices.lock()
	}
	else if (devices*.currentValue('alarm').contains('off')) {
        devices.siren()
    }
	else {
		devices.on()
	}


	def mode = find('mode', buttonNumber, value)
	if (mode != null) changeMode(mode)

	def phrase = find('phrase', buttonNumber, value)
	if (phrase != null) location.helloHome.execute(phrase)

	def textMessage = findMsg('textMessage', buttonNumber)

	def notifications = find('notifications', buttonNumber, value)
	if (notifications?.toBoolean()) sendPush(textMessage ?: "Button $buttonNumber was pressed" )

	def phone = find('phone', buttonNumber, value)
	if (phone != null) sendSms(phone, textMessage ?:"Button $buttonNumber was pressed")

    def sirens = find('sirens', buttonNumber, value)
    if (sirens != null) toggle(sirens)
		log.debug "toggle: $devices = ${devices*.currentValue('switch')}"

	if (devices*.currentValue('switch').contains('on')) {
		devices.off()
	}
	else if (devices*.currentValue('switch').contains('off')) {
		devices.on()
	}
	else if (devices*.currentValue('lock').contains('locked')) {
		devices.unlock()
	}
	else if (devices*.currentValue('lock').contains('unlocked')) {
		devices.lock()
	}
	else if (devices*.currentValue('alarm').contains('off')) {
        devices.siren()
    }
	else {
		devices.on()
	}

}

def find(type, buttonNumber, value) {
	def preferenceName = type + "_" + buttonNumber + "_" + value
	def pref = settings[preferenceName]
	if(pref != null) {
		log.debug "Found: $pref for $preferenceName"
	}

	return pref
}

def findMsg(type, buttonNumber) {
	def preferenceName = type + "_" + buttonNumber
	def pref = settings[preferenceName]
	if(pref != null) {
		log.debug "Found: $pref for $preferenceName"
	}

	return pref
}

def toggle(devices) {
	log.debug "toggle: $devices = ${devices*.currentValue('switch')}"

	if (devices*.currentValue('switch').contains('on')) {
		devices.off()
	}
	else if (devices*.currentValue('switch').contains('off')) {
		devices.on()
	}
	else if (devices*.currentValue('lock').contains('locked')) {
		devices.unlock()
	}
	else if (devices*.currentValue('lock').contains('unlocked')) {
		devices.lock()
	}
	else if (devices*.currentValue('alarm').contains('off')) {
        devices.siren()
    }
	else {
		devices.on()
	}
}

def changeMode(mode) {
	log.debug "changeMode: $mode, location.mode = $location.mode, location.modes = $location.modes"

	if (location.mode != mode && location.modes?.find { it.name == mode }) {
		setLocationMode(mode)
	}
}

// execution filter methods
private getAllOk() {
	modeOk && daysOk && timeOk
}

private getModeOk() {
	def result = !modes || modes.contains(location.mode)
	log.trace "modeOk = $result"
	result
}

private getDaysOk() {
	def result = true
	if (days) {
		def df = new java.text.SimpleDateFormat("EEEE")
		if (location.timeZone) {
			df.setTimeZone(location.timeZone)
		}
		else {
			df.setTimeZone(TimeZone.getTimeZone("America/New_York"))
		}
		def day = df.format(new Date())
		result = days.contains(day)
	}
	log.trace "daysOk = $result"
	result
}

private getTimeOk() {
	def result = true
	if (starting && ending) {
		def currTime = now()
		def start = timeToday(starting, location.timeZone).time
		def stop = timeToday(ending, location.timeZone).time
		result = start < stop ? currTime >= start && currTime <= stop : currTime <= stop || currTime >= start
	}
	log.trace "timeOk = $result"
	result
}

private hhmm(time, fmt = "h:mm a")
{
	def t = timeToday(time, location.timeZone)
	def f = new java.text.SimpleDateFormat(fmt)
	f.setTimeZone(location.timeZone ?: timeZone(time))
	f.format(t)
}

private hideOptionsSection() {
	(starting || ending || days || modes) ? false : true
}

private timeIntervalLabel() {
	(starting && ending) ? hhmm(starting) + "-" + hhmm(ending, "h:mm a z") : ""
}
