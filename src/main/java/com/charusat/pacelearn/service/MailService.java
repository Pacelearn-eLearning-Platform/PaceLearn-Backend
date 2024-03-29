package com.charusat.pacelearn.service;

import com.charusat.pacelearn.domain.Course;
import com.charusat.pacelearn.domain.User;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import tech.jhipster.config.JHipsterProperties;

/**
 * Service for sending emails.
 * <p>
 * We use the {@link Async} annotation to send emails asynchronously.
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "user";

    private static final String BASE_URL = "baseUrl";
    private static final String URL = "baseUrl";

    private final JHipsterProperties jHipsterProperties;

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    public MailService(
        JHipsterProperties jHipsterProperties,
        JavaMailSender javaMailSender,
        MessageSource messageSource,
        SpringTemplateEngine templateEngine
    ) {
        this.jHipsterProperties = jHipsterProperties;
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug(
            "Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart,
            isHtml,
            to,
            subject,
            content
        );

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
//            System.out.println("HAHAHAH Email is-->"+jHipsterProperties.getMail().getFrom());
//            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setFrom("thisisfordemo2@gmail.com");
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
        } catch (MailException | MessagingException e) {
            log.warn("Email could not be sent to user '{}'", to, e);
        }
    }

    @Async
    public void sendEmailFromTemplate(User user, String templateName, String titleKey) {
        if (user.getEmail() == null) {
            log.debug("Email doesn't exist for user '{}'", user.getLogin());
            return;
        }
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
//        String subject = messageSource.getMessage(titleKey, null, locale);
        String subject = titleKey;
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendActivationEmail(User user) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/activationEmail", "AssignmentAdministrationSystem account activation is required");
    }

    @Async
    public void sendCreationEmail(User user) {
        log.debug("Sending creation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/creationEmail", "email.activation.title");
    }

    @Async
    public void sendPasswordResetMail(User user) {
        log.debug("Sending password reset email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/passwordResetEmail", "email.reset.title");
    }


    /**
     * CUSTOM:
     * */
    public void sendCourseApprovalMail(Course course) {
        User author = course.getUser();
        log.debug("Sending course approval email to '{}'", author.getEmail());

        if (author.getEmail() == null) {
            log.debug("Email doesn't exist for user '{}'", author.getLogin());
            return;
        }
        String email = author.getEmail();
        String subject = "RE: Course approval";
        String content =
                "Dear " +
                        author.getFirstName() +
                        ",<br>" +
                        "Your course : " +
                        course.getCourseTitle() +
                        ", has been approved.<br><br>" +
                        "Regards\nTeam CharuVidya";
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, false, StandardCharsets.UTF_8.name());
            message.setTo(email);
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, true);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", email);
        } catch (MailException | MessagingException e) {
            log.warn("Email could not be sent to user '{}'", email, e);
        }
    }


    public void sendCourseDisApprovalMail(Course course) {
        User author = course.getUser();
        log.debug("Sending course disapproval email to '{}'", author.getEmail());

        if (author.getEmail() == null) {
            log.debug("Email doesn't exist for user '{}'", author.getLogin());
            return;
        }
        String email = author.getEmail();
        String subject = "RE: Revoking Course approval";
        String content =
                "Dear " +
                        author.getFirstName() +
                        ",<br>" +
                        "Your course : " +
                        course.getCourseTitle() +
                        ", has been disapproved.<br>"+"Please contact our admin for more information <br><br>" +
                        "Regards\nTeam CharuVidya";
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, false, StandardCharsets.UTF_8.name());
            message.setTo(email);
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, true);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", email);
        } catch (MailException | MessagingException e) {
            log.warn("Email could not be sent to user '{}'", email, e);
        }
    }


}
