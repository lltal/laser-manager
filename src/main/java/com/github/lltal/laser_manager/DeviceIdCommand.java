package com.github.lltal.laser_manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import static com.github.lltal.laser_manager.Constants.COMMAND_PATTERN;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeviceIdCommand implements Command {
    int COMMAND_ID = 1;

    @Override
    public byte[] getCommandText() {
        return String.format(COMMAND_PATTERN, COMMAND_ID).getBytes();
    }

    @Override
    public int getCommandId() {
        return COMMAND_ID;
    }
}
