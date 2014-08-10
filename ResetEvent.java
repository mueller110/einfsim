import desmoj.core.simulator.Event;
import desmoj.core.simulator.Model;

/**
 * The reset event
 */
public class ResetEvent extends Event<QueueEntity> {
	private EmergencyRoomModel model;

	/**
	 * constructor
	 * @param owner
	 * @param name
	 * @param showInTrace
	 */
	public ResetEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		model = (EmergencyRoomModel) owner;
	}

	@Override
	public void eventRoutine(QueueEntity queueEntity) {
		queueEntity.queue.reset();
	}
}
