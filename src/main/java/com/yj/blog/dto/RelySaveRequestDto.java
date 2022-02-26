package com.yj.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelySaveRequestDto {
    private int userId;
    private int boardId;
    private String content;
}
