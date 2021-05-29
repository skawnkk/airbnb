package com.codesquad.coco.user;

import com.codesquad.coco.oauth.ServerKey;
import com.codesquad.coco.oauth.gitoauth.GitOauth;
import com.codesquad.coco.oauth.gitoauth.GitUserInfoDTO;
import jwt.JWT;
import jwt.JWTUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.codesquad.coco.oauth.gitoauth.GitURI.*;

@RestController
public class LoginController {

    private ServerKey serverKey;
    private GitOauth deskTopGitGitOauth;
    private GitOauth iOSGitGitOauth;
    private UserService userService;

    public LoginController(ServerKey serverKey, UserService userService) {
        this.userService = userService;
        this.serverKey = serverKey;
        this.deskTopGitGitOauth = new GitOauth(
                serverKey.getDeskTopClientId(),
                serverKey.getDeskTopClientSecret(),
                DESKTOP_REDIRECT_URI.getUri(), ACCESS_TOKEN_URI.getUri(), USER_INFO_URI.getUri());
        this.iOSGitGitOauth = new GitOauth(
                serverKey.getiOSClientId(),
                serverKey.getiOSClientSecret(),
                DESKTOP_REDIRECT_URI.getUri(), ACCESS_TOKEN_URI.getUri(), USER_INFO_URI.getUri());
    }


    @PostMapping("/v1/auth")
    public JWT loginDeskTop(@RequestParam String code) {
        GitUserInfoDTO userInfo = userService.loginByGitOauth(deskTopGitGitOauth, code);
        String jwt = JWTUtils.createJWTTypeBearer(userInfo, serverKey.getJwtServerKey());
        return new JWT(jwt);
    }

    @PostMapping("/v1/auth/app")
    public JWT loginIOS(@RequestParam String code) {
        GitUserInfoDTO userInfo = userService.loginByGitOauth(iOSGitGitOauth, code);
        String jwt = JWTUtils.createJWTTypeBearer(userInfo, serverKey.getJwtServerKey());
        return new JWT(jwt);
    }
}
