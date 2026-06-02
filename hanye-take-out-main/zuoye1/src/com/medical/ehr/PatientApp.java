package com.medical.ehr;

// 患者App
public class PatientApp implements MedicalObserver {
    private String name;

    public PatientApp(String name) {
        this.name = name;
    }

    @Override
    public void update(NotificationEvent event) {
        System.out.println("\n【患者App - " + name + "】收到通知：");
        System.out.println("来源：" + event.getSource() + "，消息：" + event.getMessage());
    }
}

// 药房系统
class PharmacySystem implements MedicalObserver {
    private String name;

    public PharmacySystem(String name) {
        this.name = name;
    }

    @Override
    public void update(NotificationEvent event) {
        System.out.println("\n【药房系统 - " + name + "】收到通知：");
        System.out.println("已同步病历，准备发药");
    }
}

// 医保结算中心
class InsuranceSettlementCenter implements MedicalObserver {
    private String name;

    public InsuranceSettlementCenter(String name) {
        this.name = name;
    }

    @Override
    public void update(NotificationEvent event) {
        System.out.println("\n【医保中心 - " + name + "】收到通知：");
        System.out.println("已同步，可进行医保核算");
    }
}