package org.quantumclient.qubit.module.render;

import org.quantumclient.commons.annoations.Info;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;
import org.quantumclient.qubit.settings.CheckSetting;
import org.quantumclient.qubit.utils.annotations.AddSetting;
import org.quantumclient.qubit.utils.annotations.SetCategory;

@Info("ESP")
@SetCategory(Category.RENDER)
public class ESP extends Module {

    @AddSetting
    public final CheckSetting players = new CheckSetting("Players", true);

    @AddSetting
    public final CheckSetting mobs = new CheckSetting("Mobs", true);

    @AddSetting
    public final CheckSetting others = new CheckSetting("Others", true);

}
