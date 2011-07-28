package net.violet.platform.chor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Map.Entry;

import net.violet.common.utils.io.TmpFileManager.TmpFile;
import net.violet.common.wrapper.TmpFileWrapper;
import net.violet.content.AbstractAlterator;
import net.violet.content.converters.AbstractConverter;
import net.violet.content.converters.ContentType;

import org.apache.log4j.Logger;

/**
 * INDEX : mean : a value that lies within a range of values
 */
public final class DanceGenerator extends AbstractConverter<TmpFileWrapper> {

	private static final Logger LOGGER = Logger.getLogger(DanceGenerator.class);

	private static final int NB_SOUND_SAMPLES_PER_SECOND = 10;

	/**
	 * Interface pour une action dans la chorégraphie.
	 */
	private interface VAction {

		static enum LED {
			HEAD(4),
			BASE(0),
			LEFT(1),
			CENTER(2),
			RIGHT(3);

			private static final Map<Integer, LED> ID_LED;

			static {
				final Map<Integer, LED> theMap = new HashMap<Integer, LED>();

				for (final LED aLed : LED.values()) {
					theMap.put(aLed.getId(), aLed);
				}

				ID_LED = Collections.unmodifiableMap(theMap);
			}

			private final int id;

			private LED(int inLedId) {
				this.id = inLedId;
			}

			public int getId() {
				return this.id;
			}

			public static LED find(int inId) {
				return LED.ID_LED.get(inId);
			}

		}

		/**
		 * Ecrit la chorégraphie sur un flux d'octets.
		 * 
		 * @param inStream flux d'octets sur lequel écrire.
		 */
		void append(ByteArrayOutputStream inStream);
	}

	private static final class VMotorAction implements VAction {

		private static final byte SET_MOTOR_CHOR_CMD = 0x08;

		private final int motor;
		private final int angle;
		private final int dir;

		/**
		 * Constructeur à partir des valeurs.
		 * 
		 * @param aMotor moteur 0 (droit) ou 1 (gauche, point de vue du lapin).
		 * @param aAngle angle : 0 = vertical, 1 = devant, 2 = bas, 3 = derriere
		 * @param aDir direction : 0 = du haut vers l'avant, 1 = du haut vers
		 *            l'arriere
		 */
		public VMotorAction(int aMotor, int aAngle, int aDir) {
			this.motor = aMotor;
			this.angle = aAngle;
			this.dir = aDir;
		}

		/**
		 * Constructeur à partir d'un tokenizer.
		 * 
		 * @param inTokenizer tokenizer.
		 */
		public VMotorAction(StringTokenizer inTokenizer) {
			this.motor = Integer.parseInt(inTokenizer.nextToken());
			this.angle = Integer.parseInt(inTokenizer.nextToken());
			/* delay = */inTokenizer.nextToken();
			this.dir = Integer.parseInt(inTokenizer.nextToken());
		}

		public int getMotor() {
			return this.motor;
		}

		public int getAngle() {
			return this.angle;
		}

		public void append(ByteArrayOutputStream inStream) {
			inStream.write(VMotorAction.SET_MOTOR_CHOR_CMD);
			inStream.write(this.motor);
			inStream.write(this.angle * DanceGenerator.gNbDents / 360);
			inStream.write(this.dir);
		}
	}

	private static final class VLedAction implements VAction {

		private static final byte SET_LED_COLOR_CHOR_CMD = 0x07;

		private final int led;
		private final int red;
		private final int green;
		private final int blue;

		public VLedAction(StringTokenizer inTokenizer) {
			this.led = Integer.parseInt(inTokenizer.nextToken());
			this.red = Integer.parseInt(inTokenizer.nextToken());
			this.green = Integer.parseInt(inTokenizer.nextToken());
			this.blue = Integer.parseInt(inTokenizer.nextToken());
		}

		public void append(ByteArrayOutputStream inStream) {
			inStream.write(VLedAction.SET_LED_COLOR_CHOR_CMD);
			inStream.write(this.led);
			inStream.write(this.red);
			inStream.write(this.green);
			inStream.write(this.blue);
			inStream.write(0);
			inStream.write(0);
		}
	} // End VLedAction

