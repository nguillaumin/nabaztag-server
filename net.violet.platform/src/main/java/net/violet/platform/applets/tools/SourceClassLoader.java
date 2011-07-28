package net.violet.platform.applets.tools;

/**
 * A variation of the ClassLoader behaviour : These ClassLoaders should be able
 * to build (compile) a new class from the provided sources
 * 
 * @author christophe - Violet
 */
public interface SourceClassLoader {

	// compile and create a new class from the provided sources
	Class loadFromSource(String className, String source);

	// the real ClassLoader method
	Class<?> loadClass(String name) throws ClassNotFoundException;

}
