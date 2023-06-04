package study.thboard2.domain.vo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@Slf4j
//사용자 vo
//(vo에 암호화, 비밀번호 비교 메소드를 추가 했음, 서비스에 해도 됨, 이건 스타일)
public class UserVo extends CommonVo {
    private Integer no;                 //사용자 rownum
    private Integer userNo;             //사용자 순번(시퀀스, pk)
    private String userId;              //사용자 아이디(pk)
    private String userPassword;        //사용자 비밀번호
    private String userName;            //사용자명
    private String userEmail;           //사용자 이메일
    private char useYn;                 //사용여부
    private String modDate;


    /**
     * 비밀번호 암호화
     * @param passwordEncoder
     * @return
     */
    public UserVo hashPassword(PasswordEncoder passwordEncoder) {
        this.userPassword = passwordEncoder.encode(this.userPassword);
        return this;
    }

    /**
     * 비밀번호 확인
     * @param orgPassword 평문 암호
     * @param passwordEncoder
     * @return
     */
    public boolean checkPassword(String orgPassword, PasswordEncoder passwordEncoder) {
        //passwordEncoder.matches가 평문 암호를 해싱 암호화값과 비교 후 true or false return
        return passwordEncoder.matches(orgPassword, this.userPassword);
    }
}