package com.practice.board.domain.member.form;

import lombok.*;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class MemberMyPageForm {
    @NotBlank
    private String name;
    private String password;
    private String description;

}
