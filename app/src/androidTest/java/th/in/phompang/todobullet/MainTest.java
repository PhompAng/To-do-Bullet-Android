package th.in.phompang.todobullet;

import android.test.ActivityInstrumentationTestCase2;

import th.in.phompang.todobullet.activity.MainActivity;

/**
 * Created by พิชัย on 7/10/2558.
 */
public class MainTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity mMainActivity;

    public MainTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();
        mMainActivity = getActivity();
    }

    public void testProcon() {
        assertNotNull("mMainActivity is not null", mMainActivity);
    }

}