	/**
	 * Describes a LED state : ID LED + COLOR
	 */
	private static final class VLedPaletteAction implements VAction {

		// LED ID
		private final LED mLed;
		// digit added to PALETTE_0_IX to generate the color ID in the palette
		private final int palette;

		private static final int PALETTE_0_IX = 0xF0; // 240
		private static final byte SET_LED_PALETTE_CHOR_CMD = 0x0E;

		public VLedPaletteAction(StringTokenizer inTokenizer) {
			this(LED.find(Integer.parseInt(inTokenizer.nextToken())), Integer.parseInt(inTokenizer.nextToken()));
		}

		public VLedPaletteAction(LED aLed, int rgb) {
			this.mLed = aLed;
			this.palette = rgb;
		}

		/**
		 * Appends : PALETTE CMD + LED_ID + palette
		 * 
		 * @see palette.
		 */
		public void append(ByteArrayOutputStream inStream) {
			inStream.write(VLedPaletteAction.SET_LED_PALETTE_CHOR_CMD);
			inStream.write(this.mLed.getId());
			// So far 240 - 246
			inStream.write(VLedPaletteAction.PALETTE_0_IX + this.palette);
		}
	} // End VLedAction VDance

	private static final class VDance {

		private static final int MOTOR_RPM = 8000;
		private final int fps;

		// Caution : leave this Map as a TreeMap : we need a SortedMap here !
		private final Map<Integer, List<VAction>> actions = new TreeMap<Integer, List<VAction>>();
		private int lastTick;
		private final int[] motorPos = new int[2];
		private final int motorSpeedInFrames;

		private static int getFPSFromTokenizer(StringTokenizer inTokenizer) throws IllegalArgumentException {
			if (!inTokenizer.hasMoreTokens()) {
				throw new IllegalArgumentException("Invalid Choregraphy");
			}
			return Integer.parseInt(inTokenizer.nextToken());
		}

		/**
		 * Constructeur à partir d'un tokenizer.
		 */
		public VDance(StringTokenizer inCDL) throws IllegalArgumentException {
			this(VDance.getFPSFromTokenizer(inCDL));

			initFromCDLLdesc(inCDL);
		}

		/**
		 * Constructeur par défaut.
		 */
		public VDance() {
			this(DanceGenerator.NB_SOUND_SAMPLES_PER_SECOND);
		}

		private VDance(int inFPS) {
			this.fps = inFPS;
			this.lastTick = -1;
			this.motorSpeedInFrames = getMSToFrames(VDance.MOTOR_RPM);
		}

		public void addAction(int aTick, VAction action) {
			List<VAction> values = this.actions.get(aTick);
			if (values == null) {
				this.actions.put(aTick, values = new ArrayList<VAction>());
			}

			values.add(action);

			if (aTick > this.lastTick) {
				this.lastTick = aTick;
			}

			if (action instanceof VMotorAction) {
				this.motorPos[((VMotorAction) action).getMotor()] = ((VMotorAction) action).getAngle();
			}
		}

		private void initFromCDLLdesc(StringTokenizer inTokenizer) throws IllegalArgumentException {
			// actions
			while (inTokenizer.hasMoreTokens()) {
				final int t = Integer.parseInt(inTokenizer.nextToken());
				final String type = inTokenizer.nextToken();

				if (type.equals("motor")) {
					addAction(t, new VMotorAction(inTokenizer));
				} else if (type.equals("led")) {
					addAction(t, new VLedAction(inTokenizer));
				} else if (type.equals("palette")) {
					addAction(t, new VLedPaletteAction(inTokenizer));
				}
			}
		}

		public int frameDur10ms() {
			// the frame duration in 100th of seconds
			int framedur10ms = (int) Math.round(100. / this.fps);
			if (framedur10ms < 1) {
				framedur10ms = 1;
			}
			return framedur10ms;
		}

