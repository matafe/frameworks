package io.matafe.frameworks.common.util;

import static io.matafe.frameworks.common.util.AnnotationUtils.findAnnotation;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.Test;

/**
 * Unit Test for <code>AnnotationUtils</code>.
 * 
 * @author matafe@gmail.com
 */
public class AnnotationUtilsTest {

    @Test
    public void testFindAnnotationOnClassAtClassLevel() {
	SampleAnnotation annotation = findAnnotation(Sample01.class, SampleAnnotation.class);
	assertThat(annotation, notNullValue());
    }

    @Test
    public void testFindAnnotationOnClassAtParentClassLevel() {
	SampleAnnotation annotation = findAnnotation(Sample02.class, SampleAnnotation.class);
	assertThat(annotation, notNullValue());
    }

    @Test
    public void testFindAnnotationOnClassAtInterfaceLevel() {
	SampleAnnotation annotation = findAnnotation(Sample03.class, SampleAnnotation.class);
	assertThat(annotation, notNullValue());
    }

    @Test
    public void testFindAnnotationOnClassAtInterfaceLevelNotFound() {
	SampleAnnotation annotation = findAnnotation(ArrayList.class, SampleAnnotation.class);
	assertThat(annotation, nullValue());
    }

    @Test
    public void testFindAnnotationOnClassAtAnnotationLevel() {
	SampleAnnotation annotation = findAnnotation(Sample04.class, SampleAnnotation.class);
	assertThat(annotation, notNullValue());
    }

    @Test
    public void testFindAnnotationOnClassAtAnnotationLevelNotFound() {
	SampleAnnotation annotation = findAnnotation(Sample05.class, SampleAnnotation.class);
	assertThat(annotation, nullValue());
    }

    @Test
    public void testFindAnnotationOnMethodAtMethodLevelNotFound() throws Exception {
	Method method = Sample00.class.getMethod("doSomething", String.class);
	SampleAnnotation annotation = findAnnotation(method, SampleAnnotation.class);
	assertThat(annotation, nullValue());
    }

    @Test
    public void testFindAnnotationOnMethodAtMethodLevelFound() throws Exception {
	Method method = Sample06.class.getMethod("doSomething", String.class);
	SampleAnnotation annotation = findAnnotation(method, SampleAnnotation.class);
	assertThat(annotation, notNullValue());
    }

    @Test
    public void testFindAnnotationOnMethodAtParentClassLevel() throws Exception {
	Method method = Sample07.class.getMethod("doSomething", String.class);
	SampleAnnotation annotation = findAnnotation(method, SampleAnnotation.class);
	assertThat(annotation, notNullValue());
    }
    
    @Test
    public void testFindAnnotationOnMethodAtParentClassLevel2() throws Exception {
	Method method = Sample06m1.class.getMethod("doSomething", String.class);
	SampleAnnotation annotation = findAnnotation(method, SampleAnnotation.class);
	assertThat(annotation, notNullValue());
    }
    
    @Test
    public void testFindAnnotationOnMethodAtParentClassLevelInterface() throws Exception {
	Method method = Sample03m1.class.getMethod("doSomething", String.class);
	SampleAnnotation annotation = findAnnotation(method, SampleAnnotation.class);
	assertThat(annotation, notNullValue());
    }

    @Test
    public void testFindAnnotationOnMethodAtParentMethodLevel() throws Exception {
	Method method = Sample06m.class.getMethod("doSomething", String.class);
	SampleAnnotation annotation = findAnnotation(method, SampleAnnotation.class);
	assertThat(annotation, notNullValue());
    }

    @Test
    public void testFindAnnotationOnMethodAtInterfaceLevel() throws Exception {
	Method method = Sample03m.class.getMethod("doSomething", String.class);
	SampleAnnotation annotation = findAnnotation(method, SampleAnnotation.class);
	assertThat(annotation, notNullValue());
	annotation = findAnnotation(method, SampleAnnotation.class);
	assertThat(annotation, notNullValue());
    }

    @Test
    public void testFindAnnotationOnMethodAtAnnotationLevel() throws Exception {
	Method method = Sample04m.class.getMethod("doSomething", String.class);
	SampleAnnotation annotation = findAnnotation(method, SampleAnnotation.class);
	assertThat(annotation, notNullValue());
    }

    @Test
    public void testFindAnnotationOnMethodAtAnnotationLevelNotFound() throws Exception {
	Method method = Sample04m.class.getMethod("doAnotherthing", String.class);
	SampleAnnotation annotation = findAnnotation(method, SampleAnnotation.class);
	assertThat(annotation, nullValue());
    }
    
    @Test
    public void testFindAnnotationOnMethodNullAnnotation() throws Exception {
	Method method = Sample04m.class.getMethod("doAnotherthing", String.class);
	SampleAnnotation annotation = findAnnotation(method, null);
	assertThat(annotation, nullValue());
    }

    // test implementations...

    @Target({ TYPE, METHOD })
    @Retention(RUNTIME)
    @interface SampleAnnotation {
    }

    @Target({ TYPE, METHOD })
    @Retention(RUNTIME)
    @SampleAnnotation
    @interface AnnotationThatUseSampleAnnotation {
    }

    @Target({ TYPE, METHOD })
    @Retention(RUNTIME)
    @interface OtherAnnotation {
    }

    private static class Sample00 {
	@SuppressWarnings("unused")
	public String doSomething(String str) {
	    return str;
	}
    }

    @SampleAnnotation
    private static class Sample01 {
    }

    @SampleAnnotation
    private static class BaseSample02 {
    }

    private static class Sample02 extends BaseSample02 {
    }

    @SampleAnnotation
    private static interface ISample03 {
    }

    private static interface ISample03m {
	@SampleAnnotation
	String doSomething(String str);
    }

    private static class Sample03 implements ISample03 {
    }

    private static class Sample03m implements ISample03m {

	@Override
	public String doSomething(String str) {
	    return str;
	}
    }
    
    private static class Sample03m1 extends Sample03m {

	@Override
	public String doSomething(String str) {
	    return str;
	}
    }

    @AnnotationThatUseSampleAnnotation
    private static class Sample04 {
    }

    private static class Sample04m {

	@AnnotationThatUseSampleAnnotation
	public String doSomething(String str) {
	    return str;
	}

	@OtherAnnotation
	public String doAnotherthing(String str) {
	    return str;
	}
    }

    @OtherAnnotation
    private static class Sample05 {
    }

    private static class Sample06 {
	@SampleAnnotation
	public String doSomething(String str) {
	    return str;
	}
    }

    private static class Sample06m extends Sample06 {
    }
    
    private static class Sample06m1 extends Sample06 {
	@Override
	public String doSomething(String str) {
	    return str;
	}	
    }

    private static class Sample07 extends Sample01 {

	public String doSomething(String str) {
	    return str;
	}
    }

}
