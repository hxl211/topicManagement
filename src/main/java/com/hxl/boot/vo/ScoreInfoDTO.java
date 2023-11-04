package com.hxl.boot.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreInfoDTO {
    private Integer studentId;
    private Integer topicId;
    private Integer regularGrade;
    private Integer finalGrade;
    private Integer totalMark;
}
