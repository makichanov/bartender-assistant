package com.makichanov.bassistant.controller.command;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@FunctionalInterface
public interface ActionCommand {

    Logger LOG = LogManager.getLogger();

    CommandResult execute(HttpServletRequest request);
}
