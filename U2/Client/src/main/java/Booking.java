//package UI;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;

import java.awt.Color;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.layout.GridLayout;


import Flight.Flight;
import Flight.Seat;
//import soap.ReservationBookingClient;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class Booking extends Composite {
	private Table table;
	private ClientUI ui;
	private String meal;
	private Button[] buttonMeal = new Button[3];
	private Seat[][] seats;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public Booking(Composite parent, int style) {
		super(parent, style);
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(0, 73, 733, 849);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		TableCursor tableCursor = new TableCursor(table, SWT.NONE);
		tableCursor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem item = tableCursor.getRow();
				int row = Integer.parseInt(item.getText(1));
				int clone = Integer.parseInt(item.getText(2));
				book(row, clone);
				
			}
		});
		
		TableColumn tblclmnType = new TableColumn(table, SWT.NONE);
		tblclmnType.setWidth(173);
		tblclmnType.setText("Type");
		
		TableColumn tblclmnRow = new TableColumn(table, SWT.NONE);
		tblclmnRow.setWidth(100);
		tblclmnRow.setText("Row");
		
		TableColumn tblclmnClome = new TableColumn(table, SWT.NONE);
		tblclmnClome.setWidth(100);
		tblclmnClome.setText("Clome");
		
		TableColumn tblclmnPrice = new TableColumn(table, SWT.NONE);
		tblclmnPrice.setWidth(157);
		tblclmnPrice.setText("Price");
		
		TableColumn tblclmnBooked = new TableColumn(table, SWT.NONE);
		tblclmnBooked.setWidth(178);
		tblclmnBooked.setText("Booked?");
		
		
		buttonMeal[0] = new Button(this, SWT.RADIO);
		buttonMeal[0].addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setMeal("Standard",0);
			}
		});
		buttonMeal[0].setBounds(444, 22, 119, 20);
		buttonMeal[0].setText("Standard");
		
		buttonMeal[1] = new Button(this, SWT.RADIO);
		buttonMeal[1].addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setMeal("Vegetarian",1);
			}
		});
		buttonMeal[1].setBounds(583, 22, 119, 20);
		buttonMeal[1].setText("Vegetarian");
		
		buttonMeal[2] = new Button(this, SWT.RADIO);
		buttonMeal[2].addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setMeal("Vegan",2);
			}
		});
		buttonMeal[2].setBounds(728, 22, 119, 20);
		buttonMeal[2].setText("Vegan");

	}

	public void init(Flight f,ClientUI ui) {
		this.ui=ui;
		seats = f.seats;
		
		
		for(int row = 0; row< seats.length;row++) {
			for(int clone = 0; clone < seats[row].length;clone++) {
				Seat seat = seats[row][clone];
				int price = Integer.parseInt(f.destination);
				String bookState;
				price*=5;
				String type = seat.type;
				if(type.contentEquals("First Class")) {
					price += 200;
				}
				if(type.contentEquals("Economy Plus Class")){
					price += 100;
				}

				if(seat.booked) {
					bookState = "Booked";
				}
				else {
					bookState = "Free";
				}
				TableItem tableItem = new TableItem(table,SWT.NONE);
				tableItem.setText(new String[] {seat.type,String.valueOf(seat.row),String.valueOf(seat.clone),String.valueOf(price),bookState});
				
				if(seats[row][clone].booked) {
					//tableItem.setForeground(color);
					tableItem.setForeground(table.getDisplay().getSystemColor(SWT.COLOR_RED));
				}
				
			}
		}
	}

	
	public void book(int row,int clone) {
		boolean success = this.ui.book(row,clone,meal);
		Shell dialogShell = new Shell();
		dialogShell.setLayout(new GridLayout(1,false));
		
		MessageBox messageBox = new MessageBox(dialogShell, SWT.ICON_INFORMATION);
		messageBox.setText("INFORMATION");
		if(success) {
			
			messageBox.setMessage("Successfully!");  
		}
		else {
			messageBox.setMessage("Error! Please restart the window for new Flight List"); 
		}
		messageBox.open();
		if(!success) {
			this.ui.refreshTable();
		}
	}
	
	public void setMeal(String meal,int index) {
		this.meal=meal;
		switch (index) {
		
		case 0:
			buttonMeal[1].setSelection(false);
			buttonMeal[2].setSelection(false);
			break;
		case 1:
			buttonMeal[0].setSelection(false);
			buttonMeal[2].setSelection(false);
			break;
		default:
			buttonMeal[0].setSelection(false);
			buttonMeal[1].setSelection(false);
			break;
		}
		
		
	}
}
