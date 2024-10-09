package com.springboot.anding.service.impl;

import com.springboot.anding.config.security.JwtTokenProvider;
import com.springboot.anding.data.dto.request.RequestLikedDto;
import com.springboot.anding.data.entity.*;
import com.springboot.anding.data.entity.synopsis.Fifteen;
import com.springboot.anding.data.entity.synopsis.Five;
import com.springboot.anding.data.entity.synopsis.Ten;
import com.springboot.anding.data.repository.*;
import com.springboot.anding.data.repository.synopsis.FifteenRepository;
import com.springboot.anding.data.repository.synopsis.FiveRepository;
import com.springboot.anding.data.repository.synopsis.TenRepository;
import com.springboot.anding.service.LikedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class LikedServiceImpl implements LikedService {
    private final LikedRepository likedRepository;
    private final UserRepository userRepository;
    private final FiveRepository fiveRepository;
    private final TenRepository tenRepository;
    private final FifteenRepository fifteenRepository;
    private final Story5Repository story5Repository;
    private final Story10Repository story10Repository;
    private final Story15Repository story15Repository;
    private final JwtTokenProvider jwtTokenProvider;
    private final Logger LOGGER = LoggerFactory.getLogger(StarServiceImpl.class);

    @Autowired
    public LikedServiceImpl(LikedRepository likedRepository, UserRepository userRepository,
                            FiveRepository fiveRepository, TenRepository tenRepository,
                            FifteenRepository fifteenRepository, Story5Repository story5Repository, Story10Repository story10Repository, Story15Repository story15Repository, JwtTokenProvider jwtTokenProvider) {
        this.likedRepository = likedRepository;
        this.userRepository = userRepository;
        this.fiveRepository = fiveRepository;
        this.tenRepository = tenRepository;
        this.fifteenRepository = fifteenRepository;
        this.story5Repository = story5Repository;
        this.story10Repository = story10Repository;
        this.story15Repository = story15Repository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean addLikeForFive(RequestLikedDto requestLikedDto, HttpServletRequest servletRequest) throws Exception {
        String token = jwtTokenProvider.resolveToken(servletRequest);
        String account = jwtTokenProvider.getUsername(token);
        User user = userRepository.getByAccount(account);

        Five five = fiveRepository.findById(requestLikedDto.getFiveId())
                .orElseThrow(() -> new IllegalArgumentException("단편앤딩을 찾을 수 없습니다."));

        Liked existingLiked = likedRepository.findByUserAndFive(user, five);
        if (existingLiked != null) {
            likedRepository.delete(existingLiked);
            LOGGER.info("[deleteLiked] 단편앤딩 좋아요가 삭제되었습니다. account : {}", account);
            return false;
        } else {
            Liked liked = new Liked();
            liked.setUser(user);
            liked.setFive(five);
            //liked.setStarType(StarType.FIVE);
            liked.setLikeType(LikeType.ANDING);
            liked.setTitle(liked.getFive().getTitle());
            likedRepository.save(liked);
            LOGGER.info("[addStar] 단편앤딩 좋아요 추가되었습니다. account : {}", account);
            return true;
        }
    }

    @Override
    public boolean addLikeForTen(RequestLikedDto requestLikedDto, HttpServletRequest servletRequest) throws Exception {
        String token = jwtTokenProvider.resolveToken(servletRequest);
        String account = jwtTokenProvider.getUsername(token);
        User user = userRepository.getByAccount(account);

        Ten ten = tenRepository.findById(requestLikedDto.getTenId())
                .orElseThrow(() -> new IllegalArgumentException("단편앤딩을 찾을 수 없습니다."));

        Liked existingLiked = likedRepository.findByUserAndTen(user, ten);
        if (existingLiked != null) {
            likedRepository.delete(existingLiked);
            LOGGER.info("[deleteLiked] 단편앤딩 좋아요가 삭제되었습니다. account : {}", account);
            return false;
        } else {
            Liked liked = new Liked();
            liked.setUser(user);
            liked.setTen(ten);
            //liked.setStarType(StarType.FIVE);
            liked.setLikeType(LikeType.ANDING);
            liked.setTitle(liked.getTen().getTitle());
            likedRepository.save(liked);
            LOGGER.info("[addStar] 단편앤딩 좋아요 추가되었습니다. account : {}", account);
            return true;
        }
    }

    @Override
    public boolean addLikeForFifteen(RequestLikedDto requestLikedDto, HttpServletRequest servletRequest) throws Exception {
        String token = jwtTokenProvider.resolveToken(servletRequest);
        String account = jwtTokenProvider.getUsername(token);
        User user = userRepository.getByAccount(account);

        Fifteen fifteen = fifteenRepository.findById(requestLikedDto.getFifteenId())
                .orElseThrow(() -> new IllegalArgumentException("단편앤딩을 찾을 수 없습니다."));

        Liked existingLiked = likedRepository.findByUserAndFifteen(user, fifteen);
        if (existingLiked != null) {
            likedRepository.delete(existingLiked);
            LOGGER.info("[deleteLiked] 단편앤딩 좋아요가 삭제되었습니다. account : {}", account);
            return false;
        } else {
            Liked liked = new Liked();
            liked.setUser(user);
            liked.setFifteen(fifteen);
            //liked.setStarType(StarType.FIVE);
            liked.setLikeType(LikeType.ANDING);
            liked.setTitle(liked.getFifteen().getTitle());
            likedRepository.save(liked);
            LOGGER.info("[addStar] 단편앤딩 좋아요 추가되었습니다. account : {}", account);
            return true;
        }
    }


    @Override
    public boolean addLikeForWriterForStory5(RequestLikedDto requestLikedDto, HttpServletRequest servletRequest) throws Exception {
        String token = jwtTokenProvider.resolveToken(servletRequest);
        String account = jwtTokenProvider.getUsername(token);
        User user = userRepository.getByAccount(account);

        Story5 story5 = story5Repository.findById(requestLikedDto.getStory5Id())
                .orElseThrow(() -> new IllegalArgumentException("story5를 찾을 수 없습니다."));
        String writer = story5.getUser().getName();
        Liked existingLiked = likedRepository.findByUserAndStory5(user, story5);
        if (existingLiked != null) {
            likedRepository.delete(existingLiked);
            LOGGER.info("[deleteLiked] 작가 좋아요가 삭제되었습니다. account : {}", account);
            return false;
        } else {
            Liked liked = new Liked();
            liked.setUser(user);
            liked.setWriter(writer);
            liked.setStory5(story5);
            //liked.setStarType(StarType.FIVE);
            liked.setLikeType(LikeType.WRITER);

            likedRepository.save(liked);
            LOGGER.info("[addLiked] 작가 좋아요 추가되었습니다. account : {}", account);
            return true;
        }
    }

    @Override
    public boolean addLikeForWriterForStory10(RequestLikedDto requestLikedDto, HttpServletRequest servletRequest) throws Exception {
        String token = jwtTokenProvider.resolveToken(servletRequest);
        String account = jwtTokenProvider.getUsername(token);
        User user = userRepository.getByAccount(account);

        Story10 story10 = story10Repository.findById(requestLikedDto.getStory10Id())
                .orElseThrow(() -> new IllegalArgumentException("story10를 찾을 수 없습니다."));
        String writer = story10.getUser().getName();
        Liked existingLiked = likedRepository.findByUserAndStory10(user, story10);
        if (existingLiked != null) {
            likedRepository.delete(existingLiked);
            LOGGER.info("[deleteLiked] 작가 좋아요가 삭제되었습니다. account : {}", account);
            return false;
        } else {
            Liked liked = new Liked();
            liked.setUser(user);
            liked.setWriter(writer);
            liked.setStory10(story10);
            //liked.setStarType(StarType.FIVE);
            liked.setLikeType(LikeType.WRITER);

            likedRepository.save(liked);
            LOGGER.info("[addLiked] 작가 좋아요 추가되었습니다. account : {}", account);
            return true;
        }
    }

    @Override
    public boolean addLikeForWriterForStory15(RequestLikedDto requestLikedDto, HttpServletRequest servletRequest) throws Exception {
        String token = jwtTokenProvider.resolveToken(servletRequest);
        String account = jwtTokenProvider.getUsername(token);
        User user = userRepository.getByAccount(account);

        Story15 story15 = story15Repository.findById(requestLikedDto.getStory15Id())
                .orElseThrow(() -> new IllegalArgumentException("story15를 찾을 수 없습니다."));
        String writer = story15.getUser().getName();
        Liked existingLiked = likedRepository.findByUserAndWriter(user, writer);;
        if (existingLiked != null) {
            likedRepository.delete(existingLiked);
            LOGGER.info("[deleteLiked] 작가 좋아요가 삭제되었습니다. account : {}", account);
            return false;
        } else {
            Liked liked = new Liked();
            liked.setUser(user);
            liked.setWriter(writer);
            //liked.setStarType(StarType.FIVE);
            liked.setLikeType(LikeType.WRITER);
            liked.setStory15(story15);
            likedRepository.save(liked);
            LOGGER.info("[addLiked] 작가 좋아요 추가되었습니다. account : {}", account);
            return true;
        }
    }
}