		public int framesIn10msUnit(int inframes) {
			return (int) Math.round(inframes * 100. / (this.fps * frameDur10ms()));
		}

		public void append(ByteArrayOutputStream inStream) {
			int lastT = 0;

			for (final Entry<Integer, List<VAction>> anAction : this.actions.entrySet()) {
				final int tOutFrames = framesIn10msUnit(anAction.getKey());
				int dt = tOutFrames - lastT;
				lastT = tOutFrames;

				for (final VAction aVAction : anAction.getValue()) {
					inStream.write(dt);
					aVAction.append(inStream);
					dt = 0;
				}
			}
		}

		public int delayToMoveFromTo(int curPos, int targetAngle) {
			// fastest direction and speed from curPos
			int delta = targetAngle - curPos;
			if (delta > 180) {
				delta = delta - 360;
			}
			if (delta <= -180) {
				delta = delta + 360;
			}
			return (int) Math.round(Math.abs(delta) * this.motorSpeedInFrames / 360. - 0.5);
		}

		public void resetMotorsPos() {
			this.motorPos[0] = 0;
			this.motorPos[1] = 0;
		}

		public int addMotorAction(int targetTick, int motor, int targetAngle) {
			// suppose que motorPos est bon (dernier mouvement en date)
			// fastest direction and speed from where it is now
			// retourne 1 si ajoute action, 0 sinon
			final int curPos = this.motorPos[motor];
			int delta = targetAngle - curPos;
			if (delta > 180) {
				delta = delta - 360;
			}
			if (delta <= -180) {
				delta = delta + 360;
			}
			final int dtime = Math.abs(delta) * this.motorSpeedInFrames / 360;
			if (dtime == 0) {
				return 0;
			}

			int dir = 0;
			if (delta < 0) {
				dir = 1;
			}

			// trace("Dance Define Motor t="+(targetTick -
			// dtime)+"m="+motor+" from "
			// +this.motorPos[motor]+" to "+targetAngle
			// +" dt="+dtime+" dir="+dir);
			addAction(targetTick - dtime, new VMotorAction(motor, targetAngle, dir));

			return dtime;
		}

		public int getMSToFrames(int nbms) {
			return ((nbms / 1000) * this.fps); // we don't know why + 1
		}

		public int getLastTick() {
			return this.lastTick;
		}

		public int getMotorSpeedInFrames() {
			return this.motorSpeedInFrames;
		}
	} // END class VDance ****************************** Class Spectacle : music + VDance ******************************

	private static class Spectacle {

		/**
		 * Nombre de couleurs (allumées) par palette.
		 */
		private static final int NB_PALETTE_COLORS = 7;
		private static final Random RANDOM_HOUR = new Random(System.currentTimeMillis());
		private static final byte FRAME_DURATION_CHOR_CMD = 0x01;

		/**
		 * La dernière couleur de chaque palette est pour éteindre les leds.
		 */
		public static final int COLOR_IX_BLACK = 7;

