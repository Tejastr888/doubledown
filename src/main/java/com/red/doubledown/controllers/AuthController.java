package com.red.doubledown.controllers;


import com.red.doubledown.config.JwtProvider;
import com.red.doubledown.modal.TwoFactorAuth;
import com.red.doubledown.modal.TwoFactorOTP;
import com.red.doubledown.modal.User;
import com.red.doubledown.repository.UserRepository;
import com.red.doubledown.response.AuthResponse;
import com.red.doubledown.service.CustomerUserDetailService;
import com.red.doubledown.service.TwoFactorOtpService;
import com.red.doubledown.utils.OtpUtils;
import org.hibernate.type.TrueFalseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerUserDetailService customerUserDetailService;

    @Autowired
    private TwoFactorOtpService twoFactorOtpService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) throws Exception {

        User isEmailExists=userRepository.findByEmail(user.getEmail());
        if(isEmailExists!=null){
            throw new Exception("email already exists");
        }
        var newUser=new User(user.getFullName(), user.getEmail(), user.getPassword());
        userRepository.save(newUser);
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtProvider.generateToken(auth);
        AuthResponse res=new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("register success");

        return new ResponseEntity<>(res,HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@RequestBody User user) throws  Exception{
        String userName=user.getEmail();
        String password= user.getPassword();
        Authentication auth= authenticate(userName,password);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt=JwtProvider.generateToken(auth);

        if(user.getTwoFactorAuth().isEnabled()){
            AuthResponse res=new AuthResponse();
            res.setMessage("Two Factor Auth is Enabled");
            res.setTwofactorAuth(true);
            String otp= OtpUtils.generateOTP();
            TwoFactorOTP oldTwoFactorOtp=twoFactorOtpService.findByUser(user.getId());
        }
        AuthResponse res=new AuthResponse();

        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("login success");

        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    private Authentication authenticate(String userName, String password) {
        UserDetails userDetails=customerUserDetailService.loadUserByUsername(userName);
        if (userDetails==null){
            throw new BadCredentialsException("invalid username");
        }
        if(!password.equals(userDetails.getPassword())){
            throw new BadCredentialsException("invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,password,userDetails.getAuthorities());
    }

}
