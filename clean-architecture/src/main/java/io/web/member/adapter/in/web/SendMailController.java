package io.web.member.adapter.in.web;

import io.web.member.application.port.in.SendMailCommand;
import io.web.member.application.port.in.SendMailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SendMailController {

    @PostMapping("/Member/send/{sourceMemberId}/{targetMemberId}")
    void sendEmail(
            @PathVariable("sourceMemberId") Long sourceMemberId,
            @PathVariable("targetMemberId") Long targetMemberId ){

    }

}
