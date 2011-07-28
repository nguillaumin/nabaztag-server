package net.violet.platform.interactif;

// imports
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;

import net.violet.platform.datamodel.MockTestBase;

import org.junit.Assert;
import org.junit.Test;

/**
 * Class to access private/protected fields and methods for testing purposes.
 */
public final class PrivateAccessor extends MockTestBase {

	/**
	 * Access a given static field of a class.
	 * 
	 * @param inClass
	 *            class we want the field of.
	 * @param inName
	 *            name of the field we want to access.
	 * @return the value of the field.
	 * @throws NoSuchElementException
	 *             if the field does not exist.
	 * @throws IllegalArgumentException
	 *             if a problem occurs.
	 */
	public static Object getField(Class inClass, String inName) {
		return PrivateAccessor.doGetField(inClass, null, inName);
	}

	/**
	 * Access a given field of an object.
	 * 
	 * @param inObject
	 *            object we want the field of.
	 * @param inName
	 *            name of the field we want to access.
	 * @return the value of the field.
	 * @throws NoSuchElementException
	 *             if the field does not exist.
	 * @throws IllegalArgumentException
	 *             if a problem occurs.
	 */
	public static Object getField(Object inObject, String inName) {
		assert inObject != null;

		return PrivateAccessor.doGetField(inObject.getClass(), inObject, inName);
	}

	/**
	 * Access a given static field of a class.
	 * 
	 * @param inClass
	 *            class (of the object) we want the field of.
	 * @param inObject
	 *            object we want the field of or <code>null</code> if the field
	 *            is static.
	 * @param inName
	 *            name of the field we want to access.
	 * @return the value of the field.
	 * @throws NoSuchElementException
	 *             if the field does not exist.
	 * @throws IllegalArgumentException
	 *             if a problem occurs.
	 */
	private static Object doGetField(Class inClass, Object inObject, String inName) {
		assert inClass != null;
		assert inName != null;

		final Field theField = PrivateAccessor.findField(inClass, inName);

		if (theField == null) {
			throw new NoSuchElementException(inName + " not found");
		}

		Object theValue = null;

		try {
			final boolean wasAccessible = theField.isAccessible();
			theField.setAccessible(true);
			theValue = theField.get(inObject);
			theField.setAccessible(wasAccessible);
		} catch (final IllegalAccessException anException) {
			final IllegalArgumentException theException = new IllegalArgumentException(anException.toString());
			theException.initCause(anException);
			throw theException;
		}

		return theValue;
	}

	/**
	 * Modify a given field of an object.
	 * 
	 * @param inClass
	 *            class we want to modify the field of.
	 * @param inName
	 *            name of the field we want to access.
	 * @param inValue
	 *            new value for the field.
	 * @throws IllegalArgumentException
	 *             if a problem occurs.
	 */
	public static void setField(Class inClass, String inName, Object inValue) {
		PrivateAccessor.doSetField(inClass, null, inName, inValue);
	}

	/**
	 * Modify a given field of an object.
	 * 
	 * @param inObject
	 *            object we want to modify the field of.
	 * @param inName
	 *            name of the field we want to access.
	 * @param inValue
	 *            new value for the field.
	 * @throws IllegalArgumentException
	 *             if a problem occurs.
	 */
	public static void setField(Object inObject, String inName, Object inValue) {
		assert inObject != null;

		PrivateAccessor.doSetField(inObject.getClass(), inObject, inName, inValue);
	}

	/**
	 * Modify a given field of an object or a class.
	 * 
	 * @param inClass
	 *            class (of the object) we want to modify the field of.
	 * @param inObject
	 *            object we want to modify the field of or <code>null</code> if
	 *            the field is static.
	 * @param inName
	 *            name of the field we want to access.
	 * @param inValue
	 *            new value for the field.
	 * @throws NoSuchElementException
	 *             if the field does not exist.
	 * @throws IllegalArgumentException
	 *             if a problem occurs.
	 */
	private static void doSetField(Class inClass, Object inObject, String inName, Object inValue) {
		assert inClass != null;
		assert inName != null;

		final Field theField = PrivateAccessor.findField(inClass, inName);

		if (theField == null) {
			throw new NoSuchElementException(inName + " not found");
		}

		try {
			final boolean wasAccessible = theField.isAccessible();
			theField.setAccessible(true);
			theField.set(inObject, inValue);
			theField.setAccessible(wasAccessible);
		} catch (final IllegalAccessException anException) {
			final IllegalArgumentException theException = new IllegalArgumentException(anException.toString());
			theException.initCause(anException);
			throw theException;
		}
	}

