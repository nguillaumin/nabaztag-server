package net.violet.platform.datamodel.mock;

import java.util.List;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.NabLife;
import net.violet.platform.datamodel.Service;

public class NablifeMock extends AbstractMockRecord<NabLife, NablifeMock> implements NabLife {

	private final Service mService;
	private final String link;
	private final String setup;
	private final String mDescription;
	private final String name;
	private final long nbvote;
	private final int enabled;
	private final String img;
	private final String shortcut;
	private final long rank;
	private final String icone;
	private final long wrFlux_id;

	/**
	 * @param id
	 * @param scenario_id
	 * @param srvlist_id
	 * @param link
	 * @param setup
	 * @param description
	 * @param name
	 * @param nbvote
	 * @param enabled
	 * @param img
	 * @param shortcut
	 * @param service_id
	 * @param rank
	 * @param icone
	 * @param wrFlux_id
	 */
	public NablifeMock(long id, long srvlist_id, String link, String setup, String description, String name, long nbvote, int enabled, String img, String shortcut, Service inService, long rank, String icone, long wrFlux_id) {
		super(id);
		this.link = link;
		this.setup = setup;
		this.mDescription = description;
		this.name = name;
		this.nbvote = nbvote;
		this.enabled = enabled;
		this.img = img;
		this.shortcut = shortcut;
		this.mService = inService;
		this.rank = rank;
		this.icone = icone;
		this.wrFlux_id = wrFlux_id;
	}

	public String getDescription() {
		return this.mDescription;
	}

	public int getEnabled() {
		return this.enabled;
	}

	public String getIcone() {
		return this.icone;
	}

	public String getImg() {
		return this.img;
	}

	public List<Lang> getLangs() {
		throw new UnsupportedOperationException();
	}

	public String getLink() {
		return this.link;
	}

	public String getName() {
		return this.name;
	}

	public long getNbvote() {
		return this.nbvote;
	}

	public long getRank() {
		return this.rank;
	}

	public Service getService() {
		return this.mService;
	}

	public String getSetup() {
		return this.setup;
	}

	public String getShortcut() {
		return this.shortcut;
	}

	public long getWrFlux_id() {
		return this.wrFlux_id;
	}

	/**
	 * Interface NablifeSrv.
	 */
	public boolean isPodcast() {
		return this.mService.getId().intValue() == 1;
	}

}
