package command;

public class Result<T> {
	T result;

	public Result(T result){
		this.result = result;
	}
	
	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
	
	@Override
	public String toString(){
		return ((T)result).toString();
	}
}
