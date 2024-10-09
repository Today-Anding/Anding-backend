package com.springboot.anding.service;

import com.springboot.anding.data.dto.request.RequestLikedDto;

import javax.servlet.http.HttpServletRequest;

public interface LikedService {
    boolean addLikeForFive(RequestLikedDto requestLikedDto, HttpServletRequest servletRequest) throws Exception;

    boolean addLikeForTen(RequestLikedDto requestLikedDto, HttpServletRequest servletRequest) throws Exception;

    boolean addLikeForFifteen(RequestLikedDto requestLikedDto, HttpServletRequest servletRequest) throws Exception;

    boolean addLikeForWriterForStory5(RequestLikedDto requestLikedDto, HttpServletRequest servletRequest) throws Exception;


    boolean addLikeForWriterForStory10(RequestLikedDto requestLikedDto, HttpServletRequest servletRequest) throws Exception;

    boolean addLikeForWriterForStory15(RequestLikedDto requestLikedDto, HttpServletRequest servletRequest) throws Exception;
}