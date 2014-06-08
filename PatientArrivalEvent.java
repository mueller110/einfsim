import desmoj.core.simulator.Event;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.Queue;
import desmoj.core.simulator.SimTime;

@SuppressWarnings("deprecation")
public class PatientArrivalEvent extends Event<PatientEntity> {

	static int total = 0;
	private EmergencyRoomModel model;

	public PatientArrivalEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		model = (EmergencyRoomModel) owner;
	}

	@Override
	public void eventRoutine(PatientEntity patient) {
		Queue<PatientEntity> queue;
		int cPriority = patient.getPriority();
		total++;
		if (cPriority == 1) {
			if (!patient.treatementInterrupted) {
				patient.arrivalTime = new SimTime(model.currentTime());
			}
			queue = model.lowPriorityPatientQueue;
		} else if (cPriority == 2) {
			queue = model.lastCheckPatientQueue;
		} else {
			patient.arrivalTime = new SimTime(model.currentTime());
			queue = model.highPriorityPatientQueue;
			if (model.deathOfPatientsFlag) {
				PatientDeathEvent pde = new PatientDeathEvent(model, "Death",
						true);
				pde.schedule(patient, new SimTime(model.getDeathTime()));
				patient.deathEvent = pde;
			}
		}
		if (patient.waitingTime == null)
			patient.waitingTime = new SimTime(0.0);
		queue.insert(patient);
		// there is a free doctor?! -> no waiting
		if (!model.freeDoctorQueue.isEmpty()) {
			patient.isZero = true;
			DoctorEntity doctor = (DoctorEntity) model.freeDoctorQueue.first();
			model.freeDoctorQueue.remove(doctor);
			model.busyDoctorQueue.insert(doctor);
			queue.remove(patient);
			if (patient.getPriority() == 3 && model.deathOfPatientsFlag) {
				patient.deathEvent.cancel();
			}
			patient.treatmentStart = new SimTime(model.currentTime());
			TreatmentTermination treatmentTerm = new TreatmentTermination(
					model, "End of Treatment", true);
			if (!patient.treatementInterrupted) {
				patient.treatmentDuration = new SimTime(
						model.getTreatmentTime(patient.getPriority()));
				treatmentTerm.schedule(patient, new SimTime(
						patient.treatmentDuration));
			} else {
				patient.treatmentDuration = new SimTime(patient.rest);
				treatmentTerm.schedule(patient, new SimTime(patient.rest));
			}
			patient.treatementInterrupted = false;
			patient.treatmentTermination = treatmentTerm;
			EmergencyRoomModel.inTreatmentQueue.insert(patient);
			patient.end = model.currentTime();
		} else {
			if (patient.getPriority() == 3) {
				PatientEntity tmpPatient;
				PatientEntity firstPatient = model.inTreatmentQueue.first();
				model.inTreatmentQueue.remove(firstPatient);
				model.inTreatmentQueue.insert(firstPatient);
				do {
					tmpPatient = model.inTreatmentQueue.first();
					model.inTreatmentQueue.remove(tmpPatient);
					model.inTreatmentQueue.insert(tmpPatient);
					// System.out.println("Wer ist drin: " +
					// tmpPatient.getPriority() + " von " +
					// tmpPatient.getName());
				} while (firstPatient != tmpPatient
						&& tmpPatient.getPriority() != 1);
				if (tmpPatient.getPriority() == 1) {
					// System.out.println("priority: " +
					// tmpPatient.getPriority());
					tmpPatient.treatementInterrupted = true;
					SimTime drtime = SimTime.diff(model.currentTime(),
							tmpPatient.end);
					SimTime absolut = SimTime.add(tmpPatient.end,
							tmpPatient.treatmentDuration);
					tmpPatient.rest = SimTime.diff(absolut,
							SimTime.add(tmpPatient.end, drtime));

					// System.out.println(tmpPatient.getName() + ": " +
					// tmpPatient.getPriority() + " " + tmpPatient.rest);
					tmpPatient.treatmentTermination.cancel();
					queue.remove(patient);
					model.inTreatmentQueue.remove(tmpPatient);
					model.inTreatmentQueue.insert(patient);
					tmpPatient.treatementInterrupted = true;
					patient.isZero = true;
					PatientArrivalEvent arrival = new PatientArrivalEvent(
							model, "Reschedule", true);
					arrival.schedule(tmpPatient, new SimTime(0.0)); // instant
																	// arrival
					TreatmentTermination te = new TreatmentTermination(model,
							"3 vor 1", true);
					patient.treatmentDuration = new SimTime(
							model.getTreatmentTime(patient.getPriority()));
					te.schedule(patient, new SimTime(patient.treatmentDuration));
					if (model.deathOfPatientsFlag) {
						patient.deathEvent.cancel();
					}
				} else {
					patient.start = model.currentTime();
					patient.isZero = false;
				}
			} else {
				patient.start = model.currentTime();
				patient.isZero = false;
			}
		}

	}

}
