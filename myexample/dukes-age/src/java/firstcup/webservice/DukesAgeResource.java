/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firstcup.webservice;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

/**
 * REST Web Service
 *
 * @author johnson
 */
@Path("dukesage")
public class DukesAgeResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of DukesAgeResource
     */
    public DukesAgeResource() {
    }

    /**
     * Retrieves representation of an instance of
     * firstcup.webservice.DukesAgeResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    public String getText() {
        //Create a new Calendar for Duke's birthday
        Calendar dukesBirthday = new GregorianCalendar(1995, Calendar.MAY, 23);
        //Creat a new Calendar for today
        Calendar now = GregorianCalendar.getInstance();

        // Substract today's year from Duke's birth year
        int dukesAge = now.get(Calendar.YEAR) - dukesBirthday.get(Calendar.YEAR);
        // If todays date is before May 23, substract a year from duke's age
        if (now.before(dukesBirthday)) {
            dukesAge--;
        }
      // return a String representation of dukes age

        return "" + dukesAge;

    }

    /**
     * PUT method for updating or creating an instance of DukesAgeResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    /**
     * @PUT @Consumes("text/plain") public void putText(String content) { }
    *
     */
}
