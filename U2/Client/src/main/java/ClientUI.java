//package UI;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import java.util.ArrayList;
import java.util.HashMap;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import com.alibaba.fastjson.JSON;
import Flight.*;
//import soap.ReservationBookingClient;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ClientUI extends Composite {
	public Table table;
	public Combo combo;
	public ClientInterface client;
	public HashMap<Integer,ArrayList<String>> flightsForOneWeek;
	public int day = 0;
	public int index;
	public Display display;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ClientUI(Composite parent, int style) {
		super(parent, style);
		setLayout(null);
		
		combo = new Combo(this, SWT.NONE);
		
		combo.add("Monday");
		combo.add("Tuesday");
		combo.add("Wednesday");
		combo.add("Thursday");
		combo.add("Friday");
		combo.add("Saturday");
		combo.add("Sunday");
        // User select a item in the Combo.
        combo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	int day = combo.getSelectionIndex();
				setTableItem(day);
            }
        });
		combo.setBounds(710, 21, 171, 28);
		
		Label lblWeekday = new Label(this, SWT.NONE);
		lblWeekday.setBounds(628, 24, 76, 20);
		lblWeekday.setText("Weekday:");
		
		//table
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		TableCursor tableCursor = new TableCursor(table, SWT.NONE);
		tableCursor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//TableItem item = tableCursor.getRow();
				index = table.indexOf(tableCursor.getRow());
				openFlightBooking(index);
				
			}
		});
		table.setBounds(0, 59, 888, 523);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnIndex = new TableColumn(table, SWT.NONE);
		tblclmnIndex.setWidth(56);
		tblclmnIndex.setText("index");
		
		TableColumn tblclmnFlight = new TableColumn(table, SWT.NONE);
		tblclmnFlight.setWidth(112);
		tblclmnFlight.setText("Flight");
		
		TableColumn tblclmnFlightType = new TableColumn(table, SWT.NONE);
		tblclmnFlightType.setWidth(136);
		tblclmnFlightType.setText("Flight Type");
		
		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("Airline");
		
		TableColumn tblclmnDesitination = new TableColumn(table, SWT.NONE);
		tblclmnDesitination.setWidth(158);
		tblclmnDesitination.setText("Departure");
		
		TableColumn tblclmnArrival = new TableColumn(table, SWT.NONE);
		tblclmnArrival.setWidth(186);
		tblclmnArrival.setText("Arrival");
		
		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(135);
		tblclmnNewColumn_1.setText("Destination");

	}

	public void setClient(ClientInterface client) {
		// TODO Auto-generated method stub
		this.client = client;
		this.refreshTable();	
	}
	
	public void setDisplay(Display display) {
		this.display=display;
	}
	
	public void refreshTable() {
		this.flightsForOneWeek=this.client.getFlights();
		this.combo.select(0);
		this.setTableItem(day);
	}
	
	public void setTableItem(int day) {
		this.table.removeAll();
		this.day = day;
		ArrayList<String> flightsForOneDay = this.flightsForOneWeek.get(day);
		
		for(int i = 0; i< flightsForOneDay.size();i++) {
			TableItem tableItem = new TableItem(table,SWT.NONE);
			String json = flightsForOneDay.get(i);
			Flight flight = JSON.parseObject(json,Flight.class);
			tableItem.setText(new String[] {String.valueOf(i),flight.flightNumber,flight.flightType,flight.airline,flight.departure,flight.arrive,flight.destination});
		}	
	}
	
	public boolean book(int row,int clone,String meal) {
		boolean success = this.client.book(day, index, row, clone, meal);
		return success;
	}
	
	
	public void openFlightBooking(int index) {
		String json = this.flightsForOneWeek.get(combo.getSelectionIndex()).get(index);
		Flight flight = JSON.parseObject(json,Flight.class);
		Shell dialogShell = new Shell(display);
		dialogShell.setLayout(new GridLayout(1,false));
		Booking editor = new Booking(dialogShell,SWT.CLOSE);
		editor.init(flight,this);
		dialogShell.pack();
		dialogShell.open();  	
	}
}
