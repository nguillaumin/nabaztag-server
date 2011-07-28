package net.violet.platform.voice.server;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.violet.platform.util.CCalendar;
import net.violet.platform.util.StringShop;

public class TTSJob {

	/**
	 * id suivant.
	 */
	private static long gNextId;

	public interface TTSJobPrioritized {

		int getPriority();

		CCalendar getCreationDate();
	}

	public class TTSJobRef implements TTSJobPrioritized {

		/**
		 * Référence sur l'objet TTSJob.
		 */
		private final TTSJob mTTSJob;

		private final CCalendar mCreationDate;

		/**
		 * ID du job.
		 */
		private final long mJobID;

		/**
		 * Nom du process du job
		 */
		private final String mJobProcessName;

		/**
		 * Constructeur à partir d'un job.
		 * 
		 * @param inTTSJob
		 */
		public TTSJobRef(TTSJob inTTSJob) {
			this.mTTSJob = inTTSJob;
			this.mJobID = inTTSJob.getId();
			this.mJobProcessName = inTTSJob.getProcessName();
			this.mCreationDate = new CCalendar(false);
		}

		public TTSJob getTTSJob() {
			return this.mTTSJob;
		}

		/**
		 * récupère le numéro du Job
		 * 
		 * @return
		 */
		public long getJobID() {
			return this.mJobID;
		}

		@Override
		public int hashCode() {
			return (int) this.mJobID;
		}

		public String getProcessName() {
			return this.mJobProcessName;
		}

		public String getStatus() {
			if (this.mTTSJob != null) {
				return this.mTTSJob.getStatus();
			}
			return StringShop.EMPTY_STRING;
		}

		@Override
		public boolean equals(Object inObject) {
			return (this == inObject) || ((inObject instanceof TTSJobRef) && (((TTSJobRef) inObject).getJobID() == getJobID()));
		}

		public Map<String, String> toMap(boolean isCurrent) {
			final Map<String, String> map = new HashMap<String, String>();
			map.put("mJobProcessName", this.mJobProcessName);
			map.put("mJobID", Long.toString(this.mJobID));
			map.put("mJobVoiceName", this.mTTSJob.getVoice().getName());
			map.put("mJobVoiceLang", this.mTTSJob.getVoice().getLanguage());
			map.put("mJobText", this.mTTSJob.getText());
			map.put("mJobIsCurrent", isCurrent ? "*" : StringShop.EMPTY_STRING);
			map.put("mJobStatus", getStatus());
			return map;
		}

		public CCalendar getCreationDate() {
			return this.mCreationDate;
		}

		public int getPriority() {
			return TTSJob.this.mPriority;
		}

	}

	/**
	 * nom unique du TTSJob
	 */
	private final String mProcessName;

	/**
	 * id unique du TTSJob
	 */
	private final long mID;

	/**
	 * Nom de la voix.
	 */
	private final TTSVoice mVoice;

	/**
	 * Texte à lire.
	 */
	private final String mText;

	/**
	 * Priorité.
	 */
	private final int mPriority;

	/**
	 * Statut
	 */
	private String mStatus;

	/**
	 * Résultat
	 */
	private Map<String, Object> mResult;

	/**
	 * Mutex pour attendre le résultat.
	 */
	private final Integer mResultMutex = new Integer(0);

	/**
	 * Valeur de hachage.
	 */
	private final int mHashCode;

	/**
	 * Exception (en cas d'erreur).
	 */
	private Exception mResultException;

	private final List<TTSJob> mLinkedJobs = new LinkedList<TTSJob>();

	/**
	 * Constructeur à partir du nom de la voix, du texte et de la priorité.
	 */
	public TTSJob(String inName, TTSVoice inVoice, String inText, int inPriority) {
		this.mID = TTSJob.getNextId();
		this.mVoice = inVoice;
		this.mText = inText;
		this.mPriority = inPriority;
		this.mProcessName = inName;
		this.mStatus = "waiting";
		this.mHashCode = this.mVoice.hashCode() + this.mText.hashCode();
	}

	private static long getNextId() {
		final long theResult;
		synchronized (TTSJob.class) {
			theResult = TTSJob.gNextId;
			TTSJob.gNextId++;
		}
		return theResult;
	}

	public long getId() {
		return this.mID;
	}

	public String getText() {
		return this.mText;
	}

	public void setStatus(String inStatus) {
		this.mStatus = inStatus;
	}

	public String getStatus() {
		return this.mStatus;
	}

	public TTSJobRef getJobRef() {
		return new TTSJobRef(this);
	}

	public void linkJob(TTSJob inJob) throws Exception {
		synchronized (this.mResultMutex) {
			if (this.mResult != null) {
				inJob.setResult(this.mResult);
			} else if (this.mResultException != null) {
				inJob.setException(this.mResultException);
				throw this.mResultException;
			} else {
				this.mLinkedJobs.add(inJob);
			}
		}
	}

	public Map<String, Object> waitForResult() throws Exception {
		final Map<String, Object> theResult;
		synchronized (this.mResultMutex) {
			while (true) {
				if (this.mResult != null) {
					theResult = this.mResult;
					// tell linkedjobs
					for (final TTSJob aJob : this.mLinkedJobs) {
						aJob.setResult(this.mResult);
					}
					break;
				}
				if (this.mResultException != null) {
					// tell linkedjobs
					for (final TTSJob aJob : this.mLinkedJobs) {
						aJob.setException(this.mResultException);
					}
					throw this.mResultException;
				}

				this.mResultMutex.wait();
			}
		}
		return theResult;
	}

	public void setResult(Map<String, Object> inResult) {
		synchronized (this.mResultMutex) {
			this.mResult = inResult;
			this.mResultMutex.notifyAll();
		}
	}

	public void setException(Exception inException) {
		synchronized (this.mResultMutex) {
			this.mResultException = inException;
			this.mResultMutex.notifyAll();
		}
	}

	public TTSVoice getVoice() {
		return this.mVoice;
	}

	public String getProcessName() {
		return this.mProcessName;
	}

	@Override
	public boolean equals(Object obj) {
		return (this == obj) || ((obj instanceof TTSJob) && (((TTSJob) obj).getVoice().equals(this.getVoice())) && (((TTSJob) obj).getText().equals(this.getText())));
	}

	@Override
	public int hashCode() {
		return this.mHashCode;
	}

}
