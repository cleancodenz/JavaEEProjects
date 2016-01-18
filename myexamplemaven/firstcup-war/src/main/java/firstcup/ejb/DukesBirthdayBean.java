/**
 * Copyright (c) 2014 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package firstcup.ejb;

import firstcup.entity.FirstcupUser;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * DukesBirthdayBean is a stateless session bean that calculates the age
 * difference between a user and Duke, who was born on May 23, 1995.
 */
/**
 * DukesBirthdayBean is a stateless session bean. Stateless session beans are
 * enterprise beans that do not maintain a conversational state with a client.
 * With stateless session beans, the client makes isolated requests that do not
 * depend on any previous state or requests. If an application requires
 * conversational state, use stateful session beans.
 *
 * DukesBirthdayBean is a local enterprise bean that uses a no-interface view: A
 * local enterprise bean is visible only within the application in which it is
 * deployed. Enterprise beans with a no-interface view do not need a separate
 * business interface that the enterprise bean class implements. The enterprise
 * bean class is the only coding artifact needed to create a local, no-interface
 * enterprise bean.
 *
 * DukesBirthdayBean will be packaged within the same WAR file as the Facelets
 * web front end
 *
 */
@Stateless
public class DukesBirthdayBean {

    private static final Logger logger
            = Logger.getLogger("firstcup.ejb.DukesBirthdayBean");

    @PersistenceContext
    private EntityManager em;

    public Double getAverageAgeDifference() {
        Double avgAgeDiff = (Double) em.
                createNamedQuery("findAverageAgeDifferenceOfAllFirstcupUsers")
                .getSingleResult();
        logger.log(Level.INFO, "Average age difference is: {0}", avgAgeDiff);
        return avgAgeDiff;
    }

    public int getAgeDifference(Date date) {
        int ageDifference;

        Calendar theirBirthday = new GregorianCalendar();
        Calendar dukesBirthday = new GregorianCalendar(1995, Calendar.MAY, 23);

        // Set the Calendar object to the passed-in Date
        theirBirthday.setTime(date);

        // Subtract the user's age from Duke's age
        ageDifference = dukesBirthday.get(Calendar.YEAR)
                - theirBirthday.get(Calendar.YEAR);
        logger.log(Level.INFO, "Raw ageDifference is: {0}", ageDifference);
        // Check to see if Duke's birthday occurs before the user's. If so,
        // subtract one from the age difference
        if (dukesBirthday.before(theirBirthday) && (ageDifference > 0)) {
            ageDifference--;
        }

        // Create and store the user's birthday in the database
        FirstcupUser user = new FirstcupUser(date, ageDifference);
        em.persist(user);

        logger.log(Level.INFO, "Final ageDifference is: {0}", ageDifference);

        return ageDifference;
    }
}
