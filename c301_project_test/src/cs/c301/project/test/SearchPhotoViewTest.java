package cs.c301.project.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import cs.c301.project.SearchPhotoView;

public class SearchPhotoViewTest extends ActivityInstrumentationTestCase2<SearchPhotoView> {
	
	private SearchPhotoView searchPhotoView;
    private Button searchButton;
    private TextView textView;
    private ListView listView;
    private EditText editText;
    
	public SearchPhotoViewTest() {
		super("cs.c301.project", SearchPhotoView.class);
	}
	
	@Override
    protected void setUp() throws Exception {
        super.setUp();
        searchPhotoView = getActivity();
        textView = (TextView) searchPhotoView.findViewById(cs.c301.project.R.id.search_title);
        listView = (ListView) searchPhotoView.findViewById(cs.c301.project.R.id.search_results);
        editText = (EditText) searchPhotoView.findViewById(cs.c301.project.R.id.search_text);
        searchButton = (Button) searchPhotoView.findViewById(cs.c301.project.R.id.search_adv);
    }
	
	protected void tearDown() throws Exception {
	    super.tearDown();
	}
	
	public void testPreconditions() {
		assertNotNull(textView);
		assertNotNull(listView);
		assertNotNull(editText);
		assertNotNull(searchButton);
	}
	
	public void testViewsCreated() {
		ViewAsserts.assertOnScreen(editText.getRootView(), textView);
		ViewAsserts.assertOnScreen(editText.getRootView(), listView);
		ViewAsserts.assertOnScreen(editText.getRootView(), searchButton);
		ViewAsserts.assertOnScreen(textView.getRootView(), editText);
	}
	
	public void testZeInput() {
		editText.requestFocus();
		
		sendKeys("H E L L O ENTER");
		
		assertEquals("hello", editText.getText().toString());
	}
}
