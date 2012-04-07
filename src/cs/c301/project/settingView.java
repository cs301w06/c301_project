package cs.c301.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Settings for changing passwords.
 * @author yhu3
 */

public class settingView extends Activity
{
	private Button changePassword;
	private EditText oldPassword, newPassword1, newPassword2;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
		changePassword = (Button)findViewById(R.id.changePasswordButton);
		oldPassword = (EditText)findViewById(R.id.currentPassword);
		newPassword1 = (EditText)findViewById(R.id.newPassword1);
		newPassword2 = (EditText)findViewById(R.id.newPassword2);
		
		changePassword.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				boolean isOldPasswordCorrect = PhotoApplication.login(PhotoApplication.whoAmI(), oldPassword.getText().toString());
				
				if (isOldPasswordCorrect) {
					String newPasswordString1 = newPassword1.getText().toString();
					String newPasswordString2 = newPassword2.getText().toString();
					
					if (newPasswordString1.equals("") || newPasswordString2.equals("")) {
						Toast.makeText(getApplicationContext(), "Please enter valid passwords.", Toast.LENGTH_SHORT).show();
					}
					else if (newPasswordString1.equals(newPasswordString2)) {
						if (PhotoApplication.changePassword(PhotoApplication.whoAmI(), newPasswordString1)) {
							onBackPressed();
							Toast.makeText(getApplicationContext(), "Your password is now updated.", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(getApplicationContext(), "Something went wrong and your password could not be changed.", Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(getApplicationContext(), "The new passwords do not match.", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "Your current password is incorrect.", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
