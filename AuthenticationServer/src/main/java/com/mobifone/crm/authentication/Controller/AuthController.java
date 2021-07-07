package com.mobifone.crm.authentication.Controller;

import com.mobifone.crm.authentication.Configuration.Base64SHAPasswordEncoder;
import com.mobifone.crm.authentication.Configuration.Service.JwtTokenService;
import com.mobifone.crm.authentication.Entity.UserEntity;
import com.mobifone.crm.authentication.Model.AuthModel.LoginDTO;
import com.mobifone.crm.authentication.Model.AuthModel.RefreshTokentObj;
import com.mobifone.crm.authentication.Model.AuthModel.TokenData;
import com.mobifone.crm.authentication.Model.MessageResponse;
import com.mobifone.crm.authentication.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {
    @Autowired
    public JwtTokenService jwtTokenService;

    @Autowired
    UserService userService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDto) {
        TokenData tokenData = new TokenData();
        UserEntity userEntity = null;
        try {
            if (loginDto.getUserName() == null || loginDto.getPassword() == null) {
                throw new Exception("Username or Password is null");
            }

            String passwordEncoder = encoder.encode(loginDto.getPassword());
            userEntity = userService.getUserByUsernamePassword(loginDto.getUserName(), passwordEncoder);
            if (userEntity != null) { //authent thanh cong
                tokenData = jwtTokenService.generateTokenData(String.valueOf(userEntity.getUserId()));
            }
        } catch (Exception ex) {
            MessageResponse mes = new MessageResponse();
            mes.setMessage(ex.getMessage());
            return new ResponseEntity<MessageResponse>(mes, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<TokenData>(tokenData, HttpStatus.OK);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> RefreshTokent(@RequestBody RefreshTokentObj refreshTokenObj) {
        TokenData tokenData = new TokenData();
        try {
            tokenData = jwtTokenService.doRefreshToken(refreshTokenObj.getRefreshToken());
        } catch (Exception ex) {
            MessageResponse mes = new MessageResponse();
            mes.setMessage(ex.getMessage());
            return new ResponseEntity<MessageResponse>(mes, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<TokenData>(tokenData, HttpStatus.OK);

    }

    @PostMapping("/createUser")
    public ResponseEntity<?> CreateUser(@RequestBody UserEntity userEntity){
        try {
            UserEntity userCheck= userService.getUserByUserName(userEntity.getUserName());
            if(userCheck!=null){
                throw new Exception("Username already exists");
            }
            userEntity.setPassword(encoder.encode(userEntity.getPassword()));
            userEntity.setStatus("1");
            Date nowDate= new Date();
            userEntity.setCreateDate(nowDate);
            userEntity.setModifyDate(nowDate);
            userService.createUserEntity(userEntity);
        } catch (Exception ex) {
            MessageResponse mes = new MessageResponse();
            mes.setMessage(ex.getMessage());
            return new ResponseEntity<MessageResponse>(mes, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        MessageResponse mes = new MessageResponse();
        mes.setMessage("Create account success");
        return new ResponseEntity<MessageResponse>(mes, HttpStatus.OK);
    }


    public Base64SHAPasswordEncoder encoder = new Base64SHAPasswordEncoder();


}
