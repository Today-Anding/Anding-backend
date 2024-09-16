package com.springboot.anding.service.impl;

import com.springboot.anding.config.security.JwtTokenProvider;
import com.springboot.anding.data.dto.request.RequestStory15Dto;
import com.springboot.anding.data.dto.response.ResponseStory15Dto;
import com.springboot.anding.data.dto.response.ResponseStory5Dto;
import com.springboot.anding.data.entity.Story15;
import com.springboot.anding.data.entity.Story5;
import com.springboot.anding.data.entity.User;
import com.springboot.anding.data.entity.synopsis.Fifteen;
import com.springboot.anding.data.entity.synopsis.Five;
import com.springboot.anding.data.repository.Story15Repository;
import com.springboot.anding.data.repository.UserRepository;
import com.springboot.anding.data.repository.synopsis.FifteenRepository;
import com.springboot.anding.service.Story15Service;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class Story15ServiceImpl implements Story15Service {
    private final Logger LOGGER = LoggerFactory.getLogger(Story15ServiceImpl.class);
    private final Story15Repository story15Repository;
    private final FifteenRepository fifteenRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public ResponseStory15Dto saveStory15(RequestStory15Dto requestStory15Dto, HttpServletRequest httpServletRequest) {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String account = jwtTokenProvider.getUsername(token);

        User user = userRepository.getByAccount(account);
        Fifteen fifteen = fifteenRepository.findById(requestStory15Dto.getFifteenId())
                .orElseThrow(() -> new IllegalArgumentException("해당 시놉시스를 찾을 수 없습니다."));

        Story15 story15 = new Story15();
        story15.setUser(user);
        story15.setFifteen(fifteen);
        story15.setContent(requestStory15Dto.getContent());

        Story15 savedStory15 = story15Repository.save(story15);
        LOGGER.info("[saveStory5] saved story15Id : {}", savedStory15.getStory15_id());
        LOGGER.info("[saveStory5] saved uId : {}", savedStory15.getUser().getUid());
        LOGGER.info("[saveStory5] saved five_id : {}", savedStory15.getFifteen().getFifteen_id());

        ResponseStory15Dto responseStory15Dto = new ResponseStory15Dto();
        responseStory15Dto.setStory15_id(savedStory15.getStory15_id());
        responseStory15Dto.setContent(savedStory15.getContent());
        responseStory15Dto.setAuthor(story15.getUser().getNickname());
        return responseStory15Dto;
    }

    @Override
    public ResponseStory15Dto getStory15(Long fifteen_id, Long story15_id) throws Exception {
        Story15 story15 = story15Repository.findByFifteenIdAndStory15Id(fifteen_id,story15_id)
                .orElseThrow(() -> new Exception("해당하는 Story5를 찾을 수 없습니다."));

        ResponseStory15Dto responseStory15Dto = new ResponseStory15Dto();
        responseStory15Dto.setStory15_id(story15.getStory15_id());
        responseStory15Dto.setContent(story15.getContent());
        responseStory15Dto.setAuthor(story15.getUser().getNickname());
        return responseStory15Dto;
    }

    @Override
    public void deleteStory15(Long story15_id, HttpServletRequest httpServletRequest) throws Exception {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String account = jwtTokenProvider.getUsername(token);

        LOGGER.info("[deleteStory15] story15 삭재를 진행합니다. account : {}", account);

        if(jwtTokenProvider.validationToken(token)){
            User user = userRepository.getByAccount(account);
            Story15 story15 = story15Repository.getById(story15_id);

            if(user.getUid().equals(story15.getUser().getUid()))
                story15Repository.delete(story15);

            LOGGER.info("[deleteStory15] story15 삭제가 완료되었습니다. account : {}", account);
        }
    }

    @Override
    public long countStory15ForSynopsis(Long fifteen_id, HttpServletRequest httpServletRequest) {
        Fifteen fifteen = fifteenRepository.findById(fifteen_id)
                .orElseThrow(() -> new IllegalArgumentException("프롬프트를 찾을 수 없습니다."));
        return story15Repository.countByFifteen(fifteen);
        }

}
