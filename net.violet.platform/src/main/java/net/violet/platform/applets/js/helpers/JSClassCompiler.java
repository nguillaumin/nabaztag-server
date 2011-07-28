package net.violet.platform.applets.js.helpers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Compile a JavaScript source into a bunch of Java classes
 * 
 * @author christophe - Violet
 */
public class JSClassCompiler {

	/**
	 * Stocke le résultat d'une compilation avec Rhino ClassCompiler le tableau
	 * en entrée contient les noms des classes et leur byteCode : [["className",
	 * "<byteCode>"], ...].
	 * 
	 * @throws IOException en cas de problème à l'écriture.
	 */
	public static void saveClassFiles(Object[] compilationResult, File destDir) throws IOException {

		final String destPath = destDir.getCanonicalPath();
		final int nbClasses = compilationResult.length / 2;
		for (int i = 0; i < nbClasses; i++) {
			final String className = (String) compilationResult[i * 2];
			final byte[] bytecode = (byte[]) compilationResult[(i * 2) + 1];

			// Ecrit le fichier de cette classe dans notre répertoire
			// destination
			// On peut avoir à créer les répertoires correspondant au package
			final FileOutputStream theStream = new FileOutputStream(destPath + className.replace('.', File.separatorChar) + ".class");
			theStream.write(bytecode);
		}
	}

	public static void saveClassFiles(Object[] compilationResult, String destPath) throws IOException {
		final File destDir = new File(destPath);
		JSClassCompiler.saveClassFiles(compilationResult, destDir);

	}
}
