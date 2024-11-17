package com.github.lltal.laser_manager;

import com.fazecast.jSerialComm.SerialPort;
import org.springframework.context.ApplicationEvent;

public class PortPreparedEvent extends ApplicationEvent {
    public PortPreparedEvent(SerialPort port) {
        super(port);
    }
}
