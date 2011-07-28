package net.violet.vlisp.services;

/**
 * Interface temporaire commune à Nablife et à une structure créée au vol à
 * partir des scénarios full.
 */
public interface NablifeSrv {

	/**
	 * Accesseur sur le nom.
	 */
	String getName();

	/**
	 * Accesseur sur la description.
	 */
	String getDescription();

	/**
	 * Accesseur sur l'image.
	 */
	String getImg();

	/**
	 * Accesseur sur la petite icône.
	 */
	String getIcone();

	/**
	 * Accesseur sur l'URL courte.
	 */
	String getShortcut();

	/**
	 * Accesseur sur le lien de configuration.
	 */
	String getLink();
}
