#  sudo chmod a+rw /dev/ttyACM0
import time
import json
from firebase import firebase
import numpy as np
import serial
	
#firebase_url = "https://projo-1.firebaseio.com/"                  # Add the firebase url.
firebase_url = "https://eheal-35c69.firebaseio.com/"
ser = serial.Serial('/dev/ttyACM0', 115200)               # specify the serial port.
firebase = firebase.FirebaseApplication(firebase_url, None)
pulse_data = []
ECG_data   = []
count = 0
while True :	
	 dataz = ser.readline();
	 print dataz
	 if len(dataz.split(",")) == 3 :
		count += 1
	 	pulse_reading = dataz.split(",")[0]
		pulse_data.append(int(pulse_reading))
	 	ECG_reading_1    = dataz.split(",")[1] 
		#ECG_reading_2    = dataz.split(",")[2][:6]
		ECG_data.append(int(ECG_reading_1))
		#ECG_data.append(int(ECG_reading_2))
	 	current_time = time.strftime('%H:%M:%S')
    	 	current_date = time.strftime('%d-%m-%Y')
		mean_pulse = round(np.average(pulse_data),2)
		mean_ECG   = round(np.average(ECG_data),2)
	 	data = {'Date':current_date,'Time':current_time,'currentPulse':pulse_reading,'currentECG:1':ECG_reading_1,'avgPulse':mean_pulse,'avgECG':mean_ECG}
	 	firebase.post('/Reading : '+ str(count),data)
	 time.sleep(5)
