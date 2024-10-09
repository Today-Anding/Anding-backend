package com.springboot.anding.service.impl;

import com.springboot.anding.config.security.JwtTokenProvider;
import com.springboot.anding.data.dto.request.RequestStarDto;
import com.springboot.anding.data.dto.response.ResponseStarDto;
import com.springboot.anding.data.dto.response.ResponseStarListDto;
import com.springboot.anding.data.entity.*;
import com.springboot.anding.data.entity.synopsis.Fifteen;
import com.springboot.anding.data.entity.synopsis.Five;
import com.springboot.anding.data.entity.synopsis.Ten;
import com.springboot.anding.data.repository.*;
import com.springboot.anding.data.repository.synopsis.FifteenRepository;
import com.springboot.anding.data.repository.synopsis.FiveRepository;
import com.springboot.anding.data.repository.synopsis.TenRepository;
import com.springboot.anding.service.StarService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class StarServiceImpl implements StarService {
    private final StarRepository starRepository;
    private final UserRepository userRepository;
    private final FiveRepository fiveRepository;
    private final TenRepository tenRepository;
    private final FifteenRepository fifteenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final Logger LOGGER = LoggerFactory.getLogger(StarServiceImpl.class);
    @Autowired
    public StarServiceImpl(StarRepository starRepository, UserRepository userRepository, FiveRepository fiveRepository, TenRepository tenRepository, FifteenRepository fifteenRepository, JwtTokenProvider jwtTokenProvider) {
        this.starRepository = starRepository;
        this.userRepository = userRepository;
        this.fiveRepository = fiveRepository;
        this.tenRepository = tenRepository;
        this.fifteenRepository = fifteenRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Override
    public boolean addStarForFive(RequestStarDto requestStarDto, HttpServletRequest servletRequest) throws Exception {
        String token = jwtTokenProvider.resolveToken(servletRequest);
        String account = jwtTokenProvider.getUsername(token);
        User user = userRepository.getByAccount(account);

        Five five = fiveRepository.findById(requestStarDto.getFiveId())
                .orElseThrow(() -> new IllegalArgumentException("단편앤딩을 찾을 수 없습니다."));

        Star existingStar = starRepository.findByUserAndFive(user, five);
        if (existingStar != null) {
            starRepository.delete(existingStar);
            LOGGER.info("[deleteStar] 단편앤딩 읽기목록 삭제되었습니다. account : {}", account);
            return false;
        } else {
            Star star = new Star();
            star.setUser(user);
            star.setFive(five);
            star.setStarType(StarType.FIVE);
            star.setTitle(star.getFive().getTitle());
            star.setCreatedAt(LocalDateTime.now());
            starRepository.save(star);
            LOGGER.info("[addStar] 단편앤딩 읽기목록 추가되었습니다. account : {}", account);
            return true;
        }
    }

    @Override
    public boolean addStarForTen(RequestStarDto requestStarDto, HttpServletRequest servletRequest) throws Exception {
        String token = jwtTokenProvider.resolveToken(servletRequest);
        String account = jwtTokenProvider.getUsername(token);
        User user = userRepository.getByAccount(account);

        Ten ten = tenRepository.findById(requestStarDto.getTenId())
                .orElseThrow(() -> new IllegalArgumentException("중편앤딩을 찾을 수 없습니다."));

        Star existingStar = starRepository.findByUserAndTen(user, ten);
        if (existingStar != null) {
            starRepository.delete(existingStar);
            LOGGER.info("[deleteStar] 중편앤딩 읽기목록 삭제되었습니다. account : {}", account);
            return false;
        } else {
            Star star = new Star();
            star.setUser(user);
            star.setTen(ten);
            star.setStarType(StarType.TEN);
            star.setTitle(star.getTen().getTitle());
            star.setCreatedAt(LocalDateTime.now());
            starRepository.save(star);
            LOGGER.info("[addStar] 중편앤딩 읽기목록 추가되었습니다. account : {}", account);
            return true;
        }
    }
    @Override
    public boolean addStarForFifteen(RequestStarDto requestStarDto, HttpServletRequest servletRequest) throws Exception {
        String token = jwtTokenProvider.resolveToken(servletRequest);
        String account = jwtTokenProvider.getUsername(token);
        User user = userRepository.getByAccount(account);

        Fifteen fifteen = fifteenRepository.findById(requestStarDto.getFifteenId())
                .orElseThrow(() -> new IllegalArgumentException("장편앤딩 읽기목록 찾을 수 없습니다."));

        Star existingStar = starRepository.findByUserAndFifteen(user, fifteen);
        if (existingStar != null) {
            starRepository.delete(existingStar);
            LOGGER.info("[deleteStar] 장편앤딩 읽기목록 삭제되었습니다. account : {}", account);
            return false;
        } else {
            Star star = new Star();
            star.setUser(user);
            star.setFifteen(fifteen);
            star.setStarType(StarType.FIFTEEN);
            star.setCreatedAt(LocalDateTime.now());
            star.setTitle(star.getFifteen().getTitle());
            starRepository.save(star);

            LOGGER.info("[addStar] 장편앤딩 읽기목록 추가되었습니다. account : {}", account);
            return true;
        }
    }
    @Override
    public ResponseStarListDto getTop4RecentStar(HttpServletRequest httpServletRequest) {
        ModelMapper mapper = new ModelMapper();
        List<ResponseStarDto> responseStarDtoList = new ArrayList<>();
        ResponseStarListDto responseStarListDto = new ResponseStarListDto();

        String token = jwtTokenProvider.resolveToken(httpServletRequest);

        if (jwtTokenProvider.validationToken(token)) {
            String account = jwtTokenProvider.getUsername(token);
            User user = userRepository.getByAccount(account);

            List<Star> starList = starRepository.findTop4ByUserOrderByCreatedAtDesc(user);
            for (Star star : starList) {
                ResponseStarDto responseStarDto = mapper.map(star, ResponseStarDto.class);
                responseStarDto.setTitle(star.getTitle());
                responseStarDtoList.add(responseStarDto);
            }
            responseStarListDto.setItems(responseStarDtoList);
        }
        return responseStarListDto;
    }


    /*@Override
    public long countStarForStory5(Long story5Id) {
        Story5 story5 = story5Repository.findById(story5Id)
                .orElseThrow(() -> new IllegalArgumentException("Story5를 찾을 수 없습니다."));
        return starRepository.countByStory5(story5);
    }*/


}
