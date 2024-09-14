package com.springboot.anding.service.impl.synopsis;

import com.springboot.anding.config.security.JwtTokenProvider;
import com.springboot.anding.controller.synopsis.FiveController;
import com.springboot.anding.data.dto.request.synopsis.RequestFiveDto;
import com.springboot.anding.data.dto.response.synopsis.ResponseFiveDto;
import com.springboot.anding.data.dto.response.synopsis.ResponseFiveListDto;
import com.springboot.anding.data.entity.User;
import com.springboot.anding.data.entity.synopsis.Five;
import com.springboot.anding.data.repository.UserRepository;
import com.springboot.anding.data.repository.synopsis.FiveRepository;
import com.springboot.anding.service.synopsis.FiveService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FiveServiceImpl implements FiveService {

    private final FiveRepository fiveRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final Logger LOGGER = LoggerFactory.getLogger(FiveController.class);


    @Override
    public ResponseFiveDto saveFive(RequestFiveDto requestFiveDto, HttpServletRequest httpServletRequest) {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String account = jwtTokenProvider.getUsername(token);
        User user = userRepository.getByAccount(account);

        Five five = new Five();
        five.setUser(user);
        five.setTitle(requestFiveDto.getTitle());
        five.setDescription(requestFiveDto.getDescription());
        five.setContent(requestFiveDto.getContent());
        five.setThumbnail(requestFiveDto.getThumbnail());

        Five savedFive = fiveRepository.save(five);

        ResponseFiveDto responseFiveDto = new ResponseFiveDto();
        responseFiveDto.setFive_id(savedFive.getFive_id());
        responseFiveDto.setTitle(savedFive.getTitle());
        responseFiveDto.setDescription(savedFive.getDescription());
        responseFiveDto.setContent(savedFive.getContent());
        responseFiveDto.setThumbnail(savedFive.getThumbnail());


        LOGGER.info("[saveFive] 단편 시놉시스 생성완료. account : {}", account);
        return responseFiveDto;
    }

    @Override
    public ResponseFiveDto getFive(Long five_id) throws Exception {
        LOGGER.info("[getFive] 단편 시놉시스 조회를 진행합니다. five_id : {}", five_id);
        Five five = fiveRepository.findById(five_id)
                .orElseThrow(() -> new Exception("단편 시놉시스를 찾을 수 없습니다."));
        ResponseFiveDto responseFiveDto = new ResponseFiveDto();
        responseFiveDto.setFive_id(five.getFive_id());
        responseFiveDto.setTitle(five.getTitle());
        responseFiveDto.setDescription(five.getDescription());
        responseFiveDto.setContent(five.getContent());
        responseFiveDto.setThumbnail(five.getThumbnail());

        LOGGER.info("[getFive] 단편 시놉시스 조회 완료. five_id : {}", five_id);

        return responseFiveDto;
    }

    @Override
    public ResponseFiveListDto getFiveList(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        ModelMapper mapper = new ModelMapper();
        List<ResponseFiveDto> responseFiveDtoList = new ArrayList<>();
        ResponseFiveListDto responseFiveListDto = new ResponseFiveListDto();
        List<Five> fiveList = fiveRepository.findAll();
        for (Five five : fiveList){
            ResponseFiveDto responseFiveDto = mapper.map(five,ResponseFiveDto.class);
            responseFiveDtoList.add(responseFiveDto);
        }
        responseFiveListDto.setItems(responseFiveDtoList);
        return responseFiveListDto;
    }

    @Override
    public void deleteFive(Long five_id, HttpServletRequest httpServletRequest) throws Exception {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String account = jwtTokenProvider.getUsername(token);

        LOGGER.info("[deleteFive] 시놉시스 삭제를 진행합니다. account : {}", account);
        if (jwtTokenProvider.validationToken(token)) {
            User user = userRepository.getByAccount(account);
            Five five = fiveRepository.findById(five_id)
                    .orElseThrow(() -> new Exception("해당 시놉시스를 찾을 수 없습니다."));

            if (user.getUid().equals(five.getUser().getUid())) {
                fiveRepository.delete(five);
            }else {
                LOGGER.info("[deleteFive] 시놉시스 삭제실패. account : {} ", account);
                throw new Exception("해당 시놉시스를 삭제할 권한이 없습니다.");
            }
        }
    }
}
