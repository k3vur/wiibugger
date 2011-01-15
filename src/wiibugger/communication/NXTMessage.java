package wiibugger.communication;


public class NXTMessage {
	
	public static final short PORT_A = 0;
	public static final short PORT_B = 1;
	public static final short PORT_C = 2;
	public static final short PORT_1 = 3;
	public static final short PORT_2 = 4;
	public static final short PORT_3 = 5;
	public static final short PORT_4 = 6;
	
	
	public static final short MOTOR_FORWARD = 0;
	public static final short MOTOR_BACKWARD = 1;
	public static final short MOTOR_STOP = 2;
	public static final short MOTOR_ROTATE = 3;
	public static final short MOTOR_ROTATE_TO = 4;
	public static final short MOTOR_FLOAT = 5;
	
	public static final short CLOSE_MESSAGE = -127;
	
	private short nxtDevice;
	private short port;
	private short operation;
	private short value;
	
	public NXTMessage(short nxtDevice, short port, short operation, short value) throws IllegalArgumentException {
		this.nxtDevice = nxtDevice; 
		if(port > 7) throw new IllegalArgumentException();
		this.port = port;
		if(operation > 15) throw new IllegalArgumentException();
		this.operation = operation;
		if(value > 511) throw new IllegalArgumentException();
		this.value = value;
	}
		
	public NXTMessage (short input) {	
		// bitmasks
		// first 3 bits
		this.port = (short) ((input & 0xE000) >> 13);
		// next 4 bits
		this.operation = (short)((input & 0x1E00) >> 9);
		// rest
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
	
	public short getOutput () {
		return (short)( (this.port << 13) | (this.operation << 9) | (this.value) ); 
	}

	public short getNxtDevice() {
		return nxtDevice;
	}
}

