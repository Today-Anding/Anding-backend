package com.springboot.anding.service.impl;

import com.springboot.anding.config.security.JwtTokenProvider;
import com.springboot.anding.data.dto.request.RequestStory5Dto;
import com.springboot.anding.data.dto.response.ResponseStory5Dto;
import com.springboot.anding.data.entity.Story5;
import com.springboot.anding.data.entity.User;
import com.springboot.anding.data.entity.synopsis.Five;
import com.springboot.anding.data.repository.Story5Repository;
import com.springboot.anding.data.repository.UserRepository;
import com.springboot.anding.data.repository.synopsis.FiveRepository;
import com.springboot.anding.service.Story5Service;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
@Service
@RequiredArgsConstructor
public class Story5ServiceImpl implements Story5Service {
    private final Logger LOGGER = LoggerFactory.getLogger(Story5ServiceImpl.class);
    private final Story5Repository story5Repository;
    private final FiveRepository fiveRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public ResponseStory5Dto saveStory5(RequestStory5Dto requestStory5Dto, HttpServletRequest httpServletRequest) {

        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String account = jwtTokenProvider.getUsername(token);

        User user = userRepository.getByAccount(account);
        Five five = fiveRepository.findById(requestStory5Dto.getFiveId())
                .orElseThrow(() -> new IllegalArgumentException("해당 시놉시스를 찾을 수 없습니다."));

        Story5 story5 = new Story5();
        story5.setUser(user);
        story5.setFive(five);
        story5.setContent(requestStory5Dto.getContent());

        Story5 savedStory5 = story5Repository.save(story5);
        LOGGER.info("[saveStory5] saved story5Id : {}", savedStory5.getStory5_id());
        LOGGER.info("[saveStory5] saved uId : {}", savedStory5.getUser().getUid());
        LOGGER.info("[saveStory5] saved five_id : {}", savedStory5.getFive().getFive_id());

        ResponseStory5Dto responseStory5Dto = new ResponseStory5Dto();
        responseStory5Dto.setStory5_id(savedStory5.getStory5_id());
        responseStory5Dto.setContent(savedStory5.getContent());
        responseStory5Dto.setAuthor(story5.getUser().getNickname());
        return responseStory5Dto;
    }

    @Override
    public ResponseStory5Dto getStory5(Long five_id, Long story5_id) throws Exception {
        Story5 story5 = story5Repository.findByFiveIdAndStory5Id(five_id, story5_id)
                .orElseThrow(() -> new Exception("해당하는 Story5를 찾을 수 없습니다."));

        ResponseStory5Dto responseStory5Dto = new ResponseStory5Dto();
        responseStory5Dto.setStory5_id(story5.getStory5_id());
        responseStory5Dto.setContent(story5.getContent());
        responseStory5Dto.setAuthor(story5.getUser().getNickname());
        return responseStory5Dto;
    }

    @Override
    public void deleteStory5(Long story5_id, HttpServletRequest httpServletRequest) throws Exception {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String account = jwtTokenProvider.getUsername(token);

        LOGGER.info("[deleteStory5] story5 삭재를 진행합니다. account : {}", account);

        if(jwtTokenProvider.validationToken(token)){
            User user = userRepository.getByAccount(account);
            Story5 story5 = story5Repository.getById(story5_id);

            if(user.getUid().equals(story5.getUser().getUid()))
                story5Repository.delete(story5);

            LOGGER.info("[deleteStory5] story5 삭제가 완료되었습니다. account : {}", account);
        }
    }

    @Override
    public long countStory5ForSynopsis(Long five_id, HttpServletRequest httpServletRequest) {
        Five five = fiveRepository.findById(five_id)
                .orElseThrow(() -> new IllegalArgumentException("프롬프트를 찾을 수 없습니다."));
        return story5Repository.countByFive(five);
    }
}
