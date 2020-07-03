package top.soliloquize.groovy;

import groovy.lang.*;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author wb
 * @date 2020/7/3
 */
public class Groovys {
    private static final Logger logger = LoggerFactory.getLogger(Groovys.class);

    /**
     * java执行groovy脚本
     *
     * @param script 字符串格式的脚本
     * @return 脚本执行结果
     */
    public static Object executeShellScript(String script) {
        GroovyShell shell = new GroovyShell();
        try {
            return shell.evaluate(script);
        } catch (Exception e) {
            logger.error("Execute groovy failed", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * java执行groovy脚本
     *
     * @param script 字符串格式脚本
     * @param map    绑定的参数
     * @return 脚本执行结果
     */
    public static Object executeShellScript(String script, Map<String, Object> map) {
        try {
            return executeShellScript(script, binding(map));
        } catch (Exception e) {
            logger.error("Execute groovy failed", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 绑定参数
     *
     * @param map 绑定的参数
     * @return binding
     */
    private static Binding binding(Map<String, Object> map) {
        Binding binding = new Binding();
        map.forEach(binding::setProperty);
        return binding;
    }

    /**
     * java执行groovy脚本
     *
     * @param script  字符串格式的脚本
     * @param binding binding
     * @return 脚本执行结果
     */
    public static Object executeShellScript(String script, Binding binding) {
        GroovyShell shell = new GroovyShell(binding);
        return shell.evaluate(script);
    }

    /**
     * java执行groovy脚本
     *
     * @param filePath 脚本路径
     * @return 脚本执行结果
     * @throws IOException IO异常
     */
    public static Object executeShellScriptFile(String filePath) throws IOException {
        File file = new File(filePath);
        return executeShellScriptFile(file);
    }

    /**
     * java执行groovy脚本
     *
     * @param file 脚本文件
     * @return 脚本执行结果
     * @throws IOException IO异常
     */
    public static Object executeShellScriptFile(File file) throws IOException {
        GroovyShell shell = new GroovyShell();
        Object obj = shell.evaluate(file);
        shell.getClassLoader().clearCache();
        return obj;
    }

    /**
     * java执行groovy脚本
     *
     * @param filePath 脚本路径
     * @param map      绑定参数
     * @return 脚本执行结果
     * @throws IOException IO异常
     */
    public static Object executeShellScriptFile(String filePath, Map<String, Object> map) throws IOException {
        File file = new File(filePath);
        return executeShellScriptFile(binding(map), file);
    }

    /**
     * java执行groovy脚本
     *
     * @param binding binding
     * @param file    脚本文件
     * @return 脚本执行结果
     * @throws IOException IO异常
     */
    public static Object executeShellScriptFile(Binding binding, File file) throws IOException {
        GroovyShell shell = new GroovyShell(binding);
        return shell.evaluate(file);
    }

    /**
     * java执行groovy脚本
     *
     * @param file 脚本文件
     * @param map  绑定的参数
     * @return 脚本执行的结果
     * @throws IOException IO异常
     */
    public static Object executeShellScriptFile(File file, Map<String, Object> map) throws IOException {
        return executeShellScriptFile(binding(map), file);
    }

    /**
     * 获取groovy类的反射对象，并调用其方法
     *
     * @param filePath   脚本路径
     * @param args       方法参数
     * @param methodName 方法名
     * @return 方法执行结果
     * @throws IOException            IO异常
     * @throws IllegalAccessException 访问权限异常
     * @throws InstantiationException 实例化异常
     */
    public static Object invokeGroovyMethod(String filePath, Object args, String methodName) throws IOException, IllegalAccessException, InstantiationException {
        //获取 groovy 类的反射对象
        GroovyObject groovyObj = getClass(filePath);
        //调用 groovyDemo 成员方法
        return groovyObj.invokeMethod(methodName, args);
    }

    /**
     * 通过GroovyClassLoader获取Class
     *
     * @param filePath 脚本路径
     * @return 类实例
     * @throws IOException            IO异常
     * @throws IllegalAccessException 访问权限异常
     * @throws InstantiationException 实例化异常
     */
    public static GroovyObject getClass(String filePath) throws IOException, IllegalAccessException, InstantiationException {
        GroovyClassLoader gcl = new GroovyClassLoader();
        Class<?> clazz = gcl.parseClass(new File(filePath));
        return (GroovyObject) clazz.newInstance();
    }

    /**
     * 动态加载脚本
     *
     * @param filePath 脚本路径(不包括文件名)
     * @param fileName 脚本名称
     * @param map      绑定的参数
     * @return 脚本执行的结果
     * @throws IOException       IO异常
     * @throws ResourceException 资源异常
     * @throws ScriptException   脚本异常
     */
    public static Object runScriptEngine(String[] filePath, String fileName, Map<String, Object> map) throws IOException, ResourceException, ScriptException {
        GroovyScriptEngine gse = new GroovyScriptEngine(filePath);
        return gse.run(fileName, binding(map));
    }

    /**
     * 动态加载脚本中的方法
     *
     * @param filePath 脚本路径(不包括文件名)
     * @param fileName 脚本名称
     * @param funName  方法名称
     * @param map      绑定的参数
     * @param oval     方法参数
     * @return 脚本执行的结果
     * @throws IOException       IO异常
     * @throws ResourceException 资源异常
     * @throws ScriptException   脚本异常
     */
    public static Object runScriptEngine(String[] filePath, String fileName, String funName, Map<String, Object> map, Object oval) throws IOException, ResourceException, ScriptException {
        GroovyScriptEngine gse = new GroovyScriptEngine(filePath);
        Script script = gse.createScript(fileName, binding(map));
        return script.invokeMethod(funName, oval);
    }

    /**
     * 包装脚本,获得错误发生行,可更改以满足日志需求
     *
     * @param script 脚本
     * @return 脚本
     */
    public static String wrapperScript2GetErrorLine(String script) {

        return "try {\n" +
                script +
                "} catch (Exception e) {\n" +
                "    String str = e.getStackTrace().toString()\n" +
                "    def pattern = (str =~ /groovy.(\\d+)./)\n" +
                "    println('The error occurred on line ' + pattern[0][1])\n" +
                "    throw new RuntimeException(e)\n" +
                "}\n" +
                "\n";
    }

}
