/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package checkdata;

import java.io.IOException;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;

/**
 *
 * @author senja
 */
public class HbaseCheckData {
     public static void main(String[] args) throws IOException {
        // Create HBase configuration
        org.apache.hadoop.conf.Configuration config = HBaseConfiguration.create();
        
        // Establish connection
        try (Connection connection = ConnectionFactory.createConnection(config)) {
            // Get table
            Table table = connection.getTable(TableName.valueOf("petugass"));
            
            // Create scan object
            Scan scan = new Scan();
            
            // Get result scanner
            ResultScanner scanner = table.getScanner(scan);
            
            // Iterate over results
            for (Result result = scanner.next(); result != null; result = scanner.next()) {
                System.out.println(result);
            }
            
            // Close scanner
            scanner.close();
        }
     }
}
