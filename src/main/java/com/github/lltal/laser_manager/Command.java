package com.github.lltal.laser_manager;

import org.springframework.beans.factory.annotation.Autowired;

public interface Command {
    byte[] getCommandText();

    int getCommandId();

    @Autowired
    default void regMe(CommandMap commandMap) {
        commandMap.registerCommand(getCommandId(), this);
    }
}
