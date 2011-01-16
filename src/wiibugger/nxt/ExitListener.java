package wiibugger.nxt;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;

public class ExitListener implements ButtonListener {

	@Override
	public void buttonPressed(Button arg0) {
		System.exit(0);
	}

	@Override
	public void buttonReleased(Button arg0) { }

}
