package com.makichanov.bassistant.controller.ajax.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.makichanov.bassistant.controller.ajax.AjaxCommand;
import com.makichanov.bassistant.controller.command.*;
import com.makichanov.bassistant.exception.ServiceException;
import com.makichanov.bassistant.model.entity.User;
import com.makichanov.bassistant.model.service.UserService;
import com.makichanov.bassistant.model.service.impl.UserServiceImpl;
import com.makichanov.bassistant.model.util.validator.ParameterValidator;
import com.makichanov.bassistant.model.util.validator.impl.ParameterValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class DefineUsernameIsFreeAjaxCommand implements AjaxCommand {

    private static final Logger LOG = LogManager.getLogger();
    private static final String EMPTY_RESULT = "{}";

    @Override
    public String execute(HttpServletRequest request) {
        String username = request.getParameter(RequestParameter.USERNAME);
        ParameterValidator validator = ParameterValidatorImpl.getInstance();
        if (validator.validateUsername(username)) {
            UserService service = UserServiceImpl.getInstance();
            Optional<User> requestedUser;
            try {
                requestedUser = service.findByUsername(username);
            } catch (ServiceException e) {
                LOG.error("Failed to find user by username " + username, e);
                return EMPTY_RESULT;
            }
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                User currentUser = (User) request.getSession().getAttribute(SessionAttribute.USER);
                Boolean isFree = requestedUser.map(user -> user.getUserId() == currentUser.getUserId()).orElse(true);
                return objectMapper.writeValueAsString(isFree);
            } catch (JsonProcessingException e) {
                return EMPTY_RESULT;
            }
        } else {
            return EMPTY_RESULT;
        }
    }
}
