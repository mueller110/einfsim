import desmoj.core.simulator.Event;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.Queue;
import desmoj.core.simulator.SimTime;

@SuppressWarnings("deprecation")
public class TreatmentTermination extends Event<PatientEntity> {
	private EmergencyRoomModel model;
	static int removed = 0;

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

		if (patient.getPriority() == 1 || patient.getPriority() == 3) {
			patient.setPriority(2);
			PatientArrivalEvent arrival = new PatientArrivalEvent(model,
					"Last check of Patient", true);
			arrival.schedule(patient, new SimTime(0.0)); // instant arrival
		} else {
			model.allPatientsQueue.insert(patient);
		}
		
		EmergencyRoomModel.treatedPantientQueue.remove(patient);
		
		if (queue != null) {
			PatientEntity nextPatient = queue.first();
			queue.remove(nextPatient);
			EmergencyRoomModel.treatedPantientQueue.insert(nextPatient);
			if (nextPatient.getPriority() == 2) {
				nextPatient.departureTime = model.currentTime();
				nextPatient.waitingTime = SimTime.add(
						SimTime.diff(nextPatient.end, nextPatient.arrivalTime),
						SimTime.diff(nextPatient.departureTime, nextPatient.start2));
				if (SimTime.isSmallerOrEqual(nextPatient.waitingTime,
						new SimTime(5.0))) {
					model.underFive++;
				}
			} else {
				nextPatient.end = model.currentTime();
			}

			TreatmentTermination treatmentTerm = new TreatmentTermination(
					model, "End of Treatment", true);
			treatmentTerm.schedule(
					nextPatient,
					new SimTime(model.getTreatmentTime(nextPatient
							.getPriority())));
			nextPatient.treatmentTermination = treatmentTerm;

		} else {
			DoctorEntity doctor = (DoctorEntity) model.busyDoctorQueue.first();
			// make doc available
			model.busyDoctorQueue.remove(doctor);
			model.freeDoctorQueue.insert(doctor);
		}

	}
}
