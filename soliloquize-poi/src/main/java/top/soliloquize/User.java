package top.soliloquize;

//import lombok.Builder;
//import lombok.Data;

import java.util.Date;

/**
 * @author wb
 * @date 2019/12/18
 */
//@Data
//@Builder
public class User {
    private String name;
    private Double age;

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public Double getAge() {
        return age;
    }

    public User setAge(Double age) {
        this.age = age;
        return this;
    }

    //    private String height;
//    private Date date;
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Double getAge() {
//        return age;
//    }
//
//    public void setAge(Double age) {
//        this.age = age;
//    }
//
//    public String getHeight() {
//        return height;
//    }
//
//    public void setHeight(String height) {
//        this.height = height;
//    }
//    private String userId;
//    private String userName;
//    private String userGroup;
//    private String statusUpdatedBy;
//    private Date stringUpdatedDate;
//    private String userType;
//    private String createdBy;
//    private Date createdDate;
//    private String updatedBy;
//    private Date updatedDate;
}