		private static enum TYPES {
			SYNCHRONOUS {

				@Override
				public void addAction(VDance inDance, VAction.LED[] inLeds, int inColor, int inDurationOn, int inFrame) {
					for (final VAction.LED inLed : inLeds) {
						inDance.addAction(inFrame, new VLedPaletteAction(inLed, inColor));
					}
				}
			},
			INCREASING {

				@Override
				public void addAction(VDance inDance, VAction.LED[] inLeds, int inColor, int inDurationOn, int inFrame) {
					final int frameDelay = inDurationOn / inLeds.length;
					int theFrame = inFrame;
					for (final VAction.LED inLed : inLeds) {
						inDance.addAction(theFrame, new VLedPaletteAction(inLed, inColor));
						theFrame = theFrame + frameDelay;
					}
				}
			},
			DECREASING {

				@Override
				public void addAction(VDance inDance, VAction.LED[] inLeds, int inColor, int inDurationOn, int inFrame) {
					final int frameDelay = inDurationOn / inLeds.length;
					int theFrame = inFrame;
					for (int j = 0; j < inLeds.length; j++) {
						inDance.addAction(theFrame, new VLedPaletteAction(inLeds[inLeds.length - 1 - j], inColor));
						theFrame = theFrame + frameDelay;
					}
				}
			},
			CENTER {

				@Override
				public void addAction(VDance inDance, VAction.LED[] inLeds, int inColor, int inDurationOn, int inFrame) {
					if (inLeds.length % 2 == 0) {
						// pair
						inDance.addAction(inFrame, new VLedPaletteAction(inLeds[inLeds.length / 2], inColor));
						inDance.addAction(inFrame, new VLedPaletteAction(inLeds[(int) Math.round(1 + inLeds.length / 2.)], inColor));
					} else {
						// impair
						inDance.addAction(inFrame, new VLedPaletteAction(inLeds[(int) Math.round((inLeds.length) / 2. - 1.)], inColor));
					}
				}
			},
			OUTER {

				@Override
				public void addAction(VDance inDance, VAction.LED[] inLeds, int inColor, int inDurationOn, int inFrame) {
					inDance.addAction(inFrame, new VLedPaletteAction(inLeds[inLeds.length - 1], inColor));
					inDance.addAction(inFrame, new VLedPaletteAction(inLeds[0], inColor));
				}
			};

			public abstract void addAction(VDance inDance, VAction.LED[] inLeds, int inColor, int inDurationOn, int inFrame);
		}

		private static int TYPES_RANDOM_RANGE = TYPES.values().length;

		private static TYPES[] TYPES_ARRAY = TYPES.values();

		private final VDance vdance;

		public Spectacle(String inCDL) throws IllegalArgumentException {
			this.vdance = new VDance(new StringTokenizer(inCDL, net.violet.common.StringShop.COMMA));
		}

		public Spectacle() {
			this.vdance = new VDance();
		}

		/**
		 * @param inFrame
		 * @param iLeds
		 * @param allLedsSynchronous
		 * @param forNbFrames duration in frame
		 */
		void ledsSetColor(int inFrame, VAction.LED[] iLeds, boolean allLedsSynchronous, int forNbFrames) {
			final int theColor = Spectacle.RANDOM_HOUR.nextInt(Spectacle.NB_PALETTE_COLORS);

			if (iLeds.length > 1) {
				if (allLedsSynchronous) {
					for (final VAction.LED iLed : iLeds) {
						this.vdance.addAction(inFrame, new VLedPaletteAction(iLed, theColor));
					}

				} else {
					Spectacle.TYPES_ARRAY[Spectacle.RANDOM_HOUR.nextInt(Spectacle.TYPES_RANDOM_RANGE)].addAction(this.vdance, iLeds, theColor, forNbFrames, inFrame);
				} // synchronous
			} else {
				// only one
				this.vdance.addAction(inFrame, new VLedPaletteAction(iLeds[0], theColor));
			}
		}

		void motorsCreateChoregraphy(List<Integer> frames, List<int[]> acts) {
			// gets a maximal choregraphy definition
			// filters the events according to dynamics
			// builds the dance

			final double targetMvtsPerMn = 80.;
			final int lastFrame = this.vdance.getLastTick() + 1;
			int targetMvts = (int) Math.round(lastFrame * targetMvtsPerMn / this.vdance.getMSToFrames(60000));

			// au moins un mouvement d'oreilles
			if (targetMvts < 1) {
				targetMvts = 1;
			}

			// virer des mouvements au hasard
			while (frames.size() > targetMvts) {
				final int i = Spectacle.RANDOM_HOUR.nextInt(frames.size());
				// laisser toujours le premier mouvement
				if (i > 0) {
					frames.remove(i);
					acts.remove(i);
				}
			}

			// trace("final motor mvts ="+String(mvts));
			// Insert
			this.vdance.resetMotorsPos();
			if (frames.size() > 0) {
				for (int i = 0; i < frames.size(); i++) {
					final int iFrame = frames.get(i);
					final int[] motorangle = acts.get(i);
					this.vdance.addMotorAction(iFrame, motorangle[0], motorangle[1]);
				}
			}
		}

