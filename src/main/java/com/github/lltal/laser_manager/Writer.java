package com.github.lltal.laser_manager;

import com.fazecast.jSerialComm.SerialPort;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class Writer implements Runnable {
    final CommandMap commandMap;
    SerialPort port;

    @EventListener
    public void onContextRefreshEvent(PortPreparedEvent portPreparedEvent) {
        port = (SerialPort) portPreparedEvent.getSource();
        new Thread(this).start();
    }

    @Override
    public void run() {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

        while (true){
            try {
                String inputLine = consoleReader.readLine();

                if (inputLine.equals("exit"))
                    return;
                if (!isInteger(inputLine))
                    continue;
                System.out.println("last error code: " + port.getLastErrorCode());
                System.out.println("last error location:" + port.getLastErrorLocation());

                int commandId = Integer.parseInt(inputLine);
                byte[] commandText = commandMap.getCommand(commandId).getCommandText();
                commandText = ("$1;\r").getBytes(StandardCharsets.US_ASCII);
                OutputStream outputStream = port.getOutputStream();
                outputStream.write(commandText);

                System.out.println("write: " + Arrays.toString(commandText));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean isInteger(String inputLine) {
        try {
            Integer.parseInt(inputLine);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
