/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entities.RentRecord;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

/**
 *
 * @author root
 */
@Stateless
public class TimerConsultarRenta {

    @EJB
    private AsynchronousRentRecord asynchronousRentRecord;

    @Schedule(dayOfWeek = "*", month = "*", hour = "*", dayOfMonth = "*", year = "*", minute = "*/10", second = "30", persistent = false)
    
    public void myTimer() {
        System.out.println("[TIMER_R]Timer consulta eventos: " + new Date());
        this.printRents();
        System.out.println("[TIMER_R_END]Timer consulta eventos: " + new Date());
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public void printRents()
    {
        try {
            Future<List<RentRecord>> answerFuture = this.asynchronousRentRecord.getRentRecords();
            List<RentRecord> answer = answerFuture.get();
            System.out.println("[TIMER_R]Rents size = "+answer.size());
            System.out.println("[TIMER_R] "+answer);
            
        } catch (InterruptedException ex) {
            Logger.getLogger(TimerConsultarRenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(TimerConsultarRenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
