package com.medical.ehr;

// 会诊请求
public class ConsultationRequest {
    private String specialist;
    private String reason;

    public ConsultationRequest(String specialist, String reason) {
        this.specialist = specialist;
        this.reason = reason;
    }

    public String getSpecialist() { return specialist; }
    public String getReason() { return reason; }
}

// 处方
class Prescription {
    private String medication;
    private String dosage;
    private String instructions;

    public Prescription(String medication, String dosage, String instructions) {
        this.medication = medication;
        this.dosage = dosage;
        this.instructions = instructions;
    }

    public String getMedication() { return medication; }
    public String getDosage() { return dosage; }
    public String getInstructions() { return instructions; }
}