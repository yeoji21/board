package com.practice.board.domain.member.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class MemberUpdateForm {
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    private String description;
}