		private static final int[][] MOTORS_2_MOVE = { { 0 }, { 1 }, { 0, 1 }, { 1, 0 }, { 0, 1 }, { 1, 0 } };

		void motorsInsertMotorAtFrame(int iFrame, int[] lastMotorMove, int[] lastMotorPos, List<Integer> frames, List<int[]> acts, boolean theFirstMvt) {
			int angle = 45;
			int dangle = 45;
			int motor;

			// which motor(s)
			final int[] motorsToMove = Spectacle.MOTORS_2_MOVE[Spectacle.RANDOM_HOUR.nextInt(Spectacle.MOTORS_2_MOVE.length)];
			final int[] angles = { 0, 0 };

			// nouvelle position
			if (theFirstMvt || (Spectacle.RANDOM_HOUR.nextInt(3) == 1) || (motorsToMove.length == 2)) {
				angle = Spectacle.RANDOM_HOUR.nextInt(4) * 90;
				dangle = 0;
			} else {
				angle = 0;
				dangle = (Spectacle.RANDOM_HOUR.nextInt(5) - 2) * 20;
			}

			// LOGGER.debug("FR="+iFrame+" motors="+motorsToMove+" a="+angle);

			for (int imotor = 0; imotor < motorsToMove.length; imotor++) {
				motor = motorsToMove[imotor];

				if (angle == 0) {
					angle = lastMotorPos[motor] + dangle;
				}

				if (angle < 0) {
					angle = angle + 360;
				}
				if (angle >= 360) {
					angle = angle - 360;
				}

				if (lastMotorPos[motor] == angle) {
					// already there
					angles[imotor] = -1;
					continue;
				}

				final int dtime = this.vdance.delayToMoveFromTo(lastMotorPos[motor], angle);

				// enough time
				if (iFrame < lastMotorMove[motor] + dtime) {
					//trace("not enough time to move motor, now="+iFrame+" last="
					// +lastMotorMove[motor]+"dtime="+dtime);
					// drop both then..
					return;
				}

				angles[imotor] = angle;
			}

			for (int imotor = 0; imotor < motorsToMove.length; imotor++) {
				if (angles[imotor] == -1) {
					continue;
				}

				motor = motorsToMove[imotor];

				final int[] aMvt = { motor, angles[imotor] };
				frames.add(iFrame);
				acts.add(aMvt);

				lastMotorPos[motor] = angles[imotor];
				lastMotorMove[motor] = iFrame;
			}
		}

		void motorsRandomFromAudioEvents(List<Integer> iFrames) {
			// definit les mouvements de oreilles
			// en fonction d'une liste d'evenements d'importance
			final List<Integer> frames = new ArrayList<Integer>();
			final List<int[]> acts = new ArrayList<int[]>();

			int i;
			int iFrame;
			final int[] lastMotorMove = { 0, 0 }; // the last motor move
			final int[] lastMotorPos = { 0, 0 }; // cur pos of motor
			final int lastFrame = this.vdance.getLastTick();
			boolean theFirstMvt = true;

			// fabrique une chore maximale
			for (i = 0; i < iFrames.size(); i++) {
				iFrame = iFrames.get(i);

				// laisser de la place pour le dernier coda
				if (iFrame > lastFrame - this.vdance.getMotorSpeedInFrames() / 2.) {
					break;
				}

				motorsInsertMotorAtFrame(iFrame, lastMotorMove, lastMotorPos, frames, acts, theFirstMvt);

				theFirstMvt = false;
			}

			motorsCreateChoregraphy(frames, acts);
		}

