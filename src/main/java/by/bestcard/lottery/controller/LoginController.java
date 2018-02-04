package by.bestcard.lottery.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping(value = "/login")
    public String submitMobileNumber(@ModelAttribute MobileNumber mobileNumber, HttpServletRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        String number = mobileNumber.getMobileNumber();
        String url = "https://phoneverification.devinotele.com/GenerateCode";
        String requestJson = "{\"destinationNumber\":\"" + number + "\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-ApiKey", "SypxhGPcE37R12XTX121id8jUvn70L4S");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        String answer = restTemplate.postForObject(url, entity, String.class);
        log.info(answer);
        request.getSession().setAttribute("mobileNumber", mobileNumber);
        return "redirect:/sms-code-verification";
    }

    @GetMapping("/sms-code-verification")
    public String smsVerificationForm() {
        return "sms-code-verification";
    }

    @PostMapping("/sms-code-verification")
    public String smsCodeValidation(@ModelAttribute SmsCode smsCode, HttpServletRequest request) {
        MobileNumber mobileNumber = (MobileNumber) request.getSession().getAttribute("mobileNumber");
        String requestJson = "{\"DestinationNumber\":" + mobileNumber.getMobileNumber() + "\", \"Code\":" + smsCode.getSmsCode() + "\"}";
        log.info(requestJson);
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://phoneverification.devinotele.com/CheckCode";
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-ApiKey", "SypxhGPcE37R12XTX121id8jUvn70L4S");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        String answer = restTemplate.postForObject(url, entity, String.class);
        log.info(answer);
        return "redirect:/";
    }

}

