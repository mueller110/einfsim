import desmoj.core.simulator.Event;
import desmoj.core.simulator.Model;

/**
 * Event that resets a queue, triggered after the initial phase
 */
public class ResetEvent extends Event<QueueEntity> {
	

	/**
	 * constructor
	 * @param owner
	 * @param name
	 * @param showInTrace
	 */
	public ResetEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		
	}

	@Override
	public void eventRoutine(QueueEntity queueEntity) {
		queueEntity.queue.reset();
	}
}
