import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import java.rmi.RemoteException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import interfaces.IFlightServer;
import model.Flight;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
//import org.eclipse.wb.swt.SWTResourceManager;

public class FlightEditor extends Composite {
	private Text airline;
	private Text flightNumber;
	private Text departure;
	private Text departureTime;
	private Text departureTerminal;
	private Text arrive;
	private Text arrivalTime;
	private Text arrivalTerminal;
	private Flight flight;
	private Shell shell;
	private IFlightServer stub;
	private String clientName;
	private FlightClient client;
	private Text airlineName;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public FlightEditor(Composite parent, int style) {
		super(parent, style);
		setLayout(null);
		this.shell=(Shell) parent;
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setBounds(15, 23, 134, 20);
		lblNewLabel.setText("Operating Airline\uFF1A");
		
		airline = new Text(this, SWT.BORDER);
		airline.setBounds(155, 20, 193, 26);
		
		Label lblFlightNumber = new Label(this, SWT.NONE);
		lblFlightNumber.setBounds(40, 61, 109, 20);
		lblFlightNumber.setText("Flight Number:");
		
		flightNumber = new Text(this, SWT.BORDER);
		flightNumber.setBounds(155, 58, 565, 26);
		
		Label lblNewLabel_1 = new Label(this, SWT.NONE);
		lblNewLabel_1.setBounds(15, 120, 135, 20);
		lblNewLabel_1.setText("Departure Airport:");
		
		departure = new Text(this, SWT.BORDER);
		departure.setBounds(155, 117, 565, 26);
		
		Label lblDepartureTime = new Label(this, SWT.NONE);
		lblDepartureTime.setBounds(21, 151, 129, 20);
		lblDepartureTime.setText("Departure Time\uFF1A");
		
		departureTime = new Text(this, SWT.BORDER);
		departureTime.setBounds(155, 148, 565, 26);
		
		Label lblDepartureTerminal = new Label(this, SWT.NONE);
		lblDepartureTerminal.setBounds(5, 182, 145, 20);
		lblDepartureTerminal.setText("Departure Terminal:");
		
		departureTerminal = new Text(this, SWT.BORDER);
		departureTerminal.setBounds(155, 179, 565, 26);
		
		Label lblArrivalAirport = new Label(this, SWT.NONE);
		lblArrivalAirport.setBounds(42, 263, 108, 20);
		lblArrivalAirport.setText("Arrival Airport:");
		
		arrive = new Text(this, SWT.BORDER);
		arrive.setBounds(155, 260, 565, 26);
		
		Label lblArrivalTime = new Label(this, SWT.NONE);
		lblArrivalTime.setBounds(59, 294, 91, 20);
		lblArrivalTime.setText("Arrival Time:");
		
		arrivalTime = new Text(this, SWT.BORDER);
		arrivalTime.setBounds(155, 291, 565, 26);
		
		Label lblArrivalTerminal = new Label(this, SWT.NONE);
		lblArrivalTerminal.setBounds(32, 325, 118, 20);
		lblArrivalTerminal.setText("Arrival Terminal:");
		
		arrivalTerminal = new Text(this, SWT.BORDER);
		arrivalTerminal.setBounds(155, 322, 565, 26);
		
		Button btnSave = new Button(this, SWT.NONE);
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				editorFlight();
				//System.out.println(flight.airline);
				try {
					client.updateFlight(flight);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				getShell().setVisible(false);
			}
		});
		btnSave.setBounds(587, 367, 98, 30);
		btnSave.setText("Save");
		
		Button btnCancel = new Button(this, SWT.NONE);
		
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				getShell().setVisible(false);
			}
		});
		btnCancel.setBounds(468, 367, 98, 30);
		btnCancel.setText("Cancel");
		
		Button btnDelete = new Button(this, SWT.NONE);
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				try {
					client.deleteFlight(flight);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				getShell().setVisible(false);
			}
		});
		//btnDelete.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btnDelete.setBounds(10, 367, 98, 30);
		btnDelete.setText("Delete");
		
		Label lblName = new Label(this, SWT.NONE);
		lblName.setBounds(374, 23, 55, 20);
		lblName.setText("Name:");
		
		airlineName = new Text(this, SWT.BORDER);
		airlineName.setBounds(435, 20, 269, 26);

	}
	
	public void setFlight(Flight f) {
		this.flight=f;
		airline.setText(this.flight.airline);
		flightNumber.setText(flight.flightNumber);
		departure.setText(flight.departure);
		departureTime.setText(flight.departureTime);
		departureTerminal.setText(flight.departureTerminal);
		arrive.setText(flight.arrive);
		arrivalTime.setText(flight.arrivalTime);
		arrivalTerminal.setText(flight.arrivalTerminal);
		airlineName.setText(flight.airlineName);
	}
	public void editorFlight() {
		this.flight.airline=airline.getText();
		this.flight.flightNumber=flightNumber.getText();
		this.flight.departure=departure.getText();
		this.flight.departureTime=departureTime.getText();
		this.flight.departureTerminal=departureTerminal.getText();
		this.flight.arrive=arrive.getText();
		this.flight.arrivalTime=arrivalTime.getText();
		this.flight.arrivalTerminal=arrivalTerminal.getText();
		this.flight.airlineName=airlineName.getText();
	}


	public void setClient(FlightClient client) {
		this.client = client;
	}
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
