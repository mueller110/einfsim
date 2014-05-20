import desmoj.core.simulator.Event;
import desmoj.core.simulator.Model;



public class ResetEvent extends Event<QueueEntity> {	
	private EmergencyRoomModel model;
	public ResetEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		model = (EmergencyRoomModel) owner;
	}
	
	@Override
	public void eventRoutine(QueueEntity queueEntity) {
		queueEntity.queue.reset();
		System.out.println("Doing the fancy stuff");
	}
}