	/**
	 * Invoke a given class method.
	 * 
	 * @param inClass
	 *            class we want to invoke a method of.
	 * @param inName
	 *            name of the method we want to invoke.
	 * @param inArgs
	 *            arguments for the invocation.
	 * @return the result of the method call.
	 * @throws InvocationTargetException
	 *             if a problem occurs.
	 * @throws NoSuchElementException
	 *             if the method does not exist.
	 */
	public static Object invokeMethod(Class inClass, String inName, Object[] inArgs) throws InvocationTargetException {
		return PrivateAccessor.doInvokeMethod(inClass, null, inName, inArgs);
	}

	/**
	 * Invoke a given instance method.
	 * 
	 * @param inObject
	 *            object we want to invoke the method of.
	 * @param inName
	 *            name of the method we want to invoke.
	 * @param inArgs
	 *            arguments for the invocation.
	 * @return the result of the method call.
	 * @throws InvocationTargetException
	 *             if a problem occurs.
	 * @throws NoSuchElementException
	 *             if the method does not exist.
	 */
	public static Object invokeMethod(Object inObject, String inName, Object[] inArgs) throws InvocationTargetException {
		assert inObject != null;

		return PrivateAccessor.doInvokeMethod(inObject.getClass(), inObject, inName, inArgs);
	}

	/**
	 * Invoke a given instance method.
	 * 
	 * @param inClass
	 *            class we want to invoke the method of.
	 * @param inObject
	 *            <code>this</code> for the method call, or <code>null</code> if
	 *            the method is static.
	 * @param inName
	 *            name of the method we want to invoke.
	 * @param inArgs
	 *            arguments for the invocation.
	 * @return the result of the method call.
	 * @throws InvocationTargetException
	 *             if a problem occurs.
	 * @throws NoSuchElementException
	 *             if the method does not exist.
	 */
	private static Object doInvokeMethod(Class inClass, Object inObject, String inName, Object[] inArgs) throws InvocationTargetException {
		assert inClass != null;
		assert inName != null;

		final Method theMethod = PrivateAccessor.findMethod(inClass, inName);

		if (theMethod == null) {
			throw new NoSuchElementException(inName + " not found");
		}

		Object theResult = null;

		try {
			final boolean wasAccessible = theMethod.isAccessible();
			theMethod.setAccessible(true);
			theResult = theMethod.invoke(inObject, inArgs);
			theMethod.setAccessible(wasAccessible);
		} catch (final IllegalAccessException anException) {
			final IllegalArgumentException theException = new IllegalArgumentException(anException.toString());
			theException.initCause(anException);
			throw theException;
		}

		return theResult;
	}

	/**
	 * Find a given field of a class.
	 * 
	 * @param inClass
	 *            class we want the field of.
	 * @param inName
	 *            name of the field we want to access.
	 * @return the field (reflect) object or <code>null</code> if it couldn't be
	 *         found.
	 */
	private static Field findField(Class inClass, String inName) {
		assert inClass != null;
		assert inName != null;
		Field theResult = null;

		// Iterate on all fields to find the one with the name we're looking
		// for.
		final Field[] theFields = inClass.getDeclaredFields();
		final int nbFields = theFields.length;
		for (int indexFields = 0; indexFields < nbFields; indexFields++) {
			final Field theField = theFields[indexFields];
			if (inName.equals(theField.getName())) {
				theResult = theField;
				break;
			}
		}

		return theResult;
	}

	/**
	 * Find a given method of a class.
	 * 
	 * @param inClass
	 *            class we want the method of.
	 * @param inName
	 *            name of the method we want to access.
	 * @return the method (reflect) object or <code>null</code> if it couldn't
	 *         be found.
	 */
	private static Method findMethod(Class inClass, String inName) {
		assert inClass != null;
		assert inName != null;
		Method theResult = null;

		// Iterate on all methods to find the one with the name we're looking
		// for.
		final Method[] theMethods = inClass.getDeclaredMethods();
		final int nbMethods = theMethods.length;
		for (int indexMethods = 0; indexMethods < nbMethods; indexMethods++) {
			final Method theMethod = theMethods[indexMethods];
			if (inName.equals(theMethod.getName())) {
				theResult = theMethod;
				break;
			}
		}

		return theResult;
	}

	@Test
	public void testPrivateAccessor() {
		Assert.assertTrue(true);
	}
}
