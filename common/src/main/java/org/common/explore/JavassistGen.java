package org.common.explore;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/10/22 21:05
 */
public class JavassistGen {

    public static void main(String[] args) throws CannotCompileException, IOException {
        ClassPool classPool = ClassPool.getDefault();
        CtClass cc = classPool.makeClass("Programmer");
        CtMethod method = CtMethod.make("public void code(){}", cc);
        method.insertBefore("System.out.println(\"I'm a Programmer, Just Coding...\");");
        cc.addMethod(method);
        cc.writeFile("./Programmer");
    }

}
