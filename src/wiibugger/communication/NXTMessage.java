package wiibugger.communication;

/**
 * Represents a message that is send from the pc to an nxt.
 * It contains the following information:
 *
 * The actual message transmitted is a 16 bit short composed
 * of these four informations:
 * <ul>
 * 	<li>1 bit for the nxt (ARM or MOVE)</li>
 *  <li>2 bits for the motor (A, B or C)</li>
 *  <li>4 bits for the action (e.g. forward, stop, rotate_to, ...)</li>
 *  <li>9 bits for the parameter (e.g. rotation speed, ...)</li>
 * </ul>
 */
public class NXTMessage {
	
	public static final short MOTOR_A = 0;
	public static final short MOTOR_B = 1;
	public static final short MOTOR_C = 2;
	
	public static final short LEFT_WHEEL  = MOTOR_B;
	public static final short RIGHT_WHEEL = MOTOR_A;
	public static final short TURN        = MOTOR_C;
	
	public static final short WHOLE_ARM  = MOTOR_A;
	public static final short ARM_MIDDLE = MOTOR_B;
	public static final short CLAW       = MOTOR_C;
	
	public static final short ARM  = 1;
	public static final short MOVE = 0;	
	
	public static final short MOTOR_FORWARD   = 0;
	public static final short MOTOR_BACKWARD  = 1;
	public static final short MOTOR_STOP      = 2;
	public static final short MOTOR_ROTATE    = 3;
	public static final short MOTOR_ROTATE_TO = 4;
	public static final short MOTOR_FLOAT     = 5;
	
	/**
	 * When the nxt recceives this message, it closes the connection
	 */
	public static final short CLOSE_MESSAGE = -127; // 1111111111111111

	private short nxt;
	private short port;
	private short operation;
	private short value;
	
	/**
	 * Compose a message with the given information.
	 * @param nxt
	 * @param port
	 * @param operation
	 * @param value
	 * @throws IllegalArgumentException
	 */
	public NXTMessage(short nxt, short port, short operation, short value) throws IllegalArgumentException {
		if (nxt > 1) throw new IllegalArgumentException();
		this.nxt = nxt;
		if (port > 3) throw new IllegalArgumentException();
		this.port = port;
		if (operation > 15) throw new IllegalArgumentException();
		this.operation = operation;
		if (value > 511) throw new IllegalArgumentException();
		this.value = value;
	}
	
	/**
	 * Create an NXTMessage with a given message-short.
	 * Helpful for decoding the message.
	 * @param input The short containing a message.
	 */
	public NXTMessage (short input) {	
		/*
		 * Get value by applying a bitmask and shifting it to the right
		 */
		
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
	
	/**
	 * Get the actual bit-form of the message.
	 * @return
	 */
	public short getOutput() {
		return (short)( (this.nxt << 15) | (this.port << 13) | (this.operation << 9) | (this.value) ); 
	}
	
	public boolean isArm() {
		return nxt == ARM;
	}
	
	public boolean isMove() {
		return nxt == MOVE;
	}
	
	public String toString() {
		return Integer.toBinaryString(getOutput());
	}
}

