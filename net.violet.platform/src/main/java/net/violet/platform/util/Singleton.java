package net.violet.platform.util;

public class Singleton<Element> {

	private Element theElement;

	public Singleton(Element inElement) {
		this.theElement = inElement;
	}

	public Singleton() {

	}

	public void setElement(Element inElement) {
		this.theElement = inElement;
	}

	public Element getElement() {
		return this.theElement;
	}

	@Override
	public String toString() {
		return "The Element : " + this.theElement.toString();
	}
}
