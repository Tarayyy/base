package hu.bme.mit.train.controller;

import hu.bme.mit.train.interfaces.TrainController;
import java.util.Timer;
import java.util.TimerTask;

public class TrainControllerImpl implements TrainController {

	private int step = 0;
	private int referenceSpeed = 0;
	private int speedLimit = 0;
	private boolean winterOperation = false;
	private boolean alarmState = false;
	public TrainControllerImpl() {
		Timer t = new Timer( );
		t.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				followSpeed();
			}
		}, 1000,5000);
	}

	@Override
	public void followSpeed() {
		if (referenceSpeed < 0) {
			referenceSpeed = 0;
		} else {
		    if(referenceSpeed+step > 0) {
                referenceSpeed += step;
            } else {
		        referenceSpeed = 0;
            }
		}

		enforceSpeedLimit();
	}

	@Override
	public int getReferenceSpeed() {
		return referenceSpeed;
	}

	@Override
	public void setSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
		enforceSpeedLimit();
		
	}

	private void enforceSpeedLimit() {
		if (referenceSpeed > speedLimit) {
			referenceSpeed = speedLimit;
		}
	}

	@Override
	public void setJoystickPosition(int joystickPosition) {
		if (winterOperation) {
			joystickPosition = joystickPosition/2;
		}
		else {
			//do nothing
		}

		this.step = joystickPosition;
	}

	public void setWinterOperation (boolean winterOperation) {
		this.winterOperation = winterOperation;
	}
	public void setAlarmState(boolean alarmState) {
		this.alarmState = alarmState;
	}

	public boolean getAlarmState() {
		return alarmState;
	}
}
