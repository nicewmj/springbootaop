package com.springboot.demo.aspect;

import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;


/**
 * 切面类通过 @Pointcut定义的切入点为com.example.aop包下的所有函数做切人，
 * 通过 @Before实现切入点的前置通知，通过 @AfterReturning记录请求返回的对象。
 * 1）、通过JoinPoint可以获得通知的签名信息，如目标方法名、目标方法参数信息等；
 * 2）、通过RequestContextHolder来获取请求信息，Session信息；
 */
@Configuration
@Aspect
public class TestAspect {
    private static Logger logger = LoggerFactory.getLogger(TestAspect.class);

    /**
     * 定义切入点，切入点为com.springboot.demo.controller下的所有函数
     * 切入点表达式的格式：execution([可见性]返回类型[声明类型].方法名(参数)[异常])
     * 1) *：匹配所有字符
     * 2) ..：一般用于匹配多个包，多个参数
     * 3) +：表示类及其子类
     * 4)运算符有：&&,||,!
     */
    @Pointcut("execution(public * com.springboot.demo.controller..*.*(..))")
    public void logger(){}

    /**
     * 前置通知：在连接点之前执行的通知
     * @param joinPoint
     * @throws Throwable
     */

    @Before("logger()")
    public void myBefor(JoinPoint joinPoint){
        // 接收到请求，记录请求内容
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request =  requestAttributes.getRequest();

        //如果要获取Session信息的话，可以这样写：
        HttpSession session = (HttpSession) requestAttributes.resolveReference(RequestAttributes.REFERENCE_SESSION);

        // 记录下请求内容
        logger.info("进入方法前，我要把请求这个方法的参数拿到 打印出来...");
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * 后置返回通知
     * 这里需要注意的是:
     *      如果参数中的第一个参数为JoinPoint，则第二个参数为返回值的信息
     *      如果参数中的第一个参数不为JoinPoint，则第一个参数为returning中对应的参数
     *       returning：限定了只有目标方法返回值与通知方法相应参数类型时才能执行后置返回通知，否则不执行，
     *       对于returning对应的通知方法参数为Object类型将匹配任何目标返回值
     * @param joinPoint
     * @param keys
     * @AfterReturning(value = POINT_CUT,returning = "keys")
     * public void doAfterReturningAdvice1(JoinPoint joinPoint,Object keys){  }
     */
    @AfterReturning(returning = "ret",pointcut = "logger()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("RESPONSE : " + ret);
        logger.info("一切都结束了");
    }

    /**
     * 后置最终通知（目标方法只要执行完了就会执行后置通知方法）
     * @param joinPoint
     */
    @After(value = "logger()")
    public void myAfter(JoinPoint joinPoint){
        logger.info("后置最终通知执行了!!!!一切都结束了");

    }

    /**
     * 后置异常通知
     *  定义一个名字，该名字用于匹配通知实现方法的一个参数名，当目标方法抛出异常返回后，将把目标方法抛出的异常传给通知方法；
     *  throwing:限定了只有目标方法抛出的异常与通知方法相应参数异常类型时才能执行后置异常通知，否则不执行，
     *           对于throwing对应的通知方法参数为Throwable类型将匹配任何异常。
     * @param joinPoint
     * @param exception
     */
    @AfterThrowing(value = "logger()",throwing = "exception")
    public void testThrow(JoinPoint joinPoint,Throwable exception){
        //目标方法名：
        logger.info(joinPoint.getSignature().getName());
        //判断是何种异常
        if(exception instanceof NullPointerException){
            logger.info("发生了空指针异常!!!!!");
        }
    }

    /**
     * 环绕通知：
     *   环绕通知非常强大，可以决定目标方法是否执行，什么时候执行，执行时是否需要替换方法参数，执行完毕是否需要替换返回值。
     *   环绕通知第一个参数必须是org.aspectj.lang.ProceedingJoinPoint类型
     */
    @Around(value = "logger()")
    public Object tetsAround(ProceedingJoinPoint proceedingJoinPoint) {
        logger.info("环绕通知的目标方法名: " +proceedingJoinPoint.getSignature().getName());
        try {
            Object proceed = proceedingJoinPoint.proceed();
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    /**
     * 有时候我们定义切面的时候，切面中需要使用到目标对象的某个参数，如何使切面能得到目标对象的参数呢？可以使用args来绑定。
     * 如果在一个args表达式中应该使用类型名字的地方使用一个参数名字，那么当通知执行的时候对象的参数值将会被传递进来。
     * @param id
     */
    @Before("execution(public * com.springboot.demo.controller..*.*(..)) &&" + "args(id,..)")
    public void twiceAsOld1(Long id){
        logger.info ("切面before执行了。。。。id==" + id);

    }

}
