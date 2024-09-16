package com.springboot.anding.service.impl;

import com.springboot.anding.config.security.JwtTokenProvider;
import com.springboot.anding.data.dto.request.RequestStory10Dto;
import com.springboot.anding.data.dto.response.ResponseStory10Dto;
import com.springboot.anding.data.dto.response.ResponseStory5Dto;
import com.springboot.anding.data.entity.Story10;
import com.springboot.anding.data.entity.Story5;
import com.springboot.anding.data.entity.User;
import com.springboot.anding.data.entity.synopsis.Five;
import com.springboot.anding.data.entity.synopsis.Ten;
import com.springboot.anding.data.repository.Story10Repository;
import com.springboot.anding.data.repository.UserRepository;
import com.springboot.anding.data.repository.synopsis.TenRepository;
import com.springboot.anding.service.Story10Service;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
@Service
@RequiredArgsConstructor
public class Story10ServiceImpl implements Story10Service {

    private final Logger LOGGER = LoggerFactory.getLogger(Story10ServiceImpl.class);
    private final Story10Repository story10Repository;
    private final TenRepository tenRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public ResponseStory10Dto saveStory10(RequestStory10Dto requestStory10Dto, HttpServletRequest httpServletRequest) {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String account = jwtTokenProvider.getUsername(token);

        User user = userRepository.getByAccount(account);
        Ten ten = tenRepository.findById(requestStory10Dto.getTenId())
                .orElseThrow(() -> new IllegalArgumentException("해당 시놉시스를 찾을 수 없습니다."));

        Story10 story10 = new Story10();
        story10.setUser(user);
        story10.setTen(ten);
        story10.setContent(requestStory10Dto.getContent());

        Story10 savedStory10 = story10Repository.save(story10);
        LOGGER.info("[saveStory10] saved story10Id : {}", savedStory10.getStory10_id());
        LOGGER.info("[saveStory10] saved uId : {}", savedStory10.getUser().getUid());
        LOGGER.info("[saveStory10] saved ten_id : {}", savedStory10.getTen().getTen_id());

        ResponseStory10Dto responseStory10Dto = new ResponseStory10Dto();
        responseStory10Dto.setStory10_id(savedStory10.getStory10_id());
        responseStory10Dto.setContent(savedStory10.getContent());
        responseStory10Dto.setAuthor(story10.getUser().getNickname());
        return responseStory10Dto;
    }

    @Override
    public ResponseStory10Dto getStory10(Long ten_id, Long story10_id) throws Exception {
        Story10 story10 = story10Repository.findByTenIdAndStory10Id(ten_id, story10_id)
                .orElseThrow(() -> new Exception("해당하는 Story5를 찾을 수 없습니다."));

        ResponseStory10Dto responseStory10Dto = new ResponseStory10Dto();
        responseStory10Dto.setStory10_id(story10.getStory10_id());
        responseStory10Dto.setContent(story10.getContent());
        responseStory10Dto.setAuthor(story10.getUser().getNickname());
        return responseStory10Dto;
    }

    @Override
    public void deleteStory10(Long story10_id, HttpServletRequest httpServletRequest) throws Exception {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String account = jwtTokenProvider.getUsername(token);

        LOGGER.info("[deleteStory10] story10 삭재를 진행합니다. account : {}", account);

        if(jwtTokenProvider.validationToken(token)){
            User user = userRepository.getByAccount(account);
            Story10 story10 = story10Repository.getById(story10_id);

            if(user.getUid().equals(story10.getUser().getUid()))
                story10Repository.delete(story10);

            LOGGER.info("[deleteStory10] story10 삭제가 완료되었습니다. account : {}", account);
        }
    }

    @Override
    public long countStory10ForSynopsis(Long ten_id, HttpServletRequest httpServletRequest) {
        Ten ten = tenRepository.findById(ten_id)
                .orElseThrow(() -> new IllegalArgumentException("프롬프트를 찾을 수 없습니다."));
        return story10Repository.countByTen(ten);
    }

}