		/**
		 * @see class doc for index
		 * @param soundData
		 * @return
		 */
		List<Integer> generateLEDsFromAudio(double[] soundData) {
			final List<Integer> iFrames = new ArrayList<Integer>();
			// tete : allumee quand son > seuil, avec moyenne
			final int meanFrames = this.vdance.getMSToFrames(300);
			final int longMeanFrames = this.vdance.getMSToFrames(1900);
			double mean = 0D;
			double longMean = 0D;
			boolean isOn = false;
			double lastValue = 0D;
			int lastFrameChanged = 0;
			final double minDiff = .1;
			final int minDelay = 2 * this.vdance.getMSToFrames(50);
			int offAt = -1;

			// HEAD LED
			for (int frameCounter = 0; frameCounter < soundData.length - 2; frameCounter++) {
				final double aDataBloc = soundData[frameCounter];
				final double aDataBlocAbs = Math.abs(aDataBloc);

				final int dFrames = Math.min(frameCounter + 1, meanFrames);

				mean = (mean * (dFrames - 1) + aDataBlocAbs) / dFrames;

				final int longdFrames = Math.min(frameCounter + 1, longMeanFrames);

				longMean = (longMean * (longdFrames - 1) + aDataBlocAbs) / longdFrames;

				if ((mean > longMean * 1.1) && !isOn) {
					// allume
					this.vdance.addAction(frameCounter, new VLedPaletteAction(VAction.LED.HEAD, Spectacle.RANDOM_HOUR.nextInt(7)));
					isOn = true;
				} else if ((mean < longMean / 1.1) && isOn) {
					// eteint
					this.vdance.addAction(frameCounter, new VLedPaletteAction(VAction.LED.HEAD, Spectacle.COLOR_IX_BLACK));
					isOn = false;
				}

				if ((offAt != -1) && (frameCounter >= offAt)) {
					this.vdance.addAction(frameCounter, new VLedPaletteAction(VAction.LED.LEFT, Spectacle.COLOR_IX_BLACK));
					this.vdance.addAction(frameCounter, new VLedPaletteAction(VAction.LED.CENTER, Spectacle.COLOR_IX_BLACK));
					this.vdance.addAction(frameCounter, new VLedPaletteAction(VAction.LED.RIGHT, Spectacle.COLOR_IX_BLACK));
					offAt = -1;
				}

				// THE REST OF THE SCUM
				if ((Math.abs(aDataBloc - lastValue) > minDiff) && (frameCounter > lastFrameChanged + minDelay)) {
					// change
					ledsSetColor(frameCounter, new VAction.LED[] { VAction.LED.LEFT, VAction.LED.CENTER, VAction.LED.RIGHT }, false, minDelay);

					lastValue = aDataBloc;
					lastFrameChanged = frameCounter;
					offAt = frameCounter + minDelay + 10;
				}
			}

			// BASE LED, the iFrame list is use to synchronize the BASE LED with
			// the ear motors
			final double footSens = 0.1;
			final int footDelay = this.vdance.getMSToFrames(100);
			final int maxDelay = this.vdance.getMSToFrames(500);

			int iFrame = 0;
			lastFrameChanged = 0;
			lastValue = 0D;
			final int initColor = Spectacle.RANDOM_HOUR.nextInt(7);

			int curCol = initColor;
			this.vdance.addAction(iFrame, new VLedPaletteAction(VAction.LED.BASE, curCol));
			iFrames.add(iFrame);
			lastValue = soundData[iFrame];
			lastFrameChanged = iFrame;

			while (++iFrame < soundData.length - 2) {
				final double curvalue = soundData[iFrame];
				if (((Math.abs(curvalue - lastValue) > footSens) && (iFrame > lastFrameChanged + footDelay)) || (iFrame > lastFrameChanged + maxDelay)) {
					curCol = (curCol == Spectacle.COLOR_IX_BLACK) ? initColor : Spectacle.COLOR_IX_BLACK;
					this.vdance.addAction(iFrame, new VLedPaletteAction(VAction.LED.BASE, curCol));

					iFrames.add(iFrame);
					lastFrameChanged = iFrame;
				}
			}
			return iFrames;
		}

		private int readFourBytesInt(byte[] audio, long offset) {

			int result = 0;
			for (int i = 0; i < 4; i++) {
				int temp = audio[(int) offset + i];

				if (temp < 0) {
					temp = temp + 256; // unsigned...
				}

				temp = temp << (i * 8);
				result = result + temp;
			}

			return result;

		}

