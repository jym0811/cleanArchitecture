package io.web.member.domain;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member {

    private String id;

    private String passwd;

    private String name;

}
