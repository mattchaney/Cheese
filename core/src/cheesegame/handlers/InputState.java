package cheesegame.handlers;

public class InputState {
	
	public static boolean[] keys;
	public static boolean[] pkeys;
	
	public static int x;
	public static int y;
	
	public static final int NUM_KEYS = 10;
	public static final int MOUSE1 = 0;
	public static final int JUMP = 1;
	public static final int BUTTON2 = 2;
	public static final int LEFT = 3;
	public static final int RIGHT = 4;
	public static final int F1 = 5;
	public static final int F5 = 6;
	public static final int UNDO = 7;
	public static final int CLEAR = 8;
	public static final int ESC = 9;
	
	static {
		keys = new boolean[NUM_KEYS];
		pkeys = new boolean[NUM_KEYS];
	}
	
	public static void update() {
		for(int i=0; i < NUM_KEYS; i++) {
			pkeys[i] = keys[i];
		}
	}
	
	public static void setKey(int i, boolean b) {
		keys[i] = b;
	}
	
	public static void setClickCoords(int nx, int ny) {
		x = nx;
		y = ny;
	}
	
	public static boolean isDown(int i) {
		return keys[i];
	}
	
	public static boolean isPressed(int i) {
		return keys[i] && !pkeys[i];
	}
	
}
