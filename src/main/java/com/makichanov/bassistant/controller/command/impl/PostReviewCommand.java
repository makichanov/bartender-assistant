package com.makichanov.bassistant.controller.command.impl;

import com.makichanov.bassistant.controller.command.*;
import com.makichanov.bassistant.controller.manager.JspManager;
import com.makichanov.bassistant.controller.manager.PagePath;
import com.makichanov.bassistant.exception.ServiceException;
import com.makichanov.bassistant.model.entity.Cocktail;
import com.makichanov.bassistant.model.entity.User;
import com.makichanov.bassistant.model.service.CocktailService;
import com.makichanov.bassistant.model.service.ReviewService;
import com.makichanov.bassistant.model.service.impl.CocktailServiceImpl;
import com.makichanov.bassistant.model.service.impl.ReviewServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

// TODO: 10/9/2021 f5 protection
public class PostReviewCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request) {
        String reviewText = request.getParameter(RequestParameter.REVIEW_TEXT);
        String reviewMarkString = request.getParameter(RequestParameter.REVIEW_MARK);
        String cocktailIdString = request.getParameter(RequestParameter.ID);
        int reviewMark = Integer.parseInt(reviewMarkString);
        int cocktailId = Integer.parseInt(cocktailIdString);
        User author = (User) request.getSession().getAttribute(SessionAttribute.USER);
        ReviewService reviewService = ReviewServiceImpl.getInstance();
        CocktailService cocktailService = CocktailServiceImpl.getInstance();
        Optional<Cocktail> reviewTo;
        try {
            reviewTo = cocktailService.findById(cocktailId);
        } catch (ServiceException e) {
            return JspManager.getPage(PagePath.COCKTAILS);
        }
        if (reviewTo.isEmpty()) {
            // TODO: 10/9/2021 go to "cocktail not found" page
            return JspManager.getPage(PagePath.ERROR404);
        }
        request.setAttribute(RequestAttribute.COCKTAIL, reviewTo.get());
        try {
            reviewService.createReview(author.getUserId(), cocktailId, reviewText, reviewMark);
        } catch (ServiceException e) {
            return JspManager.getPage(PagePath.ERROR500);
        }
        return JspManager.getPage(PagePath.SHOW_COCKTAIL);
    }
}