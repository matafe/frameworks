package io.matafe.frameworks.common.util;

import static io.matafe.frameworks.common.util.AnnotationUtils.findAnnotation;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.ArrayList;

import org.junit.Test;

/**
 * Unit Test for <code>AnnotationUtils</code>.
 * 
 * @author matafe@gmail.com
 */
public class AnnotationUtilsTest {

    @Test
    public void testFindAnnotationOnClassLevel() {
	SampleAnnotation annotation = findAnnotation(Sample01.class, SampleAnnotation.class);
	assertThat(annotation, notNullValue());
    }

    @Test
    public void testFindAnnotationOnParentClassLevel() {
	SampleAnnotation annotation = findAnnotation(Sample02.class, SampleAnnotation.class);
	assertThat(annotation, notNullValue());
    }

    @Test
    public void testFindAnnotationOnInterfaceLevel() {
	SampleAnnotation annotation = findAnnotation(Sample03.class, SampleAnnotation.class);
	assertThat(annotation, notNullValue());
    }

    @Test
    public void testFindAnnotationOnInterfaceLevelNotFound() {
	SampleAnnotation annotation = findAnnotation(ArrayList.class, SampleAnnotation.class);
	assertThat(annotation, nullValue());
    }

    @Test
    public void testFindAnnotationOnAnnotationLevel() {
	SampleAnnotation annotation = findAnnotation(Sample04.class, SampleAnnotation.class);
	assertThat(annotation, notNullValue());
    }

    @Test
    public void testFindAnnotationOnAnnotationLevelNotFound() {
	SampleAnnotation annotation = findAnnotation(Sample05.class, SampleAnnotation.class);
	assertThat(annotation, nullValue());
    }

    @Target({ TYPE })
    @Retention(RUNTIME)
    @interface SampleAnnotation {
    }

    @Target({ TYPE })
    @Retention(RUNTIME)
    @SampleAnnotation
    @interface AnnotationThatUseSampleAnnotation {
    }

    @Target({ TYPE })
    @Retention(RUNTIME)
    @interface OtherAnnotation {
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

    private static class Sample03 implements ISample03 {
    }

    @AnnotationThatUseSampleAnnotation
    private static class Sample04 {
    }

    @OtherAnnotation
    private static class Sample05 {
    }

}
