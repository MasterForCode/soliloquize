package top.soliloquize.date;

import java.time.Instant;

/**
 * @author wb
 * @date 2020/6/30
 */
public class InstantTest {
    public static void main(String[] args){
        //创建 Instant 实例
        Instant instant = Instant.now();
        System.out.println(instant);

        Instant instant1 = Instant.ofEpochSecond(20);
        System.out.println(instant1);

        Instant instant2 = Instant.ofEpochSecond(30,100);
        System.out.println(instant2);

        Instant instant3 = Instant.ofEpochMilli(1000);
        System.out.println(instant3);
    }
}
