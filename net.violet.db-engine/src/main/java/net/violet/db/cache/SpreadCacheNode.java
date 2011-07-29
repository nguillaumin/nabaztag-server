package net.violet.db.cache;

import java.io.IOException;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.violet.common.utils.server.ProcessName;
import net.violet.db.DBConstantes;
import net.violet.probes.ProbesHandler;

import org.apache.log4j.Logger;

import spread.MembershipInfo;
import spread.MessageFactory;
import spread.SpreadConnection;
import spread.SpreadConnectionImpl;
import spread.SpreadGroup;
import spread.SpreadMessage;
import spread.Exceptions.SpreadException;
import spread.Listener.AdvancedMessageListener;

/**
 * Noeud Spread.
 */
public class SpreadCacheNode extends AbstractCacheNode {

	private class MessageListener extends AdvancedMessageListener {

		private final ExecutorService mMessagePool = Executors.newFixedThreadPool(20);

		@Override
		public void messageReceived(final SpreadMessage message) {
			SpreadCacheNode.this.mTimeoutWatcher.update();
			ProbesHandler.SPREAD.addReceivedMessage();
			this.mMessagePool.execute(new Runnable() {

				public void run() {
					MessageListener.super.messageReceived(message);
				}
			});
		}

		@Override
		public void membershipMessageReceived(final SpreadMessage inMessage) {
			final MembershipInfo theInfos = inMessage.getMembershipInfo();
			if (theInfos.isRegularMembership()) {
				if (theInfos.isCausedByJoin()) {
					addedMember(theInfos.getJoined().toString());
				} else if (theInfos.isCausedByLeave()) {
					disappearedMember(theInfos.getLeft().toString());
				}

				setMembers(theInfos.getMembers());
			}
			ProbesHandler.SPREAD.addProcessedMessage();

		}

		@Override
		public void regularMessageReceived(final SpreadMessage inMessage) {
			try {
				if (inMessage.getObject() instanceof CacheMessage) {
					receiveMessage((CacheMessage) inMessage.getObject());
				}
			} catch (final SpreadException e) {
				SpreadCacheNode.LOGGER.fatal(e, e);
			} finally {
				ProbesHandler.SPREAD.addProcessedMessage();
			}
		}

	}

	private static final Logger LOGGER = Logger.getLogger(SpreadCacheNode.class);

	private final SpreadGroup mGroup = SpreadGroup.findByName("group1");
	/**
	 * Taille du pool de sender.
	 */
	private static final int NB_SENDERS = 1;

	private final Set<String> mMembers = new HashSet<String>();

	private Boolean mShutdownHook = false;

	/**
	 * Exécuteur pour envoyer les message.
	 */
	private final ExecutorService mSenderPool = Executors.newFixedThreadPool(SpreadCacheNode.NB_SENDERS);

	/**
	 * Référence sur la connection utilisée
	 */
	private final SpreadConnection mConnection = new SpreadConnectionImpl();

	private final MessageFactory mMessageFactory;

	private final MessageListener mListener;

	private final TimeoutWatcher mTimeoutWatcher = new TimeoutWatcher(300000) {

		@Override
		public void timeoutHandling() {
			broadcastMessage(PulseMessage.getDefault());
		}
	};

	/**
	 * Constructeur par défaut;
	 */
	public SpreadCacheNode() {
		this.mTimeoutWatcher.start();
		this.mListener = new MessageListener();
		connect();
		final SpreadMessage theMessage = new SpreadMessage();
		theMessage.addGroup(this.mGroup);
		theMessage.setSelfDiscard(true);
		this.mMessageFactory = new MessageFactory(theMessage);
	}

	/**
	 * Méthode pour envoyer un message aux pairs.
	 * 
	 * @param inObjectMessage message à envoyer.
	 */
	public void broadcastMessage(final CacheMessage inObjectMessage) {

		this.mSenderPool.execute(new Runnable() {

			public void run() {
				boolean reconnected = false;
				try {
					do {
						try {
							final SpreadMessage theMessage = SpreadCacheNode.this.mMessageFactory.createMessage();
							theMessage.setObject(inObjectMessage);
							SpreadCacheNode.this.mConnection.multicast(theMessage);
							reconnected = false;
						} catch (final Exception e) {
							try {
								reconnected = reconnect();
								if (!reconnected) {
									SpreadCacheNode.LOGGER.fatal(e, e);
								}
							} catch (final Exception reconnectException) {
								SpreadCacheNode.LOGGER.fatal(reconnectException, reconnectException);
							}
						}
					} while (reconnected);
				} finally {
					SpreadCacheNode.this.mTimeoutWatcher.update();
				}
			}

		});
	}

	private void setMembers(SpreadGroup[] inMembers) {
		synchronized (this.mMembers) {
			for (final SpreadGroup aGroup : inMembers) {
				this.mMembers.add(aGroup.toString());
			}
		}
	}

	/**
	 * Retourne la liste des membres du groupes.
	 * 
	 * @return la liste des adresses.
	 */
	public String[] getMembers() {
		synchronized (this.mMembers) {
			return this.mMembers.toArray(new String[this.mMembers.size()]);
		}
	}

	public void close() {
		synchronized (this.mShutdownHook) {
			this.mShutdownHook = true;
			this.mTimeoutWatcher.quit();
			try {
				this.mGroup.leave(this.mConnection);
				this.mConnection.removeListener(this.mListener);
				this.mConnection.disconnect();
				this.mSenderPool.shutdown();
			} catch (final SpreadException e) {
				SpreadCacheNode.LOGGER.fatal(e, e);
			} catch (final IOException e) {
				SpreadCacheNode.LOGGER.fatal(e, e);
			}
		}
	}

	private boolean reconnect() {
		try {
			this.mGroup.leave(this.mConnection);
		} catch (final SpreadException e) {
			SpreadCacheNode.LOGGER.fatal(e, e);
		} catch (final IOException e) {
			SpreadCacheNode.LOGGER.fatal(e, e);
		}
		this.mConnection.removeListener(this.mListener);
		this.mConnection.reconnect();
		this.mConnection.addListener(this.mListener);

		try {
			this.mGroup.join(this.mConnection);
		} catch (final Exception e) {
			SpreadCacheNode.LOGGER.fatal(e, e);
			return false;
		}

		return true;
	}

	private boolean connect() {
		synchronized (this.mShutdownHook) {
			if (!this.mShutdownHook) {
				try {
					LOGGER.debug("Trying to connect to Spread host '"+DBConstantes.SPREAD_HOST+"' with process name '"+ProcessName.getProcessName()+"'");
					this.mConnection.connect(InetAddress.getByName(DBConstantes.SPREAD_HOST), 0, ProcessName.getProcessName(), true, true);
					this.mConnection.addListener(this.mListener);
					this.mGroup.join(this.mConnection);
					return true;
				} catch (final UnknownHostException e) {
					SpreadCacheNode.LOGGER.fatal(e, e);
				} catch (final SpreadException e) {
					SpreadCacheNode.LOGGER.fatal(e, e);
				} catch (final IOException e) {
					SpreadCacheNode.LOGGER.fatal(e, e);
				}
			}

			return false;

		}
	}
}
