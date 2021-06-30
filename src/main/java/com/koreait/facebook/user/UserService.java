package com.koreait.facebook.user;

import com.koreait.facebook.common.EmailService;
import com.koreait.facebook.common.MyFileUtils;
import com.koreait.facebook.common.MySecurityUtils;
import com.koreait.facebook.feed.FeedMapper;
import com.koreait.facebook.feed.model.FeedDTO;
import com.koreait.facebook.feed.model.FeedDomain2;
import com.koreait.facebook.security.IAuthenticationFacade;
import com.koreait.facebook.user.model.UserEntity;
import com.koreait.facebook.user.model.UserProfileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired private EmailService email;
    @Autowired private MySecurityUtils secUtils;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private IAuthenticationFacade auth;
    @Autowired private MyFileUtils myFileUtils;
    @Autowired private UserMapper mapper;
    @Autowired private FeedMapper feedMapper;
    @Autowired private UserProfileMapper profileMapper;

    public int join(UserEntity param) {
        String authCd = secUtils.getRandomDigit(5);

        //비밀번호 암호화
        String hashedPw = passwordEncoder.encode(param.getPw());
        param.setPw(hashedPw);
        param.setAuthCd(authCd);
        int result = mapper.join(param);

        if(result == 1) { //메일 쏘기!! (id, authcd값을 메일로 쏜다.)
            String subject = "[얼굴책] 인증메일입니다.";
            String txt = String.format("<a href=\"http://localhost:8090/user/auth?email=%s&authCd=%s\">인증하기</a>"
                    , param.getEmail(), authCd);
            email.sendMimeMessage(param.getEmail(), subject, txt);
        }
        return result;
    }

    //이메일 인증 처리
    public int auth(UserEntity param) {
        return mapper.auth(param);
    }

    public void profileImg(MultipartFile[] imgArr) {
        UserEntity loginUser = auth.getLoginUser();
        int iuser = loginUser.getIuser();

        System.out.println("iuser : " + iuser);
        String target = "profile/" + iuser;



        UserProfileEntity param = new UserProfileEntity();
        param.setIuser(iuser);

        for (MultipartFile img : imgArr) {
            String saveFileNm = myFileUtils.transferTo(img, target);

            // saveFileNm이 null이 아니라면 t_user_profile 테이블에
            // insert하기
            if (saveFileNm != null) {
                param.setImg(saveFileNm);
                int result = profileMapper.insUserProfile(param);

                if(result == 1 && loginUser.getMainProfile() == null) {
                    UserEntity param2 = new UserEntity();
                    param2.setIuser(loginUser.getIuser());
                    param2.setMainProfile(saveFileNm);

                    if(mapper.updUser(param2) == 1) {
                        loginUser.setMainProfile(saveFileNm);
                    }
                }
            }
        }
    }

    public List<UserProfileEntity> selUserProfileList(UserEntity param) {
        return profileMapper.selUserProfileList(param);
    }

    // 메인 이미지 변경
    public Map<String, Object> updUserMainProfile(UserProfileEntity param) {
        UserEntity loginUser = auth.getLoginUser();

        param.setIuser(loginUser.getIuser());
        int result = mapper.updUserMainProfile(param);
        if(result == 1) { //시큐리티 세션에 있는 loginUser에 있는 mainProfile값도 변경해주어야 한다.
            System.out.println("img : " + param.getImg());
            loginUser.setMainProfile(param.getImg());
        }
        Map<String, Object> res = new HashMap();
        res.put("result", result);
        res.put("img", param.getImg());
        return res;
    }

    public List<FeedDomain2> selFeedList2(FeedDTO param) {
        param.setIuser(auth.getLoginUserPk());
        return feedMapper.selFeedList2(param);
    }
}
