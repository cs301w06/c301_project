package cs.c301.project.test;

import android.app.AlertDialog;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.ViewAsserts;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import cs.c301.project.GroupList;

public class GroupListTest extends ActivityInstrumentationTestCase2<GroupList> {
	
	private GroupList groupList;
	private Button addGroup, searchButton;
	private ListView listView;
	
	public GroupListTest() {
		super("cs.c301.project", GroupList.class);
	}
	
	@Override
    protected void setUp() throws Exception {
        super.setUp();
        groupList = getActivity();
        addGroup = (Button) groupList.findViewById(cs.c301.project.R.id.addgroup);
        searchButton = (Button) groupList.findViewById(cs.c301.project.R.id.searchgroup);
        listView = (ListView) groupList.findViewById(cs.c301.project.R.id.grouplistview);
    }
	
	protected void tearDown() throws Exception {
	    super.tearDown();
	}
	
	public void testPreconditions() {
		assertNotNull(addGroup);
		assertNotNull(searchButton);
		assertNotNull(listView);
	}
	
	public void testViewsCreated() {
		ViewAsserts.assertOnScreen(searchButton.getRootView(), addGroup);
		ViewAsserts.assertOnScreen(addGroup.getRootView(), searchButton);
		ViewAsserts.assertOnScreen(addGroup.getRootView(), listView);
	}
	
	public void testViewsGroup() {
		TouchUtils.tapView(this, addGroup);
		
		AlertDialog.Builder dialog = (AlertDialog.Builder)groupList.dialogs.get(0);
		
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
	
	public void testZearchView() {
		TouchUtils.tapView(this, searchButton);
		
		assertNotNull(getActivity());
	}
}
