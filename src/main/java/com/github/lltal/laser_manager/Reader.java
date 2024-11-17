package com.github.lltal.laser_manager;

import com.fazecast.jSerialComm.SerialPort;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reader implements Runnable {
    SerialPort port;

    @EventListener
    public void onContextRefreshEvent(PortPreparedEvent portPreparedEvent) {
        port = (SerialPort) portPreparedEvent.getSource();
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true){
            byte[] receivingPack = new byte[24];
            if (port.bytesAvailable() > 0) {
                System.out.println("bytes available");
                port.readBytes(receivingPack, 24);
                System.out.println(Arrays.toString(receivingPack));
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
    }

}
