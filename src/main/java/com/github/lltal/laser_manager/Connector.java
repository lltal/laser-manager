package com.github.lltal.laser_manager;

import com.fazecast.jSerialComm.SerialPort;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class Connector {
    ApplicationEventPublisher eventPublisher;

    @EventListener
    public void onContextRefreshEvent(ContextRefreshedEvent refreshedEvent) {
        String devicePortName = "cu.usbserial-110";
        SerialPort[] serialPorts = SerialPort.getCommPorts();

        for (int i = 0; i < serialPorts.length; i++) {

            String portName = serialPorts[i].getSystemPortName();
            System.out.println(serialPorts[i].getSystemPortName() + ": " + portName + ": "
                    + i);
            SerialPort port = null;
            if (portName.equals(devicePortName)) {
                try {
                    port = serialPorts[i];
                    port.setBaudRate(57600);
                    port.setParity(SerialPort.NO_PARITY);
                    port.setFlowControl(SerialPort.FLOW_CONTROL_CTS_ENABLED);
                    port.setNumDataBits(8);
                    port.setNumStopBits(1);
                    port.openPort();
                    System.out.println("connected to: " + portName + "[" + i + "]");
                    eventPublisher.publishEvent(new PortPreparedEvent(port));
                } catch (Exception e) {
                    e.printStackTrace();
                    port.closePort();
                }
            }
        }
    }
}
