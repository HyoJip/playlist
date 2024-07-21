package com.share.music.playlist.login.contoller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.share.music.playlist.common.JwtTokenProvider;
import com.share.music.playlist.login.domain.Member;
import com.share.music.playlist.login.repository.LoginRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.NamedQuery;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/login")
public class LoginController {

    private final JwtTokenProvider jwtTokenProvider;
    private final LoginRepository loginRepository;

    /*test용 하드코딩*/
    final String USERID = "jeeyeon27";
    final String LOGINID = "jeeyeon27@gmail.com";
    final String USERNM = "박지연";
    final String NICKNM = "야니";
    final String PHONENO = "010-0000-0000";

    private SecretKey sk;

    //test 22.07.25

    Member member = Member.builder()
            .userId(USERID)
            .loginId(LOGINID)
            .userNm(USERNM)
            .nickNm(NICKNM)
            .phoneno(PHONENO)
            .roles(Collections.singletonList("ROLE_USER"))
            .build();


    //test
    @GetMapping("hello")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("hello");
    }

    /**
     *로그인
     *
     * @param
     * @return member.getUsername(), member.getRoles()
     */
    @PostMapping("/mainLogin")
   // public ApiResult<MemberDTO> mainLogin(@RequestBody LoginDTO loginDTO){
    public String mainLogin(@RequestBody Map<String, String> user){

      /*  log.info("user id = {}",user.get("id"));
        Member member = loginRepository.findById(user.get("id"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 ID 입니다."));*/


        //사용자의 아이디와 비번확인하는 로직필요

       /* return ApiResult.ok(

        );*/

        //return jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
        return "";


    }

    /**
     *회원가입
     *
     */
    @PostMapping("/join")
    public String join(){
        log.info("로그인 시도됨");
        loginRepository.save(member);

        return member.toString();
    }

    @PostMapping("/test")
    public String test(@RequestBody String pemString) throws Exception {
        System.out.println("pemString = " + pemString);
        try (
          PemReader pr = new PemReader(new StringReader(pemString));
          PEMParser pp = new PEMParser(pr)
        ) {
            Object object = pp.readObject();
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
            PublicKey publicKey = converter.getPublicKey((SubjectPublicKeyInfo) object);

            System.out.println("Unmarshalled publicKey(RSA) BASE64 encoded: " + Base64.getEncoder().encodeToString(publicKey.getEncoded()));

            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(256);
            SecretKey sk = kg.generateKey();
            this.sk = sk;
            System.out.println("Generated SymmetricKey(AES256) BASE64 encoded: " + Base64.getEncoder().encodeToString(sk.getEncoded()));

            Cipher cipher = Cipher.getInstance("RSA"); // ECB/PKCS1Padding
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] bytes = cipher.doFinal(sk.getEncoded());
            System.out.println("Base64.getEncoder().encodeToString(bytes) = " + Base64.getEncoder().encodeToString(bytes));
            return Base64.getEncoder().encodeToString(bytes); // Jackson이 byte[]로 하는 (역)직렬화는 BASE64처리한다고 함
            // 으로 알고있었지만, 객체가 아닌 String으로 return 해서 그냥 그대로 리턴되는 듯... 따로 인코딩해줌
        }
    }

    @PostMapping("/test2")
    public String test2(@RequestBody CipherText dummy) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, sk, new IvParameterSpec(dummy.iv));

        byte[] bytes = cipher.doFinal(dummy.cipherText);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    @Getter
    @Setter
    public static class CipherText {
        private byte[] cipherText; // 자동으로 BASE64 Decode(Jackson)
        private byte[] iv;
    }
}
