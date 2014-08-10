import desmoj.core.simulator.Event;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.Queue;
import desmoj.core.simulator.SimTime;

/**
 * The treatment termination event represents the end of a patient's treatment
 */
@SuppressWarnings("deprecation")
public class TreatmentTermination extends Event<PatientEntity> {
	private EmergencyRoomModel model;
	static int removed = 0;

	/**
	 * constructor
	 * 
	 * @param owner
	 * @param name
	 * @param showInTrace
	 */
	public TreatmentTermination(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		model = (EmergencyRoomModel) owner;
	}

	@Override
	public void eventRoutine(PatientEntity patient) {
		Queue<PatientEntity> queue = null;
		int cPriority = patient.getPriority();
		patient.treatementInterrupted = false;

		// get a patient: 3 before 2 before 1
		if (!model.highPriorityPatientQueue.isEmpty()) {
			queue = model.highPriorityPatientQueue; // 3
		} else if (!model.lastCheckPatientQueue.isEmpty()) {
			queue = model.lastCheckPatientQueue; // 2
		} else if (!model.lowPriorityPatientQueue.isEmpty()) {
			queue = model.lowPriorityPatientQueue; // 1
		}

		// prio 1 & 3 ---> 2
		if (patient.getPriority() == 1 || patient.getPriority() == 3) {
			patient.setPriority(2);
			PatientArrivalEvent arrival = new PatientArrivalEvent(model,
					"Last check of Patient", true);
			arrival.schedule(patient, new SimTime(0.0)); // instant arrival
		} else {		
				// this was the last waiting time for nextPatient => we can set
				// its departure time
				patient.departureTime = new SimTime(model.currentTime());
		} 
		// remove the patient of the treatment queue
		EmergencyRoomModel.inTreatmentQueue.remove(patient);
		
		if (queue != null) {
			PatientEntity nextPatient = queue.first();
			queue.remove(nextPatient);
			nextPatient.treatmentStart = new SimTime(model.currentTime());
			EmergencyRoomModel.inTreatmentQueue.insert(nextPatient);

			if (nextPatient.getPriority() == 3 && model.deathOfPatientsFlag) {
				// cancel the death event
				nextPatient.deathEvent.cancel();
			}

			if (nextPatient.waitingTime == null)
				nextPatient.waitingTime = new SimTime(0.0);
			nextPatient.end = model.currentTime();
			nextPatient.waitingTime = SimTime.add(nextPatient.waitingTime,
					SimTime.diff(nextPatient.end, nextPatient.start));
			TreatmentTermination treatmentTerm = new TreatmentTermination(
					model, "End of Treatment", true);

			if (nextPatient.treatementInterrupted) {
				// get the rest of the treatment time
				nextPatient.treatmentDuration = new SimTime(nextPatient.rest);
				treatmentTerm.schedule(nextPatient, nextPatient.rest);
			} else {
				nextPatient.treatmentDuration = new SimTime(
						model.getTreatmentTime(nextPatient.getPriority()));
				treatmentTerm.schedule(nextPatient, new SimTime(
						nextPatient.treatmentDuration));
			}
			nextPatient.treatmentTermination = treatmentTerm;
		} else {
			// all (patient)queues are empty
			DoctorEntity doctor = (DoctorEntity) model.busyDoctorQueue.first();
			// make the doctor available
			model.busyDoctorQueue.remove(doctor);
			model.freeDoctorQueue.insert(doctor);
		}

	}
}
