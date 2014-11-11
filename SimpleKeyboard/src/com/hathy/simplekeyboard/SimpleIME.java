package com.hathy.simplekeyboard;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.media.AudioManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

public class SimpleIME extends InputMethodService
	implements OnKeyboardActionListener{
	
	private KeyboardView kv;
	private Keyboard keyboard;
	
	private boolean caps = false;
	
	@Override
	public View onCreateInputView() {
		kv = (KeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null);
		keyboard = new Keyboard(this, R.xml.qwerty);
		kv.setKeyboard(keyboard);
		kv.setOnKeyboardActionListener(this);
		kv.invalidateAllKeys();
		return kv;
	}

	@Override
	public void onKey(int primaryCode, int[] keyCodes) {		
		InputConnection ic = getCurrentInputConnection();
		playClick(primaryCode);
		switch(primaryCode){
		case Keyboard.KEYCODE_DELETE :
			ic.deleteSurroundingText(1, 0);
			break;
		case Keyboard.KEYCODE_SHIFT:
			caps = !caps;
			keyboard.setShifted(caps);
			kv.invalidateAllKeys();
			break;
		case Keyboard.KEYCODE_DONE:
			ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
			break;
		default:
			char code = (char)primaryCode;
			if(Character.isLetter(code) && caps){
				code = Character.toUpperCase(code);
			}
			ic.commitText(String.valueOf(code),1);					
		}
	}
	
	private void playClick(int keyCode){
		AudioManager am = (AudioManager)getSystemService(AUDIO_SERVICE);
		switch(keyCode){
		case 32: 
			am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
			break;
		case Keyboard.KEYCODE_DONE:
		case 10: 
			am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
			break;
		case Keyboard.KEYCODE_DELETE:
			am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
			break;				
		default: am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
		}		
	}

	@Override
	public void onPress(int primaryCode) {
	}

	@Override
	public void onRelease(int primaryCode) { 			
	}

	@Override
	public void onText(CharSequence text) {		
	}

	@Override
	public void swipeDown() {	
	}

	@Override
	public void swipeLeft() {
	}

	@Override
	public void swipeRight() {
	}

	@Override
	public void swipeUp() {
	}
}
