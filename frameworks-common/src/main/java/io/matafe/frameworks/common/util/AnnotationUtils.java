package io.matafe.frameworks.common.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;

/**
 * Annotation Utility.
 * 
 * @author matafe@gmail.com
 */
public abstract class AnnotationUtils {

    /** Annotated Interface cache */
    private static final Map<Class<?>, Boolean> annotatedInterfaceCache = new WeakHashMap<Class<?>, Boolean>();

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

    /**
     * Get a single {@link Annotation} of <code>annotationType</code> from the
     * supplied {@link Method}, traversing its super methods if no annotation can be
     * found on the given method itself.
     * <p>
     * Annotations on methods are not inherited by default, so we need to handle
     * this explicitly.
     * 
     * @param method
     *            the method to look for annotations on
     * @param annotationType
     *            the annotation class to look for
     * @return the annotation found, or <code>null</code> if none found
     */
    public static <A extends Annotation> A findAnnotation(final Method method, final Class<A> annotationType) {
	Objects.requireNonNull(method, "Method must not be null");
	if (annotationType == null) {
	    return null;
	}
	A annotation = getAnnotation(method, annotationType);
	Class<?> cl = method.getDeclaringClass();
	if (annotation == null) {
	    annotation = searchOnInterfaces(method, annotationType, cl.getInterfaces());
	}
	while (annotation == null) {
	    cl = cl.getSuperclass();
	    if (cl == null || cl == Object.class) {
		break;
	    }
	    try {
		final Method equivalentMethod = cl.getDeclaredMethod(method.getName(), method.getParameterTypes());
		annotation = getAnnotation(equivalentMethod, annotationType);
		if (annotation == null) {
		    annotation = searchOnInterfaces(method, annotationType, cl.getInterfaces());
		}
	    } catch (NoSuchMethodException ex) {
		// We're done...
	    }
	    if (annotation == null) {
		annotation = findAnnotation(cl, annotationType);
	    }
	}
	return annotation;
    }

    /**
     * Get a single {@link Annotation} of <code>annotationType</code> from the
     * supplied {@link Method}.
     * 
     * @param method
     *            the method to look for annotations on
     * @param annotationType
     *            the annotation class to look for
     * @return the annotations found
     */
    public static <A extends Annotation> A getAnnotation(Method method, Class<A> annotationType) {
	A ann = method.getAnnotation(annotationType);
	if (ann == null) {
	    for (Annotation metaAnn : method.getAnnotations()) {
		ann = metaAnn.annotationType().getAnnotation(annotationType);
		if (ann != null) {
		    break;
		}
	    }
	}
	return ann;
    }

    private static <A extends Annotation> A searchOnInterfaces(Method method, Class<A> annotationType,
	    Class<?>[] ifcs) {
	A annotation = null;
	for (Class<?> iface : ifcs) {
	    if (isInterfaceWithAnnotatedMethods(iface)) {
		try {
		    Method equivalentMethod = iface.getMethod(method.getName(), method.getParameterTypes());
		    annotation = getAnnotation(equivalentMethod, annotationType);
		} catch (NoSuchMethodException ex) {
		    // Skip this interface - it doesn't have the method...
		}
		if (annotation != null) {
		    break;
		}
	    }
	}
	return annotation;
    }

    private static boolean isInterfaceWithAnnotatedMethods(Class<?> iface) {
	synchronized (annotatedInterfaceCache) {
	    Boolean flag = annotatedInterfaceCache.get(iface);
	    if (flag != null) {
		return flag;
	    }
	    boolean found = false;
	    for (Method ifcMethod : iface.getMethods()) {
		if (ifcMethod.getAnnotations().length > 0) {
		    found = true;
		    break;
		}
	    }
	    annotatedInterfaceCache.put(iface, found);
	    return found;
	}
    }

}