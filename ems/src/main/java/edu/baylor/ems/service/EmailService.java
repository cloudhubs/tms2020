package edu.baylor.ems.service;

import edu.baylor.ems.model.Exam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public static final String EXAM_START_DATE_REMINDER_TEMPLATE = "<p>This is a reminder that you can take '%s' exam starting from tomorrow, %s.</p>";
    public static final String EXAM_END_DATE_REMINDER_TEMPLATE = "<p>This is a reminder that tomorrow, %s, is the last day you can take '%s' exam.</p>";
    public static final String EXAM_ASSIGNED_NOTIFICATION_TEMPLATE = "<p>You have been assigned to '%s' exam, that is available from %s to %s.</p>";
    public static final String SEND_EMAIL_FROM = "noreply@tcs.ecs.baylor.edu";

    public void sendExamStartDateReminder(Exam exam) {
        String subject = "Texas Teacher Training exam start date reminder";
        DateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy");
        String emailContent = String.format(EXAM_START_DATE_REMINDER_TEMPLATE, exam.getConfigurationName(), dateFormat.format(exam.getExamDateFrom()));
        try {
            sendEmail(subject,exam.getExaminee(), emailContent);
        } catch (MessagingException e) {
            // change later to custom exception
            e.printStackTrace();
        }
    }

    public void sendExamEndDateReminder(Exam exam) {
        String subject = "Texas Teacher Training exam end date reminder";
        DateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy");
        String emailContent = String.format(EXAM_END_DATE_REMINDER_TEMPLATE, dateFormat.format(exam.getExamDateTo()),  exam.getConfigurationName());
        try {
            sendEmail(subject,exam.getExaminee(), emailContent);
        } catch (MessagingException e) {
            // change later to custom exception
            e.printStackTrace();
        }
    }

    public void sendExamAssignmentNotification(Exam exam) {
        String subject = "Texas Teacher Training exam assignment";
        DateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy");
        String emailContent = String.format(EXAM_ASSIGNED_NOTIFICATION_TEMPLATE, exam.getConfigurationName(), dateFormat.format(exam.getExamDateFrom()), dateFormat.format(exam.getExamDateTo()) );
        try {
            sendEmail(subject,exam.getExaminee(), emailContent);
        } catch (MessagingException e) {
            // change later to custom exception
            e.printStackTrace();
        }
    }

    private void sendEmail(String subject, String sendTo, String emailContent) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        helper.setText(emailContent, true);
        helper.setFrom(SEND_EMAIL_FROM);
        helper.setTo(sendTo);
        helper.setSubject(subject);

        javaMailSender.send(message);

    }


}
