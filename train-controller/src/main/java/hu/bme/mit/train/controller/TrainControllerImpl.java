package hu.bme.mit.train.controller;

import java.util.concurrent.ScheduledFuture;

import hu.bme.mit.train.interfaces.TrainController;

public class TrainControllerImpl implements TrainController {

	private int step = 0;
	private int referenceSpeed = 0;
	private int speedLimit = 0;
	private boolean winterOperation = false;
	ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    ScheduledFuture<?> future;

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
		future.cancel(true);
		future = executor
        .schedule(followSpeed(), 5, TimeUnit.SECONDS);
	}

	public void setWinterOperation (boolean _winterOperation) {
		winterOperation = _winterOperation;
	}

}
