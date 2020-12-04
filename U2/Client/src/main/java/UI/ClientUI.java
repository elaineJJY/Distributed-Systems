package UI;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;

import java.rmi.RemoteException;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import Flight.Flight;
import soap.ReservationBookingClient;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ClientUI extends Composite {
	private Table table;
	public ReservationBookingClient client;
	public int day;
	public int index;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ClientUI(Composite parent, int style) {
		super(parent, style);
		setLayout(null);
		
		Combo combo = new Combo(this, SWT.NONE);
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
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
				TableItem item = tableCursor.getRow();
				int index = table.indexOf(tableCursor.getRow());
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

	public void setClient(ReservationBookingClient client) {
		// TODO Auto-generated method stub
		this.client = client;
		this.setTableItem(0);
	}
	
	public void setTableItem(int day) {
		this.day = day;
		List<Flight> flightsForOneDay = this.client.flightsForOneWeek.get(day);
		for(int i = 0; i< flightsForOneDay.size();i++) {
			TableItem tableItem = new TableItem(table,SWT.NONE);
			Flight flight = flightsForOneDay.get(i);
			tableItem.setText(new String[] {String.valueOf(i),flight.flightNumber,flight.flightType,flight.airline,flight.departure,flight.arrive,String.valueOf(flight.destination)});
		}
		
	}
	
	public void openFlightBooking(int index) {
		Display display = Display.getDefault();
		Shell dialogShell = new Shell(display, SWT.CLOSE);
		//shell.setLayout(new GridLayout(1,false));
		//FlightEditor editor = new FlightEditor(dialogShell,SWT.CLOSE);
		//editor.setFlight(flight);
		//editor.setClient(client);
		//dialogShell.pack();
		//dialogShell.open();  
		        
//		while(!dialogShell.isDisposed()) {
//			if(!display.readAndDispatch()) {
//				display.sleep();
//			}
//		}
//		display.dispose(); 
		
	}
}
