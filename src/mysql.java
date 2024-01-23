import java.awt.HeadlessException;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.sql.CallableStatement;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.sql.*;

public class mysql {
    
    
    int code;
    String nameAlumn;    
    String surnameAlumn;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getNameAlumn() {
        return nameAlumn;
    }

    public void setNameAlumn(String nameAlumn) {
        this.nameAlumn = nameAlumn;
    }

    public String getSurnameAlumn() {
        return surnameAlumn;
    }

    public void setSurnameAlumn(String surnameAlumn) {
        this.surnameAlumn = surnameAlumn;
    }
    
    
    
    //funciones para las consultas   
    
    public void insertAlumn(JTextField txtname, JTextField txtsurname) {
        setNameAlumn(txtname.getText());
        setSurnameAlumn(txtsurname.getText());
        
        conection mysql = new conection();
        
        String insert = "insert into alumnos (nombre, apellido) values (?,?);";

        try {
            CallableStatement cs = mysql.conectar().prepareCall(insert);
            cs.setString(1, getNameAlumn());
            cs.setString(2, getSurnameAlumn());
            cs.execute();
            
            JOptionPane.showMessageDialog(
                    null,
                    "Se insertó el registro satifactoriamente",
                    "",
                    JOptionPane.INFORMATION_MESSAGE
            );
            
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(
                    null, 
                    e,
                    "No se inserto el registro ",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    
    public void showAlumns(JTable alumn) {
        
        conection mysql = new conection() ;
        
        DefaultTableModel model = new DefaultTableModel();
        
        TableRowSorter<TableModel> orderTable = new TableRowSorter<TableModel>(model);
        alumn.setRowSorter(orderTable);
        
        
        String show = "select * from alumnos;";
        
        model.addColumn("Id");
        model.addColumn("Nombre");
        model.addColumn("Apellido");
        
        
        alumn.setModel(model);
        
        String[] data = new String[3];
        Statement st;
        
        try {
            st = mysql.conectar().createStatement();
            ResultSet rs = st.executeQuery(show);
            
            
            while (rs.next()) {                
                data[0] = rs.getString(1);
                data[1] = rs.getString(2);
                data[2] = rs.getString(3);
                
                model.addRow(data);            
            
            }alumn.setModel(model);
            
        } catch (SQLException e) {
            
            JOptionPane.showMessageDialog(
                    null,
                    e,
                    "No se pudo mostrar los registros",
                    JOptionPane.ERROR_MESSAGE
            );
            
        }
        
    }
    

    
    
}
