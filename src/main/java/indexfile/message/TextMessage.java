package indexfile.message;

import indexfile.marshaller.MessageIDMarshaller;

public class TextMessage implements Message {
	
	private String messageId;
	
	private String text;
	

	public TextMessage() {
	}

	public TextMessage(String messageId, String text) {
		this.messageId = messageId;
		this.text = text;
	}

	@Override
	public byte[] getByte() {
		byte[] b1 = MessageIDMarshaller.instance.writeBytes(messageId);
		byte[] t1 = text.getBytes();
		byte[] bytes = new byte[b1.length+t1.length];
		System.arraycopy(b1, 0, bytes, 0, b1.length);
		System.arraycopy(t1, 0, bytes, b1.length, t1.length);
		return bytes;
	}

	@Override
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	@Override
	public String getMessageId() {
		return messageId;
	}

	@Override
	public int dataType() {
		return TYPE_TEXT;
	}

	@Override
	public void parse(byte[] bytes) {
		this.messageId = MessageIDMarshaller.instance.readBytes(bytes);
		int sizeContent = bytes.length-MessageIDMarshaller.leyLength;
		byte[] bytes1 = new byte[sizeContent];
		System.arraycopy(bytes, MessageIDMarshaller.leyLength, bytes1, 0, sizeContent);
		this.text = new String(bytes1);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
	
	

}
