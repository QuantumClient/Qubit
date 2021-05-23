package org.quantumclient.qubit.module.client;

import org.quantumclient.commons.annoations.Info;
import org.quantumclient.commons.annoations.Silent;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;
import org.quantumclient.qubit.utils.annotations.SetCategory;

@Silent
@SetCategory(Category.CLIENT)
@Info(value = "ToggleMsg", description = "Alerts you when a module is togged")
public class ToggleMsg extends Module {

}
