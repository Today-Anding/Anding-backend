package com.springboot.anding.service.impl;

import com.springboot.anding.config.security.JwtTokenProvider;
import com.springboot.anding.data.dto.request.RequestStory10Dto;
import com.springboot.anding.data.dto.response.ResponseStory10Dto;
import com.springboot.anding.data.dto.response.ResponseStory5Dto;
import com.springboot.anding.data.entity.Story10;
import com.springboot.anding.data.entity.User;
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

        if (ten.isFinished()) {
            ResponseStory10Dto responseStory10Dto = new ResponseStory10Dto();
            responseStory10Dto.setMessage("이미 완료된 글입니다.");
            return responseStory10Dto;
        }

        int position = (int) (story10Repository.countByTen(ten) + 1);
        Story10 story10 = new Story10();
        story10.setUser(user);
        story10.setTen(ten);
        story10.setContent(requestStory10Dto.getContent());
        story10.setPosition(position);

        Story10 savedStory10 = story10Repository.save(story10);
        LOGGER.info("[saveStory10] saved story10Id : {}", savedStory10.getStory10_id());
        LOGGER.info("[saveStory10] saved uId : {}", savedStory10.getUser().getUid());
        LOGGER.info("[saveStory10] saved ten_id : {}", savedStory10.getTen().getTen_id());
        LOGGER.info("[saveStory10] Number of Story5 for Ten: {}", ten.getStories().size());

        if (ten.getStories().size() >= 4) {
            ten.setFinished(true); // finished를 true로 변경
            tenRepository.save(ten); // 변경된 Five 엔티티 저장
            LOGGER.info("[saveStory10] Ten is now finished: {}", ten.isFinished());
        }

        ResponseStory10Dto responseStory10Dto = new ResponseStory10Dto();
        responseStory10Dto.setStory10_id(savedStory10.getStory10_id());
        responseStory10Dto.setContent(savedStory10.getContent());
        responseStory10Dto.setAuthor(story10.getUser().getNickname());
        responseStory10Dto.setMessage("스토리추가성공~");
        return responseStory10Dto;
    }

    @Override
    public ResponseStory10Dto getStory10(Long ten_id, int position) throws Exception {
        Story10 story10 = story10Repository.findByTenIdAndPosition(ten_id,position)
                .orElseThrow(() -> new Exception("해당하는 Story10를 찾을 수 없습니다."));

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

        long count = story10Repository.countByTen(ten);

        return count+1;
    }

}
