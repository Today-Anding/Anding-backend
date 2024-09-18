package com.springboot.anding.service.impl;

import com.springboot.anding.config.security.JwtTokenProvider;
import com.springboot.anding.data.dto.request.RequestStory15Dto;
import com.springboot.anding.data.dto.response.ResponseStory15Dto;
import com.springboot.anding.data.dto.response.ResponseStory15ListDto;
import com.springboot.anding.data.dto.response.ResponseStory5Dto;
import com.springboot.anding.data.dto.response.ResponseStory5ListDto;
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
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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

        if (fifteen.isFinished()) {
            ResponseStory15Dto responseStory15Dto = new ResponseStory15Dto();
            responseStory15Dto.setMessage("이미 완료된 글입니다.");
            return responseStory15Dto;
        }

        int position = (int) (story15Repository.countByFifteen(fifteen) + 1);
        Story15 story15 = new Story15();
        story15.setUser(user);
        story15.setFifteen(fifteen);
        story15.setContent(requestStory15Dto.getContent());
        story15.setPosition(position);

        Story15 savedStory15 = story15Repository.save(story15);
        LOGGER.info("[saveStory15] saved story15Id : {}", savedStory15.getStory15_id());
        LOGGER.info("[saveStory15] saved uId : {}", savedStory15.getUser().getUid());
        LOGGER.info("[saveStory15] saved fifteen_id : {}", savedStory15.getFifteen().getFifteen_id());
        LOGGER.info("[saveStory15] Number of Story5 for Fifteen: {}", fifteen.getStories().size());

        if (fifteen.getStories().size() >= 4) {
            fifteen.setFinished(true); // finished를 true로 변경
            fifteenRepository.save(fifteen); // 변경된 Fifteen 엔티티 저장
            LOGGER.info("[saveStory15] Fifteen is now finished: {}", fifteen.isFinished());
        }
        ResponseStory15Dto responseStory15Dto = new ResponseStory15Dto();
        responseStory15Dto.setStory15_id(savedStory15.getStory15_id());
        responseStory15Dto.setContent(savedStory15.getContent());
        responseStory15Dto.setAuthor(story15.getUser().getNickname());
        responseStory15Dto.setMessage("스토리추가성공~");
        return responseStory15Dto;
    }

    @Override
    public ResponseStory15Dto getStory15(Long fifteen_id, int position) throws Exception {
        Story15 story15 = story15Repository.findByFifteenIdAndPosition(fifteen_id,position)
                .orElseThrow(() -> new Exception("해당하는 Story15를 찾을 수 없습니다."));

        ResponseStory15Dto responseStory15Dto = new ResponseStory15Dto();
        responseStory15Dto.setStory15_id(story15.getStory15_id());
        responseStory15Dto.setContent(story15.getContent());
        responseStory15Dto.setAuthor(story15.getUser().getNickname());
        return responseStory15Dto;
    }
    @Override
    public ResponseStory15ListDto getCompleteStory15List(Long fifteenId) throws Exception {
        ModelMapper mapper = new ModelMapper();
        List<ResponseStory15Dto> responseStory15DtoList = new ArrayList<>();
        ResponseStory15ListDto responseStory15ListDto = new ResponseStory15ListDto();


        Fifteen fifteen = fifteenRepository.findById(fifteenId)
                .orElseThrow(() -> new Exception("Fifteen entity not found"));

        List<Story15> completedStories = story15Repository.findByFifteenAndFinished(fifteen, true);

        for (Story15 story15 : completedStories) {
            ResponseStory15Dto responseStory15Dto = mapper.map(story15, ResponseStory15Dto.class);
            responseStory15Dto.setAuthor(story15.getUser().getNickname());
            responseStory15DtoList.add(responseStory15Dto);
        }

        responseStory15ListDto.setItems(responseStory15DtoList);
        return responseStory15ListDto;
    }

    @Override
    public ResponseStory15ListDto getImcompleteStory15List(Long fifteenId) throws Exception {
        ModelMapper mapper = new ModelMapper();
        List<ResponseStory15Dto> responseStory15DtoList = new ArrayList<>();
        ResponseStory15ListDto responseStory15ListDto = new ResponseStory15ListDto();


        Fifteen fifteen = fifteenRepository.findById(fifteenId)
                .orElseThrow(() -> new Exception("Fifteen entity not found"));

        List<Story15> incompleteStories = story15Repository.findByFifteenAndFinished(fifteen, false);


        for (Story15 story15 : incompleteStories) {
            ResponseStory15Dto responseStory15Dto = mapper.map(story15, ResponseStory15Dto.class);
            responseStory15Dto.setAuthor(story15.getUser().getNickname());
            responseStory15DtoList.add(responseStory15Dto);
        }

        responseStory15ListDto.setItems(responseStory15DtoList);
        return responseStory15ListDto;
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
        long count = story15Repository.countByFifteen(fifteen);
        return count+1;
        }

}
