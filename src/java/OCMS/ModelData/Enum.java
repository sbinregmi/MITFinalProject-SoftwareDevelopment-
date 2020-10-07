/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.ModelData;

/**
 *
 * @author SabinRegmi
 */
public class Enum {

    public enum Status {
        PENDING(0),
        ACCEPTED(1),
        REJECTED(2),
        JOINED(3);
        private int value;

        private Status(int value) {
            this.value = value;
        }
    }

    public enum Role {
        Participant,
        Admin,
        Author,
        Organizer
    }
}
