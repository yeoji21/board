package com.practice.board.domain.member;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private long id;
    private String loginId;
    private String password;
    private String name;
    private String description;

    public Member(String loginId, String password, String name, String description) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.description = description;
    }

}
