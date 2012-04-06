package cs.c301.project.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.ViewAsserts;
import android.widget.Button;
import android.widget.EditText;
import cs.c301.project.LoginView;

public class LoginViewTest extends ActivityInstrumentationTestCase2<LoginView> {

	private LoginView loginView;
	private EditText username, password;
	private Button newAccountButton, loginButton;

	public LoginViewTest() {
		super("cs.c301.project", LoginView.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		loginView = getActivity();
		
		username = (EditText) loginView.findViewById(cs.c301.project.R.id.usernameField);
		password = (EditText) loginView.findViewById(cs.c301.project.R.id.passwordField);
		newAccountButton = (Button) loginView.findViewById(cs.c301.project.R.id.newAccountButton);
		loginButton = (Button) loginView.findViewById(cs.c301.project.R.id.loginButton);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testPreconditions() {
		assertNotNull(username);
		assertNotNull(password);
		assertNotNull(newAccountButton);
		assertNotNull(loginButton);
	}

	public void testViewsCreated() {
		ViewAsserts.assertOnScreen(username.getRootView(), password);
		ViewAsserts.assertOnScreen(username.getRootView(), newAccountButton);
		ViewAsserts.assertOnScreen(username.getRootView(), loginButton);
		ViewAsserts.assertOnScreen(password.getRootView(), newAccountButton);
		ViewAsserts.assertOnScreen(password.getRootView(), loginButton);
		ViewAsserts.assertOnScreen(newAccountButton.getRootView(), loginButton);
	}

	public void testViewsRegister() {
	
		TouchUtils.tapView(this, username);
		sendKeys("T E S T");
		
		TouchUtils.tapView(this, password);
		sendKeys("P A S S");

		assertNotNull(getActivity());
		TouchUtils.tapView(this, newAccountButton);
	}
	
	public void testUserInput() {
		
		TouchUtils.tapView(this, username);
		sendKeys("H E L L O");
		assertEquals("hello", username.getText().toString());

		TouchUtils.tapView(this, password);
		sendKeys("H E L L O");
		assertEquals("hello", password.getText().toString());
	}
	
	public void testViewsSuccessLogin() {	

		TouchUtils.tapView(this, username);
		sendKeys("D O C T O R");

		TouchUtils.tapView(this, password);
		sendKeys("D O C T O R");

		TouchUtils.tapView(this, loginButton);
		assertNotNull(getActivity());
	}
}
