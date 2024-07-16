package com.yksj.monitor.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
public class StuBo {
    private Integer id;

    @NotEmpty // 空字符串视为有值
    private String name;
    @NotBlank  //验证是否为空字符串或空或不传值
    private String name1;
    @NotNull
    private Integer sex;

    @Min(value = 8, message = "学生年龄最小8岁")
    @Max(value = 60, message = "年龄最大60岁")
    private Integer age;

    @Range(min = 1, max = 20, message = "学生所在班级区间为1-18")
    private Integer classroom;

    @Size(min = 2, max = 5, message = "技能填写至少2个，最多5个")
    private List<String> skill;

    @Length(min = 3, max = 8, message = "学生的英文名字长度区间在3-8")
    private String englishName;

    @Email(message = "邮箱格式不正确")
    private String email;

    /**
     *
     * 请求体：{"name":" ","name1":" ","classroom":" 20","skill":["php","java","js","css","go","rust"],"englishName":"test123456","email":"11@qq.com"}
     * 返回值：
     *{
     *     "status": 501,
     *     "msg": "error",
     *     "data": {
     *         "englishName": "学生的英文名字长度区间在3-8",
     *         "skill": "技能填写至少2个，最多5个",
     *         "sex": "不能为null",
     *         "name1": "不能为空"
     *     },
     *     "requestId": "70e23884-bb50-449d-9874-38c3e3726981"
     * }
     */

}
