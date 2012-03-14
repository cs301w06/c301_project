package cs.c301.project;

/*
 * Copyright 2012  Bryan Liles <iam@smartic.us> and Abram Hindle <abram.hindle@softwareprocess.es> . All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are
permitted provided that the following conditions are met:

   1. Redistributions of source code must retain the above copyright notice, this list of
      conditions and the following disclaimer.

   2. Redistributions in binary form must reproduce the above copyright notice, this list
      of conditions and the following disclaimer in the documentation and/or other materials
      provided with the distribution.

THIS SOFTWARE IS PROVIDED BY Bryan Liles <iam@smartic.us> and Abram Hindle <abram.hindle@softwareprocess.es> ''AS IS'' AND ANY EXPRESS OR IMPLIED
WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> OR
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those of the
authors and should not be interpreted as representing official policies, either expressed
or implied, of Bryan Liles <iam@smartic.us> and Abram Hindle <abram.hindle@softwareprocess.es>.

 * 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
public class BogoPicGenMain extends Activity {

	Uri imageFileUri;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        setBogoPic();
        
        ImageButton button = (ImageButton)findViewById(R.id.TakeAPhoto);
        OnClickListener listener = new OnClickListener() {
            public void onClick(View v) {
                //takeAPhoto();
            	setBogoPic();
            }
        }; 
        button.setOnClickListener(listener);
        
        Button acceptButton = (Button)findViewById(R.id.Accept);
        
        acceptButton.setOnClickListener( new OnClickListener() {
            public void onClick(View v) {
            	processIntent(false);
            }        	
        });
        
        Button cancelButton = (Button)findViewById(R.id.Cancel);
        
        cancelButton.setOnClickListener( new OnClickListener() {
            public void onClick(View v) {
            	processIntent(true);
            }        	
        });

        
        
        
		        
        //takeAPhoto();
    }

   

    private Bitmap ourBMP;
    private void setBogoPic() {
    	Toast.makeText(this, "Generating Photo", Toast.LENGTH_LONG).show();
		ImageButton button = (ImageButton)findViewById(R.id.TakeAPhoto);
		ourBMP = BogoPicGenParameters.generateBitmap(400, 400);
		button.setImageBitmap(ourBMP);
	}
    private File getPicturePath(Intent intent) {
        Uri uri = (Uri) intent.getExtras().get(MediaStore.EXTRA_OUTPUT);
        return new File(uri.getPath());
    }

    // call this to accept
    private void processIntent(boolean cancel) {
    	Intent intent = getIntent();
    	if (intent == null) {
    		return;
    	}
    	try {
    		if (intent.getExtras() != null) {    
    			if (cancel) {
    				Toast.makeText(this, "Photo Cancelled!", Toast.LENGTH_LONG).show();
    				setResult(RESULT_CANCELED);
    				finish();
    				return;
    			}
    			File intentPicture = getPicturePath(intent);
    			saveBMP(intentPicture, ourBMP);
    			setResult(RESULT_OK);
    		} else {
    			Toast.makeText(this, "Photo Cancelled: No Reciever?", Toast.LENGTH_LONG).show();
    			setResult(RESULT_CANCELED);
    		}
    	} catch (FileNotFoundException e) {
    		Toast.makeText(this, "Couldn't Find File to Write to?", Toast.LENGTH_LONG).show();
    		setResult(RESULT_CANCELED);    	
    	} catch (IOException e) {
    		Toast.makeText(this, "Couldn't Write File!", Toast.LENGTH_LONG).show();
    		setResult(RESULT_CANCELED);
    	}
    	finish();
    }
    private void saveBMP( File intentPicture, Bitmap ourBMP) throws IOException, FileNotFoundException {
    		OutputStream out = new FileOutputStream(intentPicture);
    		ourBMP.compress(Bitmap.CompressFormat.JPEG, 75, out);
    		out.close();
    }
    


}
