package com.makichanov.bassistant.controller.command.impl;

import com.makichanov.bassistant.controller.command.*;
import com.makichanov.bassistant.controller.command.JspManager;
import com.makichanov.bassistant.controller.command.PagePath;
import com.makichanov.bassistant.controller.util.validator.ParameterRegexp;
import com.makichanov.bassistant.exception.ServiceException;
import com.makichanov.bassistant.model.entity.Cocktail;
import com.makichanov.bassistant.model.entity.User;
import com.makichanov.bassistant.model.service.CocktailService;
import com.makichanov.bassistant.model.service.impl.CocktailServiceImpl;
import com.makichanov.bassistant.controller.util.validator.ParameterValidator;
import com.makichanov.bassistant.controller.util.validator.impl.ParameterValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class UpdateCocktailPageCommand implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        String idParam = request.getParameter(RequestParameter.ID);
        ParameterValidator validator = ParameterValidatorImpl.getInstance();
        if (validator.validatePositiveInt(idParam)) {
            int id = Integer.parseInt(idParam);
            User author = (User) request.getSession().getAttribute(SessionAttribute.USER);
            CocktailService service = CocktailServiceImpl.getInstance();
            Optional<Cocktail> toUpdate;
            try {
                toUpdate = service.findById(id);
            } catch (ServiceException e) {
                LOG.error("Failed to find cocktail by id " + id, e);
                request.setAttribute(RequestAttribute.ERROR_MESSAGE, ExceptionUtils.getStackTrace(e));
                return new CommandResult(JspManager.getPage(PagePath.ERROR500), CommandResult.RoutingType.FORWARD);
            }
            if (toUpdate.isEmpty()) {
                return new CommandResult(JspManager.getPage(PagePath.ERROR404), CommandResult.RoutingType.FORWARD);
            }
            Cocktail cocktail = toUpdate.get();
            if (cocktail.getUserId() != author.getUserId()) {
                request.setAttribute(RequestAttribute.ERROR_MESSAGE, "Sorry, you don't have enough permission to visit this page.");
                return new CommandResult(JspManager.getPage(PagePath.LOGIN), CommandResult.RoutingType.FORWARD);
            }
            request.setAttribute(RequestAttribute.COCKTAIL_NAME_REGEXP, ParameterRegexp.COCKTAIL_NAME_REGEXP);
            request.setAttribute(RequestAttribute.COCKTAIL, cocktail);
            return new CommandResult(JspManager.getPage(PagePath.EDIT_COCKTAIL), CommandResult.RoutingType.FORWARD);
        } else {
            return new CommandResult(JspManager.getPage(PagePath.ERROR400), CommandResult.RoutingType.FORWARD);
        }
    }
}
