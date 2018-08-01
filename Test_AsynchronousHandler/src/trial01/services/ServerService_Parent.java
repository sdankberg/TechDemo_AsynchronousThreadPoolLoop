package trial01.services;

import trial01.interfaces.IServerService;

public abstract class ServerService_Parent implements IServerService {

	protected final int id;
	
	public ServerService_Parent(final int id) {
		this.id = id;
	}
}
