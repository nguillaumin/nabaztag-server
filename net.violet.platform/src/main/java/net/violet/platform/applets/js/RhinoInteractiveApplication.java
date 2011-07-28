package net.violet.platform.applets.js;

/**
 * Interfaces des applications JavaScript pour le compilateur Rhino note : cette
 * interface est une version 'dégradée' de l'interface InteractiveApplication
 * qui prends et renvoie des objets POJO, alors que l'application Rhino prends
 * et renvoie des objets Scriptable
 * 
 * @author christophe - Violet
 * @see CompiledJsApplicationWrapper
 */
public interface RhinoInteractiveApplication {

	Object processEvent(Object inEvent);

}
