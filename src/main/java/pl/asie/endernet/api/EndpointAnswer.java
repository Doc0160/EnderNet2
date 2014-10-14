package pl.asie.endernet.api;

public class EndpointAnswer {
	public enum Status {
		ANSWERED,
		SERVER_ERROR,
		NOT_FOUND
	}
	
	private Object output;
	private Status status;
	
	public EndpointAnswer(Object o, Status s) {
		this.output = o;
		this.status = s;
	}
	
	public Object getOutput() {
		return output;
	}
	
	public boolean hasOutput() {
		return (status == Status.ANSWERED && output != null);
	}
	
	public Status getStatus() {
		return status;
	}
}
