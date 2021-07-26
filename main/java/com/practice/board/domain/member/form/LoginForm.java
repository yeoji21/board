package com.practice.board.domain.member.form;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginForm {
    @NotBlank
    private String loginId;

    @NotBlank
    private String password;
}
