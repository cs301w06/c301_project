package cs.c301.project.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.Button;
import cs.c301.project.MainView;

public class MainViewTest extends ActivityInstrumentationTestCase2<MainView> {
	
	private MainView mainView;
    private Button cameraButton, listButton, searchButton;
    
	public MainViewTest() {
		super("cs.c301.project", MainView.class);
	}
	
	@Override
    protected void setUp() throws Exception {
        super.setUp();
        mainView = getActivity();
        cameraButton = (Button) mainView.findViewById(cs.c301.project.R.id.main_camera);
        listButton = (Button) mainView.findViewById(cs.c301.project.R.id.main_list);
        searchButton = (Button) mainView.findViewById(cs.c301.project.R.id.main_search);
    }
	
	protected void tearDown() throws Exception {
	    super.tearDown();
	}
	
	public void testPreconditions() {
		assertNotNull(cameraButton);
		assertNotNull(listButton);
		assertNotNull(searchButton);
	}
	
	public void testViewsCreated() {
		ViewAsserts.assertOnScreen(cameraButton.getRootView(), listButton);
		ViewAsserts.assertOnScreen(listButton.getRootView(), searchButton);
		ViewAsserts.assertOnScreen(searchButton.getRootView(), cameraButton);
	}
}
