import desmoj.core.simulator.Event;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.Queue;
import desmoj.core.simulator.SimTime;

@SuppressWarnings("deprecation")
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
		} else if (!model.lastCheckPatientQueue.isEmpty()) {
			queue = model.lastCheckPatientQueue;
		} else if (!model.lowPriorityPatientQueue.isEmpty()) {
			queue = model.lowPriorityPatientQueue;
		}

		if(patient.getPriority()==1 || patient.getPriority()==3){
//			System.out.println(patient.getIdentNumber() + " into last check (" + patient.getPriority() + ")");
			patient.setPriority(2);
			PatientArrivalEvent arrival = new PatientArrivalEvent(model, "Last check of Patient", true);
			arrival.schedule(patient, new SimTime(0.0)); // instant arrival
		} else {
//			System.out.println(patient.getIdentNumber() + " leaves. (" + patient.getPriority() + ")");
		}
		
		if (queue != null) {
			PatientEntity nextPatient = queue.first();
			queue.remove(nextPatient);
			
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