		private short loadShort(byte[] audio, long offset) {
			int tmp1 = audio[(int) offset];
			final int tmp2 = audio[(int) offset + 1] * 256;

			if (tmp1 < 0) {
				tmp1 = tmp1 + 256;
			}

			return (short) (tmp1 + tmp2);
		}

		// WAVE file parsing
		private boolean strncmpbyte(byte[] audio, long offset, String str, long len) {
			final String bytestr = new String(audio, (int) offset, (int) len);
			return bytestr.equals(str);
		}

		double[] analyseWave(byte[] audio) {
			// 689877
			if (audio == null) {
				DanceGenerator.LOGGER.error("** ERROR : empty audio file");
				return new double[0];
			}

			DanceGenerator.LOGGER.debug("Start analyse WAV file");
			long pmarker = 0;
			int chunkSize;
			long framecount = 0;
			boolean more = true;
			boolean dataFound = false;
			int sampleRate = 44100;
			short bitsPerSample = 0;
			short nbChannels = 1;
			short[] shortMono = null;

			while (more) {
				if (strncmpbyte(audio, pmarker, "RIFF", 4)) {
					/* RIFF marker */
					/* get riffSize */
					pmarker += 8;

				} else if (strncmpbyte(audio, pmarker, "WAVE", 4)) {
					/* WAVE marker */
					pmarker += 4;

				} else if (strncmpbyte(audio, pmarker, "fact", 4)) {
					/* fact marker */
					pmarker += 4;

					/* get chunkSize */
					chunkSize = readFourBytesInt(audio, pmarker);
					pmarker += 4;

					/* get factSamples */

					pmarker += chunkSize;

				} else if (strncmpbyte(audio, pmarker, "fmt ", 4)) {
					/* "fmt " marker */
					pmarker += 4;

					/* get formatSize */
					chunkSize = readFourBytesInt(audio, pmarker);
					pmarker += 4;

					long tmpMarker = pmarker;
					/* formatType */
					tmpMarker += 2;
					nbChannels = loadShort(audio, tmpMarker);
					tmpMarker += 2;
					sampleRate = readFourBytesInt(audio, tmpMarker);
					tmpMarker += 10;
					/*  short blockAlign */
					bitsPerSample = loadShort(audio, tmpMarker);
					tmpMarker += 2;
					// LOGGER.debug("bitsPerSample"+bitsPerSample);

					pmarker += chunkSize;

				} else if (strncmpbyte(audio, pmarker, "data", 4)) {
					/* "data" marker */
					pmarker += 4;

					/* get chunkSize */
					chunkSize = readFourBytesInt(audio, pmarker);
					pmarker += 4;
					final int remainingBytes = audio.length - (int) pmarker;
					if (chunkSize > remainingBytes) {
						chunkSize = remainingBytes;
					}

					/* do it */
					framecount = chunkSize * 8 / (bitsPerSample * nbChannels);

					shortMono = new short[(int) framecount];
					for (int i = 0; i < framecount; i++) {
						int v = 0;
						for (int j = 0; j < nbChannels; j++) {
							v += loadShort(audio, pmarker + (i * nbChannels + j) * 2);
						}
						shortMono[i] = (short) (v / nbChannels);
					}

					pmarker += chunkSize;
					dataFound = true;
					more = false;
				} else {
					more = false;
				}
			} // while more

			if (!dataFound) {
				DanceGenerator.LOGGER.error("Bad Sound format, data not found");
				return new double[0];
			}

			final double everyi = DanceGenerator.NB_SOUND_SAMPLES_PER_SECOND * 1. / sampleRate;
			final int subframecount = (int) Math.round(framecount * everyi);
			final int every = (int) framecount / subframecount;
			final double[] res = new double[subframecount];
			// LOGGER.debug("subframecount="+subframecount);
			// LOGGER.debug("everyi="+everyi);
			short vv;
			if (shortMono != null) {
				for (int i = 0, j = 0; (i < framecount) && (j < subframecount); i += every, j++) {
					vv = shortMono[i];
					res[j] = vv / 32768D;
					// if (j < 100) LOGGER.debug("sample"+j+"="+res[j]);
				}
			}
			return res;

		}

