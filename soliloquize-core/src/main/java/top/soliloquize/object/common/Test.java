package top.soliloquize.object.common;

import com.github.houbb.asm.tool.reflection.AsmMethods;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wb
 * @date 2020/7/22
 */
public class Test {
    public static void main(String[] args) {
        test(User.class);
    }

    private static <T> ClassStruct test(Class<T> clz) {
        ClassStruct classStruct = new ClassStruct();
        classStruct.setSimpleName(clz.getSimpleName());
        classStruct.setFullyQualifiedName(clz.getName());
        classStruct.setType(clz);
        Field[] declaredFields = clz.getDeclaredFields();
        List<FieldStruct> fieldStructList = new ArrayList<>(declaredFields.length);
        classStruct.setFieldStructList(fieldStructList);
        for (Field declaredField : declaredFields) {
            FieldStruct fieldStruct = new FieldStruct();
            fieldStructList.add(fieldStruct);
            fieldStruct.setName(declaredField.getName());
            fieldStruct.setType(declaredField.getType());
        }


        Method[] declaredMethods = clz.getDeclaredMethods();
        List<MethodStruct> methodStructList = new ArrayList<>(declaredMethods.length);
        classStruct.setMethodStructList(methodStructList);
        for (Method declaredMethod : declaredMethods) {
            MethodStruct methodStruct = new MethodStruct();
            methodStructList.add(methodStruct);
            methodStruct.setName(declaredMethod.getName());
            methodStruct.setReturnType(declaredMethod.getReturnType());

            List<ParamStruct> paramStructList = new ArrayList<>();
            methodStruct.setParamStructList(paramStructList);
            Parameter[] parameters = declaredMethod.getParameters();
            List<String> param =  AsmMethods.getParamNamesByAsm(declaredMethod);
            ParamStruct paramStruct = new ParamStruct();
            if (parameters.length > 0) {
                paramStructList.add(paramStruct);
            }
            for (int i = 0; i < parameters.length; i++) {
                paramStruct.setName(param.get(i));
                paramStruct.setType(parameters[i].getType());
            }

//            declaredMethod.getTypeParameters()
        }
        System.out.println(clz.getName());
        return null;

    }
}
