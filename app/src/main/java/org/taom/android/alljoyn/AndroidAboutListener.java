package org.taom.android.alljoyn;

import org.alljoyn.bus.AboutListener;
import org.alljoyn.bus.AboutObjectDescription;
import org.alljoyn.bus.Variant;

import java.util.Map;

public class AndroidAboutListener implements AboutListener {
    @Override
    public void announced(String s, int i, short i1, AboutObjectDescription[] aboutObjectDescriptions, Map<String, Variant> map) {

    }
}
