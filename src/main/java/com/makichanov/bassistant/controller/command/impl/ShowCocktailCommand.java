package com.makichanov.bassistant.controller.command.impl;

import com.makichanov.bassistant.controller.command.*;
import com.makichanov.bassistant.controller.util.validator.ParameterValidator;
import com.makichanov.bassistant.controller.util.validator.impl.ParameterValidatorImpl;
import com.makichanov.bassistant.exception.ServiceException;
import com.makichanov.bassistant.model.entity.Comment;
import com.makichanov.bassistant.model.entity.Cocktail;
import com.makichanov.bassistant.model.entity.User;
import com.makichanov.bassistant.model.service.CocktailService;
import com.makichanov.bassistant.model.service.ReviewService;
import com.makichanov.bassistant.model.service.UserService;
import com.makichanov.bassistant.model.service.impl.CocktailServiceImpl;
import com.makichanov.bassistant.model.service.impl.ReviewServiceImpl;
import com.makichanov.bassistant.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class ShowCocktailCommand implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        String idParam = request.getParameter(RequestParameter.ID);
        ParameterValidator validator = ParameterValidatorImpl.getInstance();
        if (validator.validatePositiveInt(idParam)) {
            int cocktailId = Integer.parseInt(idParam);
            CocktailService service = CocktailServiceImpl.getInstance();
            Optional<Cocktail> toShow;
            try {
                toShow = service.findById(cocktailId);
            } catch (ServiceException e) {
                LOG.error("Failed to find cocktail by id " + cocktailId, e);
                request.setAttribute(RequestAttribute.ERROR_MESSAGE, ExceptionUtils.getStackTrace(e));
                return new CommandResult(JspManager.getPage(PagePath.ERROR500), CommandResult.RoutingType.FORWARD);
            }
            if (toShow.isPresent()) {
                request.setAttribute(RequestAttribute.COCKTAIL, toShow.get());
            } else {
                return new CommandResult(JspManager.getPage(PagePath.ERROR404), CommandResult.RoutingType.FORWARD);
            }
            UserService userService = UserServiceImpl.getInstance();
            Optional<User> author;
            int authorId = toShow.get().getUserId();
            try {
                author = userService.findById(authorId);
            } catch (ServiceException e) {
                LOG.error("Failed to find cocktail author with id " + authorId, e);
                request.setAttribute(RequestAttribute.ERROR_MESSAGE, ExceptionUtils.getStackTrace(e));
                return new CommandResult(JspManager.getPage(PagePath.ERROR500), CommandResult.RoutingType.FORWARD);
            }
            if (author.isEmpty()) {
                return new CommandResult(JspManager.getPage(PagePath.ERROR404), CommandResult.RoutingType.FORWARD);
            }
            request.setAttribute(RequestAttribute.AUTHOR, author.get());
            ReviewService reviewService = ReviewServiceImpl.getInstance();
            try {
                List<Comment> comments = reviewService.findAllComments(cocktailId);
                request.setAttribute(RequestAttribute.REVIEWS, comments);
            } catch (ServiceException e) {
                LOG.error("Failed to find reviews list for cocktail " + cocktailId, e);
                request.setAttribute(RequestAttribute.ERROR_MESSAGE, ExceptionUtils.getStackTrace(e));
                return new CommandResult(JspManager.getPage(PagePath.ERROR500), CommandResult.RoutingType.FORWARD);
            }
            return new CommandResult(JspManager.getPage(PagePath.SHOW_COCKTAIL), CommandResult.RoutingType.FORWARD);
        } else {
            return new CommandResult(JspManager.getPage(PagePath.ERROR400), CommandResult.RoutingType.FORWARD);
        }
    }
}
