package com.github.lltal.laser_manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommandMap {
    Map<Integer, Command> commands = new HashMap<>();

    public void registerCommand(int commandId, Command command) {
        commands.put(commandId, command);
    }

    public Command getCommand(int commandId) {
        return commands.get(commandId);
    }
}
