package com.memrevatan.toxifyou.business.concretes;

import com.memrevatan.toxifyou.business.abstracts.EmailService;
import com.memrevatan.toxifyou.core.EmailStatus;
import com.memrevatan.toxifyou.core.concretes.OtpGenerate;
import com.memrevatan.toxifyou.core.httpResponse.error.BadRequestException;
import com.memrevatan.toxifyou.dataAccess.OtpCodeTransactionDao;
import com.memrevatan.toxifyou.dataAccess.UserDao;
import com.memrevatan.toxifyou.entities.OtpCodeTransaction;
import com.memrevatan.toxifyou.entities.User;
import com.memrevatan.toxifyou.entities.userViewModel.OtpVerification;
import com.memrevatan.toxifyou.entities.userViewModel.OtpVerificationResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.springframework.core.io.Resource;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EmailManager implements EmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final ResourceLoader resourceLoader;
    private final OtpCodeTransactionDao otpCodeTransactionDao;
    private final UserDao userDao;

    @Override
    public void sendEmail(String to, String subject, String htmlContext) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to); // TODO bu kısım veritabanında kullanıcının mail adresini çekerek belirlenecektir.
        helper.setSubject(subject);
        helper.setText(htmlContext, true); // helper.setText(htmlContext, TRUE); --> HTML içerikleri için bu şekilde yapmalısın.

        mailSender.send(message);
    }

    @Override
    public void sendOtpCodeEmail(String to, long userId) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // image convert to base64
            String imagePath = "classpath:logo.png";
            Resource resource = resourceLoader.getResource(imagePath);
            InputStream inputStream = resource.getInputStream();
            byte[] imageBytes = IOUtils.toByteArray(inputStream);
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            //

            String otpCode = OtpGenerate.generateOtpCode();

            Context context = new Context();
            context.setVariable("otpCode", otpCode);
            context.setVariable("image", base64Image);
            String htmlContext = templateEngine.process("otpcode.html", context);

            helper.setTo(to);
            helper.setSubject("Toxify Otp Code");
            helper.setText(htmlContext, true);
            OtpCodeTransaction otpCodeTransaction = new OtpCodeTransaction();

            otpCodeTransaction.setOtpCode(otpCode);
            otpCodeTransaction.setEmail(to);
            otpCodeTransaction.setStatus(EmailStatus.UNAPPROVED.name());
            otpCodeTransaction.setUserId(userId);
            otpCodeTransactionDao.save(otpCodeTransaction);

            mailSender.send(message);
        } catch (Exception e) {
            throw new BadRequestException();
        }

    }

    @Override
    public OtpVerificationResponse otpVerification(OtpVerification otpVerification) {
        if(otpVerification.getEmail() == null || otpVerification.getUserId() == 0) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                String username = userDetails.getUsername();
                User user = userDao.findByUsername(username);
                otpVerification.setEmail(user.getEmail());
                otpVerification.setUserId(user.getId());
            }
        }
        Optional<OtpCodeTransaction> otpCodeTransaction = otpCodeTransactionDao.findByUserId(otpVerification.getUserId());
        if (otpCodeTransaction.isEmpty()) {
            return OtpVerificationResponse.builder()
                    .email(otpVerification.getEmail())
                    .userId(otpVerification.getUserId())
                    .status(EmailStatus.UNAPPROVED.name())
                    .success(false)
                    .message("Kayit bulunamadi")
                    .build();
        }
        OtpCodeTransaction codeTransaction =  otpCodeTransaction.get();
        if (!Objects.equals(codeTransaction.getOtpCode(), otpVerification.getOtpCode())) {
            return OtpVerificationResponse.builder()
                    .email(otpVerification.getEmail())
                    .userId(otpVerification.getUserId())
                    .status(EmailStatus.UNAPPROVED.name())
                    .success(false)
                    .message("Otp Code unapproved.")
                    .build();
        }
        codeTransaction.setStatus(EmailStatus.APPROVED.name());
        OtpCodeTransaction saveEntity = otpCodeTransactionDao.save(codeTransaction);
        return OtpVerificationResponse.builder()
                .success(true)
                .status(saveEntity.getStatus())
                .email(saveEntity.getEmail())
                .userId(saveEntity.getUserId())
                .message("Otp code approved!")
                .build();
    }

}
