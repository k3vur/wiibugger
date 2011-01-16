package wiibugger.communication;


public class NXTMessage {
	
	public static final short PORT_A = 0;
	public static final short PORT_B = 1;
	public static final short PORT_C = 2;
	
	public static final short LEFT_WHEEL = PORT_B;
	public static final short RIGHT_WHEEL = PORT_A;
	public static final short TURN = PORT_C;
	
	public static final short WHOLE_ARM = PORT_A;
	public static final short ARM_MIDDLE = PORT_B;
	public static final short CLAW = PORT_C;
	
	public static final short ARM  = 1;
	public static final short MOVE = 0;	
	
	public static final short MOTOR_FORWARD = 0;
	public static final short MOTOR_BACKWARD = 1;
	public static final short MOTOR_STOP = 2;
	public static final short MOTOR_ROTATE = 3;
	public static final short MOTOR_ROTATE_TO = 4;
	public static final short MOTOR_FLOAT = 5;
	
	public static final short CLOSE_MESSAGE = -127; // 1111111111111111

	private short nxt;
	private short port;
	private short operation;
	private short value;
		
	public NXTMessage(short nxt, short port, short operation, short value) throws IllegalArgumentException {
		if (nxt > 1 || nxt < 0) throw new IllegalArgumentException();
		this.nxt = nxt;
		if (port > 3 || port < 1) throw new IllegalArgumentException();
		this.port = port;
		if (operation > 15) throw new IllegalArgumentException();
		this.operation = operation;
		if (value > 511) throw new IllegalArgumentException();
		this.value = value;
	}
		
	public NXTMessage (short input) {	
		// bitmasks
		// first bit    (1000000000000000)
		this.nxt = (short)((input & 0x8000) >> 15);
		// next 2 bits  (0110000000000000)
		this.port = (short)((input & 0x6000) >> 13);
		// next 4 bits  (0001111000000000)
		this.operation = (short)((input & 0x1E00) >> 9);
		// lasat 9 bits (0000000111111111)
		this.value = (short)(input & 0x01FF);
	}

	public short getPort() {
		return port;
	}	

	public short getOperation() {
		return operation;
	}

	public short getValue() {
		return value;
	}
	
	public short getNxt() {
		return nxt;
	}
	
	public short getOutput() {
		return (short)( (this.nxt << 15) | (this.port << 13) | (this.operation << 9) | (this.value) ); 
	}
	
//	public void setArm() {
//		if (isArm()) return;
//		
//		this.armOrMove = true;
//		this.port = (short) (ARM | port);
//	}
//
//	public void setMove() {
//		if (isMove()) return;
//		
//		this.armOrMove = false;
//		this.port = (short) (port & 3); // 3 = 11 => last two bits
//	}
	
	public boolean isArm() {
		return nxt == ARM;
	}
	
	public boolean isMove() {
		return nxt == MOVE;
	}
}

