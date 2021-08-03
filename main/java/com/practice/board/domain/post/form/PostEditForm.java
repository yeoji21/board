package com.practice.board.domain.post.form;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostEditForm {

    private Long id;

    private String name;

    @NotBlank
    private String title;
    @NotNull
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd [HH:mm]")
    private Date postDate;
}
