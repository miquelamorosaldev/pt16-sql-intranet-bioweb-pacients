/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persist;

/**
 *
 * @author alumne
 */
public class DBConnectionException extends Exception {

    /**
     * Creates a new instance of <code>DBConnectioException</code> without
     * detail message.
     */
    public DBConnectionException() {
    }

    /**
     * Constructs an instance of <code>DBConnectioException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public DBConnectionException(String msg) {
        super(msg);
    }
}
