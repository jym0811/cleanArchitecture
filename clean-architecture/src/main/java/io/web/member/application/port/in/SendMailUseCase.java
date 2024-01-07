package io.web.member.application.port.in;

public interface SendMailUseCase {
    boolean sendMail(SendMailCommand command);

}
