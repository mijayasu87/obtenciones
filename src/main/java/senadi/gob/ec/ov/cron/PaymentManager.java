/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.gob.ec.ov.cron;

import senadi.gob.ec.ov.util.Operations;

/**
 *
 * @author michael
 */
//@Singleton
//@Startup // para que se inicie con el servidor
public class PaymentManager {
    //@Schedule(hour = "1", minute = "1", second = "0", persistent = false)
    //@Transactional
    public void breederPaymentRegister() {
        System.out.println("🕒 Scheduler ejecutado vegetable_forms: " + Operations.getCurrentTimeStamp());
        
    }
}
