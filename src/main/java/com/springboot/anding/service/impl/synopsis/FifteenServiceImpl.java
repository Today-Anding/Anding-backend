package com.springboot.anding.service.impl.synopsis;

import com.springboot.anding.config.security.JwtTokenProvider;
import com.springboot.anding.controller.synopsis.FiveController;
import com.springboot.anding.data.dto.request.synopsis.RequestFifteenDto;
import com.springboot.anding.data.dto.response.synopsis.ResponseFifteenDto;
import com.springboot.anding.data.dto.response.synopsis.ResponseFifteenListDto;
import com.springboot.anding.data.dto.response.synopsis.ResponseTenDto;
import com.springboot.anding.data.dto.response.synopsis.ResponseTenListDto;
import com.springboot.anding.data.entity.User;
import com.springboot.anding.data.entity.synopsis.Fifteen;
import com.springboot.anding.data.entity.synopsis.Ten;
import com.springboot.anding.data.repository.UserRepository;
import com.springboot.anding.data.repository.synopsis.FifteenRepository;
import com.springboot.anding.data.repository.synopsis.FiveRepository;
import com.springboot.anding.service.synopsis.FifteenService;
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
public class FifteenServiceImpl implements FifteenService {

    private final FifteenRepository fifteenRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final Logger LOGGER = LoggerFactory.getLogger(FiveController.class);

    @Override
    public ResponseFifteenDto saveFifteen(RequestFifteenDto requestFifteenDto, HttpServletRequest httpServletRequest) {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String account = jwtTokenProvider.getUsername(token);
        User user = userRepository.getByAccount(account);

        Fifteen fifteen = new Fifteen();
        fifteen.setUser(user);
        fifteen.setTitle(requestFifteenDto.getTitle());
        fifteen.setDescription(requestFifteenDto.getDescription());
        fifteen.setContent(requestFifteenDto.getContent());
        fifteen.setThumbnail(requestFifteenDto.getThumbnail());

        Fifteen savedFifteen = fifteenRepository.save(fifteen);

        ResponseFifteenDto responseFifteenDto = new ResponseFifteenDto();
        responseFifteenDto.setFifteen_id(savedFifteen.getFifteen_id());
        responseFifteenDto.setTitle(savedFifteen.getTitle());
        responseFifteenDto.setDescription(savedFifteen.getDescription());
        responseFifteenDto.setContent(savedFifteen.getContent());
        responseFifteenDto.setThumbnail(savedFifteen.getThumbnail());

        LOGGER.info("[saveTen] 장편 시놉시스 생성완료. account : {}", account);
        return responseFifteenDto;
    }

    @Override
    public ResponseFifteenDto getFifteen(Long fifteen_id) throws Exception{
        LOGGER.info("[getFifteen] 장편 시놉시스 조회를 진행합니다. fifteen_id : {}", fifteen_id);
        Fifteen fifteen = fifteenRepository.findById(fifteen_id)
                .orElseThrow(() -> new Exception("중편 시놉시스를 찾을 수 없습니다."));
        ResponseFifteenDto responseFifteenDto = new ResponseFifteenDto();
        responseFifteenDto.setFifteen_id(fifteen.getFifteen_id());
        responseFifteenDto.setTitle(fifteen.getTitle());
        responseFifteenDto.setDescription(fifteen.getDescription());
        responseFifteenDto.setContent(fifteen.getContent());
        responseFifteenDto.setThumbnail(fifteen.getThumbnail());

        LOGGER.info("[getFifteen] 징편 시놉시스 완료. fifteen_id : {}", fifteen_id);

        return responseFifteenDto;
    }

    @Override
    public ResponseFifteenListDto getFifteenList(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        ModelMapper mapper = new ModelMapper();
        List<ResponseFifteenDto> responseFifteenDtoList = new ArrayList<>();
        ResponseFifteenListDto responseFifteenListDto = new ResponseFifteenListDto();
        List<Fifteen> fifteenList = fifteenRepository.findAll();
        for (Fifteen fifteen : fifteenList){
            ResponseFifteenDto responseFifteenDto = mapper.map(fifteen,ResponseFifteenDto.class);
            responseFifteenDtoList.add(responseFifteenDto);
        }
        responseFifteenListDto.setItems(responseFifteenDtoList);
        return responseFifteenListDto;
    }

    @Override
    public void deleteFifteen(Long fifteen_id, HttpServletRequest httpServletRequest) throws Exception {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String account = jwtTokenProvider.getUsername(token);

        LOGGER.info("[deleteFifteen] 시놉시스 삭제를 진행합니다. account : {}", account);
        if (jwtTokenProvider.validationToken(token)) {
            User user = userRepository.getByAccount(account);
            Fifteen fifteen = fifteenRepository.findById(fifteen_id)
                    .orElseThrow(() -> new Exception("해당 시놉시스를 찾을 수 없습니다."));

            if (user.getUid().equals(fifteen.getUser().getUid())) {
                fifteenRepository.delete(fifteen);
            }else {
                LOGGER.info("[deleteFifteen] 시놉시스 삭제실패. account : {} ", account);
                throw new Exception("해당 시놉시스를 삭제할 권한이 없습니다.");
            }
        }
    }
}
