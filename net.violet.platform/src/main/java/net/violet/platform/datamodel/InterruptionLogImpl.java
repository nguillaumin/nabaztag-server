package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import net.violet.db.connector.Connector.ConnectionMode;
import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;

import org.apache.log4j.Logger;

public class InterruptionLogImpl extends ObjectRecord<InterruptionLog, InterruptionLogImpl> implements InterruptionLog {


	private static final Logger LOGGER = Logger.getLogger(InterruptionLogImpl.class);

	/**
	 * Spécification
	 */
	static final SQLObjectSpecification<InterruptionLogImpl> SPECIFICATION = new SQLObjectSpecification<InterruptionLogImpl>("interruption_log", InterruptionLogImpl.class, new SQLKey("id"), false, ConnectionMode.STATS);

	/**
	 * BC => Le lapin se fait rebooté BUTTON_SIMPLE_CLIC => On a fait un simple
	 * clic sur le bouton BUTTON_SIMPLE_CLIC_ONREAD => On a fait un simple clic
	 * sur le bouton lors de la lecture BUTTON_DOUBLE_CLIC => On a fait un
	 * double clic sur le bouton BUTTON_DOUBLE_CLIC_ONREAD => On a fait un
	 * double clic sur le bouton lors de la lecture BUTTON_RECO => On fait un
	 * clic long pour la reco BUTTON_PTT => On fait un clic rapide suivi d'un
	 * clic long pour le push to talk EARS => Les oreilles du lapin ont été
	 * modifiés (coté lapin et non api) RFID = > une rfid a été detecté par
	 * l'objet OFFLINE => objet déconnecté
	 */
	public static enum WHAT_OPTION {
		BC,
		BUTTON_SIMPLE_CLIC,
		BUTTON_SIMPLE_CLIC_ONREAD,
		BUTTON_DOUBLE_CLIC,
		BUTTON_DOUBLE_CLIC_ONREAD,
		BUTTON_RECO,
		BUTTON_PTT,
		EARS,
		RFID,
		OFFLINE
	};

	/**
	 * Liste des colonnes pour le constructeur pour les nouveaux
	 * enregistrements.
	 */
	private static final String[] NEW_COLUMNS = new String[] { "object_id", "what", "param", "date", };

	private static final Timer TIMER = new Timer("InterruptionLog", true);
	private static final long DELAY = 0L;

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	protected InterruptionLogImpl(long id) throws SQLException {
		init(id);
	}

	protected InterruptionLogImpl() {
		// This space for rent.
	}

	/**
	 * Constructeur pour un nouvel enregistrement.
	 */
	private InterruptionLogImpl(VObject inObject, Enum<WHAT_OPTION> inWhat, String inParam) {
		if (inObject != null) {
			this.object_id = inObject.getId();
		} else {
			this.object_id = 0; // ce sont les lapins non inscrits pour le bc.jsp
		}
		this.what = inWhat.toString();
		this.param = inParam;
		this.date = new Timestamp(System.currentTimeMillis());
	}

	public static void insert(VObject inObject, Enum<WHAT_OPTION> inWhat, String inParam) {
		final VObject theObject = inObject;
		final Enum<WHAT_OPTION> theWhat = inWhat;
		final String theParam = inParam;
		final TimerTask theTask = new TimerTask() {

			@Override
			public void run() {
				try {
					final InterruptionLogImpl theRecord = new InterruptionLogImpl(theObject, theWhat, theParam);
					theRecord.insertRecord(InterruptionLogImpl.NEW_COLUMNS);
				} catch (final Throwable e) {
					InterruptionLogImpl.LOGGER.fatal(e, e);
				}
			}
		};
		InterruptionLogImpl.TIMER.schedule(theTask, InterruptionLogImpl.DELAY);
	}

	/**
	 * Champs de l'enregistrement.
	 */
	protected long id;
	protected long object_id; // object concerné
	protected String what; // action fait sur l'objet
	protected String param; // les paramètres si il y en a
	protected Date date; // date de l'action

	/**
	 * Accesseur à partir d'un id.
	 * 
	 * @param id id de l'objet.
	 * @return l'enregistrement (ou <code>null</code>).
	 */
	public static InterruptionLog find(long id) {
		InterruptionLog interruptionLog = null;
		try {
			interruptionLog = AbstractSQLRecord.findByKey(InterruptionLogImpl.SPECIFICATION, InterruptionLogImpl.SPECIFICATION.getTableKeys()[0], id);
		} catch (final SQLException se) {
			InterruptionLogImpl.LOGGER.fatal(se, se);
		}
		return interruptionLog;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public SQLObjectSpecification<InterruptionLogImpl> getSpecification() {
		return InterruptionLogImpl.SPECIFICATION;
	}
}
