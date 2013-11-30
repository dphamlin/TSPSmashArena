package game;

import java.io.IOException;

import java.util.concurrent.locks.*;

public class ServerRunnable implements Runnable {
	private Server theServer;
	private int i;
	private RemoteParticipant p;

	public ServerRunnable(Server s, int i){
		theServer = s;
		this.i = i;
		p = (RemoteParticipant) theServer.getParticipantList().get(i);
	}

	public void run() {

		// gets Message from client
		while(true){
			theServer.getLock().lock();
			try {
				while(!theServer.getRReady()){
					theServer.getCondition().await();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				theServer.getLock().unlock();
			}

			if (p.isActive()){ // Only try to read from active players;
				// thread will be responsible for changing 
				// back to active on reconnect
				try {
					if (p.getSocket().isClosed()){
						throw new IOException("Socket closed.");
					}
					if (!p.isSpectator())
						p.readMessage();
				}
				catch (IOException e) {
					System.err.println("Participant disconnected on reading message. Set to inactive. " + e.getMessage());
					p.setActive(false);
					theServer.getActPlLock().lock();
					try {
						theServer.setActivePlayerCount(theServer.getActivePlayerCount()-1);
					} finally {
						theServer.getActPlLock().unlock();
					}
				}
			}

			theServer.getLock().lock();
			try {
				theServer.setCount(theServer.getCount()-1);
				if(theServer.getCount() == 0){
					theServer.getCondition().signalAll();
				}
			} finally {
				theServer.getLock().unlock();
			}


			// sends Message to client
			theServer.getLock().lock();
			try {
				while(!theServer.getWReady()){
					theServer.getCondition().await();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				theServer.getLock().unlock();
			}

			if (p.isActive()){
				try {
					if (p.isSpectator() && !p.isConfirmedSpectator()) {
						System.out.println("About to send a 3");
						p.writeToClient(theServer.getGson().toJson((new Message(3,null))));
						p.setIsConfirmedSpectator(true);
						theServer.setNamesSent(false);
					}
					else
						p.writeToClient(theServer.getGson().toJson(theServer.getMessage()));
				}
				catch (IOException e) {
					System.err.println("Participant disconnected while writing message.  Set to inactive. " + e.getMessage());
					p.setActive(false);
					theServer.getActPlLock().lock();
					try {
						theServer.setActivePlayerCount(theServer.getActivePlayerCount()-1);
					} finally {
						theServer.getActPlLock().unlock();
					}
				}
			}

			theServer.getLock().lock();
			try {
				theServer.setCount(theServer.getCount()-1);
				if(theServer.getCount() == 0){
					theServer.getCondition().signalAll();
				}
			} finally {
				theServer.getLock().unlock();
			}
		}
	}

}