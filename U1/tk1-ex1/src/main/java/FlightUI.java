import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.widgets.Shell;
import interfaces.IFlightServer;
import model.Flight;
import org.eclipse.swt.widgets.TableItem;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
public class FlightUI extends Composite {
	//private DataBindingContext m_bindingContext;
	public Table table;
	private String row;
	//private IFlightServer stub;
	//private String clientName;
	private List<Flight> flights = new ArrayList(); 
	protected Shell shell; 
	public FlightClient client;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public FlightUI(Composite parent, int style) {
		super(parent, style);
		//shell=(Shell)parent;
		//setLayout(null);
		
		ViewForm viewForm = new ViewForm(this, SWT.NONE);
		viewForm.setBounds(565, 3, 0, 0);
		
		Label label = new Label(this, SWT.SEPARATOR | SWT.VERTICAL);
		label.setBounds(71, 0, 0, 20);
		
		Label label_1 = new Label(this, SWT.SEPARATOR | SWT.VERTICAL);
		label_1.setBounds(71, -13, -3, 415);
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(0, 3, 760, 442);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnIndex = new TableColumn(table, SWT.NONE);
		tblclmnIndex.setWidth(52);
		tblclmnIndex.setText("index");
		
		TableColumn tblclmnAirline = new TableColumn(table, SWT.NONE);
		tblclmnAirline.setWidth(78);
		tblclmnAirline.setText("Airline");
		
		TableColumn tblclmnFlightNumber = new TableColumn(table, SWT.NONE);
		tblclmnFlightNumber.setWidth(119);
		tblclmnFlightNumber.setText("Flight Number");
		
		TableColumn tblclmnDeparture = new TableColumn(table, SWT.NONE);
		tblclmnDeparture.setWidth(100);
		tblclmnDeparture.setText("Departure");
		
		TableColumn tblclmnDepartureTime = new TableColumn(table, SWT.NONE);
		tblclmnDepartureTime.setWidth(141);
		tblclmnDepartureTime.setText("Departure Time");
		
		TableColumn tblclmnArrive = new TableColumn(table, SWT.NONE);
		tblclmnArrive.setWidth(69);
		tblclmnArrive.setText("Arrive");
		
		TableColumn tblclmnArrivalTime = new TableColumn(table, SWT.NONE);
		tblclmnArrivalTime.setWidth(109);
		tblclmnArrivalTime.setText("Arrival Time");
		
		TableColumn tblclmnTerminal = new TableColumn(table, SWT.NONE);
		tblclmnTerminal.setWidth(88);
		tblclmnTerminal.setText("Terminal");
		
		TableCursor tableCursor = new TableCursor(table, SWT.NONE);
		
		Button btnNew = new Button(this, SWT.NONE);
		btnNew.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Flight f;
				try {
					f = new Flight();
					f.init("new","00000");
					addTableItem(String.valueOf(row)+1,f);
					
					open(f,Integer.parseInt(row));
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		btnNew.setBounds(328, 451, 98, 30);
		btnNew.setText("New");
		tableCursor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem item = tableCursor.getRow();
				int row = table.indexOf(tableCursor.getRow());
				try {
					open(flights.get(row),row);
					
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		//m_bindingContext = initDataBindings();

	}
	public void open(Flight flight,int row) throws RemoteException {
		//Display display = Display.getDefault();
		//shell = new Shell();  
		Shell dialogShell = new Shell(shell, SWT.CLOSE);
		//shell.setLayout(new GridLayout(1,false));
		FlightEditor editor = new FlightEditor(dialogShell,SWT.CLOSE);
		editor.setFlight(flight);
		editor.setClient(client);
		dialogShell.pack();
		dialogShell.open();  
        
//		 while(!dialogShell.isDisposed()) {
//			 if(!display.readAndDispatch()) {
//				 display.sleep();
//			 }
//		 }
//		 display.dispose();
		 
	}
		

	public void setFlights(List<Flight> flights) {
		table.removeAll();
		this.flights=flights;
		
		for(int i = 0; i<flights.size();i++) {
			row = String.valueOf(i);
			addTableItem(String.valueOf(i),(Flight) flights.get(i));
		 }
		
	}
	public void setClient(FlightClient client) {
		this.client = client;
	}

	public void addTableItem(String index,Flight f) {
		TableItem tableItem = new TableItem(table,SWT.NONE);
		tableItem.setText(new String[] {index,f.airline,f.flightNumber,f.departure,f.departureTime,f.arrive,f.arrivalTime,f.arrivalTerminal});
		row=index;
	}
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
