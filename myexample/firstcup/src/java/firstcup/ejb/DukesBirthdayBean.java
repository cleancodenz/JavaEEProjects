/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firstcup.ejb;

import firstcup.entity.FirstcupUser;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author johnson
 */
/**
 * DukesBirthdayBean is a stateless session bean. Stateless session beans are
 * enterprise beans that do not maintain a conversational state with a client.
 * With stateless session beans, the client makes isolated requests that do not
 * depend on any previous state or requests. If an application requires
 * conversational state, use stateful session beans.
 *
 * To create DukesBirthdayBean, create one Java class: DukesBirthdayBean, the
 * enterprise bean class. DukesBirthdayBean is a local enterprise bean that uses
 * a no-interface view, meaning two things. First, a local enterprise bean is
 * only visible within the application in which it is deployed. Second,
 * enterprise beans with a no-interface view do not need a separate business
 * interface that the enterprise bean class implements.
 *
 * The enterprise bean class is the only coding artifact needed to create a
 * local, no-interface enterprise bean.
 *
 * DukesBirthdayBean will be packaged within the same WAR file as the Facelets
 * web front-end.
 *
 *
 */
@Stateless
public class DukesBirthdayBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private static final Logger logger
            = Logger.getLogger("firstcup.ejb.DukesBirthdayBean");

    @PersistenceContext
    private EntityManager em;

    public Double getAverageAgeDifference() {
        Double avgAgeDiff
                = (Double) em.createNamedQuery("findAverageAgeDifferenceOfAllFirstcupUsers")
                .getSingleResult();
        logger.info("Average age difference is: " + avgAgeDiff);
        return avgAgeDiff;
    }

    public int getAgeDifference(Date date) {
        int ageDifference;

        Calendar theirBirthday = new GregorianCalendar();
        Calendar dukesBirthday = new GregorianCalendar(1995, Calendar.MAY, 23);

        theirBirthday.setTime(date);

        ageDifference = dukesBirthday.get(Calendar.YEAR)
                - theirBirthday.get(Calendar.YEAR);

        logger.info("Raw ageDifference is: " + ageDifference);
        // Check to see if Duke's birthday occurs before the user's. If so,
        // subtract one from the age difference
        if (dukesBirthday.before(theirBirthday) && (ageDifference > 0)) {
            ageDifference--;
        }

        // create and store the user's birthday in the database
        FirstcupUser user = new FirstcupUser(date, ageDifference);
        em.persist(user);

        logger.info("Final ageDifference is: " + ageDifference);

        return ageDifference;

    }

}
