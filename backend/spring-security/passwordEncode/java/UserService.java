package study.thboard2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.thboard2.domain.vo.UserVo;
import study.thboard2.mapper.UserMapper;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserMapper userMapper;
    //아래 추가
    private final PasswordEncoder bCryptPasswordEncoder;

    /**
     * 사용자 정보 저장
     * @param  userVo
     * @throws Exception
     */
    @Transactional
    public void regUser(UserVo userVo) throws Exception{
        //비밀번호 암호화
        userVo.hashPassword(bCryptPasswordEncoder);
        userMapper.insertUser(userVo);
    }

    /**
     * 아이디/비밀번호 확인
     * @param userId
     * @param userPassword
     * @return
     */
    public String login(String userId, String userPassword) throws Exception{
        UserVo userInfo = userMapper.selectByUserId(userId);
        return (userInfo.checkPassword(userPassword, bCryptPasswordEncoder) == true ? userInfo.getUserId() : "none");
    } 
}