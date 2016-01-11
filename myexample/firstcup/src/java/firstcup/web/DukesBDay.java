/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firstcup.web;

import firstcup.ejb.DukesBirthdayBean;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.validation.constraints.NotNull;

/**
 *
 * @author johnson
 */
/**
 replace @managedbean with @Named(value = "DukesBDay")
 **/
@javax.inject.Named(value = "DukesBDay")
@SessionScoped
public class DukesBDay {

    @EJB
    private DukesBirthdayBean dukesBirthdayBean;

    /**
     * Creates a new instance of DukesBDay
     */
    public DukesBDay() {

        age = -1;
        yourBD = null;
        ageDiff = -1;
        absAgeDiff = -1;
        averageAgeDifference = -1.0;

    }

    //age for getting Duke's age from the web service
    protected int age;
    //yourBD to hold the user's birth date
    @NotNull
    protected Date yourBD;
    //ageDiff to get the age difference from the enterprise bean
    protected int ageDiff;
    //absAgeDiff to hold the absolute value of the age difference
    protected int absAgeDiff;
    //averageAgeDifference to hold the average age difference of all users
    protected Double averageAgeDifference;
    private static final Logger logger = Logger.getLogger("firstcup.web.DukesBDay");

    public int getAge() {
        // Use the java.net.* APIs to access the Duke's Age RESTful web service
        HttpURLConnection connection = null;
        BufferedReader rd = null;
        StringBuilder sb = null;
        String line = null;
        URL serverAddress = null;

        try {
            serverAddress = new URL(
                    "http://localhost:8080/DukesAgeService/resources/dukesAge");
            connection = (HttpURLConnection) serverAddress.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setReadTimeout(10000);

            // Make the connection to Duke's Age
            connection.connect();

            // Read in the response
            rd = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            sb = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }

            // Convert the response to an int
            age = Integer.parseInt(sb.toString());
        } catch (MalformedURLException e) {
            logger.warning("A MalformedURLException occurred.");
            e.printStackTrace();
        } catch (ProtocolException e) {
            logger.warning("A ProtocolException occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            logger.warning("An IOException occurred");
            e.printStackTrace();
        }

        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getYourBD() {
        return yourBD;
    }

    public void setYourBD(Date yourBD) {
        this.yourBD = yourBD;
    }

    public int getAgeDiff() {
        return ageDiff;
    }

    public void setAgeDiff(int ageDiff) {
        this.ageDiff = ageDiff;
    }

    public int getAbsAgeDiff() {
        return absAgeDiff;
    }

    public void setAbsAgeDiff(int absAgeDiff) {
        this.absAgeDiff = absAgeDiff;
    }

    public Double getAverageAgeDifference() {
        return averageAgeDifference;
    }

    public void setAverageAgeDifference(Double averageAgeDifference) {
        this.averageAgeDifference = averageAgeDifference;
    }

    /**
     * During this task, you will create a processBirthday method to get the
     * difference in age between the user's age and Duke's age from the EJB, set
     * the absAgeDiff variable to the absolute value of the age difference, and
     * set a result string that will forward the user to the display page.
     *
     *
     */
    public String processBirthday() {
        this.setAgeDiff(dukesBirthdayBean.getAgeDifference(yourBD));
        logger.info("age diff from dukesbday " + ageDiff);
        this.setAbsAgeDiff(Math.abs(this.getAgeDiff()));
        logger.info("absAgeDiff " + absAgeDiff);
        this.setAverageAgeDifference(dukesBirthdayBean.getAverageAgeDifference());
        logger.info("averageAgeDifference " + averageAgeDifference);
        return "/response.xhtml";
    }
}
