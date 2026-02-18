package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.JPanel;
import javax.swing.event.ChangeListener;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JOptionPane;

import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.awt.event.ActionEvent;

public class UCNDatePicker extends JPanel {
	private static final long serialVersionUID = 1L;
	private JDatePickerImpl dp;

	private class DateLabelFormatter extends AbstractFormatter {
		private static final long serialVersionUID = 1L;
		private String datePattern = "yyyy-MM-dd";
		private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

		@Override
		public Object stringToValue(String text) throws ParseException {
			return dateFormatter.parseObject(text);
		}

		@Override
		public String valueToString(Object value) throws ParseException {
			if (value != null) {
				Calendar cal = (Calendar) value;
				return dateFormatter.format(cal.getTime());
			}

			return "";
		}

	}

	/**
	 * Create the panel.
	 */
	public UCNDatePicker() {
		setLayout(new BorderLayout(0, 0));
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);

		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		
		add(datePicker, BorderLayout.NORTH);
		dp = datePicker;
	}

	public LocalDate getValue() {
		LocalDate res = null;
		if (dp != null) {
			Date dm = (Date) dp.getModel().getValue();
			if (dm != null) {
				res = LocalDate.ofInstant(dm.toInstant(), ZoneId.systemDefault());
			}
		}
		return res;
	}

	/**
	 * Must override setEnabled to disable/enable all components in JDatePicker
	 */
	@Override
	public void setEnabled(boolean enable) {
		super.setEnabled(enable);
		setEnabledRec(this.dp, enable);
	}

	private void setEnabledRec(Component comp, boolean enable) {
		comp.setEnabled(enable);
		if (comp instanceof JPanel) {
			JPanel panel = (JPanel) comp;
			for (Component c : panel.getComponents()) {
				setEnabledRec(c, enable);
			}
		}

	}

	private UCNDatePicker beforeThisPicker, afterThisPicker;

	/**
	 * Set up a rule that one UCNDatePicker must always be set
	 * to a date later or equal to another.<br>
	 * <code>
	 * UCNDatePicker dpFrom, dpTo;<br>
	 * ...<br>
	 * dpTo.after(dpFrom);<br><br>
	 * </code>
	 * From: [2023-07-07 |v|]     To: [2023-07-08 |v|]<br>
	 * <br>
	 * Or: date picker "to" must have a value after "date picker from"<br>
	 * @param p
	 */
	public void after(UCNDatePicker p) {
		// [beforeThisPicker -- this] -- afterThisPicker
		beforeThisPicker = p;
		beforeThisPicker.before(this);
		beforeThisPicker.dp.addActionListener((e) -> {
			if (getValue() == null || getValue().isBefore(p.getValue())) {
				dp.getModel().setDate(beforeThisPicker.dp.getModel().getYear(),
						beforeThisPicker.dp.getModel().getMonth(), beforeThisPicker.dp.getModel().getDay());
			}
		});
	}

	private void before(UCNDatePicker p) {
		// beforeThisPicker -- [this -- afterThisPicker]
		afterThisPicker = p;
		JDatePicker other = afterThisPicker.dp;
		afterThisPicker.dp.addActionListener((e) -> {
			if (getValue() == null || afterThisPicker.getValue().isBefore(getValue())) {
				other.getModel().setDate(dp.getModel().getYear(), dp.getModel().getMonth(), dp.getModel().getDay());
			}
		});
	}

}
