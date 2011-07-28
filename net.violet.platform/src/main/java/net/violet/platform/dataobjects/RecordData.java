package net.violet.platform.dataobjects;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import net.violet.db.records.Record;

/**
 * Classe pour un data object sur un (unique) record.
 * 
 * @param <T> classe du Record
 */
public abstract class RecordData<T extends Record<T>> {

	private static final Map<Class<RecordData>, WeakHashMap<Record, RecordData<? extends Record>>> CACHE = new HashMap<Class<RecordData>, WeakHashMap<Record, RecordData<? extends Record>>>();

	private final T mRecordRef;

	protected static <U extends Record<U>, T extends RecordData<U>> T getData(U inRecord, Class<T> inClass, Class<? extends Record> inRecordInterface) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		//return inClass.getDeclaredConstructor(inRecordInterface).newInstance(inRecord);
		Map<U, T> theMap = (Map<U, T>) (Object) RecordData.CACHE.get(inClass);
		T recordData = null;

		if (theMap == null) {
			synchronized (RecordData.CACHE) {
				theMap = (Map<U, T>) (Object) RecordData.CACHE.get(inClass);

				if (theMap == null) {

					theMap = new WeakHashMap<U, T>();

					recordData = inClass.getDeclaredConstructor(inRecordInterface).newInstance(inRecord);
					theMap.put(inRecord, recordData);
					RecordData.CACHE.put((Class<RecordData>) (Object) inClass, (WeakHashMap<Record, RecordData<? extends Record>>) (Object) theMap);
					return recordData;
				}
			}
		}
		recordData = theMap.get(inRecord);

		if (((recordData == null) || (recordData.getRecord() == null) || recordData.getRecord().isInvalid())) {
			synchronized (RecordData.CACHE) {
				recordData = theMap.get(inRecord);

				if ((recordData == null) || (recordData.getRecord() == null) || recordData.getRecord().isInvalid()) {
					recordData = inClass.getDeclaredConstructor(inRecordInterface).newInstance(inRecord);
					theMap.put(inRecord, recordData);
					RecordData.CACHE.put((Class<RecordData>) (Object) inClass, (WeakHashMap<Record, RecordData<? extends Record>>) (Object) theMap);
				}
			}
		}

		return recordData;
	}

	public static final void clearForTest() {

		for (final Map aMap : RecordData.CACHE.values()) {
			aMap.clear();
		}
	}

	protected RecordData(T inRecord) {
		this.mRecordRef = (inRecord == null) ? null : inRecord;
	}

	protected T getRecord() {
		return (this.mRecordRef == null) ? null : this.mRecordRef;
	}

	/**
	 * @return TRUE s'il n'y a pas d'enregistrement sous-jacent
	 */
	public final boolean isEmpty() {
		return (this.mRecordRef == null);
	}

	public final boolean isValid() {
		return (this.mRecordRef != null);
	}

	@Override
	public boolean equals(Object inOther) {

		if ((inOther == null) || !(inOther instanceof RecordData)) { // should not be called this way
			return false;
		}

		final Record myRecord = this.getRecord();
		final Record otherRecord = ((RecordData) inOther).getRecord();

		return (myRecord == otherRecord) || ((myRecord != null) && myRecord.equals(otherRecord));
	}

	@Override
	public final int hashCode() {
		final Record myRecord = this.getRecord();
		return (myRecord == null) ? 0 : myRecord.hashCode();
	}

	public final boolean delete() {
		final Record myRecord = this.getRecord();
		return ((myRecord == null) || !doDelete()) ? false : myRecord.delete();
	}

	protected boolean doDelete() {
		return true;
	}
}
