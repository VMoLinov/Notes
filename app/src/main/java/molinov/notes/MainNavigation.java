package molinov.notes;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainNavigation {

    private final FragmentManager fragmentManager;

    public MainNavigation(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void addFragment(Fragment fragment, boolean useBackStack) {
        FragmentTransaction ft = fragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, fragment);
        if (useBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
    }
}
