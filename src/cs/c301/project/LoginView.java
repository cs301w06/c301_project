package cs.c301.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Allows multiple users for the application, user is allowed to 
 * register a new account or log into a previous account where the
 * user can edit their photos by adding, adding annotations, adding
 * tags and choosing the group of where the photo belongs to 
 * 
 * @author yhu3
 *
 */
public class LoginView extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		final EditText usernameField = (EditText) findViewById(R.id.usernameField);
		final EditText passwordField = (EditText) findViewById(R.id.passwordField);
		
		Button loginButton = (Button) findViewById(R.id.loginButton);
		loginButton.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					String username = usernameField.getText().toString();
					String password = passwordField.getText().toString();
					
					if (!username.equals("") && !password.equals("")) {
						boolean success = PhotoApplication.login(username, password);
						
						if (success) {
							Toast.makeText(getApplicationContext(), "Welcome back " + username + "!", Toast.LENGTH_SHORT).show();
							Intent intent = new Intent(getApplicationContext(), MainView.class);
							
							if (username.trim().equals("doctor")) {
								intent.putExtra("isDoctor", true);
								PhotoApplication.toggleDoctor();
							}
							
							usernameField.setText("");
							passwordField.setText("");
							
							startActivity(intent);
						} else {
							Toast.makeText(getApplicationContext(), "The username or password is incorrect.", Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(getApplicationContext(), "Please enter your username and password to login.", Toast.LENGTH_SHORT).show();
					}
				}
		});
		
		Button newAccountButton = (Button) findViewById(R.id.newAccountButton);
		newAccountButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				String username = usernameField.getText().toString();
				String password = passwordField.getText().toString();
				
				if (!username.equals("") && !password.equals("")) {
					boolean success = PhotoApplication.newAccount(username, password);
					
					if (success) {
						Toast.makeText(getApplicationContext(), "Welcome to your new account " + username + "!", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(getApplicationContext(), MainView.class);
						usernameField.setText("");
						passwordField.setText("");
						
						startActivity(intent);
					} else {
						Toast.makeText(getApplicationContext(), "This account could not be created.", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "To create a new account, please enter a username and password in the above fields.", Toast.LENGTH_SHORT).show();
				}
			}
	});
	}
}
