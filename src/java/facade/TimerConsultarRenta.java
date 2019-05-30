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

    @Schedule(dayOfWeek = "*", month = "*", hour = "*", dayOfMonth = "*", year = "*", minute = "*/1", second = "30", persistent = false)
    
    public void myTimer() {
        System.out.println("[Timer]Timer consulta eventos: " + new Date());
        this.printRents();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public void printRents()
    {
        try {
            Future<List<RentRecord>> answerFuture = this.asynchronousRentRecord.getRentRecords();
            List<RentRecord> answer = answerFuture.get();
            System.out.println("Rents size = "+answer.size());
            System.out.println(answer);
            
        } catch (InterruptedException ex) {
            Logger.getLogger(TimerConsultarRenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(TimerConsultarRenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
