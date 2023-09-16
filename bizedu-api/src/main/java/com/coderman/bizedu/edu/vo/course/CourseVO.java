package com.coderman.bizedu.edu.vo.course;

import com.coderman.bizedu.edu.model.course.CourseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CourseVO extends CourseModel {

    @ApiModelProperty(value = "课程创建人")
    private String creatorName;

}
