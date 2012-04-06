package cs.c301.project.test;

import android.app.AlertDialog;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.ViewAsserts;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import cs.c301.project.GroupList;
import cs.c301.project.TagList;

public class TagListTest extends ActivityInstrumentationTestCase2<TagList> {
	
	private TagList tagList;
	private Button addTag, searchButton;
	private ListView listView;

	public TagListTest() {
		super("cs.c301.project", TagList.class);
	}
	
	@Override
    protected void setUp() throws Exception {
        super.setUp();
        tagList = getActivity();
        
        addTag = (Button) tagList.findViewById(cs.c301.project.R.id.addtag);
        searchButton = (Button) tagList.findViewById(cs.c301.project.R.id.searchtag);
        listView = (ListView) tagList.findViewById(cs.c301.project.R.id.tagListView);
    }
	
	protected void tearDown() throws Exception {
	    super.tearDown();
	}
	
	public void testPreconditions() {
		assertNotNull(addTag);
		assertNotNull(searchButton);
		assertNotNull(listView);
	}
	
	public void testViewsCreated() {
		ViewAsserts.assertOnScreen(addTag.getRootView(), searchButton);
		ViewAsserts.assertOnScreen(addTag.getRootView(), listView);
		ViewAsserts.assertOnScreen(searchButton.getRootView(), listView);
	}
	
	public void testViewsTag() {
		TouchUtils.tapView(this, addTag);
		
		AlertDialog.Builder dialog = (AlertDialog.Builder) tagList.dialogs.get(0);
		
		int childCountPrevious = listView.getChildCount();
		
		assertNotNull(dialog);
		
		sendKeys("A B C D E F G H I J K L ENTER");
		
		TextView injected = (TextView)listView.getChildAt(childCountPrevious + 1);
		
		assertNotNull(injected);
		
		String text = injected.getText().toString();
		
		assertEquals(text, "abcdefghijkl");
		
		TouchUtils.tapView(this, injected);
		
		assertNotNull(getActivity());
		
		TextView label = (TextView)(getActivity()).findViewById(cs.c301.project.R.id.sub_title);
		
		assertEquals(label.getText().toString(), "abcdefghijkl");
	}
}
