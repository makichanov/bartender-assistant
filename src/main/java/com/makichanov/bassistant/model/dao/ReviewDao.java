package com.makichanov.bassistant.model.dao;

import com.makichanov.bassistant.model.dto.ReviewDto;
import com.makichanov.bassistant.model.entity.Review;
import com.makichanov.bassistant.exception.DaoException;

import java.util.List;

public abstract class ReviewDao extends BaseDao<Integer, Review> {

    public abstract List<ReviewDto> findAllComments(int cocktailId) throws DaoException;

    public abstract List<Review> findByUserId(int userId) throws DaoException;

    public abstract List<Review> findByCocktailId(int cocktailId) throws DaoException;

    public abstract boolean create(int userId, int cocktailId, String comment, int rate) throws DaoException;

    public abstract int countUserCocktailReviews(int userId, int cocktailId) throws DaoException;
}
