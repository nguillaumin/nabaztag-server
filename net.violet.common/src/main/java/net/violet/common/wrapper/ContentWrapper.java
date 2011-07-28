package net.violet.common.wrapper;

public abstract class ContentWrapper<Content> {

	private Content content;

	public ContentWrapper(Content inContent) {
		content = inContent;
	}

	public boolean add(Content inContent) {
		content = inContent;
		return content != null;
	}

	public void clear() {
		content = null;
	}

	public void clean() {
		clear();
	}

	public Content get() {
		return content;
	}

}
