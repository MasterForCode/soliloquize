package top.soliloquize.object;

import top.soliloquize.collection.Collections;
import top.soliloquize.lang.Strings;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author wb
 * @date 2020/7/13
 */
public class ObjectParser {
    public static void main(String[] args) {
        System.out.println();
    }
    public static <T> ClassStruct parse(Class<T> clz) {
        String set = "set";
        String get = "get";
        ClassStruct classStruct = new ClassStruct().setClassName(clz.getSimpleName()).setClassName(clz.getName());
        Field[] fields = clz.getDeclaredFields();
        Method[] methods = clz.getDeclaredMethods();
        for (Field field : fields) {
            FieldStruct fieldStruct = new FieldStruct().setFiledName(field.getName()).setType(field.getType().getSimpleName());
            classStruct.getFieldStructList().add(fieldStruct);
            Method setMethod = Collections.findFirst(Arrays.asList(methods), e -> (set + Strings.upFirst(field.getName())).equals(e.getName()));
            Method getMethod = Collections.findFirst(Arrays.asList(methods), e -> (get + Strings.upFirst(field.getName())).equals(e.getName()));
            fieldStruct.setSetMethod(setMethod);
            fieldStruct.setGetMethod(getMethod);
        }


        return classStruct;
    }
}
