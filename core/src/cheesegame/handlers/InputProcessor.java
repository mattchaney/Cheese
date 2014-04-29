package cheesegame.handlers;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class InputProcessor extends InputAdapter {
	
	@Override
	public boolean keyDown(int key) {
		switch(key) {
			case Keys.Z: 	   InputState.setKey(InputState.JUMP,    true); 	break;
			case Keys.X: 	   InputState.setKey(InputState.BUTTON2, true); 	break;
			case Keys.LEFT:    InputState.setKey(InputState.LEFT,    true); 	break;
			case Keys.RIGHT:   InputState.setKey(InputState.RIGHT,   true); 	break;
			case Keys.F1: 	   InputState.setKey(InputState.F1, 	 true); 	break;
			case Keys.F5:	   InputState.setKey(InputState.F5, 	 true);		break;
			case Buttons.LEFT: InputState.setKey(InputState.MOUSE1,  true); 	break;
			case Keys.U:	   InputState.setKey(InputState.UNDO,    true);		break;
			case Keys.C:	   InputState.setKey(InputState.CLEAR,   true);		break;
			case Keys.ESCAPE:  InputState.setKey(InputState.ESC, 	 true);		break;	
		}
		return true;
	}
	
	@Override
	public boolean keyUp(int k) {
		switch(k) {
			case Keys.Z: 	   InputState.setKey(InputState.JUMP,    false); 	break;
			case Keys.X: 	   InputState.setKey(InputState.BUTTON2, false); 	break;
			case Keys.LEFT:    InputState.setKey(InputState.LEFT,    false); 	break;
			case Keys.RIGHT:   InputState.setKey(InputState.RIGHT,   false); 	break;
			case Keys.F1: 	   InputState.setKey(InputState.F1, 	 false); 	break;
			case Keys.F5:	   InputState.setKey(InputState.F5, 	 false);	break;
			case Buttons.LEFT: InputState.setKey(InputState.MOUSE1,  false); 	break;
			case Keys.U:	   InputState.setKey(InputState.UNDO,    false);	break;
			case Keys.C:	   InputState.setKey(InputState.CLEAR,   false);	break;
			case Keys.ESCAPE:  InputState.setKey(InputState.ESC, 	 false);	break;
		}
		return true;
	}
	
	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		switch(button) {
			case Buttons.LEFT:
				InputState.setKey(InputState.MOUSE1, true);
				InputState.setClickCoords(screenX, screenY);
				break;
		}
		return true;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		switch(button) {
			case Buttons.LEFT:
				InputState.setKey(InputState.MOUSE1, false); break;
		}
		return true;
	}
}
