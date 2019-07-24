package com.example.demo.controller;

import com.example.demo.dto.AccessTokenDTO;
import com.example.demo.dto.GithubUser;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.url}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;


    @GetMapping("callback")
    public String callback(String code, String state, HttpServletRequest request, HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();//shift+enter快速换行
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_url(redirectUri);
        accessTokenDTO.setState(state);

        String accessToken=githubProvider.getAccessToken(accessTokenDTO);//ctrl+alt+v快速生成上面代码
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser!=null&&githubUser.getId()!= null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            //user.set
            user.setAvatarUrl(githubUser.getAvatar_url());
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            //登录成功，写cookie和session
            userMapper.insert(user);
            response.addCookie(new Cookie("token",token));//ctrl+p提示输入类型
            return "redirect:/";
        }else {
            //登录失败，重新登录
            return "redirect:/";
        }
    }
}
