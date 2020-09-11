package top.soliloquize.object.common;

/**
 * @author wb
 * @date 2020/8/14
 */
public class User {
    private String name;
    private Integer age;
    private Score score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }
}
