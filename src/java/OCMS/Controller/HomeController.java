/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.Controller;

import OCMS.EJB.RegistrationEJB;
import javax.ejb.EJB;
import OCMS.Entity.Registration;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;

/**
 *
 * @author SabinRegmi
 */
@RolesAllowed({"Administrator", "ConferenceManager", "Author", "Participant"})
public class HomeController {
    @EJB
    private RegistrationEJB registrationEJB;
    private Registration user = new Registration();
    
}