		void coda() {
			// FIN
			final int iFrame = this.vdance.getLastTick();
			DanceGenerator.LOGGER.debug("End of music= " + iFrame + " frames");

			// moteurs au repos a la fin
			this.vdance.addMotorAction(iFrame, 0, 0);
			this.vdance.addMotorAction(iFrame, 1, 0);

			// eteint toutes LEDs a la fin

			for (final VAction.LED aLed : VAction.LED.values()) {
				this.vdance.addAction(iFrame + 2 * DanceGenerator.NB_SOUND_SAMPLES_PER_SECOND, new VLedPaletteAction(aLed, Spectacle.COLOR_IX_BLACK));
			}
		}

		private byte[] toByteArray() {
			final ByteArrayOutputStream theBinary = new ByteArrayOutputStream();
			// Make space for the header.

			try {
				theBinary.write(new byte[5]); // 5 zeros : 4 for the byteStream length and 1 for NOP
			} catch (final IOException e) {
				DanceGenerator.LOGGER.fatal(e, e);
			}

			theBinary.write(Spectacle.FRAME_DURATION_CHOR_CMD);
			theBinary.write(this.vdance.frameDur10ms());
			this.vdance.append(theBinary);

			theBinary.write(0);
			theBinary.write(0);
			theBinary.write(0);
			theBinary.write(0);

			final byte[] theByteArray = theBinary.toByteArray();
			final int theSize = theByteArray.length - 8;
			theByteArray[0] = (byte) ((theSize >> 24) & 0xFF);
			theByteArray[1] = (byte) ((theSize >> 16) & 0xFF);
			theByteArray[2] = (byte) ((theSize >> 8) & 0xFF);
			theByteArray[3] = (byte) (theSize & 0xFF);
			return theByteArray;
		}

		byte[] generateFromAudio(byte[] audio) throws IllegalArgumentException {
			final double[] soundData = analyseWave(audio);
			if (soundData.length == 0) {
				throw new IllegalArgumentException("Sound data is empty");
			}

			DanceGenerator.LOGGER.debug("Starting audio generation, with " + soundData.length + " samples");

			// Motors : utilise les frames sur lesquelles il y a allumage de LED
			// : synchronise les oreilles aux LEDs.
			motorsRandomFromAudioEvents(generateLEDsFromAudio(soundData));

			coda();

			DanceGenerator.LOGGER.debug("End Audio generation. Success");
			return toByteArray();
		}
	} // END of class Spectacle

	private static final int gNbDents = 20;

	@Override
	protected TmpFile doConvert(TmpFileWrapper inContent, ContentType inputType, ContentType outputType) {
		try {
			if (inputType == ContentType.WAV_16) {
				return AbstractAlterator.FILES_MANAGER.new TmpFile(new Spectacle().generateFromAudio(inContent.get().getContent()));
			}

			if (inputType == ContentType.CHOR_CDL) {
				return AbstractAlterator.FILES_MANAGER.new TmpFile(new Spectacle(new String(inContent.get().getContent(), "UTF-8")).toByteArray());

			}

			assert false;

		} catch (final IllegalArgumentException e) {
			DanceGenerator.LOGGER.error(e, e); // users using the API sends the bad chor
		} catch (final IOException e) {
			DanceGenerator.LOGGER.fatal(e, e);
		} finally {
			inContent.clean();
		}

		return null;
	}

	private static final Set<ContentType> AVAILABLE_INPUT = Collections.unmodifiableSet(new HashSet<ContentType>(Arrays.asList(ContentType.WAV_16, ContentType.CHOR_CDL)));
	private static final Set<ContentType> AVAILABLE_OUTPUT = Collections.singleton(ContentType.CHOR);

	public Set<ContentType> getAvailableOutput() {
		return DanceGenerator.AVAILABLE_OUTPUT;
	}

	public Set<ContentType> getAvailableInput() {
		return DanceGenerator.AVAILABLE_INPUT;
	}
}
