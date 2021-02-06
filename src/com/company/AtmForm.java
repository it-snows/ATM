package com.company;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.stream.StreamSupport;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class AtmForm extends JFrame {

	JPanel startPanel;
	JButton btnStart;

	JPanel pinPanel;
	JLabel lblInfo;
	JPasswordField pfPin;
	JButton btnCheckPin;
	JLabel lblPinResult;
	JButton btnGoToStart;

	JPanel accountPanel;
	JList lstAccounts;
	JButton btnSelectAccount;

	JPanel actionPanel;
	JLabel lblBalance;
	JButton btnCheckBalance;
	JTextField txtTakeAmount;
	JButton btnTakeMoney;
	JButton btnBack;
	JButton btnExit;

	JPanel mainPanel;
	CardLayout cardLayout;

	Card card;

	Atm atm;

	public AtmForm() {
		super("Atm");
		setSize(300, 320);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		initAtm();
	}

	public void open() {
		initUi();
		setVisible(true);
	}

	private void initUi() {
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.BLUE);

		cardLayout = new CardLayout();
		mainPanel.setLayout(cardLayout);

		initStartPanel();
		initPinPanel();
		initAccountPanel();
		initActionPanel();

		setContentPane(mainPanel);
	}

	private void initActionPanel() {
		actionPanel = new JPanel();
		// actionPanel.setBackground(Color.darkGray);

		accountPanel.setLayout(new GridLayout(6, 1));

		lblBalance = new JLabel();
		actionPanel.add(lblBalance);

		btnCheckBalance = new JButton("CHECK BALANCE");
		actionPanel.add(btnCheckBalance);
		btnCheckBalance.addActionListener(a -> {
			lblBalance.setText(Double.toString(atm.getBalance()));
		});

		txtTakeAmount = new JTextField();
		actionPanel.add(txtTakeAmount);

		btnTakeMoney = new JButton("TAKE");
		actionPanel.add(btnTakeMoney);
		btnTakeMoney.addActionListener(a -> {
			if (txtTakeAmount.getText().isBlank()) {
				return;
			}
			var amount = Integer.valueOf(txtTakeAmount.getText());

			txtTakeAmount.setText("");

			var result = atm.takeMoney(amount);

			switch (result) {
			case SUCCESS -> {
				lblBalance.setText("Take your money");
				break;
			}
			case ATM_NOT_ENOUGH -> {
				lblBalance.setText("Pick different amount");
				break;
			}
			case ACCOUNT_NOT_ENOUGH -> {
				lblBalance.setText("Not enough money in your account");
				break;
			}
			case ERROR -> {
				lblBalance.setText("Unable to take money");
				break;
			}
			}

		});

		btnBack = new JButton("GO TO ACCOUNTS");
		actionPanel.add(btnBack);
		btnBack.addActionListener(a -> {
			cardLayout.previous(mainPanel);
		});

		btnExit = new JButton("EXIT");
		actionPanel.add(btnExit);
		btnExit.addActionListener(a -> {
			atm.exit();
			cardLayout.first(mainPanel);
		});

		mainPanel.add(actionPanel);
	}

	private void initAccountPanel() {
		accountPanel = new JPanel();
		// accountPanel.setBackground(Color.gray);

		accountPanel.setLayout(new GridLayout(2, 1));

		lstAccounts = new JList();

		accountPanel.add(lstAccounts);

		btnSelectAccount = new JButton("SELECT");

		btnSelectAccount.addActionListener(a -> {
			var accountNumber = lstAccounts.getSelectedValue().toString();
			if (atm.selectAccount(accountNumber)) {
				lblBalance.setText("");
				cardLayout.next(mainPanel);
			}
			;
		});

		accountPanel.add(btnSelectAccount);

		mainPanel.add(accountPanel);
	}

	private void initPinPanel() {
		pinPanel = new JPanel();
		// pinPanel.setBackground(Color.lightGray);
		pinPanel.setLayout(new GridLayout(4, 1));

		lblInfo = new JLabel("ENTER PIN");
		pfPin = new JPasswordField();
		btnCheckPin = new JButton("CONTINUE");
		lblPinResult = new JLabel();

		btnCheckPin.addActionListener(a -> {
			var pin = new String(pfPin.getPassword());
			var checkResult = atm.isPinValid(Integer.valueOf(pin));

			if (!checkResult) {
				lblPinResult.setText("INCORRECT PIN!");
				return;
			}

			var accountStream = StreamSupport.stream(atm.getBankAccounts().spliterator(), false);

			var accounts = accountStream.map(account -> account.getNumber()).toArray();

			lstAccounts.setListData(accounts);

			pfPin.setText("");
			cardLayout.next(mainPanel);
		});

		pinPanel.add(lblInfo);
		pinPanel.add(pfPin);
		pinPanel.add(btnCheckPin);
		pinPanel.add(lblPinResult);

		mainPanel.add(pinPanel);
	}

	private void initStartPanel() {
		startPanel = new JPanel();
		// startPanel.setBackground(Color.WHITE);
		btnStart = new JButton("Start");
		btnStart.setBackground(Color.cyan);
		btnStart.addActionListener(a -> {
			atm.enterCard(card);
			cardLayout.next(mainPanel);
		});
		startPanel.add(btnStart);
		mainPanel.add(startPanel);
	}

	private void initAtm() {

		ArrayList<BankAccount> accounts = new ArrayList<>();

		var client = new Client("Sandra", "Sniedzina", "123");

		var bankAccount1 = new BankAccount("111", 100, client);
		var bankAccount2 = new BankAccount("112", 200, client);

		card = new Card(1111, "1212 1313 1414 1515", client);

		accounts.add(bankAccount1);
		accounts.add(bankAccount2);

		atm = new Atm(10000, accounts);
	}
}
