package io.matafe.frameworks.common.util;

import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * Annotation Utility.
 * 
 * @author matafe@gmail.com
 */
public abstract class AnnotationUtils {

    /**
     * Find a single {@link Annotation} of {@code annotationType} on the supplied
     * {@link Class}, traversing its interfaces, annotations, and superclasses if
     * the annotation is not <em>directly present</em> on the given class itself.
     * <p>
     * This method explicitly handles class-level annotations which are not declared
     * as {@link java.lang.annotation.Inherited inherited} <em>as well as
     * meta-annotations and annotations on interfaces</em>.
     * <p>
     * The algorithm operates as follows:
     * <ol>
     * <li>Search for the annotation on the given class and return it if found.
     * <li>Recursively search through all annotations that the given class declares.
     * <li>Recursively search through all interfaces that the given class declares.
     * <li>Recursively search through the superclass hierarchy of the given class.
     * </ol>
     * <p>
     * Note: in this context, the term <em>recursively</em> means that the search
     * process continues by returning to step #1 with the current interface,
     * annotation, or superclass as the class to look for annotations on.
     * 
     * @param clazz
     *            the class to look for annotations on
     * @param annotationType
     *            the type of annotation to look for
     * @return the first matching annotation, or {@code null} if not found
     */
    public static <A extends Annotation> A findAnnotation(final Class<?> clazz, final Class<A> annotationType) {
	Objects.requireNonNull(clazz, "Class must not be null");
	A annotation = clazz.getAnnotation(annotationType);
	if (annotation != null) {
	    return annotation;
	}
	for (final Class<?> clazzInterfaces : clazz.getInterfaces()) {
	    annotation = findAnnotation(clazzInterfaces, annotationType);
	    if (annotation != null) {
		return annotation;
	    }
	}
	if (!Annotation.class.isAssignableFrom(clazz)) {
	    for (final Annotation ann : clazz.getAnnotations()) {
		annotation = findAnnotation(ann.annotationType(), annotationType);
		if (annotation != null) {
		    return annotation;
		}
	    }
	}
	final Class<?> superClass = clazz.getSuperclass();
	if (superClass == null || superClass == Object.class) {
	    return null;
	}
	return findAnnotation(superClass, annotationType);
    }

}