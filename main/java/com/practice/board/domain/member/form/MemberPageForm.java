package com.practice.board.domain.member.form;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class MemberPageForm {
    private String name;
    private String description;
}
