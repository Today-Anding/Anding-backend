package com.springboot.anding.service.impl;

import com.springboot.anding.config.security.JwtTokenProvider;
import com.springboot.anding.data.dto.request.RequestLikedDto;
import com.springboot.anding.data.entity.LikeType;
import com.springboot.anding.data.entity.Liked;
import com.springboot.anding.data.entity.User;
import com.springboot.anding.data.entity.synopsis.Fifteen;
import com.springboot.anding.data.entity.synopsis.Five;
import com.springboot.anding.data.entity.synopsis.Ten;
import com.springboot.anding.data.repository.LikedRepository;
import com.springboot.anding.data.repository.UserRepository;
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
    private final JwtTokenProvider jwtTokenProvider;
    private final Logger LOGGER = LoggerFactory.getLogger(StarServiceImpl.class);

    @Autowired
    public LikedServiceImpl(LikedRepository likedRepository, UserRepository userRepository,
                            FiveRepository fiveRepository, TenRepository tenRepository,
                            FifteenRepository fifteenRepository, JwtTokenProvider jwtTokenProvider) {
        this.likedRepository = likedRepository;
        this.userRepository = userRepository;
        this.fiveRepository = fiveRepository;
        this.tenRepository = tenRepository;
        this.fifteenRepository = fifteenRepository;
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
}
