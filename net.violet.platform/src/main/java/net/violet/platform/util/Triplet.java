package net.violet.platform.util;

public class Triplet<E1, E2, E3> {

	private final E1 firstElement;
	private final E2 secondElement;
	private final E3 thirdElement;

	public Triplet(E1 inFirstElement, E2 inSecondElement, E3 inThirdElement) {
		this.firstElement = inFirstElement;
		this.secondElement = inSecondElement;
		this.thirdElement = inThirdElement;
	}

	public E1 getFirst() {
		return this.firstElement;
	}

	public E2 getSecond() {
		return this.secondElement;
	}

	public E3 getThird() {
		return this.thirdElement;
	}

	@Override
	public String toString() {
		return "\nE1 : " + this.firstElement + "\nE2 : " + this.secondElement + "\nE3 : " + this.thirdElement;
	}

}
