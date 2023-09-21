package com.coderman.bizedu.edu.model.course;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This is the base record class for table: edu_course_catalog
 * Generated by MyBatis Generator.
 * @author MyBatis Generator
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value="CourseCatalogModel", description = "edu_course_catalog 实体类")
public class CourseCatalogModel extends BaseModel {
    

    @ApiModelProperty(value = "课程分类关联id")
    private Integer courseCatalogId;

    @ApiModelProperty(value = "课程id")
    private Integer courseId;

    @ApiModelProperty(value = "分类id")
    private Integer catalogId;
}