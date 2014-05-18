import desmoj.core.simulator.Event;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.Queue;
import desmoj.core.simulator.SimTime;

public class PatientArrivalEvent extends Event<PatientEntity> {
	private EmergencyRoomModel model;

	public PatientArrivalEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		model = (EmergencyRoomModel) owner;
	}

	@Override
	public void eventRoutine(PatientEntity patient) {
		Queue<PatientEntity> queue;
		int cPriority = patient.getPriority();
		if (cPriority == 1) {
			queue = model.lowPriorityPatientQueue;
		} else if (cPriority == 2 || cPriority == 4) {
			queue = model.lastCheckPatientQueue;
			patient.setPriority(4);
		} else {
			queue = model.highPriorityPatientQueue;
		}
		queue.insert(patient);

		// there is a free doctor?! -> no waiting
		if (!model.freeDoctorQueue.isEmpty()) {
			DoctorEntity doctor = (DoctorEntity) model.freeDoctorQueue.first();
			model.freeDoctorQueue.remove(doctor);
			model.busyDoctorQueue.insert(doctor);
			queue.remove(patient);
			
			// set priority to 2, for the last check queue
			if(patient.getPriority()==1 || patient.getPriority()==3){
				patient.setPriority(2);
			}
			System.out.println(patient.getPriority());
			
			TreatmentTermination treatmentTerm = new TreatmentTermination(
					model, "End of Treatment", true);
			treatmentTerm.schedule(patient,
					new SimTime(model.getTreatmentTime(patient.getPriority())));
		}

	}

}
