package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;
import net.violet.db.records.associations.SingleAssociationNull;
import net.violet.platform.datamodel.Hardware.HARDWARE;

public class ZtampImpl extends ObjectRecord<Ztamp, ZtampImpl> implements Ztamp {

	/**
	 * Spécification
	 */
	public static final SQLObjectSpecification<ZtampImpl> SPECIFICATION = new SQLObjectSpecification<ZtampImpl>("ztamp", ZtampImpl.class, new SQLKey[] { new SQLKey("id"), new SQLKey("serial") });

	/**
	 * Liste des colonnes pour le constructeur pour les nouveaux
	 * enregistrements.
	 */
	private static final String[] NEW_COLUMNS = new String[] { "serial", "batch_id", "visual", "hw_type", "isDuplicated" };

	/**
	 * Champs de l'enregistrement.
	 */
	protected long id;
	protected String serial;
	protected long batch_id;
	protected Long visual;
	protected long hw_type;
	protected boolean isDuplicated;

	/**
	 * Lot de ce ztamp.
	 */
	private final SingleAssociationNotNull<Ztamp, ZtampBatch, ZtampBatchImpl> mBatch;

	private final SingleAssociationNotNull<Ztamp, Hardware, HardwareImpl> mHardware;

	private final SingleAssociationNull<Ztamp, Files, FilesImpl> mVisual;

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	protected ZtampImpl(long id) throws SQLException {
		init(id);
		this.mBatch = new SingleAssociationNotNull<Ztamp, ZtampBatch, ZtampBatchImpl>(this, "batch_id", ZtampBatchImpl.SPECIFICATION);
		this.mHardware = new SingleAssociationNotNull<Ztamp, Hardware, HardwareImpl>(this, "hw_type", HardwareImpl.SPECIFICATION);
		this.mVisual = new SingleAssociationNull<Ztamp, Files, FilesImpl>(this, "visual", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
	}

	protected ZtampImpl() {
		this.mBatch = new SingleAssociationNotNull<Ztamp, ZtampBatch, ZtampBatchImpl>(this, "batch_id", ZtampBatchImpl.SPECIFICATION);
		this.mHardware = new SingleAssociationNotNull<Ztamp, Hardware, HardwareImpl>(this, "hw_type", HardwareImpl.SPECIFICATION);
		this.mVisual = new SingleAssociationNull<Ztamp, Files, FilesImpl>(this, "visual", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
	}

	public ZtampImpl(String inSerial, ZtampBatch inBatch, Files inVisual, HARDWARE inHwType) throws SQLException {
		this.serial = inSerial;
		this.batch_id = inBatch.getId();
		this.visual = (inVisual != null) ? inVisual.getId() : null;
		this.hw_type = inHwType.getId().longValue();
		this.isDuplicated = false;

		init(ZtampImpl.NEW_COLUMNS);
		this.mBatch = new SingleAssociationNotNull<Ztamp, ZtampBatch, ZtampBatchImpl>(this, "batch_id", ZtampBatchImpl.SPECIFICATION);
		this.mHardware = new SingleAssociationNotNull<Ztamp, Hardware, HardwareImpl>(this, "hw_type", HardwareImpl.SPECIFICATION);
		this.mVisual = new SingleAssociationNull<Ztamp, Files, FilesImpl>(this, "visual", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Ztamp#getId()
	 */
	@Override
	public Long getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Ztamp#getSpecification()
	 */
	@Override
	public SQLObjectSpecification<ZtampImpl> getSpecification() {
		return ZtampImpl.SPECIFICATION;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Ztamp#getBatch()
	 */
	public ZtampBatch getBatch() {
		return this.mBatch.get(this.batch_id);
	}

	public Files getVisual() {
		return this.mVisual.get(this.visual);
	}

	public HARDWARE getHardware() {
		return HARDWARE.findById(this.mHardware.get(this.hw_type).getId());
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Ztamp#getSerial()
	 */
	public String getSerial() {
		return this.serial;
	}

	private void setBatch_id(Map<String, Object> inUpdateMap, ZtampBatch batch) {
		if (this.batch_id != batch.getId()) {
			this.batch_id = batch.getId();
			this.mBatch.set(batch);
			inUpdateMap.put("batch_id", this.batch_id);
		}
	}

	public void setBatch(ZtampBatch batch) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setBatch_id(theUpdateMap, batch);
		update(theUpdateMap);
	}

	public boolean isDuplicated() {
		return this.isDuplicated;
	}
}
