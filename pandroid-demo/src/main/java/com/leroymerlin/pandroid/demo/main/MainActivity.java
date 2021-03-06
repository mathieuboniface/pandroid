package com.leroymerlin.pandroid.demo.main;

import android.app.Fragment;
import android.os.Bundle;

import com.leroymerlin.pandroid.app.PandroidDrawerActivity;
import com.leroymerlin.pandroid.app.ResumeState;
import com.leroymerlin.pandroid.demo.R;
import com.leroymerlin.pandroid.demo.main.anim.AnimationFragment;
import com.leroymerlin.pandroid.demo.main.anim.MaterialFragment;
import com.leroymerlin.pandroid.demo.main.event.EventFragment;
import com.leroymerlin.pandroid.demo.main.event.EventSecondFragment;
import com.leroymerlin.pandroid.demo.main.list.ListViewFragment;
import com.leroymerlin.pandroid.demo.main.list.RecyclerViewFragment;
import com.leroymerlin.pandroid.demo.main.list.SimpleRecyclerViewFragment;
import com.leroymerlin.pandroid.demo.main.mvp.PresenterFragment;
import com.leroymerlin.pandroid.demo.main.mvvm.MvvmFragment;
import com.leroymerlin.pandroid.demo.main.rest.RestFragment;
import com.leroymerlin.pandroid.demo.main.scanner.ScannerFragment;
import com.leroymerlin.pandroid.demo.main.toast.ToastFragment;
import com.leroymerlin.pandroid.event.EventBusManager;
import com.leroymerlin.pandroid.event.FragmentEventReceiver;
import com.pandroid.annotations.EventReceiver;

import java.util.List;

/**
 * Created by florian on 02/12/15.
 */
public class MainActivity extends PandroidDrawerActivity {


    public static final String DRAWER_EVENT = "DRAWER_EVENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void onResume(ResumeState state) {
        super.onResume(state);

        switch (state) {

            case FIRST_START:
                //tag::FragmentReceivers[]
                //create
                startFragment(NavigationLeftFragment.class);
                //same as
                //eventBusManager.send(new FragmentOpener(NavigationLeftFragment.class));
                //...
                //end::FragmentReceivers[]

                break;
            case ROTATION:
                break;
            case VIEW_RESTORED:
                break;
        }

    }

    @Override
    protected int getDrawerId() {
        return R.id.main_drawer;
    }


    @Override
    public int getToolbarId() {
        return R.id.main_toolbar;
    }


    @Override
    public Fragment getCurrentFragment() {
        return getFragmentManager().findFragmentById(R.id.main_content_container);
    }

    //tag::FragmentReceivers[]
    @Override
    public List<EventBusManager.EventBusReceiver> getReceivers() {
        List<EventBusManager.EventBusReceiver> receivers = super.getReceivers();
        //This receiver will handle the NavigationLeftFragment and open it in the R.id.main_navigation_container container
        FragmentEventReceiver navigationReceiver = new FragmentEventReceiver().setContainerId(R.id.main_navigation_container).addFragment(NavigationLeftFragment.class);
        receivers.add(navigationReceiver);

        receivers.add(new FragmentEventReceiver().setContainerId(R.id.main_content_container)
                .setAnim(new int[]{R.animator.demo_in, R.animator.demo_out, R.animator.demo_in, R.animator.demo_out})
                .addFragment(ScannerFragment.class)
                .addFragment(EventFragment.class)
                .addFragment(RestFragment.class)
                .addFragment(ListViewFragment.class)
                .addFragment(RecyclerViewFragment.class)
                .addFragment(SimpleRecyclerViewFragment.class)
                .addFragment(AnimationFragment.class)
                .addFragment(MvvmFragment.class)
                .addFragment(ToastFragment.class)
                .addFragment(PresenterFragment.class)
        );
        //tag::FragmentWithOpener[]
        receivers.add(new FragmentEventReceiver()
                .setContainerId(R.id.main_content_container)
                .setAnim(FragmentEventReceiver.ANIM_MATERIAL)
                .addFragment(MaterialFragment.class)
                .setBackStackTag("material"));
        //end::FragmentWithOpener[]
        receivers.add(new FragmentEventReceiver().setContainerId(R.id.main_content_container)
                .setAnim(FragmentEventReceiver.ANIM_SLIDE)
                .addFragment(EventSecondFragment.class)
                .setBackStackTag("back")
        );
        return receivers;
    }
    //end::FragmentReceivers[]


    @EventReceiver(DRAWER_EVENT)
    public void closeDrawer(boolean close) {
        if (close)
            closeDrawer();
        else
            openDrawer();
    }
}
