package net.violet.content;

import net.violet.common.utils.concurrent.ThreadWatcher;
import net.violet.common.utils.concurrent.units.AbstractProcessUnit;

/**
 * The thread safety of the mState member relies on the use of
 * {@link ScriptProcessUnit} - it is not Thread safe on its own
 */
public class ScriptProcessUnit<M> extends AbstractProcessUnit<M, String, byte[]> {

	/**
	 * Convenient builder used to ensure the integrity of any
	 * {@link ScriptProcessUnit}
	 */
	public static class ScriptProcessUnitBuilder<M> {

		private M mSource;
		private String mOptionLine;
		private boolean mHasTarget;
		private ThreadWatcher mThreadWatcher;

		public ScriptProcessUnitBuilder(M inSource) {
			this.mSource = inSource;
		}

		public void setAllButSource(String inOptionLine, boolean inHasTarget, ThreadWatcher inWatcher) {
			this.mOptionLine = inOptionLine;
			this.mHasTarget = inHasTarget;
			this.mThreadWatcher = inWatcher;
		}

		public void setLineNWatcher(String inOptionLine, ThreadWatcher inWatcher) {
			setAllButSource(inOptionLine, true, inWatcher);
		}

		/**
		 * Builds a {@link ScriptProcessUnit} from the previously given values.
		 * 
		 * @return the new {@link ScriptProcessUnit} if the given data were
		 *         coherente, <code>null</code> otherwise
		 */
		public ScriptProcessUnit<M> build() {
			if ((this.mOptionLine != null) && (this.mThreadWatcher != null)) {
				final ScriptProcessUnit<M> theUnit = new ScriptProcessUnit<M>(this.mSource, this.mOptionLine, this.mHasTarget, this.mThreadWatcher);
				return theUnit;
			}
			return null;
		}

		/**
		 * Sets all the inner instance variables to <code>null</code>
		 */
		public void clear() {
			this.mSource = null;
			this.mOptionLine = null;
			this.mHasTarget = false;
			this.mThreadWatcher = null;
		}
	}

	private final boolean useTarget;

	/**
	 * Creates a new {@link ScriptProcessUnit}, Use the
	 * {@link ScriptProcessUnitBuilder} see above
	 * 
	 * @param inSource
	 * @param inOptionLine
	 * @param inhasTarget
	 * @param inWatcher
	 */
	private ScriptProcessUnit(M inSource, String inOptionLine, boolean inhasTarget, ThreadWatcher inWatcher) {
		super(inSource, inOptionLine, inWatcher);

		this.useTarget = inhasTarget;
	}

	public boolean getProcessTarget() {
		return this.useTarget;
	}

}
