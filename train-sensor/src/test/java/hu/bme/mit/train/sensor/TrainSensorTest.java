package hu.bme.mit.train.sensor;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class TrainSensorTest {
    TrainUser mockTU;
    TrainController mockTC;
    TrainSensor sensor;

    @Before
    public void before() {
        mockTU = mock(TrainUser.class);
        mockTC = mock(TrainController.class);
        sensor = new TrainSensor(mockTC, mockTU);
    }

    @Test
    public void setSpeedLimitNoAlarm() {
        when(mockTC.getReferenceSpeed()).thenReturn(50);
        when(mockTC.setAlarmState(true)).thenReturn(void);
        sensor.setSpeedLimit(55);
        verify(mockTC, times(0)).setAlarmState(true);
    }

    @Test
    public void setSpeedLimitNegativeAlarm() {
        when(mockTC.getReferenceSpeed()).thenReturn(50);
        when(mockTC.setAlarmState(true)).thenReturn(void);
        sensor.setSpeedLimit(-1);
        verify(mockTC, times(1)).setAlarmState(true);
    }

    @Test
    public void setSpeedLimitTooHighAlarm() {
        when(mockTC.getReferenceSpeed()).thenReturn(50);
        when(mockTC.setAlarmState(true)).thenReturn(void);
        sensor.setSpeedLimit(600);
        verify(mockTC, times(1)).setAlarmState(true);
    }


    @Test
    public void setSpeedLimitRelativeTooLowAlarm() {
        when(mockTC.getReferenceSpeed()).thenReturn(150);
        when(mockTC.setAlarmState(true)).thenReturn(void);
        sensor.setSpeedLimit(50);
        verify(mockTC, times(1)).setAlarmState(true);
    }

}
