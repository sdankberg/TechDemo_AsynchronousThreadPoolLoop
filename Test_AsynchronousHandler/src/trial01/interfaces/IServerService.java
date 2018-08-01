package trial01.interfaces;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public interface IServerService {
	
	<T> T onFuture(Future<T> future) throws Exception;
	<T> Callable<T> onCall();
}
