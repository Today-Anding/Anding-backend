package com.springboot.anding.service.impl.synopsis;

import com.springboot.anding.config.security.JwtTokenProvider;
import com.springboot.anding.controller.synopsis.FiveController;
import com.springboot.anding.data.dto.request.synopsis.RequestTenDto;
import com.springboot.anding.data.dto.response.synopsis.ResponseFiveDto;
import com.springboot.anding.data.dto.response.synopsis.ResponseFiveListDto;
import com.springboot.anding.data.dto.response.synopsis.ResponseTenDto;
import com.springboot.anding.data.dto.response.synopsis.ResponseTenListDto;
import com.springboot.anding.data.entity.User;
import com.springboot.anding.data.entity.synopsis.Five;
import com.springboot.anding.data.entity.synopsis.Ten;
import com.springboot.anding.data.repository.UserRepository;
import com.springboot.anding.data.repository.synopsis.FiveRepository;
import com.springboot.anding.data.repository.synopsis.TenRepository;
import com.springboot.anding.service.synopsis.TenService;
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
public class TenServiceImpl implements TenService {

    private final TenRepository tenRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final Logger LOGGER = LoggerFactory.getLogger(FiveController.class);

    @Override
    public ResponseTenDto saveTen(RequestTenDto requestTenDto, HttpServletRequest httpServletRequest) {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String account = jwtTokenProvider.getUsername(token);
        User user = userRepository.getByAccount(account);

        Ten ten = new Ten();
        ten.setUser(user);
        ten.setTitle(requestTenDto.getTitle());
        ten.setDescription(requestTenDto.getDescription());
        ten.setContent(requestTenDto.getContent());
        ten.setThumbnail(requestTenDto.getThumbnail());

        Ten savedTen = tenRepository.save(ten);

        ResponseTenDto responseTenDto = new ResponseTenDto();
        responseTenDto.setTen_id(savedTen.getTen_id());
        responseTenDto.setTitle(savedTen.getTitle());
        responseTenDto.setDescription(savedTen.getDescription());
        responseTenDto.setContent(savedTen.getContent());
        responseTenDto.setThumbnail(savedTen.getThumbnail());

    LOGGER.info("[saveTen] 중편 시놉시스 생성완료. account : {}", account);
        return responseTenDto;
    }

    @Override
    public ResponseTenDto getTen(Long ten_id) throws Exception{
        LOGGER.info("[getTen] 중편 시놉시스 조회를 진행합니다. ten_id : {}", ten_id);
        Ten ten = tenRepository.findById(ten_id)
                .orElseThrow(() -> new Exception("중편 시놉시스를 찾을 수 없습니다."));
        ResponseTenDto responseTenDto = new ResponseTenDto();
        responseTenDto.setTen_id(ten.getTen_id());
        responseTenDto.setTitle(ten.getTitle());
        responseTenDto.setDescription(ten.getDescription());
        responseTenDto.setContent(ten.getContent());
        responseTenDto.setThumbnail(ten.getThumbnail());

        LOGGER.info("[getTen] 중편 시놉시스 완료. ten_id : {}", ten_id);

        return responseTenDto;
    }

    @Override
    public ResponseTenListDto getTenList(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        ModelMapper mapper = new ModelMapper();
        List<ResponseTenDto> responseTenDtoList = new ArrayList<>();
        ResponseTenListDto responseTenListDto = new ResponseTenListDto();
        List<Ten> tenList = tenRepository.findByFinishedTrue();
        for (Ten ten : tenList){
            ResponseTenDto responseTenDto = mapper.map(ten,ResponseTenDto.class);
            responseTenDtoList.add(responseTenDto);
        }
        responseTenListDto.setItems(responseTenDtoList);
        return responseTenListDto;
    }

    @Override
    public void deleteTen(Long ten_id, HttpServletRequest httpServletRequest) throws Exception {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String account = jwtTokenProvider.getUsername(token);

        LOGGER.info("[deleteTen] 시놉시스 삭제를 진행합니다. account : {}", account);
        if (jwtTokenProvider.validationToken(token)) {
            User user = userRepository.getByAccount(account);
            Ten ten = tenRepository.findById(ten_id)
                    .orElseThrow(() -> new Exception("해당 시놉시스를 찾을 수 없습니다."));

            if (user.getUid().equals(ten.getUser().getUid())) {
                tenRepository.delete(ten);
            }else {
                LOGGER.info("[deleteTen] 시놉시스 삭제실패. account : {} ", account);
                throw new Exception("해당 시놉시스를 삭제할 권한이 없습니다.");
            }
        }
    }
}
