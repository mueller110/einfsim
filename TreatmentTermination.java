import desmoj.core.simulator.Event;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.Queue;
import desmoj.core.simulator.SimTime;

public class TreatmentTermination extends Event<PatientEntity> {
	private EmergencyRoomModel model;

	public TreatmentTermination(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		model = (EmergencyRoomModel) owner;
	}

	@Override
	public void eventRoutine(PatientEntity patient) {
		Queue<PatientEntity> queue = null;
		int cPriority = patient.getPriority();

		if (!model.highPriorityPatientQueue.isEmpty()) {
			queue = model.highPriorityPatientQueue;
		} else if (!model.lowPriorityPatientQueue.isEmpty()) {
			queue = model.lowPriorityPatientQueue;
		} else if (!model.lastCheckPatientQueue.isEmpty()) {
			queue = model.lastCheckPatientQueue;
		}

		if (queue != null) {
			PatientEntity nextPatient = queue.first();
			queue.remove(nextPatient);
			
			//rm
			if(patient.getPriority()==2){
				PatientArrivalEvent arrival = new PatientArrivalEvent(model, "Last check of Patient", true);
				arrival.schedule(patient, new SimTime(0.0)); // instant arrival
			}
			//rm:end
			
			TreatmentTermination treatmentTerm = new TreatmentTermination(
					model, "End of Treatment", true);
			treatmentTerm.schedule(
					nextPatient,
					new SimTime(model.getTreatmentTime(nextPatient
							.getPriority())));

		} else {
			DoctorEntity doctor = (DoctorEntity) model.busyDoctorQueue.first();
			// make doc available
			model.busyDoctorQueue.remove(doctor);
			model.freeDoctorQueue.insert(doctor);
		}

	}
}
